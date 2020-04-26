package com.simba.simbaweather;


import com.simba.simbaweather.data.bean.WeatherBean;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: luojunjie
 * @createDate: 2020/4/19 16:04
 */

// 消息增删的view
public interface ICityChangeView {

    void onCityChange(List<CityInfoManager.CityManagerBean> cityManagerBeanList, Map<Integer, WeatherBean> weatherBeanList);

}
