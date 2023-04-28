package com.kerbalogy.srpc.core.loadbalance;

import com.kerbalogy.srpc.core.transport.dto.RpcRequest;

import java.util.List;

/**
 * @Author : Artis Yao
 */
public interface LoadBalance {

    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);

}
