package com.kerbalogy.srpc.core.provider;

import com.kerbalogy.srpc.core.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.constant.TransportConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.kerbalogy.srpc.core.registry.ServiceRegistry;
import com.kerbalogy.srpc.core.registry.localImpl.LocalServiceRegistry;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地实现，测试用
 * @Author : Artis Yao
 */
@Slf4j
public class LocalServiceProvider implements ServiceProvider{

    /**
     * key: rpc service name(interface name + version + group)
     * value: service object
     */

    private final Map<String, Object> serviceMap;
    private final Set<String> registeredService;
    private final ServiceRegistry serviceRegistry;

    public LocalServiceProvider() {
        this.serviceMap = new ConcurrentHashMap<>();
        this.registeredService = this.serviceMap.keySet();
        this.serviceRegistry = new LocalServiceRegistry();
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getServiceName();
        System.out.println(rpcServiceName);
        if (registeredService.contains(rpcServiceName)) {
            return ;
        }

        // jdk17，registeredService和serviceMap已经绑定了，无需操作
        //  registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("Add service: {} and interfaces:{}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if (null == service) {
            throw new IllegalStateException();
        }

        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        this.addService(rpcServiceConfig);
        serviceRegistry.registerService(rpcServiceConfig.getServiceName(), TransportConstant.LOCAL_SERVER_ADDRESS);
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return this.serviceRegistry;
    }
}
