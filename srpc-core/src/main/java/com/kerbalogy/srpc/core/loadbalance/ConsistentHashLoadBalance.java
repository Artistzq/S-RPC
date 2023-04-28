package com.kerbalogy.srpc.core.loadbalance;

import com.kerbalogy.srpc.core.loadbalance.LoadBalance;
import com.kerbalogy.srpc.core.transport.dto.RpcRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : Artis Yao
 */
public class ConsistentHashLoadBalance implements LoadBalance {

    private final ConcurrentHashMap<String, ConsistentHashSelector> selectors = new ConcurrentHashMap<>();

    @Override
    public String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest) {
        if (serviceUrlList == null || serviceUrlList.isEmpty()) {
            return null;
        }

        if (serviceUrlList.size() == 1) {
            return serviceUrlList.get(0);
        }

        int identityHashCode = System.identityHashCode(serviceUrlList);
        String rpcServiceName = rpcRequest.getRpcServiceName();
        ConsistentHashSelector selector = selectors.get(rpcServiceName);

        // check for updates
        if (selector == null || selector.getIdentityHashCode() != identityHashCode) {
            selectors.put(rpcServiceName, new ConsistentHashSelector(serviceUrlList, 160, identityHashCode));
            selector = selectors.get(rpcServiceName);
        }
//        return selector.select(rpcServiceName + Arrays.stream(rpcRequest.getParameters()));
        String host = "127.0.0.1";
        try {
           host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
//        return selector.select(rpcServiceName + host + Thread.currentThread().getId());
        return selector.select(rpcServiceName + host);
    }
}

class ConsistentHashSelector {

    private final TreeMap<Long, String> virtualInvokers;
    private final int identityHashCode;

    public int getIdentityHashCode() {
        return identityHashCode;
    }

    ConsistentHashSelector(List<String> invokers, int replicaNumber, int identityHashCode) {
        this.virtualInvokers = new TreeMap<>();
        this.identityHashCode = identityHashCode;

        for (String invoker : invokers) {
            for (int i = 0; i < replicaNumber / 4; i++) {
                byte[] digest = md5(invoker + i);
                for (int h = 0; h < 4; h++) {
                    long m = hash(digest, h);
                    virtualInvokers.put(m, invoker);
                }
            }
        }
    }

    static byte[] md5(String key) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
            md.update(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return md.digest();
    }

    static long hash(byte[] digest, int idx) {
        return ((long) (digest[3 + idx * 4] & 255) << 24 | (long) (digest[2 + idx * 4] & 255) << 16 | (long) (digest[1 + idx * 4] & 255) << 8 | (long) (digest[idx * 4] & 255)) & 4294967295L;
    }

    public String select(String rpcServiceKey) {
        byte[] digest = md5(rpcServiceKey);
        long key = hash(digest, 0);
        System.out.println(rpcServiceKey + " " + key);
        return selectForKey(key);
    }

    public String selectForKey(long hashCode) {
        Map.Entry<Long, String> entry = virtualInvokers.tailMap(hashCode, true).firstEntry();

        if (entry == null) {
            entry = virtualInvokers.firstEntry();
        }

        return entry.getValue();
    }
}