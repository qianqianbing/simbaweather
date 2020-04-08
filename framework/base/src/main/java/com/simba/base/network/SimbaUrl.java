package com.simba.base.network;


/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/08
 * 描    述：网络请求url公共管理类
 * 修订历史：
 * ================================================
 */
public class SimbaUrl {

    //环境类型
    private static String SERVER_TYPE = "debug";

    //主机
    private static String BASE_HOST;

    /**
     * 设置服务器环境
     */
    static {
        if ("debug".equals(SERVER_TYPE)) {                    //测试环境
            BASE_HOST = "";
        } else if ("gray".equals(SERVER_TYPE)) {              //灰度环境
            BASE_HOST = "";
        } else {
            BASE_HOST = "";           //生产环境
        }
    }

    //********************************************* 在下面注册各模块的api地址 ******************************************
    //********************************************* 在下面注册各模块的api地址 ******************************************
    //********************************************* 在下面注册各模块的api地址 ******************************************

    /**
     * 获取用户信息
     */
    public static final String GET_USER_INFO = BASE_HOST + "/record/holter-order/check-device-state";


}