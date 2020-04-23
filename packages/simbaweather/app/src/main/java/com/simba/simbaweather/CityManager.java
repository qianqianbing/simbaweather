package com.simba.simbaweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simba.simbaweather.data.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class CityManager {
    private static String TAG = "CityManager";
    SharedPreferences sp;
    List<ICityChangeView> iCityChangeViewList;
    List<CityManagerBean> cityList ;
    private static CityManager cityManager;
    private static String CITY_STATE = "cityState";

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

       sp = MyApplication.getMyApplication().getSharedPreferences("mysp", Context.MODE_PRIVATE);

       cityList = getDataList(CITY_STATE);
       if(cityList == null || cityList.size() == 0){
           cityList.add(new CityManagerBean(true, -1,""));
           setDataList(CITY_STATE, cityList);
       }

        List<CityManagerBean> getList = getDataList(CITY_STATE);
        for(CityManagerBean cityBean :getList){
            Log.e(TAG,"city id  " + cityBean.getCityId() + " city name " + cityBean.getCityName());
        }
    }

    public List<CityManagerBean> getCityList(){
        return cityList;
    }

    public void updateCityState(boolean isAdd, int cityId ){
        //增加城市
        if(isAdd){
            boolean isContained = false;
            for(CityManagerBean cityBean :cityList){
                if(cityBean.getCityId() == cityId){
                    isContained = true;
                    break;
                }
            }
            if(!isContained){
                cityList.add(new CityManagerBean(false, cityId, ""));
            }

        }else {
            boolean isContained = false;
            for(CityManagerBean cityBean :cityList){
                if(cityBean.getCityId() == cityId){
                    isContained = true;
                    cityList.remove(cityBean);
                    break;
                }
            }

        }
        setDataList(CITY_STATE, cityList);

        for (ICityChangeView iCityChangeView : iCityChangeViewList){
            iCityChangeView.onCityChange(cityList);
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

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public  void setDataList(String tag, List<CityManagerBean> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(tag,strJson).commit();
    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public  List<CityManagerBean> getDataList(String tag) {
        List<CityManagerBean> datalist=new ArrayList<CityManagerBean>();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<CityManagerBean>>() {
        }.getType());
        return datalist;

    }


    public class CityManagerBean {
        //是否是定位城市
        boolean isLocationCity;
        //城市id
        int cityId;
        //城市名
        String cityName;

        public CityManagerBean(boolean isLocationCity, int cityId, String cityName) {
            this.isLocationCity = isLocationCity;
            this.cityId = cityId;
            this.cityName = cityName;
        }

        public boolean isLocationCity() {
            return isLocationCity;
        }

        public void setLocationCity(boolean locationCity) {
            isLocationCity = locationCity;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
}
