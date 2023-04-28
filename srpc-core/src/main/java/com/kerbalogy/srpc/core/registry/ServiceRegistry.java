package com.kerbalogy.srpc.core.registry;

import java.net.InetSocketAddress;

/**
 * @Author : Artis Yao
 */
public interface ServiceRegistry {

    /**
     * Register service. 格式为 <服务名称, 服务所在的地址>
     * @param rpcServiceName rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

    /**
     * 清除所有注册信息
     */
    void clearAllRegistry();
}
