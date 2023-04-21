package com.kerbalogy.srpc.config;

import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.constant.TransportConstant;
import com.kerbalogy.srpc.core.server.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 *
 * 当服务器关闭时，为所有服务做一些事情，比如unregister
 * TODO: AOP
 * @Author : Artis Yao
 */
@Slf4j
public class ShutdownHook {

    private static final ShutdownHook SHUTDOWN_HOOK = new ShutdownHook();

    public static ShutdownHook getShutdownHook() {
        return SHUTDOWN_HOOK;
    }

    public void clearAll() {
        log.info("add shutdown hook for clear all.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(
                        InetAddress.getLocalHost().getHostAddress(), TransportConstant.LOCAL_SERVER_PORT);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }));
    }

    public void clearAll(Class<? extends ServiceProvider> clazz) {
        log.info("add shutdown hook for clear all for {}.", clazz.getName());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(
                        InetAddress.getLocalHost().getHostAddress(), TransportConstant.LOCAL_SERVER_PORT);
                SingletonFactory.getInstance(clazz).getServiceRegistry().clearRegistry();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }));
    }






}
