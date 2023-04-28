import com.kerbalogy.srpc.api.services.TimeService;
import com.kerbalogy.srpc.api.services.TimeServiceImpl;
import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.core.server.socket.SocketRpcServer;

import java.util.Date;

/**
 * @Author : Artis Yao
 */
public class SocketServerMain {

    public static void main(String[] args) {

        SocketRpcServer socketRpcServer = new SocketRpcServer(9998);

        // config是对service的封装
        TimeService service = new TimeServiceImpl();
        RpcServiceConfig config = new RpcServiceConfig();
        config.setService(service);

        socketRpcServer.registerService(config);
        socketRpcServer.start();
    }

}
