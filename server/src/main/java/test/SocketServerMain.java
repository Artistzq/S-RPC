package test;

import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.server.socket.SocketRpcServer;
import test.service.TestService;
import test.service.TestServiceImpl;

/**
 * @Author : Artis Yao
 */
public class SocketServerMain {

    public static void main(String[] args) {
        SocketRpcServer socketRpcServer = initServices();
        socketRpcServer.start();
    }

    public static SocketRpcServer initServices() {

        TestService helloService = new TestServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer(10086);

        // config是对service的封装
        RpcServiceConfig config = new RpcServiceConfig();
        config.setService(helloService);

        socketRpcServer.registerService(config);

        return socketRpcServer;
    }

}
