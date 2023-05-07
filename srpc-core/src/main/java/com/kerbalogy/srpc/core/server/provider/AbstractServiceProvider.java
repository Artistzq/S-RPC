package com.kerbalogy.srpc.core.server.provider;

import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.constant.TransportConstant;
import com.kerbalogy.srpc.core.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : Artis Yao
 */
@Slf4j
public abstract class AbstractServiceProvider implements ServiceProvider {

    /**
     * key: rpc service name(interface name + version + group)
     * value: service object
     */

    protected Map<String, Object> serviceMap;
    protected Set<String> registeredService;
    protected ServiceRegistry serviceRegistry;

    public AbstractServiceProvider() {
        this.serviceMap = new ConcurrentHashMap<>();
        this.registeredService = this.serviceMap.keySet();
        this.serviceRegistry = defineRegistry();
    }

    abstract ServiceRegistry defineRegistry();

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
        // TODO: 换成当前的公网或局域网IP而不是本机地址
        String host = "127.0.0.1";
        serviceRegistry.registerService(rpcServiceConfig.getServiceName(), new InetSocketAddress(host, TransportConstant.NETTY_SERVER_PORT));
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return this.serviceRegistry;
    }
}
