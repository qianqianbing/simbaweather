package com.simba.trafficmanagement;

import android.app.Activity;

import com.google.gson.Gson;
import com.simba.framework.define.NetworkDefine;

public class MainActivity extends Activity {

    Gson gson = new Gson();
    public void requestHttp (){
        NetworkDefine.getNetworkAddress();
    }
}
