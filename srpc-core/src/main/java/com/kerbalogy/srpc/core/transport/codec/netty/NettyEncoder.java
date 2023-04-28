package com.kerbalogy.srpc.core.transport.codec.netty;

import com.kerbalogy.srpc.core.transport.codec.Encoder;
import com.kerbalogy.srpc.core.transport.dto.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author : Artis Yao
 */
public class NettyEncoder extends MessageToByteEncoder<RpcMessage> implements Encoder {
    @Override
    public byte[] encode(Object obj) {
        return new byte[0];
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) throws Exception {

    }
}
