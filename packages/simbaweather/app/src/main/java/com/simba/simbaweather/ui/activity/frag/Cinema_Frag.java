package com.simba.simbaweather.ui.activity.frag;

import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.model.Response;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.di.cityidMvp.CityIdContract;
import com.simba.simbaweather.di.cityidMvp.CityIdPresnter;
import com.simba.simbaweather.ui.activity.view.RecyclerViewDivider;
import com.simba.simbaweather.ui.adapter.CityIdAdapter;
import com.simba.simbaweather.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/*
 *@Auther:王自阳
 *@Date: 2019/9/4
 *@Time:21:49
 *@Description:
 * */
public class Cinema_Frag extends BaseFragment<CityIdContract.ICityIdView, CityIdPresnter<CityIdContract.ICityIdView>> implements CityIdContract.ICityIdView {

//
//    @BindView(R.id.img_location)
//    ImageView imgLocation;
//    @BindView(R.id.tv_city)
//    TextView tvCity;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.tv_refreshtime)
//    ImageView tvRefreshtime;
//    @BindView(R.id.tv_runacity_c)
//    TextView tvRunacityC;

    @BindView(R.id.rv_weather_c)
    RecyclerView rvWeatherC;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_climate)
    TextView tvClimate;
    @BindView(R.id.tv_airquality)
    TextView tvAirquality;
    @BindView(R.id.tv_max_min)
    TextView tvMaxMin;
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
    @BindView(R.id.tv_strengthgrade)
    TextView tvStrengthgrade;
    @BindView(R.id._ultravioletradiator)
    ImageView Ultravioletradiator;
    @BindView(R.id.beijingid)
    RelativeLayout beijingid;
    @BindView(R.id.tv_washcarstatus)
    TextView tvWashcarstatus;
    private SharedPreferences mysp;
    private List<CitySearchBean.DataBean.WeatherListBean> weatherList;
    private int cityId = -1;
    private String uviStatus;
    private String washCarStatus;

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
                //紫外线
                uviStatus = response.body().getWeatherToday().getUviStatus();
                //洗车
                washCarStatus = response.body().getWeatherToday().getWashCarStatus();

                tvTemperature.setText("" + temp + "°");
                tvClimate.setText("" + response.body().getWeatherToday().getCondition());
                tvWindspeed.setText("" + response.body().getWeatherToday().getWindDir() + "  " + response.body().getWeatherToday().getWindLevel() + "级");
                tvMaxMin.setText("" + tempDay + "°/" + tempNight + "°");
                tvAirquality.setText("" + response.body().getWeatherToday().getAqi() + "  " + response.body().getWeatherToday().getAqiValue());
                tvAirhumidity.setText("湿度   " + response.body().getWeatherToday().getHumidity() + "%");
                tvStrengthgrade.setText("" + uviStatus);
                tvWashcarstatus.setText("" + washCarStatus);
                conditionId = response.body().getWeatherToday().getConditionId();
                onlisteneritem.onItemClick(city,district);
                if (conditionId.equals("8")) {
                    beijingid.setBackgroundResource(R.drawable.bj_overcasrsky);
                }

                //设置recyclerview线性布局外加快速适配器
                rvWeatherC.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                CityIdAdapter weatherAdapter = new CityIdAdapter(R.layout.item_weather, response.body().getWeatherList());
                rvWeatherC.setAdapter(weatherAdapter);
                //设置自定义分割线
                rvWeatherC.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, R.drawable.itemdecoration));
                //rvWeather.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //        tvRunacityC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), CityManagerActivity.class);
//                intent.putExtra("city", city);
//                intent.putExtra("district", district);
//                intent.putExtra("conditionId", conditionId);
//                intent.putExtra("temp", temp);
//                intent.putExtra("tempDay", tempDay);
//                intent.putExtra("tempNight", tempNight);
//                getActivity().startActivity(intent);
//            }
//        });
    public interface onlisteneritem {
        void onItemClick(String city, String district);
    }

    private Home_Frag.onlisteneritem onlisteneritem;

    //定义回调方法
    public void setItemOnClickInterface(Home_Frag.onlisteneritem onlisteneritem) {
        this.onlisteneritem = onlisteneritem;
    }
}
