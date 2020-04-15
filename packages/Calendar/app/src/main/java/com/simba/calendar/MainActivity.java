package com.simba.calendar;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.base.BaseActivity;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.base.utils.LogUtil;
import com.simba.calendar.model.DailyInformation;
import com.simba.calendar.model.StatutoryHoliday;
import com.simba.calendar.utils.TimePickerBuilderHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private final String[] weeks = new String[]{"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    private TextView mTvMainLeftToday;
    private TextView mTvMainLeftYearMonth;
    private TextView mTvMainWeek;
    private TextView mTvMainLunar;
    private TextView mTvMainTrunkAndBranch;
    private TextView mTvMainShould;
    private TextView mTvMainAWordADay;
    private TextView mTvMainAWordADayStr;
    private TextView mTvMainRightYearMonth;
    private TextView mTvMainYearView;
    private CalendarView mCvMainCalendarView;
    private ImageView mIvMainToday;
    private ImageView mIvMainSetting;

    private TimePickerView pvTime;

    //下面2个静态值方便在年视图中月份标记判断使用
    public static int CUR_MONTH, CUR_YEAR;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mTvMainLeftToday = (TextView) findViewById(R.id.tv_main_left_today);
        mTvMainLeftYearMonth = (TextView) findViewById(R.id.tv_main_left_year_month);
        mTvMainWeek = (TextView) findViewById(R.id.tv_main_week);
        mTvMainLunar = (TextView) findViewById(R.id.tv_main_lunar);
        mTvMainTrunkAndBranch = (TextView) findViewById(R.id.tv_main_trunk_and_branch);
        mTvMainShould = (TextView) findViewById(R.id.tv_main_should);
        mTvMainAWordADay = (TextView) findViewById(R.id.tv_main_a_word_a_day);
        mTvMainAWordADayStr = (TextView) findViewById(R.id.tv_main_a_word_a_day_str);
        mTvMainRightYearMonth = (TextView) findViewById(R.id.tv_main_right_year_month);
        mTvMainYearView = (TextView) findViewById(R.id.tv_main_year_view);
        mCvMainCalendarView = (CalendarView) findViewById(R.id.cv_main_calendar_view);
        mIvMainToday = (ImageView) findViewById(R.id.iv_main_today);
        mIvMainSetting = (ImageView) findViewById(R.id.iv_main_setting);
    }

    @Override
    public void initData() {
        int year = mCvMainCalendarView.getCurYear();
        int month = mCvMainCalendarView.getCurMonth();
        int day = mCvMainCalendarView.getCurDay();
        CUR_MONTH = month;
        CUR_YEAR = year;
        mTvMainRightYearMonth.setText(String.format(Locale.SIMPLIFIED_CHINESE, "%d年%d月", year, month));
        selectDay(year, month, day, mCvMainCalendarView.getSelectedCalendar().getWeek());
        updateWorkAndRestDay(year);

    }

    @Override
    protected void initListener() {
        mTvMainRightYearMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        mTvMainYearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCvMainCalendarView.isYearSelectLayoutVisible()) {
                    mCvMainCalendarView.closeYearSelectLayout();
                    return;
                }
                mIvMainToday.setEnabled(true);
                mCvMainCalendarView.showYearSelectLayout(mCvMainCalendarView.getCurYear());
            }
        });
        mIvMainToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCvMainCalendarView.isYearSelectLayoutVisible())
                    mCvMainCalendarView.closeYearSelectLayout();

                mCvMainCalendarView.scrollToCurrent(true);
                selectDay(mCvMainCalendarView.getCurYear(), mCvMainCalendarView.getCurMonth(), mCvMainCalendarView.getCurDay(), mCvMainCalendarView.getSelectedCalendar().getWeek());
                mIvMainToday.setEnabled(false);
            }
        });
        mIvMainSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
        mCvMainCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                mIvMainToday.setEnabled(true);
                mTvMainRightYearMonth.setText(String.format(Locale.SIMPLIFIED_CHINESE, "%d年%d月", calendar.getYear(), calendar.getMonth()));

                selectDay(calendar.getYear(), calendar.getMonth(), calendar.getDay(), calendar.getWeek());
                CUR_MONTH = calendar.getMonth();
                CUR_YEAR = calendar.getYear();
            }
        });
    }

    /**
     * 选择年月日周，更新左边视图，标题视图等
     *
     * @param year
     * @param month
     * @param day
     * @param week
     */
    private void selectDay(int year, int month, int day, int week) {
        mTvMainLeftToday.setText(String.valueOf(day));
        mTvMainLeftYearMonth.setText(String.format(Locale.SIMPLIFIED_CHINESE, "%d-%s", year, formatStr(month)));
        mTvMainWeek.setText(weeks[week]);

        //更新农历数据，每日一言
        updateDailyInformation(year, month, day);
    }

    /**
     * 更新主界面左边布局，农历，天干纪年，宜，每日一言
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    private void updateDailyInformation(int year, int month, int day) {
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
                            mTvMainTrunkAndBranch.setText(dailyInformation.year + "年 【" + dailyInformation.animal + "年】");

                            if (dailyInformation.chinesedesc != null)
                                mTvMainLunar.setText(dailyInformation.chinesedesc);
                            if (dailyInformation.proper != null)
                                mTvMainShould.setText(dailyInformation.proper.replace(".", " "));
                            if (dailyInformation.word != null)
                                mTvMainAWordADayStr.setText(dailyInformation.word);
                        }
                    }
                });

    }


    /**
     * 更新法定节假日标记
     *
     * @param year
     */
    private void updateWorkAndRestDay(int year) {
        OkGo.<List<StatutoryHoliday>>post(SimbaUrl.CALENDAR_GET_HOLIDAY_BY_YEAR)
                .tag(this)
                .upJson("{\"year\": \"" + year + "\"}")
                .execute(new JsonCallback<List<StatutoryHoliday>>() {
                    @Override
                    public void onSuccess(Response<List<StatutoryHoliday>> response) {
                        if (isCode200()) {
                            try {
                                List<StatutoryHoliday> statutoryHolidayList = response.body();
                                Map<String, Calendar> map = new HashMap<>();
                                for (StatutoryHoliday statutoryHoliday : statutoryHolidayList) {
                                    Calendar calendar = new Calendar();
                                    calendar.setYear(Integer.parseInt(statutoryHoliday.date.substring(0, 4)));
                                    calendar.setMonth(Integer.parseInt(statutoryHoliday.date.substring(5, 7)));
                                    calendar.setDay(Integer.parseInt(statutoryHoliday.date.substring(8, 10)));
                                    if (statutoryHoliday.state == 1) {
                                        calendar.setScheme("rest");
                                    } else if (statutoryHoliday.state == 0) {
                                        calendar.setScheme("work");
                                    }
                                    map.put(calendar.toString(), calendar);
                                }
                                mCvMainCalendarView.setSchemeDate(map);
                            } catch (Exception e) {
                                LogUtil.e(e);
                            }
                        }
                    }
                });
    }


    /**
     * 显示日期选择器
     */
    private void showTimePicker() {
        if (pvTime == null) {
            pvTime = new TimePickerBuilderHelper(this, new TimePickerBuilderHelper.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(java.util.Calendar date, View v) {
                    mCvMainCalendarView.scrollToCalendar(date.get(java.util.Calendar.YEAR), date.get(java.util.Calendar.MONTH) + 1, date.get(java.util.Calendar.DAY_OF_MONTH));
                }
            }).getTimePickerView();
        }
        pvTime.show();
    }

    private String formatStr(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

}
