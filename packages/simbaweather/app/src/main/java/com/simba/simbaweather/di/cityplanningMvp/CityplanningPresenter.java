package com.simba.simbaweather.di.cityplanningMvp;

import com.lzy.okgo.model.Response;
import com.simba.simbaweather.data.bean.CityInfo;
import com.simba.simbaweather.ui.base.BasePresenter;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/14 16:06
 */
public class CityplanningPresenter<V extends CityplanningContract.ICityplanningShowView> extends BasePresenter<V> {

    private final CityplanningShowModel cityplanningShowModel;

    public CityplanningPresenter() {
        cityplanningShowModel = new CityplanningShowModel();
    }

    public void RequestCityPlnningData() {
        cityplanningShowModel.RequestCityplanningData(new CityplanningContract.ICityplanningShowModel.CityplanningShowBack() {
            @Override
            public void getCityplanningData(Response<List<CityInfo>> response) {
                getView().CityplanningShowData(response);
            }
        });
    }

    public void RequestSearchData(String searchValue) {
        cityplanningShowModel.RequestSearchgData(searchValue, new CityplanningContract.ICityplanningShowModel.SearchShowBack() {
            @Override
            public void getSearchData(Response<List<CityInfo>> response) {

            }
        });
    }
}
