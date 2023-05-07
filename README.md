# S-RPC
A Simple RPC for JAVA.

## 用法

### A 服务端
1. 定义服务接口
```java
package com.kerbalogy.srpc.api.services;

public interface TimeService {

    public String getCurrentTime();

}
```
2. 实现服务
```java
package com.kerbalogy.srpc.api.services;

import java.util.Date;

public class TimeServiceImpl implements TimeService {
    @Override
    public String getCurrentTime() {
        return "Current time is: " + new Date().toString();
    }
}
```
3. 开启服务器，注册服务
```java
import com.kerbalogy.srpc.api.services.TimeService;
import com.kerbalogy.srpc.api.services.TimeServiceImpl;
import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.server.socket.SocketRpcServer;

public class SocketServerMain {

    public static void main(String[] args) {
        // 实例化服务器
        SocketRpcServer socketRpcServer = new SocketRpcServer(9998);

        // config是对service的封装
        TimeService service = new TimeServiceImpl();
        RpcServiceConfig config = new RpcServiceConfig();
        config.setService(service);
        
        // 注册服务
        socketRpcServer.registerService(config);
        
        // 启动服务器
        socketRpcServer.start();
    }
}
```
至此，服务端的操作已经完成了。

### B 客户端
1. 远程调用
```java
import com.kerbalogy.srpc.api.services.TimeService;
import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.client.RpcClient;
import com.kerbalogy.srpc.core.client.proxy.RpcClientProxy;
import com.kerbalogy.srpc.core.client.socket.SocketRpcClient;

public class SocketClientMain {

    public static void main(String[] args) {
        // 定义客户端，定义注册中心
        RpcClient rpcClient = new SocketRpcClient("redis");
        // 获得接口服务的代理类
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceConfig);
        TimeService service = rpcClientProxy.getProxy(TimeService.class);
        // 远程过程调用
        System.out.println(service.getCurrentTime());
    }

}
```
