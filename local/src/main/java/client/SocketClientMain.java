package client;

import com.kerbalogy.srpc.core.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.proxy.RpcClientProxy;
import com.kerbalogy.srpc.core.transport.client.RpcClient;
import com.kerbalogy.srpc.core.transport.client.socket.SocketRpcClient;
import com.kerbalogy.srpc.server.service.Message;
import com.kerbalogy.srpc.server.service.TestService;

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
