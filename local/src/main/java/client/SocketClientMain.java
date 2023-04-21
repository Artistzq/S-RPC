package client;

import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.client.proxy.RpcClientProxy;
import com.kerbalogy.srpc.core.client.RpcClient;
import com.kerbalogy.srpc.core.client.socket.SocketRpcClient;

import com.kerbalogy.test.service.Message;
import com.kerbalogy.test.service.TestService;

/**
 * @Author : Artis Yao
 */
public class SocketClientMain {

    public static void main(String[] args) {
        RpcClient rpcClient = new SocketRpcClient();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceConfig);
        TestService service = rpcClientProxy.getProxy(TestService.class);

        String res = service.hello(new Message("1", "2"));
        System.out.println(res);
        res = service.cat(new Message("666", "777"));
        System.out.println(res);
    }

}
