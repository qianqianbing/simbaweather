package com.simba.framework.define;

public class NetworkDefine {

    //是生产环境还是开发环境
    static boolean isProduce = true;

    private static String developNetworkAddress = "";
    private static String produceNetworkAddress = "";

    public static String getNetworkAddress(){
        if(isProduce){
            return produceNetworkAddress;
        }else {
            return developNetworkAddress;
        }
    }
}
