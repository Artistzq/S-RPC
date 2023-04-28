package com.kerbalogy.srpc.core.loadbalance;

import com.kerbalogy.srpc.core.transport.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * @Author : Artis Yao
 */
public class RandomLoadBalance implements LoadBalance{

    @Override
    public String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest) {

        if (serviceUrlList == null || serviceUrlList.isEmpty()) {
            return null;
        }

        if (serviceUrlList.size() == 1) {
            return serviceUrlList.get(0);
        }

        return serviceUrlList.get(new Random().nextInt(0, serviceUrlList.size()));
    }

}
