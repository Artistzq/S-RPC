package com.kerbalogy.srpc.core.server;

import com.kerbalogy.srpc.config.RpcServiceConfig;

/**
 * @Author : Artis Yao
 */
public interface RpcServer {

    /**
     * 向注册中心注册自己的一个服务
     * @param config 一个服务
     */
    public void registerService(RpcServiceConfig config);

    public void start();

}
