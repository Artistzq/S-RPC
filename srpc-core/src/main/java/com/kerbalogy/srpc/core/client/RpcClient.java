package com.kerbalogy.srpc.core.client;

import com.kerbalogy.srpc.core.transport.dto.RpcRequest;
import com.kerbalogy.srpc.core.transport.dto.RpcResponse;


/**
 * send rpc request。
 * @Author : Artis Yao
 */
public interface RpcClient {
    /**
     * Send rpc request to server and get result.
     * 向服务器发送Rpc请求并获得结果
     * @param rpcRequest Rpc request body
     * @return data from server.
     */
    public RpcResponse sendRpcRequest(RpcRequest rpcRequest);

}
