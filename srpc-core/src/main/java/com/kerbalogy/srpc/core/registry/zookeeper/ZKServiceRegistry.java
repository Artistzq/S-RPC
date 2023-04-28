package com.kerbalogy.srpc.core.registry.zookeeper;

import com.kerbalogy.srpc.config.ZKConfig;
import com.kerbalogy.srpc.constant.RpcConstant;
import com.kerbalogy.srpc.constant.TransportConstant;
import com.kerbalogy.srpc.core.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @Author : Artis Yao
 */
@Slf4j
public class ZKServiceRegistry implements ServiceRegistry {
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = ZKConfig.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        // 获取ZK客户端
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        // 注册节点
        CuratorUtils.createPersistentNode(zkClient, servicePath);
        log.info("register service [{}] to ZooKeeper.", servicePath);
    }

    @Override
    public void clearAllRegistry() {
        InetSocketAddress inetSocketAddress = TransportConstant.NETTY_SERVER_ADDRESS;
        CuratorUtils.clearRegistry(CuratorUtils.getZkClient(), inetSocketAddress);
    }

    public void clearAllRegistry(InetSocketAddress inetSocketAddress) {
        CuratorUtils.clearRegistry(CuratorUtils.getZkClient(), inetSocketAddress);
    }
}
