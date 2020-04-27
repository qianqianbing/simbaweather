package com.simba.message.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.simba.message.MessageService;

/**
 * @author chefengyun
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootReceiver", "onReceive="+intent.getAction()+", now start MessageService.");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, MessageService.class));
        }else{
            context.startService(new Intent(context, MessageService.class));
        }
    }
}
