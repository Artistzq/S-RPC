package com.kerbalogy.srpc.core.proxy;

import com.kerbalogy.srpc.core.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.enums.RpcErrorMessageEnum;
import com.kerbalogy.srpc.core.enums.RpcResponseCodeEnum;
import com.kerbalogy.srpc.core.exception.RpcException;
import com.kerbalogy.srpc.core.transport.client.RpcClient;
import com.kerbalogy.srpc.core.transport.client.socket.SocketRpcClient;
import com.kerbalogy.srpc.core.transport.dto.RpcRequest;
import com.kerbalogy.srpc.core.transport.dto.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 定义处理逻辑，动态代理类
 * 当动态代理类调用方法时，实际上调用的下面的invoke方法
 *
 * @Author : Artis Yao
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    public static final String INTERFACE_NAME = "interfaceName";

    private final RpcClient rpcClient;
    private final RpcServiceConfig rpcServiceConfig;
    private Class<?> clazz;

    public RpcClientProxy(RpcClient rpcClient, RpcServiceConfig rpcServiceConfig) {
        this.rpcClient = rpcClient;
        this.rpcServiceConfig = rpcServiceConfig;
        this.clazz = null;
    }

    public RpcClientProxy(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
        this.rpcServiceConfig = new RpcServiceConfig();
        this.clazz = null;
    }

    /**
     * 获得代理对象，是一个服务
     * TODO: read
     * @param clazz Service的接口的class对象
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        this.clazz = clazz;
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    /**
     * 通过getProxy获得代理类，通过该invoke方法调用方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Invoke: [{}.{}]", clazz.getName(), method.getName());
        RpcRequest rpcRequest = RpcRequest.builder()
                .methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceConfig.getGroup())
                .version(rpcServiceConfig.getVersion())
                .build();

        /**
         * 发送请求
         */
        RpcResponse rpcResponse = null;
        if (rpcClient instanceof SocketRpcClient) {
            rpcResponse = rpcClient.sendRpcRequest(rpcRequest);
        }

        check(rpcResponse, rpcRequest);
        return rpcResponse.getData();
    }

    public void check(RpcResponse rpcResponse, RpcRequest rpcRequest) {
        if (rpcResponse == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcErrorMessageEnum.REQUEST_NOT_MATCH_RESPONSE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getCode() == 0 || rpcResponse.getCode() != RpcResponseCodeEnum.SUCCESS.getCode()) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }
}
