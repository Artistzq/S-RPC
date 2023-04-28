package com.kerbalogy.srpc.core.server.provider;

import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.constant.TransportConstant;
import com.kerbalogy.srpc.core.registry.zookeeper.ZKServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import com.kerbalogy.srpc.core.registry.ServiceRegistry;
import com.kerbalogy.srpc.core.registry.local.LocalServiceRegistry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地实现，测试用
 * @Author : Artis Yao
 */
@Slf4j
public class LocalServiceProvider extends AbstractServiceProvider{
    @Override
    ServiceRegistry defineRegistry() {
        return SingletonFactory.getInstance(LocalServiceRegistry.class);
    }
}
