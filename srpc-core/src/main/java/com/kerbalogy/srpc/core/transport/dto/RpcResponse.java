package com.kerbalogy.srpc.core.transport.dto;

import com.kerbalogy.srpc.enums.RpcResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author : Artis Yao
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 715745410605631233L;
    private String requestId;

    private int code;
    private String message;
    private Object data;

    /**
     * 成功的响应
     * @param data
     * @param requestId
     * @return
     */
    public static RpcResponse success(Object data, String requestId) {
        RpcResponse response = new RpcResponse();
        response.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());
        response.setRequestId(requestId);
        // data是null也返回
        response.setData(data);

        return response;
    }

    public static <T> RpcResponse fail(RpcResponseCodeEnum rpcResponseCodeEnum) {
        RpcResponse response = new RpcResponse();
        response.setCode(rpcResponseCodeEnum.getCode());
        response.setMessage(rpcResponseCodeEnum.getMessage());
        return response;
    }
}
