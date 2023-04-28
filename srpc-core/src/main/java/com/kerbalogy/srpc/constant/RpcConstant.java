package com.kerbalogy.srpc.constant;

/**
 * @Author : Artis Yao
 */
public class RpcConstant {

    public static final byte[] MAGIC_NUMBER = {(byte) 'g', (byte) 'r', (byte) 'p', (byte) 'c'};

    public static final byte VERSION = 1;

    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024;

}
