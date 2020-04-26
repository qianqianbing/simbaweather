package com.simba.message.manager;

import android.util.Log;

import com.simba.message.protocol.IProtocol;
import com.simba.message.protocol.cloud.SimbaCloudProtocol;

/**
 * @author chefengyun
 */
public class ProtocolFactory {

    private static IProtocol protocol;

    public static IProtocol creat(){
        if(protocol == null){

            //TODO 添加 /etc/config.xml 动态区分 TBox 厂商


            // 慧翰
            protocol = new SimbaCloudProtocol();
            Log.w("TBox", "Init protocol of Simba Cloud.");
        }
        return protocol;
    }


}
