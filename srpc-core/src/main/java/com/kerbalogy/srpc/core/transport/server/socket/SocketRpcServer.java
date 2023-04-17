package com.kerbalogy.srpc.core.transport.server.socket;

import com.kerbalogy.sprc.common.concurrent.threadpool.ThreadPoolFactoryUtil;
import com.kerbalogy.sprc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.core.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.config.ShutdownHook;
import com.kerbalogy.srpc.core.constant.TransportConstant;
import com.kerbalogy.srpc.core.provider.LocalServiceProvider;
import com.kerbalogy.srpc.core.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Socket Server
 * @Author : Artis Yao
 */
@Slf4j
public class SocketRpcServer {

    /**
     * 线程池
     */
    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;

    public SocketRpcServer() {
        this.threadPool = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        this.serviceProvider = SingletonFactory.getInstance(LocalServiceProvider.class);
    }

    public void registerService(RpcServiceConfig config) {
        serviceProvider.publishService(config);
    }

    public void start() {
        try {
            // 建立Socket服务器
            ServerSocket server = new ServerSocket();
//            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(TransportConstant.LOCAL_SERVER_ADDRESS);
            log.info("Rpc server started. Listening in [{}:{}]...", server.getInetAddress(), server.getLocalPort());
            // 添加计划：服务器关闭时，清除所有注册信息
            ShutdownHook.getShutdownHook().clearAll(serviceProvider.getClass());

            // 请求连接的客户端的socket
            Socket socket;
            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                // 用线程池处理
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }

            // 关闭线程池
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }
}
