package com.simba.simbaweather.di.cityplanningMvp;

import com.lzy.okgo.model.Response;
import com.simba.simbaweather.data.bean.CityplanningBean;
import com.simba.simbaweather.data.bean.SearchBean;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/14 15:56
 */
public interface CityplanningContract {
    public interface ICityplanningShowView {
        public void CityplanningShowData(Response<List<CityplanningBean.DataBean>> response);
        public void SearchShowData(Response<List<SearchBean.DataBean>> response);
    }

    public interface ICityplanningShowPresenter {
        public void Attech();

        public void Deatch();
    }

    public interface ICityplanningShowModel {
        public void RequestCityplanningData(CityplanningShowBack cityplanningShowBack);

        public interface CityplanningShowBack {
            void getCityplanningData(Response<List<CityplanningBean.DataBean>> response);
        }
        public void RequestSearchgData(String searchValue,SearchShowBack searchShowBack);

        public interface SearchShowBack {
            void getSearchData(Response<List<SearchBean.DataBean>> response);
        }
    }
}
