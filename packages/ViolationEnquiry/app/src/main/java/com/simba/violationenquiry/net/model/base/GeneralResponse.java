package com.simba.violationenquiry.net.model.base;

import java.io.Serializable;

/**
 * BASE
 *
 * @param <T>
 */
public class GeneralResponse<T> implements Serializable {

    //"code": 0,
//  "data": [
//    {
//
//    }
//  ],
//  "message": "string",
//  "success": true
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
