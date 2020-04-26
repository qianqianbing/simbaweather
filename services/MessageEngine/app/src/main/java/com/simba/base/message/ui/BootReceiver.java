package com.simba.base.message.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.simba.base.message.MessageService;

/**
 * @author chefengyun
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootReceiver", "onReceive="+intent.getAction()+", now start MessageService.");
        context.startService(new Intent(context, MessageService.class));
    }
}