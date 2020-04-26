/**
 * 作    者：luojunjie
 * 版    本：1.0
 * 创建时间: 2020/4/17
 * 描    述：
 */
package com.simba.base.network;

public class ConstantDefine {


    //********************************************* 会员中心 ******************************************
    public static final String ACTION = "action";
    public static final String CALLBACKURL = "callbackurl";
    public static final String DEVICEID = "deviceid";
    public static final String VEHICLELOGINID = "vehicleLoginId";
    public static final String WeChatURL = "http://simbaui.simbalink.cn";
    public static final String WeChatQRCodeURL = "http://simbaui.simbalink.cn/page/qrcode_simba.jpg";  //微信公众号的URL
    public static final int QRTYPE_ACTIVATION = 0; //二维码类型 0：激活
    public static final int QRTYPE_LOGIN = 1; //二维码类型 1：登陆

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final int MAN = 1;
    public static final int WOMAN = 2;
    public static final int SEX_UNKNOW = 0;

    //是否为车主：0否 1是
    public static final int CAR_OWNER = 1;
    public static final int NOT_CAR_OWNER = 0;

    //登陆失败的原因
    public static final int NETWORK_ERROR = 404;
}
