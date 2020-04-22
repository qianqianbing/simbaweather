package com.simba.simbaweather.di.cityidMvp;

import android.app.Activity;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.data.bean.CityplanningBean;
import com.simba.simbaweather.ui.activity.AddpendActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/20 10:27
 */
public class CityIdModel implements CityIdContract.ICityIdModel {

    private JSONObject jsonObject;
    private AddpendActivity activity=new AddpendActivity();

    @Override
    public void RequestCityIdData(String cityid, CityIdBack cityIdBack) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("cityid", cityid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<CitySearchBean.DataBean>post(SimbaUrl.WEATHER_GET_WEATHER_INDEXCITY)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<CitySearchBean.DataBean>() {
                    @Override
                    public void onSuccess(Response<CitySearchBean.DataBean> response) {
                        cityIdBack.getCityIdData(response);
                    }
                });
    }
}
