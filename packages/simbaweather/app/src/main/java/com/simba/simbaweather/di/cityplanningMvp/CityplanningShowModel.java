package com.simba.simbaweather.di.cityplanningMvp;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.network.JsonCallback;
import com.simba.simbaweather.data.bean.CityplanningBean;
import com.simba.simbaweather.data.bean.SearchBean;

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

        OkGo.<List<CityplanningBean.DataBean>>post("http://cp.simbalink.cn/backend/weather/recommendCityList")
                .tag(this)
                .execute(new JsonCallback<List<CityplanningBean.DataBean>>() {
                    @Override
                    public void onSuccess(Response<List<CityplanningBean.DataBean>> response) {

                        cityplanningShowBack.getCityplanningData(response);
                        Log.i("111223", "onSuccess: "+response);
                    }

                });
    }

    @Override
    public void RequestSearchgData(String searchValue, SearchShowBack searchShowBack) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("searchValue",searchValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<List<SearchBean.DataBean>>post("http://cp.simbalink.cn/backend/weather/matchingCity")
                .upJson(jsonObject)
                .execute(new JsonCallback<List<SearchBean.DataBean>>() {
                    @Override
                    public void onSuccess(Response<List<SearchBean.DataBean>> response) {
                        searchShowBack.getSearchData(response);
                    }
                });
    }



}
