package com.simba.message.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.simba.base.os.SystemProperties;
import com.simba.message.R;

import java.util.List;
import java.util.Random;

public class N {

    public static final String ChannelId = "MessageService";

    public static void show(Context context) {
        N.show(context, "标题", "我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知", R.drawable.icon_news_wy);
    }

    public static void show(Context context, String title, String text, int iconId) {
        N.show(context, title, text, iconId, "com.android.settings", "com.android.settings.Settings");
    }

    public static void show(Context context, String title, String text, int iconId, String pkg, String cls) {
        int index = new Random().nextInt(100);
        title += index;

        Log.e("N", "Notification title["+title+"] message["+text+"]");

        Intent clickIntent = new Intent("action.simba.message").setComponent(new ComponentName(pkg, cls));
        PendingIntent clickPI = PendingIntent.getActivity(context, index, clickIntent,0);

        Intent cacelIntent = new Intent(ActionReceiver.ACTION).putExtra("extra", index);
        PendingIntent cacelPI = PendingIntent.getBroadcast(context, index, cacelIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, N.ChannelId);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), iconId))
                .setSmallIcon(iconId)
                .setUsesChronometer(true)
                .setContentIntent(clickPI)
                .setDeleteIntent(cacelPI)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

        Notification notification = builder.build();
        if(SystemProperties.getBoolean("debug.message.notification", false)){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.transient_notification);
            remoteViews.setTextViewText(android.R.id.title, title);
            remoteViews.setTextViewText(android.R.id.message, text);
            remoteViews.setTextViewText(android.R.id.summary, "5分钟前");
            remoteViews.setImageViewResource(android.R.id.icon, iconId);
            notification.contentView = remoteViews;
            notification.bigContentView = remoteViews;
        }
        NotificationManagerCompat.from(context).notify(index, notification);
    }

    public static String getTopActivityPackageName(Context context) {
        String topActivityPackage = null;
        ActivityManager activityManager = (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            topActivityPackage = runningTaskInfos.get(0).topActivity.getPackageName();
        }
        return topActivityPackage;
    }

    public static class ActionReceiver extends BroadcastReceiver {

        public static final String ACTION = "action.simba.message.cancel";

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, intent.getAction() + " "+intent.getIntExtra("extra", 0), Toast.LENGTH_LONG).show();
        }
    }

}
