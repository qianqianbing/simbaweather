package com.simba.simbaweather;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.network.ConstantDefine;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.data.bean.SearchBean;
import com.simba.simbaweather.data.bean.WeaTher;
import com.simba.simbaweather.di.cityidMvp.CityIdContract;
import com.simba.simbaweather.di.weatherShowMvp.WeatherShowContract;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.simba.base.network.SimbaUrl.ACCOUNT_LOGIN;
import static com.simba.base.network.SimbaUrl.ACCOUNT_USER_INFO;

public class HttpRequest {
    private static String TAG = "HttpRequest";
    private static HttpRequest httpRequest;

    public synchronized static HttpRequest getIntance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    public HttpRequest() {

    }
    public interface WeatherHandler{
        void handleWeatherResult(Response<WeaTher.DataBean> response);
    }


    public void requestWeatherDataByLocation(Activity mContext, String lat, String lng, final WeatherHandler weatherHandler) {
        //gps定位获取经纬度发给后台拿到当地天气的数据

        JSONObject jsonObject = new JSONObject();
        try {
            System.out.println("lat" + lat + ", lng" + lng);
            jsonObject.put("lat", lat);
            jsonObject.put("lng", lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<WeaTher.DataBean>post(SimbaUrl.WEATHER_GET_INDEX_LOCATION)
                .tag(mContext)
                .upJson(jsonObject)
                .execute(new JsonCallback<WeaTher.DataBean>() {

                    private WeaTher.DataBean data;

                    @Override
                    public void onSuccess(Response<WeaTher.DataBean> response) {
                        if (isCode200()) {
                            weatherHandler.handleWeatherResult(response);
                        }
                    }
                });
    }
    public void requestWeatherDataByCityId(Activity mContext, int cityId, final WeatherHandler weatherHandler) {
        //gps定位获取经纬度发给后台拿到当地天气的数据
        JSONObject jsonObject = new JSONObject();


            try {
                jsonObject.put("cityid", ""+cityId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkGo.<WeaTher.DataBean>post(SimbaUrl.WEATHER_GET_WEATHER_INDEXCITY)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new JsonCallback<WeaTher.DataBean>() {
                        @Override
                        public void onSuccess(Response<WeaTher.DataBean> response) {
                            if (isCode200()) {
                                weatherHandler.handleWeatherResult(response);
                            }
                        }
                    });
        }

}
