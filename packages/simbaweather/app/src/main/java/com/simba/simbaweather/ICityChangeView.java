package com.simba.simbaweather;


import java.util.List;

/**
 * @description:
 * @author: luojunjie
 * @createDate: 2020/4/19 16:04
 */

// 消息增删的view
public interface ICityChangeView {

    void onCityChange(List<Integer> cityIdList);

}
