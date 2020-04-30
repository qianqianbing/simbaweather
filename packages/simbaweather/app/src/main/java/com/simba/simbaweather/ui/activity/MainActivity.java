package com.simba.simbaweather.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.simba.base.base.BaseActivity;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.utils.SimbaToast;
import com.simba.simbaweather.CityInfoManager;
import com.simba.simbaweather.ICityChangeView;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.MyApplication;
import com.simba.simbaweather.data.WeatherIconUtil;
import com.simba.simbaweather.data.bean.WeatherBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
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

    private static String TAG = "MainActivity";
    @BindView(R.id.tv_refreshthread)
    ImageView tvRefreshthread;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.tv)
    TextView tv;
    private WeatherPagerAdapter weatherPagerAdapter;
    int position;
    private boolean connected;
    private WeatherBean weatherBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //进入页面进行刷新
        connected = NetworkUtils.isConnected();
        if (connected) {
            tvRefreshtime.setVisibility(View.VISIBLE);
            tvRefreshthread.setVisibility(View.INVISIBLE);
            CityInfoManager.getInstance().requestWeatherInfo();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            tvTime.setText("中国天气  更新于：" + simpleDateFormat.format(date));
            DialogUtil.buildProgress(getApplicationContext(), "加载中").setCancelOnTouchOutside(true).show();

        } else {
            SimbaToast.showInfo(getApplicationContext(), "当前无网络,请稍后重试");
        }
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
//        定位图标只在首页展示
        if (position > 0) {
            imgLocation.setVisibility(View.INVISIBLE);
        } else {
            imgLocation.setVisibility(View.VISIBLE);
        }

//        LogUtil.e(showTab.getTabCount() + "");
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

        public WeatherPagerAdapter() {
            super();
            mViewList = new ArrayList<>();
        }

        public String getCityNameByPosition(int position) {

            if (weatherBeanMap != null) {
                int cityId = cityManagerBeanList.get(position).getCityId();
                if (position == 0) {
                    return weatherBeanMap.get(cityId).getCity().getCity() + "·" + weatherBeanMap.get(cityId).getCity().getDistrict();
                } else {
                    return weatherBeanMap.get(cityId).getCity().getDistrict();
                }
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
            for (int i = 0; i < cityManagerBeanList.size(); i++) {
//            for (CityInfoManager.CityManagerBean cityManagerBean : cityManagerBeanList) {
                CityInfoManager.CityManagerBean cityManagerBean = cityManagerBeanList.get(i);
                if (mViewList.size() > i) {
                    mRootView = mViewList.get(i);
                } else {
                    LayoutInflater layoutInflater = getLayoutInflater();
                    mRootView = layoutInflater.inflate(R.layout.item_cinema, null, false);
                }

                if (weatherBeanMap != null) {

                    if (cityManagerBean.isLocationCity()) {
                        weatherBean = weatherBeanMap.get(0);
                    } else {
                        weatherBean = weatherBeanMap.get(cityManagerBean.getCityId());
                    }

                    ((TextView) mRootView.findViewById(R.id.tv_temperature)).setText(weatherBean.getWeatherToday().getTemp() + "°");
                    ((TextView) mRootView.findViewById(R.id.tv_climate)).setText(weatherBean.getWeatherToday().getCondition());
                    ((TextView) mRootView.findViewById(R.id.tv_windspeed)).setText(weatherBean.getWeatherToday().getWindDir() + " " + weatherBean.getWeatherToday().getWindLevel() + "级");
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
                if (mViewList.size() > i) {
                } else {
                    mViewList.add(mRootView);
                }

            }
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.e(TAG, "instantiateItem" + position);
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            Log.e(TAG, "destroyItem" + position);
            container.removeView(mViewList.get(position));

        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    protected void initData() {
        //  SimbaToast.showInfo(getApplicationContext(),"cityinfomanger");
    }

    @Override
    protected void initListener() {
    }


    @Override
    public void onCityChange(List<CityInfoManager.CityManagerBean> cityManagerBeanList, Map<Integer, WeatherBean> weatherBeanMap) {
        if (weatherPagerAdapter == null) {
            SimbaToast.showInfo(getApplicationContext(), "当前无网络,请稍后重试");
        } else {
            weatherPagerAdapter.setWeatherInfoList(cityManagerBeanList, weatherBeanMap);
        }


    }


    @OnClick({R.id.tv_refreshtime, R.id.tv_runacity, R.id.tv_refreshthread})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refreshtime:
                tvRefreshthread.setVisibility(View.VISIBLE);
                tvRefreshtime.setVisibility(View.INVISIBLE);
                //网络状态判断
                connected = NetworkUtils.isConnected();
                if (connected) {
                    tvRefreshtime.setVisibility(View.VISIBLE);
                    tvRefreshthread.setVisibility(View.INVISIBLE);
                    CityInfoManager.getInstance().requestWeatherInfo();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// HH:mm:ss
                    //获取当前时间
                    Date date = new Date(System.currentTimeMillis());
                    tvTime.setText("中国天气  更新于：" + simpleDateFormat.format(date));
                    DialogUtil.buildProgress(getApplicationContext(), "加载中").setCancelOnTouchOutside(true).show();

                } else {
                    SimbaToast.showInfo(getApplicationContext(), "当前无网络,请稍后重试");
                }
                break;
            case R.id.tv_runacity:
                Intent intent = new Intent(this, CityManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_refreshthread:
//                2020-03-23 19:32
                tvRefreshthread.setVisibility(View.VISIBLE);
                tvRefreshtime.setVisibility(View.INVISIBLE);
                tvTime.setText("天气刷新中，请稍等");

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CityInfoManager.getInstance().unRegisterCityChangeView(this);
    }

}
