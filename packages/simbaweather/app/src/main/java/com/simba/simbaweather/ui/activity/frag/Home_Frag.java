package com.simba.simbaweather.ui.activity.frag;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.model.Response;
import com.simba.base.utils.LocationUtil;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.LocationUtils;
import com.simba.simbaweather.data.bean.WeaTher;
import com.simba.simbaweather.di.weatherShowMvp.WeatherShowContract;
import com.simba.simbaweather.di.weatherShowMvp.WeatherShowPresenter;
import com.simba.simbaweather.ui.activity.CityManagerActivity;
import com.simba.simbaweather.ui.activity.view.RecyclerViewDivider;
import com.simba.simbaweather.ui.adapter.WeatherAdapter;
import com.simba.simbaweather.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *@Auther:王自阳
 *@Date: 2019/9/4
 *@Time:21:16
 *@Package_name:com.bw.movie.data.frag
 *@Description:
 * */
public class Home_Frag extends BaseFragment<WeatherShowContract.IWeatherShowView, WeatherShowPresenter<WeatherShowContract.IWeatherShowView>> implements WeatherShowContract.IWeatherShowView {

    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.rv_weather)
    RecyclerView rvWeather;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_climate)
    TextView tvClimate;
    @BindView(R.id.tv_airquality)
    TextView tvAirquality;
    @BindView(R.id.iv_windspeed)
    ImageView ivWindspeed;
    @BindView(R.id.tv_windspeed)
    TextView tvWindspeed;
    @BindView(R.id.iv_airhumidity)
    ImageView ivAirhumidity;
    @BindView(R.id.tv_airhumidity)
    TextView tvAirhumidity;
    @BindView(R.id.iv_ultravioletradiator)
    ImageView ivUltravioletradiator;
    @BindView(R.id.v_ultravioletradiator)
    TextView vUltravioletradiator;
    @BindView(R.id._ultravioletradiator)
    ImageView Ultravioletradiator;
    @BindView(R.id.tv_max_min)
    TextView tvMaxMin;
    @BindView(R.id.tv_runacity)
    TextView tvRunacity;
    @BindView(R.id.beijingid)
    RelativeLayout beijingid;
    @BindView(R.id.tv_refreshtime)
    ImageView tvRefreshtime;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private List<WeaTher.DataBean.WeatherListBean> weatherList;
    private String conditionId;
    private String city;
    private String district;
    private String temp;
    private String tempDay;
    private String tempNight;
    private Location gpsLocation;

    @Override
    public void WeatherShowData(Response<WeaTher.DataBean> response) {
        weatherList = response.body().getWeatherList();
        if (response.body() != null) {
            try {
                //定位城市
                city = response.body().getCity().getCity();
                district = response.body().getCity().getDistrict();
                //实时温度
                temp = response.body().getWeatherToday().getTemp();
                //最高温度/最低温度
                tempDay = response.body().getWeatherToday().getTempDay();
                tempNight = response.body().getWeatherToday().getTempNight();
                tvCity.setText("" + city + "·" + district);
                tvTemperature.setText("" + temp + "°");
                tvClimate.setText("" + response.body().getWeatherToday().getCondition());
                tvWindspeed.setText("" + response.body().getWeatherToday().getWindDir() + "  " + response.body().getWeatherToday().getWindLevel() + "级");
                tvMaxMin.setText("" + tempDay + "°/" + tempNight + "°");
                tvAirquality.setText("" + response.body().getWeatherToday().getAqi() + "  " + response.body().getWeatherToday().getAqiValue());
                tvAirhumidity.setText("湿度   " + response.body().getWeatherToday().getHumidity() + "%");
                conditionId = response.body().getWeatherToday().getConditionId();
                if (conditionId.equals("8")) {
                    beijingid.setBackgroundResource(R.drawable.bj_overcasrsky);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        //设置recyclerview线性布局外加快速适配器
        rvWeather.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        WeatherAdapter weatherAdapter = new WeatherAdapter(R.layout.item_weather, response.body().getWeatherList());
        rvWeather.setAdapter(weatherAdapter);
        //设置自定义分割线
        rvWeather.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, R.drawable.itemdecoration));
        //rvWeather.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
    }


    @Override
    public void initData() {
       /*
        gpsLocation = LocationUtils.getGPSLocation(getContext());

        Criteria c = new Criteria();//Criteria类是设置定位的标准信息（系统会根据你的要求，匹配最适合你的定位供应商），一个定位的辅助信息的类
        c.setPowerRequirement(Criteria.POWER_LOW);//设置低耗电
        c.setAltitudeRequired(true);//设置需要海拔
        c.setBearingAccuracy(Criteria.ACCURACY_COARSE);//设置COARSE精度标准
        c.setAccuracy(Criteria.ACCURACY_LOW);//设置低精度
        //... Criteria 还有其他属性，就不一一介绍了
        Location best = LocationUtils.getBestLocation(getContext(), c);
        if (best == null) {
            //   Toast.makeText(getContext(), " best location is null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "best location: lat==" + best.getLatitude() + " lng==" + best.getLongitude(), Toast.LENGTH_SHORT).show();
        }
        if (gpsLocation == null) {

        } else {
            fPresenter.WeathershowRequestData("" + best.getAltitude(), "" + best.getLongitude());
        }
        */
       Location location = LocationUtil.getInstance(getContext()).getLocationInfo();
       if(location == null){
           fPresenter.WeathershowRequestData("" + 32.298741, "" + 118.840485);
       }else {
           fPresenter.WeathershowRequestData("" + location.getAltitude(), "" + location.getLongitude());
       }
    }

    @Override
    protected WeatherShowPresenter<WeatherShowContract.IWeatherShowView> oncreatePresenter() {
        return new WeatherShowPresenter<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.item_home;
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
                fPresenter.WeathershowRequestData("" + 32.298741, "" + 118.840485);
                break;
            case R.id.tv_runacity:
                Intent intent = new Intent(getContext(), CityManagerActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("district", district);
                intent.putExtra("conditionId", conditionId);
                intent.putExtra("temp", temp);
                intent.putExtra("tempDay", tempDay);
                intent.putExtra("tempNight", tempNight);
                startActivity(intent);
                break;
        }
    }
}
