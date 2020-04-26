package com.simba.simbaweather.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.simba.base.base.BaseActivity;
import com.simba.base.utils.LogUtil;
import com.simba.simbaweather.CityInfoManager;
import com.simba.simbaweather.ICityChangeView;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.MyApplication;
import com.simba.simbaweather.data.WeatherIconUtil;
import com.simba.simbaweather.data.bean.WeatherBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements ICityChangeView {

    ViewPager showVp;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_refreshtime)
    ImageView tvRefreshtime;
    @BindView(R.id.tv_runacity)
    TextView tvRunacity;
    @BindView(R.id.show_tab)
    TabLayout showTab;


    ArrayList<Fragment> fragmentList = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    private Runnable update;
    private WeatherPagerAdapter weatherPagerAdapter;
    int position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        showVp = findViewById(R.id.show_vp);
        CityInfoManager.getInstance().registerCityChangeView(this, this);

        //showVp.setAdapter(myFragmentPagerAdapter);
        weatherPagerAdapter = new WeatherPagerAdapter();
        weatherPagerAdapter.setWeatherInfoList(CityInfoManager.getInstance().getCityList(), null);

        showVp.setAdapter(weatherPagerAdapter);

        showTab.setupWithViewPager(showVp);


        setTab(position);

        showVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCity.setText(weatherPagerAdapter.getCityNameByPosition(position));

                MainActivity.this.position = position;

                setTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setTab(int position) {
        LogUtil.e(showTab.getTabCount() + "");
        for (int i = 0; i < showTab.getTabCount(); i++) {
            showTab.getTabAt(i).setText("").setIcon(R.mipmap.circledrop);
        }
        showTab.getTabAt(position).setText("").setIcon(R.mipmap.stripswitch);
    }

    class WeatherPagerAdapter extends PagerAdapter {
        private List<CityInfoManager.CityManagerBean> cityManagerBeanList;
        private Map<Integer, WeatherBean> weatherBeanMap;
        private List<View> mViewList;
        private View mRootView;

        public String getCityNameByPosition(int position) {
            if (weatherBeanMap != null) {
                int cityId = cityManagerBeanList.get(position).getCityId();
                return weatherBeanMap.get(cityId).getCity().getCity() + "·" + weatherBeanMap.get(cityId).getCity().getDistrict();
            }
            return "";
        }

        public void setWeatherInfoList(List<CityInfoManager.CityManagerBean> cityManagerBeanList, Map<Integer, WeatherBean> weatherInfoList) {
            this.cityManagerBeanList = cityManagerBeanList;
            this.weatherBeanMap = weatherInfoList;
            initViewList();
            notifyDataSetChanged();
            tvCity.setText(getCityNameByPosition(0));
        }

        @Override
        public int getCount() {
            return cityManagerBeanList.size();
        }

        public void initViewList() {
            mViewList = new ArrayList<>();
            for (CityInfoManager.CityManagerBean cityManagerBean : cityManagerBeanList) {

                LayoutInflater layoutInflater = getLayoutInflater();
                mRootView = layoutInflater.inflate(R.layout.item_cinema, null, false);
                if (weatherBeanMap != null) {
                    WeatherBean weatherBean;
                    if (cityManagerBean.isLocationCity()) {
                        weatherBean = weatherBeanMap.get(0);
                    } else {
                        weatherBean = weatherBeanMap.get(cityManagerBean.getCityId());
                    }

                    ((TextView) mRootView.findViewById(R.id.tv_temperature)).setText(weatherBean.getWeatherToday().getTemp() + "°");
                    ((TextView) mRootView.findViewById(R.id.tv_climate)).setText(weatherBean.getWeatherToday().getCondition());
                    ((TextView) mRootView.findViewById(R.id.tv_windspeed)).setText(weatherBean.getWeatherToday().getWindDir() + " " + weatherBean.getWeatherToday().getWindLevel());
                    ((TextView) mRootView.findViewById(R.id.tv_max_min)).setText(weatherBean.getWeatherToday().getTempDay() + "°/" + weatherBean.getWeatherToday().getTempNight() + "°");
                    ((TextView) mRootView.findViewById(R.id.tv_airquality)).setText(weatherBean.getWeatherToday().getAqi() + "  " + weatherBean.getWeatherToday().getAqiValue());
                    ((TextView) mRootView.findViewById(R.id.tv_airhumidity)).setText("湿度   " + weatherBean.getWeatherToday().getHumidity() + "%");
                    ((TextView) mRootView.findViewById(R.id.tv_strengthgrade)).setText(weatherBean.getWeatherToday().getUviStatus());
                    ((TextView) mRootView.findViewById(R.id.tv_washcarstatus)).setText(weatherBean.getWeatherToday().getWashCarStatus());


                    List<WeatherBean.WeatherListBean> weatherBeanList = weatherBean.getWeatherList();
                    ((TextView) mRootView.findViewById(R.id.weather_data_1).findViewById(R.id.tv_day)).setText(weatherBeanList.get(0).getDayStr());
                    ((TextView) mRootView.findViewById(R.id.weather_data_1).findViewById(R.id.tv_date)).setText(weatherBeanList.get(0).getDate());
                    ((TextView) mRootView.findViewById(R.id.weather_data_1).findViewById(R.id.tv_weathersituation)).setText(weatherBeanList.get(0).getCondition());
                    ((TextView) mRootView.findViewById(R.id.weather_data_1).findViewById(R.id.tv_tirtmp)).setText(weatherBeanList.get(0).getTempDay() + "°/" + weatherBeanList.get(0).getTempNight() + "°");
                    ((ImageView) mRootView.findViewById(R.id.weather_data_1).findViewById(R.id.miv_img)).setImageDrawable(WeatherIconUtil.getWeatherIconByType(MyApplication.getMyApplication(), weatherBeanList.get(0).getConditionId()));

                    ((TextView) mRootView.findViewById(R.id.weather_data_2).findViewById(R.id.tv_day)).setText(weatherBeanList.get(1).getDayStr());
                    ((TextView) mRootView.findViewById(R.id.weather_data_2).findViewById(R.id.tv_date)).setText(weatherBeanList.get(1).getDate());
                    ((TextView) mRootView.findViewById(R.id.weather_data_2).findViewById(R.id.tv_weathersituation)).setText(weatherBeanList.get(1).getCondition());
                    ((TextView) mRootView.findViewById(R.id.weather_data_2).findViewById(R.id.tv_tirtmp)).setText(weatherBeanList.get(1).getTempDay() + "°/" + weatherBeanList.get(1).getTempNight() + "°");
                    ((ImageView) mRootView.findViewById(R.id.weather_data_2).findViewById(R.id.miv_img)).setImageDrawable(WeatherIconUtil.getWeatherIconByType(MyApplication.getMyApplication(), weatherBeanList.get(1).getConditionId()));

                    ((TextView) mRootView.findViewById(R.id.weather_data_3).findViewById(R.id.tv_day)).setText(weatherBeanList.get(2).getDayStr());
                    ((TextView) mRootView.findViewById(R.id.weather_data_3).findViewById(R.id.tv_date)).setText(weatherBeanList.get(2).getDate());
                    ((TextView) mRootView.findViewById(R.id.weather_data_3).findViewById(R.id.tv_weathersituation)).setText(weatherBeanList.get(2).getCondition());
                    ((TextView) mRootView.findViewById(R.id.weather_data_3).findViewById(R.id.tv_tirtmp)).setText(weatherBeanList.get(2).getTempDay() + "°/" + weatherBeanList.get(2).getTempNight() + "°");
                    ((ImageView) mRootView.findViewById(R.id.weather_data_3).findViewById(R.id.miv_img)).setImageDrawable(WeatherIconUtil.getWeatherIconByType(MyApplication.getMyApplication(), weatherBeanList.get(2).getConditionId()));

                    ((TextView) mRootView.findViewById(R.id.weather_data_4).findViewById(R.id.tv_day)).setText(weatherBeanList.get(3).getDayStr());
                    ((TextView) mRootView.findViewById(R.id.weather_data_4).findViewById(R.id.tv_date)).setText(weatherBeanList.get(3).getDate());
                    ((TextView) mRootView.findViewById(R.id.weather_data_4).findViewById(R.id.tv_weathersituation)).setText(weatherBeanList.get(3).getCondition());
                    ((TextView) mRootView.findViewById(R.id.weather_data_4).findViewById(R.id.tv_tirtmp)).setText(weatherBeanList.get(3).getTempDay() + "°/" + weatherBeanList.get(3).getTempNight() + "°");
                    ((ImageView) mRootView.findViewById(R.id.weather_data_4).findViewById(R.id.miv_img)).setImageDrawable(WeatherIconUtil.getWeatherIconByType(MyApplication.getMyApplication(), weatherBeanList.get(3).getConditionId()));

                }
                mViewList.add(mRootView);
            }
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViewList.get(position));

        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
    }


    @Override
    public void onCityChange(List<CityInfoManager.CityManagerBean> cityManagerBeanList, Map<Integer, WeatherBean> weatherBeanMap) {
        weatherPagerAdapter.setWeatherInfoList(cityManagerBeanList, weatherBeanMap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CityInfoManager.getInstance().unRegisterCityChangeView(this);
    }

    @OnClick({R.id.tv_refreshtime, R.id.tv_runacity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refreshtime:
                Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
                t.setToNow(); // 取得系统时间。
                int year = t.year;
                int month = t.month + 1;
                int day = t.monthDay;
                int hour = t.hour; // 0-23
                int minute = t.minute;
                int second = t.second;
//                2020-03-23 19:32
                tvTime.setText("中国天气  更新于：" + year + "-" + month + "-" + day + "  " + hour + ":" + minute);

                break;
            case R.id.tv_runacity:
                Intent intent = new Intent(this, CityManagerActivity.class);
                startActivity(intent);
                break;
        }
    }

}
