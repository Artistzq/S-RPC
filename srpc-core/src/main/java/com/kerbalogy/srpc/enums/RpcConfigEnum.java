package com.kerbalogy.srpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Artis Yao
 */
@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;
}
