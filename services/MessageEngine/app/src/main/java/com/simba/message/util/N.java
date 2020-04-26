package com.simba.message.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.simba.message.R;
import com.simba.message.ui.AboutActivity;

import java.util.List;
import java.util.Random;

public class N {

    public static void tapNotification(Context context) {
        N.tapNotification(context, "标题", "我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知我是一个通知", R.drawable.ic_news_wy);
    }

    public static void tapNotification(Context context, String title, String text, int iconId) {
        N.tapNotification(context, title, text, iconId, "com.android.settings", "com.android.settings.Settings");

    }

    public static void tapNotification(Context context, String title, String text, int iconId, String pkg, String cls) {
        int index = new Random().nextInt(100);
        title += index;

        Intent clickIntent = new Intent();
        clickIntent.setComponent(new ComponentName(pkg, cls));
        clickIntent.setAction("action.simba.message");

        Intent cacelIntent = new Intent(ActionReceiver.ACTION);
        cacelIntent.putExtra("extra", index);
        PendingIntent cacelPI = PendingIntent.getBroadcast(context, index, cacelIntent, 0);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(AboutActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(clickIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Notification.Builder builder = new Notification.Builder(MainActivity.this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelid1");
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.transient_notification);
        remoteViews.setTextViewText(android.R.id.title, title);
        remoteViews.setTextViewText(android.R.id.message, text);
        remoteViews.setTextViewText(android.R.id.summary, "5分钟前");
        remoteViews.setImageViewResource(android.R.id.icon, iconId);

        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setDeleteIntent(cacelPI)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

        Notification notification = builder.build();
        notification.bigContentView = remoteViews;
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
