package com.kerbalogy.srpc.core.registry.local;

import com.kerbalogy.srpc.core.registry.ServiceRegistry;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author : Artis Yao
 */
public class LocalServiceRegistry implements ServiceRegistry {

    private Set<String> container;
    private int port;

    public LocalServiceRegistry() {
        this.container = new HashSet<>();
    }

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        container.add(rpcServiceName);
        port = inetSocketAddress.getPort();
    }

    @Override
    public void clearAllRegistry() {
        this.container.clear();
    }
}
