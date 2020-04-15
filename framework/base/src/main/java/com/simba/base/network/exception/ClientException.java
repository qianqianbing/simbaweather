package com.simba.base.network.exception;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/18
 * @Desc :客户端自定义错误信息封装
 */
public class ClientException extends Exception {
    private int code;//错误码
    private String msg;//错误信息

    public ClientException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ClientException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}