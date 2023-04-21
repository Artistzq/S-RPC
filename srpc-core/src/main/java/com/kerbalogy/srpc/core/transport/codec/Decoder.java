package com.kerbalogy.srpc.core.transport.codec;

/**
 * @Author : Artis Yao
 */
public interface Decoder {

    <T> T decode(byte[] bytes, Class<T> clazz);

}
