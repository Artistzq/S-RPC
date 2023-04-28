package com.kerbalogy.srpc.core.registry.local;

import com.kerbalogy.srpc.constant.TransportConstant;
import com.kerbalogy.srpc.core.registry.ServiceDiscovery;
import com.kerbalogy.srpc.core.transport.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * @Author : Artis Yao
 */
public class LocalServiceDiscovery implements ServiceDiscovery {
    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        return TransportConstant.LOCAL_SERVER_ADDRESS;
    }
}
