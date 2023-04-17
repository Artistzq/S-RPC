package com.kerbalogy.srpc.core.transport.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @Author : Artis Yao
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    /**
     * 接口名，即服务名的组成部分
     */
    private String interfaceName;
    /**
     * 欲调用的方法名
     */
    private String methodName;
    /**
     * 调用的方法的参数数组
     */
    private Object[] parameters;
    /**
     * 调用的方法的参数类型数组
     */
    private Class<?>[] paramTypes;
    /**
     * 版本
     */
    private String version;
    /**
     * 处理一个接口有多个实现类
     */
    private String group;

    /**
     * 返回RpcService的名称
     * @return
     */
    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup();
    }



}
