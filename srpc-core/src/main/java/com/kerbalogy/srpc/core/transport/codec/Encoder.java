package com.kerbalogy.srpc.core.transport.codec;

/**
 * @Author : Artis Yao
 */
public interface Encoder {

    byte[] encode(Object obj);

}
