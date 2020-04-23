package com.simba.simbaweather.ui.activity.frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.model.Response;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.di.cityidMvp.CityIdContract;
import com.simba.simbaweather.di.cityidMvp.CityIdPresnter;
import com.simba.simbaweather.ui.activity.CityManagerActivity;
import com.simba.simbaweather.ui.adapter.CityIdAdapter;
import com.simba.simbaweather.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/*
 *@Auther:王自阳
 *@Date: 2019/9/4
 *@Time:21:49
 *@Package_name:com.bw.movie.data.frag
 *@Description:
 * */
public class Cinema_Frag extends BaseFragment<CityIdContract.ICityIdView, CityIdPresnter<CityIdContract.ICityIdView>> implements CityIdContract.ICityIdView {


    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_refreshtime)
    ImageView tvRefreshtime;
    @BindView(R.id.tv_runacity_c)
    TextView tvRunacityC;
    @BindView(R.id.tv_temperature_c)
    TextView tvTemperatureC;
    @BindView(R.id.tv_climate_c)
    TextView tvClimateC;
    @BindView(R.id.tv_airquality_c)
    TextView tvAirqualityC;
    @BindView(R.id.tv_max_min_c)
    TextView tvMaxMinC;
    @BindView(R.id.iv_windspeed_c)
    ImageView ivWindspeedC;
    @BindView(R.id.tv_windspeed_c)
    TextView tvWindspeedC;
    @BindView(R.id.iv_airhumidity_c)
    ImageView ivAirhumidityC;
    @BindView(R.id.tv_airhumidity_c)
    TextView tvAirhumidityC;
    @BindView(R.id.iv_ultravioletradiator_c)
    ImageView ivUltravioletradiatorC;
    @BindView(R.id.tv_ultravioletradiator_c)
    TextView tvUltravioletradiatorC;
    @BindView(R.id.v_ultravioletradiator_c)
    ImageView vUltravioletradiatorC;
    @BindView(R.id.beijingid)
    RelativeLayout beijingid;
    @BindView(R.id.rv_weather_c)
    RecyclerView rvWeatherC;
    private SharedPreferences mysp;
    private List<CitySearchBean.DataBean.WeatherListBean> weatherList;
    private int cityId = -1;

    public Cinema_Frag(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public void initData() {
        //mysp = getActivity().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        fPresenter.RequestCityData("" + cityId);
    }

    @Override
    protected CityIdPresnter<CityIdContract.ICityIdView> oncreatePresenter() {
        return new CityIdPresnter<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.item_cinema;
    }

    private String conditionId;
    private String city;
    private String district;
    private String temp;
    private String tempDay;
    private String tempNight;

    @Override
    public void CityIdData(Response<CitySearchBean.DataBean> response) {
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
                tvTemperatureC.setText("" + temp + "°");
                tvClimateC.setText("" + response.body().getWeatherToday().getCondition());
                tvWindspeedC.setText("" + response.body().getWeatherToday().getWindDir() + "  " + response.body().getWeatherToday().getWindLevel() + "级");
                tvMaxMinC.setText("" + tempDay + "°/" + tempNight + "°");
                tvAirqualityC.setText("" + response.body().getWeatherToday().getAqi() + "  " + response.body().getWeatherToday().getAqiValue());
                tvAirhumidityC.setText("湿度   " + response.body().getWeatherToday().getHumidity() + "%");
                conditionId = response.body().getWeatherToday().getConditionId();
                if (conditionId.equals("8")) {
                    beijingid.setBackgroundResource(R.drawable.bj_overcasrsky);
                }
                rvWeatherC.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                CityIdAdapter cityIdAdapter = new CityIdAdapter(R.layout.item_weather, weatherList);
                rvWeatherC.setAdapter(cityIdAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        tvRunacityC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CityManagerActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("district", district);
                intent.putExtra("conditionId", conditionId);
                intent.putExtra("temp", temp);
                intent.putExtra("tempDay", tempDay);
                intent.putExtra("tempNight", tempNight);
                getActivity().startActivity(intent);
            }
        });

    }
}
