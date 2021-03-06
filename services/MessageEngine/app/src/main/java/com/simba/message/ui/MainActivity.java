package com.simba.message.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.simba.message.CommandEngnie;
import com.simba.message.CommandService;
import com.simba.message.MessageService;
import com.simba.message.bean.MemeberMsgData;
import com.simba.message.util.N;
import com.simba.message.R;
import com.simba.service.callbacks.IServiceDataCallback;
import com.simba.service.callbacks.OnInitListener;
import com.simba.service.data.DataWrapper;

/**
 * @author chefengyun
 */
public class MainActivity extends Activity {

    private String TAG = this.getClass().getSimpleName();

    CommandEngnie mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate().");
        this.setContentView(R.layout.activity_main);

//        Log.e(TAG, com.simba.message.util.DataUtils.bytes2HexString("{\"code\":200,\"title\":\"会员中心\",\"message\":\"尊敬的用户, 您的车机已激活 SimbaUI.\"}".getBytes()));
//        Log.e(TAG, com.simba.message.util.DataUtils.bytes2HexString("{\"message\":null,\"code\":200,\"data\":{\"token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1OTAzMDEyNDgsInVzZXJJZCI6IjEiLCJzdXBwb3J0IjpmYWxzZX0.YFT_mV6MEemccIzypGmnpbr-jKFjicAki8LE5GasBCXN0xS7BgxZpZtbRhvzSpGFszirTNzxKc-GzOrXMqex8A\",\"openid\":null,\"userId\":null},\"success\":true}\n".getBytes()));
//        Log.e(TAG, com.simba.message.util.DataUtils.bytes2HexString("{\"code\":200,\"title\":\"网易新闻\",\"message\":\"IT之家4月23日消息 苹果iPhone SE 2于4月16日正式发布，搭载A13仿生处理器，采用4.7英寸屏幕，售价3299元起，将于明日（4月24日）正式开售.\"}".getBytes()));

        this.startService(new Intent(this, MessageService.class));
        this.startService(new Intent(this, CommandService.class));

        mManager = CommandEngnie.getInstance(this);
        mManager.setOnInitListener(mListener);
        mManager.bindService();

        this.registerReceiver(notificationReceiver, new IntentFilter(N.ActionReceiver.ACTION));
    }

    private N.ActionReceiver notificationReceiver = new N.ActionReceiver();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy().");

        this.unregisterReceiver(notificationReceiver);
        mManager.unregisterCallback(mCallback);
        mManager.unbindService();
    }

    public void notification(View v){
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                if("com.android.launcher".equals(N.getTopActivityPackageName(getApplicationContext()))){
                    Toast.makeText(getApplicationContext(), "AI 卡片优先", Toast.LENGTH_SHORT).show();
                }else{
                    N.show(getApplicationContext());
                }
            }
        }, 1234);
    }

    private OnInitListener mListener = new OnInitListener() {
        @Override
        public void onServiceConnected() {
            Log.i(TAG, "Msg Service Connected");
            mManager.registerCallback(mCallback);

        }

        @Override
        public void onServiceDisconnected() {
            Log.i(TAG, "Msg Service Disconnected");
        }
    };

    private IServiceDataCallback mCallback = new IServiceDataCallback.Stub() {
        @Override
        public void onChange(DataWrapper dataWrapper) {
            Log.i(TAG, dataWrapper.toString());
            switch (dataWrapper.getDataCode()) {
                case MemeberMsgData.CODE:
                    MemeberMsgData data = (MemeberMsgData) dataWrapper.getData();

                    // TODO member msg
                    data.getMessage();
                    break;
            }
        }

        @Override
        public boolean accept(int i)  {
            return true;
        }
    };

}
