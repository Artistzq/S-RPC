package com.kerbalogy.srpc.constant;

import java.net.InetSocketAddress;

/**
 * @Author : Artis Yao
 */
public class TransportConstant {

    public static int LOCAL_SERVER_PORT = 10086;

    public static InetSocketAddress LOCAL_SERVER_ADDRESS = new InetSocketAddress("127.0.0.1", LOCAL_SERVER_PORT);

    public static int NETTY_SERVER_PORT = 9998;

    public static InetSocketAddress NETTY_SERVER_ADDRESS = new InetSocketAddress("127.0.0.1", NETTY_SERVER_PORT);
}
