package com.kerbalogy.srpc.core.registry.redis;

import com.kerbalogy.srpc.common.utils.PropertiesFileUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;


/**
 * @Author : Artis Yao
 */
public class JedisPoolUtils {

    // 从获取连接到使用，到释放，这里要

    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        //获取数据，设置到JedisPoolConfig中
        Properties properties = PropertiesFileUtil.readPropertiesFile("rpc.properties");
        //获取连接参数
        config.setMaxTotal(Integer.parseInt(properties.getProperty("redis.pool.maxTotal")));
        config.setMaxTotal(Integer.parseInt(properties.getProperty("redis.pool.maxIdle")));
        String host = properties.getProperty("redis.host");
        int timeout = Integer.parseInt(properties.getProperty("redis.timeout"));
        int port = Integer.parseInt(properties.getProperty("redis.port"));
        String password = properties.getProperty("redis.password");
        int database = Integer.parseInt(properties.getProperty("redis.database"));

        //初始化JedisPool
        jedisPool = new JedisPool(config, host, port, timeout, password, database);
    }

    /**
     * 获取连接的方法
     */
    private static Jedis getJedis(){
        return jedisPool.getResource();//获取连接
    }

    /**
     * 采用回调方式确保资源关闭
     * @param callable
     * @param <T>
     * @return
     */
    public static <T> T execute(JedisCallable<T> callable) {
        try (Jedis jedis = jedisPool.getResource()) {
            return callable.call(jedis);
        }
    }

}