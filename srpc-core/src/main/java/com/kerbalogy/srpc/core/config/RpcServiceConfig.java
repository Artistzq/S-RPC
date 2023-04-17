package com.kerbalogy.srpc.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对Service的封装，包含版本、接口实现类等信息
 * @Author : Artis Yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcServiceConfig {

    /**
     * Version of service.
     */
    private String version = "";

    /**
     * Distinguish by group when the interface has multiple implementation classes.
     */
    private String group = "";

    /**
     * Target service. Is Usually a Interface.
     */
    private Object service;

    /**
     * TODO
     * @return
     */
    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    /**
     * 返回服务名
     * @return 服务名。CanonicalName
     */
    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }

}
