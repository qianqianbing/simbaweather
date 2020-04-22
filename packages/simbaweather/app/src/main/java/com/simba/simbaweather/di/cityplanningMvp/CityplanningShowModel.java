package com.simba.simbaweather.di.cityplanningMvp;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.simbaweather.data.bean.CityInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/10 10:37
 */
public class CityplanningShowModel implements CityplanningContract.ICityplanningShowModel {


    @Override
    public void RequestCityplanningData(CityplanningShowBack cityplanningShowBack) {

        OkGo.<List<CityInfo>>post(SimbaUrl.WEATHER_GET_WEATHER_RECOMMENDCITYLIST)
                .tag(this)
                .execute(new JsonCallback<List<CityInfo>>() {
                    @Override
                    public void onSuccess(Response<List<CityInfo>> response) {

                        cityplanningShowBack.getCityplanningData(response);
                        Log.i("111223", "onSuccess: " + response);
                    }

                });
    }

    @Override
    public void RequestSearchgData(String searchValue, SearchShowBack searchShowBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("searchValue", searchValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<List<CityInfo>>post(SimbaUrl.WEATHER_GET_WEATHER_MATCHINGCITY)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<List<CityInfo>>() {
                    @Override
                    public void onSuccess(Response<List<CityInfo>> response) {
                        searchShowBack.getSearchData(response);
                    }
                });
    }


}
