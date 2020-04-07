package com.simba.violationenquiry.net.utils;

public class HttpParameters {

    public final static String BASE_URL = "http://cp.simbalink.cn";

    /**
     * 新增车辆基本信息
     */
    public final static String REQUEST_ADD_CAR_INFO = BASE_URL + "/violate/addinfo";
    /**
     * 删除车辆基本信息
     */
    public final static String REQUEST_DELETE_CAR = BASE_URL + "/violate/delete";
    /**
     * 查询车辆列表
     */
    public final static String REQUEST_CAR_LIST = BASE_URL + "/violate/get/";

    /**
     * 获取违章详情
     */
    public final static String REQUEST_CAR_DETAIL = BASE_URL + "/violate/getViolate";
}
