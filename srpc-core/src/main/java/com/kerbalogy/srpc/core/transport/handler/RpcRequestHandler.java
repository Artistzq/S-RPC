package com.kerbalogy.srpc.core.transport.handler;

import com.kerbalogy.sprc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.core.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import com.kerbalogy.srpc.core.provider.LocalServiceProvider;
import com.kerbalogy.srpc.core.provider.ServiceProvider;
import com.kerbalogy.srpc.core.transport.dto.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author : Artis Yao
 */
@Slf4j
public class RpcRequestHandler {

    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        // 本地Provider测试
        this.serviceProvider = SingletonFactory.getInstance(LocalServiceProvider.class);
    }

    /**
     * 处理rpcRequest
     * @param rpcRequest rpc请求
     * @return 处理结果
     */
    public Object handle(RpcRequest rpcRequest) {
        // 根据rpcRequest中的服务名，由serviceProvider[服务提供者]来找到对应服务
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        // 调用目标程序
        return invokeTargetMethod(rpcRequest, service);
    }

    public Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            // 从service中拿到方法，从rpcRequest中取出方法名，参数类型
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            // 执行方法，拿到结果
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service：[{}] successful invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }



}
