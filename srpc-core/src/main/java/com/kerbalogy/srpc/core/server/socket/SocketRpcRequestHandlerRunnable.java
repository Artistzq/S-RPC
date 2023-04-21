package com.kerbalogy.srpc.core.server.socket;

import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.enums.RpcResponseCodeEnum;
import com.kerbalogy.srpc.core.transport.dto.RpcRequest;
import com.kerbalogy.srpc.core.transport.dto.RpcResponse;
import com.kerbalogy.srpc.core.server.handler.RpcRequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * TODO: read
 * @Author : Artis Yao
 */
@Slf4j
public class SocketRpcRequestHandlerRunnable implements Runnable{

    /**
     * 客户端的socket，里面存储了RpcRequest
     */
    private final Socket socket;
    /**
     * 单例的Handler
     */
    private final RpcRequestHandler rpcRequestHandler;

    public SocketRpcRequestHandlerRunnable(Socket socket) {
        this.socket = socket;
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void run() {
        log.info("服务器使用线程[{}]异步处理服务器请求", Thread.currentThread().getName());
        log.info("Server handle message from client by thread [{}]", Thread.currentThread().getName());
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            // 反序列化
            RpcRequest rpcRequest = (RpcRequest) inputStream.readObject();

            Object result = rpcRequestHandler.handle(rpcRequest);

            // 通过output回复Response
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(RpcResponse.success(result, rpcRequest.getRequestId()));
            outputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(RpcResponse.fail(RpcResponseCodeEnum.FAIL));
                outputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
