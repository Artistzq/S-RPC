package com.kerbalogy.srpc.core.provider;

import com.kerbalogy.srpc.core.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.registry.ServiceRegistry;

import java.util.Map;
import java.util.Set;

/**
 * 存储并提供服务的Object
 * @Author : Artis Yao
 */
public interface ServiceProvider {

    /**
     * @param rpcServiceConfig rpc service 的相关属性
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName rpc服务名，interface+group+version
     * @return rpc服务
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig rpc service 的一个封装
     */
    void publishService(RpcServiceConfig rpcServiceConfig);

    ServiceRegistry getServiceRegistry();
}
