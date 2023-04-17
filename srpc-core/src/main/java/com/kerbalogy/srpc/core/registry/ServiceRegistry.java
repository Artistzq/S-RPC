package com.kerbalogy.srpc.core.registry;

import java.net.InetSocketAddress;

/**
 * @Author : Artis Yao
 */
public interface ServiceRegistry {

    /**
     * Register service.
     * @param rpcServiceName rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

    void clearRegistry();
}
