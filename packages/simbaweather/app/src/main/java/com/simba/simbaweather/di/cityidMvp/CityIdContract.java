package com.simba.simbaweather.di.cityidMvp;

import com.lzy.okgo.model.Response;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.data.bean.CityplanningBean;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/20 10:23
 */
public interface CityIdContract {
    public interface ICityIdView {
        public void CityIdData(Response<CitySearchBean.DataBean> response);
    }

    public interface ICityIdPresenter {
        public void Attech();

        public void Deatch();
    }

    public interface ICityIdModel {
        public void RequestCityIdData(String cityid,CityIdBack cityIdBack);

        public interface CityIdBack {
            void getCityIdData(Response<CitySearchBean.DataBean> response);
        }
    }
}
