package com.kerbalogy.srpc.core.registry.redis;

import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.config.RedisConfig;
import com.kerbalogy.srpc.config.ZKConfig;
import com.kerbalogy.srpc.constant.TransportConstant;
import com.kerbalogy.srpc.core.registry.ServiceRegistry;
import com.kerbalogy.srpc.core.registry.zookeeper.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;
import redis.clients.jedis.params.ScanParams;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Artis Yao
 */
public class RedisServiceRegistry implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String key = RedisConfig.MAIN_KEY + ":" + rpcServiceName;
        JedisPoolUtils.execute(jedis -> jedis.sadd(key, inetSocketAddress.toString().substring(1)));
    }

    @Override
    public void clearAllRegistry() {
        JedisPoolUtils.execute(jedis ->
                jedis.scan("0", new ScanParams().match(RedisConfig.MAIN_KEY + "*").count(Integer.MAX_VALUE))
        ).getResult().forEach(JedisPoolUtils.execute(jedis -> jedis::del));
    }
}
