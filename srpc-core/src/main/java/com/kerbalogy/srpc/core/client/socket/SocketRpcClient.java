package com.kerbalogy.srpc.core.client.socket;

import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.core.registry.zookeeper.ZKServiceDiscovery;
import com.kerbalogy.srpc.exception.RpcException;
import com.kerbalogy.srpc.core.registry.ServiceDiscovery;
import com.kerbalogy.srpc.core.registry.local.LocalServiceDiscovery;
import com.kerbalogy.srpc.core.client.RpcClient;
import com.kerbalogy.srpc.core.transport.dto.RpcRequest;
import com.kerbalogy.srpc.core.transport.dto.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author : Artis Yao
 */
@Slf4j
public class SocketRpcClient implements RpcClient {

    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
//        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension()
        this.serviceDiscovery = SingletonFactory.getInstance(ZKServiceDiscovery.class);
    }

    @Override
    public RpcResponse sendRpcRequest(RpcRequest rpcRequest) {
        // 先找到地址
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        log.info("Find service address: [{}:{}]", inetSocketAddress.getAddress(), inetSocketAddress.getPort());

        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 通过输出流发送数据到Server
            objectOutputStream.writeObject(rpcRequest);
            //通过输入流读取响应
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (RpcResponse) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败", e);
        }
    }
}
