package com.simba.calendar;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.SPStaticUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
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

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    private final String[] weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    @BindView(R.id.tv_main_left_today)
    TextView mTvMainLeftToday;
    @BindView(R.id.tv_main_left_year_month)
    TextView mTvMainLeftYearMonth;
    @BindView(R.id.tv_main_week)
    TextView mTvMainWeek;
    @BindView(R.id.tv_main_lunar)
    TextView mTvMainLunar;
    @BindView(R.id.tv_main_trunk_and_branch)
    TextView mTvMainTrunkAndBranch;
    @BindView(R.id.tv_main_right_year_month)
    TextView mTvMainRightYearMonth;
    @BindView(R.id.tv_main_year_view)
    TextView mTvMainYearView;
    @BindView(R.id.cv_main_calendar_view)
    CalendarView mCvMainCalendarView;
    @BindView(R.id.iv_main_today)
    ImageView mIvMainToday;
    @BindView(R.id.iv_main_setting)
    ImageView mIvMainSetting;
    @BindView(R.id.tv_main_should)
    TextView mTvMainShould;
    @BindView(R.id.tv_main_should_group)
    LinearLayout mLlMainShouldGroup;
    @BindView(R.id.tv_main_holiday)
    TextView mTvMainHoliday;
    @BindView(R.id.tv_main_a_word_a_day_str)
    TextView mTvMainAWordADayStr;
    @BindView(R.id.ll_main_a_word_a_day)
    LinearLayout mLlMainAWordADay;
    @BindView(R.id.ll_main_network_retry)
    LinearLayout mLlMainNetworkRetry;
    @BindView(R.id.iv_man_network_loading)
    ImageView mIvManNetworkLoading;
    @BindView(R.id.ll_main_network_loading)
    LinearLayout mLlMainNetworkLoading;

    //下面2个静态值方便在年视图中月份标记判断使用
    public static int CUR_MONTH, CUR_YEAR;
    private TimePickerView pvTime;
    private Boolean setting_almanac, setting_holiday;
    private Animation animation;
    boolean networkErr = false;
    private Calendar today;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_main_network_load);
    }

    @Override
    public void initData() {
        //设置日历范围
        mCvMainCalendarView.setRange(1900, 1, 1, mCvMainCalendarView.getCurYear() + 30, 12, 31);
        today = mCvMainCalendarView.getSelectedCalendar();
        selectDay(today);
        updateWorkAndRestDay(today.getYear());
    }

    @Override
    protected void initListener() {
        mTvMainRightYearMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTimeInMillis(mCvMainCalendarView.getSelectedCalendar().getTimeInMillis());
                showTimePicker(calendar);
            }
        });
        mTvMainYearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCvMainCalendarView.isYearSelectLayoutVisible()) {
                    mCvMainCalendarView.closeYearSelectLayout();
                    return;
                }
                mCvMainCalendarView.showYearSelectLayout(mCvMainCalendarView.getSelectedCalendar().getYear());
            }
        });
        mIvMainToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCvMainCalendarView.isYearSelectLayoutVisible())
                    mCvMainCalendarView.closeYearSelectLayout();
                mCvMainCalendarView.scrollToCurrent(true);
                selectDay(mCvMainCalendarView.getSelectedCalendar());
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
                selectDay(calendar);
            }
        });
        mCvMainCalendarView.setOnYearViewChangeListener(new CalendarView.OnYearViewChangeListener() {
            @Override
            public void onYearViewChange(boolean isClose) {
                if (!isClose)
                    mIvMainToday.setEnabled(true);
            }
        });
        mLlMainNetworkRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDay(mCvMainCalendarView.getSelectedCalendar());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setting_holiday = SPStaticUtils.getBoolean(SettingActivity.KEY_SETTING_HOLIDAY_PUSH, true);
        mTvMainHoliday.setVisibility(setting_holiday ? View.VISIBLE : View.GONE);

        setting_almanac = SPStaticUtils.getBoolean(SettingActivity.KEY_SETTING_ALMANAC, true);
        if (setting_almanac && !networkErr)
            mLlMainShouldGroup.setVisibility(View.VISIBLE);
        else
            mLlMainShouldGroup.setVisibility(View.GONE);
    }

    /**
     * 选择年月日周，更新左边视图，标题视图等
     *
     * @param calendar
     */
    private void selectDay(Calendar calendar) {
        CUR_MONTH = calendar.getMonth();
        CUR_YEAR = calendar.getYear();

        mTvMainLeftToday.setText(String.valueOf(calendar.getDay()));
        mTvMainLeftYearMonth.setText(String.format(Locale.SIMPLIFIED_CHINESE, "%d-%s", calendar.getYear(), formatStr(calendar.getMonth())));
        mTvMainWeek.setText(weeks[calendar.getWeek()]);
        mTvMainRightYearMonth.setText(String.format(Locale.SIMPLIFIED_CHINESE, "%d年%d月", calendar.getYear(), calendar.getMonth()));

        //设置节日
        if (!TextUtils.isEmpty(calendar.getSolarTerm())) {
            mTvMainHoliday.setText(calendar.getSolarTerm());
        } else if (!TextUtils.isEmpty(calendar.getGregorianFestival())) {
            mTvMainHoliday.setText(calendar.getGregorianFestival());
        } else if (!TextUtils.isEmpty(calendar.getTraditionFestival())) {
            mTvMainHoliday.setText(calendar.getTraditionFestival());
        } else {
            mTvMainHoliday.setText("");
        }
//        mTvMainHoliday.setText(calendar.getLunar());

        //设置今天按钮状态
        if (calendar.compareTo(today) == 0) {
            mIvMainToday.setEnabled(false);
        } else {
            mIvMainToday.setEnabled(true);
        }

        //更新农历数据，每日一言
        updateDailyInformation(calendar.getYear(), calendar.getMonth(), calendar.getDay());
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
                    public void onStart(Request<DailyInformation, ? extends Request> request) {
                        super.onStart(request);
                        mTvMainTrunkAndBranch.setText("");
                        mTvMainLunar.setText("");
                        mLlMainShouldGroup.setVisibility(View.GONE);
                        mLlMainAWordADay.setVisibility(View.GONE);
                        mLlMainNetworkLoading.setVisibility(View.VISIBLE);
                        mIvManNetworkLoading.startAnimation(animation);
                        mLlMainNetworkRetry.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(Response<DailyInformation> response) {
                        if (isCode200()) {
                            DailyInformation dailyInformation = response.body();
                            if (dailyInformation.year != null && dailyInformation.animal != null)
                                mTvMainTrunkAndBranch.setText(String.format("%s年 【%s年】", dailyInformation.year, dailyInformation.animal));
                            if (dailyInformation.chinesedesc != null)
                                mTvMainLunar.setText(dailyInformation.chinesedesc);
                            if (dailyInformation.proper != null)
                                mTvMainShould.setText(dailyInformation.proper.replace(".", " "));
                            if (dailyInformation.word != null)
                                mTvMainAWordADayStr.setText(dailyInformation.word);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mIvManNetworkLoading.clearAnimation();
                        mLlMainNetworkLoading.setVisibility(View.GONE);
                        if (isCode200()) {
                            mLlMainShouldGroup.setVisibility(setting_almanac == null || setting_almanac ? View.VISIBLE : View.GONE);
                            mLlMainAWordADay.setVisibility(View.VISIBLE);
                            mLlMainNetworkRetry.setVisibility(View.GONE);
                            networkErr = false;
                        } else {
                            mLlMainShouldGroup.setVisibility(View.GONE);
                            mLlMainAWordADay.setVisibility(View.GONE);
                            mLlMainNetworkRetry.setVisibility(View.VISIBLE);
                            networkErr = true;
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
    private void showTimePicker(java.util.Calendar calendar) {
        if (pvTime == null) {
            pvTime = new TimePickerBuilderHelper(this, new TimePickerBuilderHelper.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(java.util.Calendar date, View v) {
                    mCvMainCalendarView.scrollToCalendar(date.get(java.util.Calendar.YEAR), date.get(java.util.Calendar.MONTH) + 1, date.get(java.util.Calendar.DAY_OF_MONTH));
                }
            }).getTimePickerView();
        }
        pvTime.setDate(calendar);
        pvTime.show();
    }

    private String formatStr(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

}
