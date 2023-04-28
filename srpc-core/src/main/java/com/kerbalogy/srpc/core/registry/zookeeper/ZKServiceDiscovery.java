package com.kerbalogy.srpc.core.registry.zookeeper;

import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.core.loadbalance.ConsistentHashLoadBalance;
import com.kerbalogy.srpc.core.loadbalance.LoadBalance;
import com.kerbalogy.srpc.core.loadbalance.RandomLoadBalance;
import com.kerbalogy.srpc.core.registry.ServiceDiscovery;
import com.kerbalogy.srpc.core.transport.dto.RpcRequest;
import com.kerbalogy.srpc.enums.RpcErrorMessageEnum;
import com.kerbalogy.srpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Objects;

/**
 * TODO: 负载均衡
 * @Author : Artis Yao
 */
@Slf4j
public class ZKServiceDiscovery implements ServiceDiscovery {

    LoadBalance loadBalance;

    public ZKServiceDiscovery() {
        loadBalance = SingletonFactory.getInstance(ConsistentHashLoadBalance.class);
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (serviceUrlList == null || serviceUrlList.isEmpty()) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }

        // 从列表选取服务所在的主机
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddress = targetServiceUrl.split(":");
        String host = socketAddress[0];
        int port = Integer.parseInt(socketAddress[1]);
        return new InetSocketAddress(host, port);
    }
}
