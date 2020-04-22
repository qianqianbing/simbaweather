package com.simba.simbaweather.data.bean;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/14 16:03
 */
public class CityplanningBean {


    /**
     * message : null
     * code : 200
     * data : [{"id":"39","nation":null,"province":"上海市","city":"上海市","district":"上海市"},{"id":"2","nation":null,"province":"北京市","city":"北京市","district":"北京市"},{"id":"24","nation":null,"province":"天津市","city":"天津市","district":"天津市"},{"id":"3176","nation":null,"province":"澳门特别行政区","city":"澳门特别行政区","district":"澳门特别行政区"},{"id":"52","nation":null,"province":"重庆市","city":"重庆市","district":"重庆市"},{"id":"3173","nation":null,"province":"香港特别行政区","city":"香港特别行政区","district":"香港特别行政区"}]
     * success : true
     */

    private Object message;
    private int code;
    private boolean success;
    private List<CityInfo> data;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<CityInfo> getData() {
        return data;
    }

    public void setData(List<CityInfo> data) {
        this.data = data;
    }

}
