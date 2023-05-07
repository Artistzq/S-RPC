package com.kerbalogy.srpc.core.server.provider;

import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.core.registry.ServiceRegistry;
import com.kerbalogy.srpc.core.registry.redis.RedisServiceRegistry;

/**
 * @Author : Artis Yao
 */
public class RedisServiceProvider extends AbstractServiceProvider{
    @Override
    ServiceRegistry defineRegistry() {
        return SingletonFactory.getInstance(RedisServiceRegistry.class);
    }
}
