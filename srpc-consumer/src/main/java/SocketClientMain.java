import com.kerbalogy.srpc.api.services.TimeService;
import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.client.RpcClient;
import com.kerbalogy.srpc.core.client.proxy.RpcClientProxy;
import com.kerbalogy.srpc.core.client.socket.SocketRpcClient;

/**
 * @Author : Artis Yao
 */
public class SocketClientMain {

    public static void main(String[] args) {
        RpcClient rpcClient = new SocketRpcClient("redis");
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceConfig);

        TimeService service = rpcClientProxy.getProxy(TimeService.class);

        System.out.println(service.getCurrentTime());
    }

}
