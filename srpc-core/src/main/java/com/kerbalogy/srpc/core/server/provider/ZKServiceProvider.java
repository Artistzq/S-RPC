package com.kerbalogy.srpc.core.server.provider;

import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.registry.ServiceRegistry;
import com.kerbalogy.srpc.core.registry.zookeeper.ZKServiceRegistry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : Artis Yao
 */
public class ZKServiceProvider extends AbstractServiceProvider {

    @Override
    ServiceRegistry defineRegistry() {
        return SingletonFactory.getInstance(ZKServiceRegistry.class);
    }
}
