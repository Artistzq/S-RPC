package com.kerbalogy.srpc.core.registry;

import com.kerbalogy.srpc.core.transport.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * @Author : Artis Yao
 */
public interface ServiceDiscovery {

    /**
     * Lookup service by rpcServiceName
     * @param rpcRequest rpc 请求
     * @return service address
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);

}
