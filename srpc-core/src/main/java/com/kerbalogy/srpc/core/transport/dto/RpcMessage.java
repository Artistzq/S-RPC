package com.kerbalogy.srpc.core.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : Artis Yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcMessage {

    private byte messageType;
    private byte codec;
    private byte compress;
    private int requestId;
    private Object data;

}
