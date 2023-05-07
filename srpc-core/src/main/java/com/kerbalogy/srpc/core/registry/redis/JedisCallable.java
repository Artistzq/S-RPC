package com.kerbalogy.srpc.core.registry.redis;

/**
 * @Author : Artis Yao
 */

import redis.clients.jedis.Jedis;

public interface JedisCallable<T> {

    public T call(Jedis jedis);

}
