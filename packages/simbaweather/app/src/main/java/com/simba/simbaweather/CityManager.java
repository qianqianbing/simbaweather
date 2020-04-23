package com.simba.simbaweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.simba.base.utils.SpStaticUtils;
import com.simba.simbaweather.data.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class CityManager {
    private static String TAG = "CityManager";
    SharedPreferences sp;
    List<ICityChangeView> iCityChangeViewList;
    List<Integer> cityIdList ;
    private static CityManager cityManager;

    private static String CITY_NUMBER = "cityNumber";
    private static String CITY_1 = "city1";
    private static String CITY_2 = "city2";
    private static String CITY_3 = "city3";
    private static String CITY_4 = "city4";


    public static CityManager getInstance(){
        if (cityManager == null){
            cityManager = new CityManager();
        }
        return cityManager;
    }

    public  CityManager() {
       cityIdList = new ArrayList<>();
       sp = MyApplication.getMyApplication().getSharedPreferences("mysp", Context.MODE_PRIVATE);
       int cityNumber = sp.getInt(CITY_NUMBER, 0);
       Log.e(TAG, "cityNumber is " + cityNumber);
       switch (cityNumber){
           case 0:
                break;
           case 1:
               cityIdList.add(sp.getInt(CITY_1, 0));
               break;
           case 2:
               cityIdList.add(sp.getInt(CITY_1, 0));
               cityIdList.add(sp.getInt(CITY_2, 0));
                break;
           case 3:
               cityIdList.add(sp.getInt(CITY_1, 0));
               cityIdList.add(sp.getInt(CITY_2, 0));
               cityIdList.add(sp.getInt(CITY_3, 0));
                break;
           case 4:
               cityIdList.add(sp.getInt(CITY_1, 0));
               cityIdList.add(sp.getInt(CITY_2, 0));
               cityIdList.add(sp.getInt(CITY_3, 0));
               cityIdList.add(sp.getInt(CITY_4, 0));
                break;
       }
    }

    public List<Integer> getCityIdList(){
        return cityIdList;
    }

    public void updateCityState(boolean isAdd, int cityId ){
        //增加城市
        if(isAdd){
            if(!cityIdList.contains(Integer.valueOf(cityId))){
                cityIdList.add(Integer.valueOf(cityId));
            }
        }else {
            if(cityIdList.contains(Integer.valueOf(cityId))){
                cityIdList.remove(Integer.valueOf(cityId));
            }
        }
        sp.edit().putInt(CITY_NUMBER, cityIdList.size()).commit();
        switch (cityIdList.size()){
            case 0:
                break;
            case 1:
                sp.edit().putInt(CITY_1,cityIdList.get(0)).commit();
                break;
            case 2:
                sp.edit().putInt(CITY_1,cityIdList.get(0)).commit();
                sp.edit().putInt(CITY_2,cityIdList.get(1)).commit();
                break;
            case 3:
                sp.edit().putInt(CITY_1,cityIdList.get(0)).commit();
                sp.edit().putInt(CITY_2,cityIdList.get(1)).commit();
                sp.edit().putInt(CITY_3,cityIdList.get(2)).commit();
                break;
            case 4:
                sp.edit().putInt(CITY_1,cityIdList.get(0)).commit();
                sp.edit().putInt(CITY_2,cityIdList.get(1)).commit();
                sp.edit().putInt(CITY_3,cityIdList.get(2)).commit();
                sp.edit().putInt(CITY_4,cityIdList.get(3)).commit();
                break;
        }
        for (ICityChangeView iCityChangeView : iCityChangeViewList){
            iCityChangeView.onCityChange(cityIdList);
        }
    }


    public void registerCityChangeView(ICityChangeView iCityChangeView){
        if(iCityChangeViewList == null){
            iCityChangeViewList = new ArrayList<>();
        }
        if (!iCityChangeViewList.contains(iCityChangeView)){
            iCityChangeViewList.add(iCityChangeView);
        }
    }

    public void unRegisterCityChangeView(ICityChangeView iCityChangeView){
        if(iCityChangeViewList.contains(iCityChangeView)){
            iCityChangeViewList.remove(iCityChangeView);
        }
    }



}
