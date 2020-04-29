package com.simba.message;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.simba.base.os.ServiceManager;
import com.simba.message.impl.CommandImpl;
import com.simba.message.manager.ParserManager;
import com.simba.message.manager.ProtocolFactory;
import com.simba.message.util.N;
import com.simba.service.callbacks.OnInitListener;

/**
 * @author chefengyun
 */
public class CommandService extends Service {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private CommandImpl mService;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate().");
        N.startForeground(this);
        mContext = this.getApplicationContext();
        ProtocolFactory.creat().init(mContext);

        mService = new CommandImpl(this);
        ServiceManager.addService(CommandEngnie.SERVICE_NAME, mService.asBinder());

        MessageEngnie.getInstance(this).setOnInitListener(new OnInitListener() {
            @Override
            public void onServiceConnected() {
                // msg bind ok
                MessageEngnie.getInstance(mContext).registerCallback(msgCallback);
            }

            @Override
            public void onServiceDisconnected() {

            }
        });
        MessageEngnie.getInstance(this).bindService();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mService.asBinder();
    }

    private IMessageCallback msgCallback = new IMessageCallback.Stub(){

        @Override
        public void onChange(int msgType, String jsonData){
            ParserManager.get().parseCmd(msgType, jsonData);
        }
        @Override
        public void onSocketStateChange(boolean state){

        }

    };
}
