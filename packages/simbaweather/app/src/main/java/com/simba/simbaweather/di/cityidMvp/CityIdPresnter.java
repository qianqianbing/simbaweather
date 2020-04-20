package com.simba.simbaweather.di.cityidMvp;

import com.lzy.okgo.model.Response;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.data.bean.CityplanningBean;
import com.simba.simbaweather.ui.base.BasePresenter;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/20 10:28
 */
public class CityIdPresnter<V extends CityIdContract.ICityIdView> extends BasePresenter<V> {

    private final CityIdModel cityIdModel;

    public CityIdPresnter() {
        cityIdModel = new CityIdModel();
    }
    public void RequestCityData(String cityid){
       cityIdModel.RequestCityIdData(cityid, new CityIdContract.ICityIdModel.CityIdBack() {
           @Override
           public void getCityIdData(Response<CitySearchBean.DataBean> response) {
               getView().CityIdData(response);
           }
       });
    }
}
