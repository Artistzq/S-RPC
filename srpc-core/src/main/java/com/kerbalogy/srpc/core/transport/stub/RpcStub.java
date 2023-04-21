package com.kerbalogy.srpc.core.transport.stub;

import com.kerbalogy.srpc.core.transport.codec.Decoder;
import com.kerbalogy.srpc.core.transport.codec.Encoder;

/**
 * @Author : Artis Yao
 */
public class RpcStub {

    private Decoder decoder;
    private Encoder encoder;


    public byte[] marshalling(Object obj) {
        return encoder.encode(obj);
    }

    public <T> Object unMarshalling(byte[] bytes, Class<T> clazz) {
        return decoder.decode(bytes, clazz);
    }

}
