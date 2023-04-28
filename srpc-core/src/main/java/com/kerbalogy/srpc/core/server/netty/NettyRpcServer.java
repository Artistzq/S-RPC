package com.kerbalogy.srpc.core.server.netty;

import com.kerbalogy.srpc.common.concurrent.threadpool.ThreadPoolFactoryUtil;
import com.kerbalogy.srpc.common.factory.SingletonFactory;
import com.kerbalogy.srpc.config.RpcServiceConfig;
import com.kerbalogy.srpc.config.ShutdownHook;
import com.kerbalogy.srpc.constant.TransportConstant;
import com.kerbalogy.srpc.core.server.RpcServer;
import com.kerbalogy.srpc.core.server.provider.LocalServiceProvider;
import com.kerbalogy.srpc.core.server.provider.ServiceProvider;
import com.kerbalogy.srpc.core.server.provider.ZKServiceProvider;
import com.kerbalogy.srpc.core.transport.codec.Encoder;
import com.kerbalogy.srpc.core.transport.codec.netty.NettyDecoder;
import com.kerbalogy.srpc.core.transport.codec.netty.NettyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author : Artis Yao
 */
@Slf4j
public class NettyRpcServer implements RpcServer {

    // TODO: 换成ZK 注册中心
    private final ServiceProvider serviceProvider = SingletonFactory.getInstance(LocalServiceProvider.class);

    @Override
    public void registerService(RpcServiceConfig config) {
        serviceProvider.publishService(config);
    }

    /**
     * TODO: Read SneakyThrows
     */
    @Override
    @SneakyThrows
    public void start() {
        // 清除Provider对应的注册中心的所有注册
        ShutdownHook.getShutdownHook().clearAll(serviceProvider.getClass());
        String host = TransportConstant.NETTY_SERVER_ADDRESS.getHostString();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventExecutorGroup serviceHandlerGroup = new DefaultEventExecutorGroup(
                Runtime.getRuntime().availableProcessors() * 2,
                ThreadPoolFactoryUtil.createThreadFactory("service-handler-group", false)
        );

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 控制TCP是否启用Nagle算法
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否打开TCP底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 系统用于临时存放已完成三次握手的请求的队列的最大长度
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 客户端第一次进行请求时初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 60s内没有收到客户端请求就关闭连接
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new NettyEncoder());
                            pipeline.addLast(new NettyDecoder());
                            pipeline.addLast(serviceHandlerGroup, new NettyRpcRequestHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(host, TransportConstant.NETTY_SERVER_PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Occur exception when start netty server: ", e);
        } finally {
            log.error("Shutdown boosGroup and workerGroup.");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            serviceHandlerGroup.shutdownGracefully();
        }
    }
}
