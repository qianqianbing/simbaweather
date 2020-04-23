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

    //主机 结尾不要带/
    public static String BASE_HOST;

    /**
     * 设置服务器环境
     */
    static {
        if ("debug".equals(SERVER_TYPE)) {                    //测试环境
            BASE_HOST = "http://cp.simbalink.cn/backend";
        } else if ("gray".equals(SERVER_TYPE)) {              //灰度环境
            BASE_HOST = "";
        } else {
            BASE_HOST = "";           //生产环境
        }
    }

    //********************************************* 在下面注册各模块的api地址 ******************************************
    //********************************************* 在下面注册各模块的api地址 ******************************************
    //********************************************* 在下面注册各模块的api地址 ******************************************


    //********************************************* 违章App模块 ******************************************
    /**
     * 新增车辆基本信息
     */
    public final static String REQUEST_ADD_CAR_INFO = BASE_HOST + "/violate/addinfo";
    /**
     * 删除车辆基本信息
     */
    public final static String REQUEST_DELETE_CAR = BASE_HOST + "/violate/delete";
    /**
     * 查询车辆列表
     */
    public final static String REQUEST_CAR_LIST = BASE_HOST + "/violate/get/";
    /**
     * 获取违章详情
     */
    public final static String REQUEST_CAR_DETAIL = BASE_HOST + "/violate/getViolate";

    //********************************************* 日历App模块 ******************************************
    /**
     * 查询某天黄历信息
     */
    public final static String CALENDAR_GET_ALMANAC_BY_DATE = BASE_HOST + "/almanac/getOneAlmanacByDate";
    /**
     * 根据年份查询法定节假日
     */
    public final static String CALENDAR_GET_HOLIDAY_BY_YEAR = BASE_HOST + "/almanac/getHolidayByYear";

    //********************************************* 天气模块APP ******************************************
    /**
     * 根据经纬度获取当地天气
     */
    public final static String WEATHER_GET_INDEX_LOCATION=BASE_HOST+"weather/index/location";
    /**
     * 城市推荐列表
     */
    public final static String WEATHER_GET_WEATHER_RECOMMENDCITYLIST=BASE_HOST+"weather/recommendCityList";
    /**
     * 城市模糊查询
     */
    public final static String WEATHER_GET_WEATHER_MATCHINGCITY=BASE_HOST+"weather/matchingCity";
    /**
     * 城市ID查询
     */
    public final static String WEATHER_GET_WEATHER_INDEXCITY=BASE_HOST+"weather/index/city";



    //********************************************* 会员中心App模块 ******************************************
    //主机 结尾不要带,会员中心暂时和其他不一样
    public static String BASE_HOST_ACCOUNT = "http://simbaui.simbalink.cn/backend";
    /**
     * 获取各种二维码的url
     */
    public final static String ACCOUNT_GET_QRCODE = BASE_HOST_ACCOUNT + "/account/getQRCode";
    /**
     * 账号密码登陆
     */
    public final static String ACCOUNT_LOGIN = BASE_HOST_ACCOUNT + "/login/accountLogin";
    /**
     * 查询账号的信息
     */
    public final static String ACCOUNT_USER_INFO = BASE_HOST_ACCOUNT + "/login/userInfo";

    /**
     * 获取各种二维码的url
     */
    public final static String ACCOUNT_WEBAUTHLOGIN = BASE_HOST_ACCOUNT + "/login/webAuthLogin";

    /**
     * 获取各种二维码的url
     */
    public final static String ACCOUNT_ISA = BASE_HOST_ACCOUNT + "/vehicle/isActive\n";
}