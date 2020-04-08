package com.simba.base.network.model;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc : 返回数据结构
 */
public class GeneralResponse<T> implements Serializable {

    public int code;
    public String message;
    public boolean success;
    /**
     * 数据
     */
    public T data;

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
