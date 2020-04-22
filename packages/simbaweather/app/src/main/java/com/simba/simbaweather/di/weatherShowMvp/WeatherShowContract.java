package com.simba.simbaweather.di.weatherShowMvp;

import com.lzy.okgo.model.Response;
import com.simba.simbaweather.data.bean.WeaTher;

/**
 * @author wzy
 * @description:天气首页契约
 * @date :2020/4/10 10:34
 */
public interface WeatherShowContract {
    public interface IWeatherShowView {
        public void WeatherShowData(Response<WeaTher.DataBean> response);


    }

    public interface IWeatherShowPresenter {
        public void Attech();

        public void Deatch();
    }

    public interface IWeatherShowModel {
        public void RequestDetailsData(String lat, String lng, WeatherShowBack weatherShowBack);

        public interface WeatherShowBack {
            void getDetailsData(Response<WeaTher.DataBean> response);
        }
    }
}
