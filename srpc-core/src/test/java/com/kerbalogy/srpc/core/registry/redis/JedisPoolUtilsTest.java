package com.kerbalogy.srpc.core.registry.redis;

import junit.framework.TestCase;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author : Artis Yao
 */
public class JedisPoolUtilsTest extends TestCase {

    @Test
    public void testExecute() {

        String s = JedisPoolUtils.execute(jedis -> {
            jedis.set("hello", "1");
            return jedis.get("hello");
        });

        String loc = "127.0.0.1";
        JedisPoolUtils.execute(jedis -> jedis.sadd("rpc", loc));
        JedisPoolUtils.execute(jedis -> jedis.sadd("rpc", loc + "1"));
        JedisPoolUtils.execute(jedis -> jedis.sadd("rpc", loc));
//        JedisPoolUtils.execute(jedis -> jedis.spop("rpc"));
//        try {
//            Thread.sleep(1000 * 5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        JedisPoolUtils.execute(jedis -> jedis.del("rpc"));
        Set<String> set = JedisPoolUtils.execute(jedis -> jedis.smembers("rpc"));
        List<String> list = new ArrayList<>(set);
        list.forEach(System.out::println);

        List<String> keys = JedisPoolUtils.execute(jedis -> jedis.scan("0", new ScanParams().match("srpc*").count(Integer.MAX_VALUE)))
                .getResult();
        keys.forEach(System.out::println);

        keys.forEach(JedisPoolUtils.execute(jedis -> jedis::del));

    }
}