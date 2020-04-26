package com.simba.simbaweather;

import android.app.Activity;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.simbaweather.data.bean.WeatherBean;


import org.json.JSONException;
import org.json.JSONObject;

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
        void handleWeatherResult(int cityId, Response<WeatherBean> response);
    }


    public void requestWeatherDataByLocation(Context mContext, String lat, String lng, final WeatherHandler weatherHandler) {
        //gps定位获取经纬度发给后台拿到当地天气的数据

        JSONObject jsonObject = new JSONObject();
        try {
            System.out.println("lat" + lat + ", lng" + lng);
            jsonObject.put("lat", lat);
            jsonObject.put("lng", lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<WeatherBean>post(SimbaUrl.WEATHER_GET_INDEX_LOCATION)
                .tag(mContext)
                .upJson(jsonObject)
                .execute(new JsonCallback<WeatherBean>() {

                    private WeatherBean data;

                    @Override
                    public void onSuccess(Response<WeatherBean> response) {
                        if (isCode200()) {
                            weatherHandler.handleWeatherResult(0, response);
                        }
                    }
                });
    }


    public void requestWeatherDataByCityId(Context mContext, int cityId, final WeatherHandler weatherHandler) {
        //gps定位获取经纬度发给后台拿到当地天气的数据
        JSONObject jsonObject = new JSONObject();


            try {
                jsonObject.put("cityid", ""+cityId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkGo.<WeatherBean>post(SimbaUrl.WEATHER_GET_WEATHER_INDEXCITY)
                    .tag(mContext)
                    .upJson(jsonObject)
                    .execute(new JsonCallback<WeatherBean>() {
                        @Override
                        public void onSuccess(Response<WeatherBean> response) {
                            if (isCode200()) {
                                weatherHandler.handleWeatherResult(cityId, response);
                            }
                        }
                    });
        }

}
