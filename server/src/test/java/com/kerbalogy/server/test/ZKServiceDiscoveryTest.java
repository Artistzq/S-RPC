package com.kerbalogy.server.test;

import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.constant.TransportConstant;
import com.kerbalogy.srpc.core.registry.zookeeper.ZKServiceDiscovery;
import com.kerbalogy.srpc.core.registry.zookeeper.ZKServiceRegistry;
import com.kerbalogy.srpc.core.server.socket.SocketRpcServer;
import com.kerbalogy.srpc.core.transport.dto.RpcRequest;
import junit.framework.TestCase;
import org.junit.Test;
import test.service.Message;
import test.service.TestService;
import test.service.TestServiceImpl;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * @Author : Artis Yao
 */
public class ZKServiceDiscoveryTest extends TestCase {

    @Test
    public void testLookupService() throws NoSuchMethodException {

        ZKServiceDiscovery zkServiceDiscovery = new ZKServiceDiscovery();

        ZKServiceRegistry zkServiceRegistry = new ZKServiceRegistry();

        TestService helloService = new TestServiceImpl();
        // config是对service的封装
        RpcServiceConfig config = new RpcServiceConfig();
        config.setService(helloService);
        System.out.println(config.getServiceName());
//        zkServiceRegistry.registerService(config.getServiceName(), TransportConstant.LOCAL_SERVER_ADDRESS);
////        zkServiceRegistry.clearAllRegistry(TransportConstant.LOCAL_SERVER_ADDRESS);
//        zkServiceRegistry.registerService(config.getServiceName(), new InetSocketAddress("127.0.0.2", 10086));
//        zkServiceRegistry.registerService(config.getServiceName(), new InetSocketAddress("127.0.0.3", 10086));
//        zkServiceRegistry.registerService(config.getServiceName(), new InetSocketAddress("127.0.0.4", 10086));

        Method method = TestService.class.getMethod("cat", Message.class);
        RpcRequest rpcRequest = RpcRequest.builder()
                .methodName(method.getName())
                .interfaceName(method.getDeclaringClass().getName())
                .parameters(new Object[0])
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group("")
                .build();

        InetSocketAddress inetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        System.out.println(inetSocketAddress.getHostString());
        inetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        System.out.println(inetSocketAddress.getHostString());
        inetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        System.out.println(inetSocketAddress.getHostString());
        inetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        System.out.println(inetSocketAddress.getHostString());
        inetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        System.out.println(inetSocketAddress.getHostString());
        inetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        System.out.println(inetSocketAddress.getHostString());
        inetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        System.out.println(inetSocketAddress.getHostString());

        new Thread(() -> {
            ZKServiceDiscovery aaazkServiceDiscovery = new ZKServiceDiscovery();
            InetSocketAddress aaa = aaazkServiceDiscovery.lookupService(rpcRequest);
            aaa = aaazkServiceDiscovery.lookupService(rpcRequest);
            System.out.println(aaa.getHostString());
        }).start();
    }
}