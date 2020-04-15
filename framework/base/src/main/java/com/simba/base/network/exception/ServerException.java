package com.simba.base.network.exception;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/18
 * @Desc :服务端返回的错误信息
 */
public class ServerException extends RuntimeException {
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}