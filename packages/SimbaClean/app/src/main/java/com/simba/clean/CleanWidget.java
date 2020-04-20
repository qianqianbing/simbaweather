package com.simba.clean;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import java.io.InputStream;

/**
 * Implementation of App Widget functionality.
 */
public class CleanWidget extends AppWidgetProvider {

    private static String TAG = "CleanWidget";

    private RemoteViews views;
    private AppWidgetManager appWidgetManager;
    private Context mContext;
    private int appWidgetId;
    private int total = 80;
    private static Bitmap bmp;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 1:
                    int id = (int) message.obj;
                    Log.i(TAG, "id---------------------------" + id);
                    String iconName = getIconName(id);
                    try {
                        InputStream is = mContext.getResources().getAssets().open(iconName);
                        bmp = BitmapFactory.decodeStream(is);
                        is.close();
                        updateView();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "e1-----" + e.getMessage());
                        Log.i(TAG, "e2-----" + e.toString());

                    }

                    id = id + 1;
                    if (id * 2 <= total) {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = id;
                        mHandler.sendMessageDelayed(msg, 16);
                    }
                    break;
            }

            return false;
        }
    });


    public void  updateView(){
        views = new RemoteViews(mContext.getPackageName(), R.layout.clean_widget);
        views.setImageViewBitmap(R.id.bg, bmp);
        ComponentName componentName = new ComponentName(mContext,getClass());
        appWidgetManager.updateAppWidget(componentName, views);
    }


    public String getIconName(int id) {

        id = id;
        String iconName;
        if (id < 10) {
            iconName = "loading/" + "0" + id + ".png";

        } else {
            iconName = "loading/" + id + ".png";
        }
        Log.i(TAG, "iconName----------" + iconName);
        Log.i(TAG, "-------------------------------------------------------------------------");
        return iconName;
    }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.i(TAG, "onUpdate..");
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        this.appWidgetManager = appWidgetManager;
        this.mContext = context;
        this.appWidgetId = appWidgetIds[0];


        Log.i(TAG, "onUpdate..");
        Log.i(TAG, "appWidgetId:" + appWidgetId);
        updateAppWidget(context, appWidgetManager, appWidgetId);
        views = new RemoteViews(context.getPackageName(), R.layout.clean_widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        Message msg = new Message();
        msg.what = 1;
        msg.obj = 0;
        mHandler.sendMessageDelayed(msg, 4000);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.i(TAG, "onEnabled..");

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.i(TAG, "onDisabled..");

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        this.mContext = context;
        Log.i(TAG, "onReceive：" + intent.getAction());

        if (intent.getAction().equals("com.qinggan.account.exited")) {
            Log.i(TAG, "msg update");
            appWidgetManager = AppWidgetManager.getInstance(context);
            Message msg = new Message();
            msg.what = 1;
            msg.obj = 0;
            mHandler.sendMessageDelayed(msg, 3000);

        }

    }
}

