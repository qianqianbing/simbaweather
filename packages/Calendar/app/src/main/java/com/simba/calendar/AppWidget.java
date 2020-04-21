package com.simba.calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.base.utils.SpStaticUtils;
import com.simba.calendar.model.DailyInformation;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    private final String[] weeks = new String[]{"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private Context context;
    private RemoteViews views;
    private AppWidgetManager appWidgetManager;
    private int appWidgetId;

    void updateAppWidget() {

        if (context == null)
            return;

        // Construct the RemoteViews object
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        views.setTextViewText(R.id.tv_widget_day, calendar.get(Calendar.DAY_OF_MONTH) + "");
        views.setTextViewText(R.id.tv_widget_week_day, weeks[calendar.get(Calendar.DAY_OF_WEEK)]);
        views.setTextViewText(R.id.tv_widget_month, (calendar.get(Calendar.MONTH) + 1) + "月");

//        views.setTextViewText(R.id.tv_widget_city, "");
//        views.setTextViewText(R.id.tv_widget_weather, "");
//        views.setTextViewText(R.id.tv_widget_temperature, "");
//        views.setTextViewText(R.id.tv_widget_temperature_range, "");

        //其他城市
//        views.setViewVisibility(R.id.rl_widget_other, View.VISIBLE);
//        views.setTextViewText(R.id.tv_widget_other_city, "");
//        views.setTextViewText(R.id.tv_widget_other_weather, "");
//        views.setTextViewText(R.id.tv_widget_other_temperature, "");
//        views.setTextViewText(R.id.tv_widget_other_temperature_range, "");


        //根据配置 节日、黄历是否显示
        boolean setting_almanac = SpStaticUtils.getBoolean(SettingActivity.KEY_SETTING_ALMANAC, true);
        views.setViewVisibility(R.id.tv_widget_should, setting_almanac ? View.VISIBLE : View.GONE);

        //日历点击跳转
        Intent calendarIntent = new Intent(context, MainActivity.class);
        PendingIntent calendarPending = PendingIntent.getActivity(context, 0, calendarIntent, 0);
        views.setOnClickPendingIntent(R.id.rl_widget_bg, calendarPending);

        //天气点击跳转
        Intent weatherIntent = new Intent(context, SettingActivity.class);
        PendingIntent weatherPending = PendingIntent.getActivity(context, 0, weatherIntent, 0);
        views.setOnClickPendingIntent(R.id.rl_widget_local, weatherPending);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        getLunar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.appWidgetId = appWidgetIds[0];
        this.views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        updateAppWidget();
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void getLunar(int year, int month, int day) {

        JSONObject jsonObject = new JSONObject();
        try {
            String date = year + "-" + formatStr(month) + "-" + formatStr(day);
            jsonObject.put("startdate", date);
            jsonObject.put("enddate", date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkGo.<DailyInformation>post(SimbaUrl.CALENDAR_GET_ALMANAC_BY_DATE)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<DailyInformation>() {
                    @Override
                    public void onSuccess(Response<DailyInformation> response) {
                        if (isCode200()) {
                            DailyInformation dailyInformation = response.body();

                            //农历、黄历、每日一言
                            views.setTextViewText(R.id.tv_widget_lunar, dailyInformation.chinesedesc);
                            views.setTextViewText(R.id.tv_widget_should, "宜 " + dailyInformation.proper.replace(".", " "));
                            views.setTextViewText(R.id.tv_widget_a_word_a_day, dailyInformation.word);

                            appWidgetManager.updateAppWidget(appWidgetId, views);

                        }
                    }
                });
    }

    private String formatStr(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    /**
     * 节日是否显示
     *
     * @param context
     */
    public final static void updateHolidayView(Context context, boolean setting_holiday) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);//获得appwidget管理实例，用于管理appwidget以便进行更新操作
        ComponentName componentName = new ComponentName(context, AppWidget.class);//获得所有本程序创建的appwidget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);//获取远程视图
        remoteViews.setViewVisibility(R.id.tv_widget_should, setting_holiday ? View.VISIBLE : View.GONE);
        manager.updateAppWidget(componentName, remoteViews);
    }

    /**
     * 黄历是否显示
     *
     * @param context
     */
    public final static void updateLunarView(Context context, boolean setting_almanac) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);//获得appwidget管理实例，用于管理appwidget以便进行更新操作
        ComponentName componentName = new ComponentName(context, AppWidget.class);//获得所有本程序创建的appwidget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);//获取远程视图
        remoteViews.setViewVisibility(R.id.tv_widget_should, setting_almanac ? View.VISIBLE : View.GONE);
        manager.updateAppWidget(componentName, remoteViews);
    }
}

