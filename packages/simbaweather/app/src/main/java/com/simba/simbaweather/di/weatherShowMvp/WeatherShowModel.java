package com.simba.simbaweather.di.weatherShowMvp;

import androidx.fragment.app.Fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.simbaweather.data.bean.WeaTher;
import com.simba.simbaweather.ui.activity.frag.Home_Frag;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author wzy
 * @description:
 * @date :2020/4/10 10:37
 */
public class WeatherShowModel implements WeatherShowContract.IWeatherShowModel {
private Home_Frag fragment=new Home_Frag();
    @Override
    public void RequestDetailsData(String lat, String lng, final WeatherShowBack weatherShowBack) {
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
                .tag(fragment)
                .upJson(jsonObject)
                .execute(new JsonCallback<WeaTher.DataBean>() {

                    private WeaTher.DataBean data;

                    @Override
                    public void onSuccess(Response<WeaTher.DataBean> response) {
                        if (isCode200()) {
                            weatherShowBack.getDetailsData(response);
                        }
                    }
                });

    }
}
