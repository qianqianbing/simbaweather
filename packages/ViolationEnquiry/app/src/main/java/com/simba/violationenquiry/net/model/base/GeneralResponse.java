package com.simba.violationenquiry.net.model.base;

import java.io.Serializable;

/**
 * BASE
 *
 * @param <T>
 */
public class GeneralResponse<T> implements Serializable {


    public int code;
    public String msg;
    /**
     * 数据
     */
    public T data;

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
