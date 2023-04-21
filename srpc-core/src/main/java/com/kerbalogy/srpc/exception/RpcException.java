package com.kerbalogy.srpc.exception;

import com.kerbalogy.srpc.enums.RpcErrorMessageEnum;

/**
 * @Author : Artis Yao
 */
public class RpcException extends RuntimeException{
    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ". Details: " + detail);
    }

}
