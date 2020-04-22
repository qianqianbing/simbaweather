package com.simba.simbaweather.di.weatherShowMvp;

import com.lzy.okgo.model.Response;
import com.simba.simbaweather.data.bean.WeaTher;
import com.simba.simbaweather.ui.base.BasePresenter;

/**
 * @author wzy
 * @description:
 * @date :2020/4/10 10:37
 */
public class WeatherShowPresenter<V extends WeatherShowContract.IWeatherShowView> extends BasePresenter<V> {

    private final WeatherShowModel weatherShowModel;

    public WeatherShowPresenter() {
        weatherShowModel = new WeatherShowModel();
    }

    public void WeathershowRequestData(String lat, String lng) {
        weatherShowModel.RequestDetailsData(lat, lng, new WeatherShowContract.IWeatherShowModel.WeatherShowBack() {
            @Override
            public void getDetailsData(Response<WeaTher.DataBean> response) {
                getView().WeatherShowData(response);
            }
        });
    }
}
