package com.simba.simbaweather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.simba.base.utils.LocationUtil;
import com.simba.simbaweather.data.MyApplication;
import com.simba.simbaweather.data.bean.WeatherBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityInfoManager implements HttpRequest.WeatherHandler {
    private static String TAG = "CityManager";
    SharedPreferences sp;
    List<ICityChangeView> iCityChangeViewList;
    List<CityManagerBean> cityList;
    Map<Integer, WeatherBean> weatherBeanMap;
    private static CityInfoManager cityManager;
    private static String CITY_STATE = "cityState";

    public static CityInfoManager getInstance() {
        if (cityManager == null) {
            cityManager = new CityInfoManager();
        }
        return cityManager;
    }

    public CityInfoManager() {
        sp = MyApplication.getMyApplication().getSharedPreferences("mysp", Context.MODE_PRIVATE);
        cityList = getDataList(CITY_STATE);
        if (cityList == null || cityList.size() == 0) {
            cityList.add(new CityManagerBean(true, 0, ""));
            setDataList(CITY_STATE, cityList);
        }
        List<CityManagerBean> getList = getDataList(CITY_STATE);
        for (CityManagerBean cityBean : getList) {
            Log.e(TAG, "city id  " + cityBean.getCityId() + " city name " + cityBean.getCityName());
        }
    }

    public List<CityManagerBean> getCityList() {
        return cityList;
    }

    public void updateCityState(boolean isAdd, int cityId) {
        //增加城市
        if (isAdd) {
            boolean isContained = false;
            for (CityManagerBean cityBean : cityList) {
                if (cityBean.getCityId() == cityId) {
                    isContained = true;
                    break;
                }
            }
            if (!isContained) {
                cityList.add(new CityManagerBean(false, cityId, ""));
            }
        } else {
            boolean isContained = false;
            for (CityManagerBean cityBean : cityList) {
                if (cityBean.getCityId() == cityId) {
                    isContained = true;
                    cityList.remove(cityBean);
                    break;
                }
            }
        }
        setDataList(CITY_STATE, cityList);
        requestWeatherInfo();
    }

    public void registerCityChangeView(ICityChangeView iCityChangeView, Activity context) {
        if (iCityChangeViewList == null) {
            iCityChangeViewList = new ArrayList<>();
        }
        if (!iCityChangeViewList.contains(iCityChangeView)) {
            iCityChangeViewList.add(iCityChangeView);
        }
        //如果天气的列表为空或者和城市列表个数不一样，需要请求网络
        Log.e(TAG, "city size is " + cityList.size());
        if (weatherBeanMap == null || weatherBeanMap.size() != cityList.size()) {
            requestWeatherInfo();
        } else {
            iCityChangeView.onCityChange(cityList, weatherBeanMap);
        }
    }

    public void requestWeatherInfo() {
        weatherBeanMap = new HashMap<>();
        if (cityList != null && cityList.size() != 0) {
            for (CityInfoManager.CityManagerBean city : cityList) {
                if (city.isLocationCity()) {
                    Location location = LocationUtil.getInstance(MyApplication.getMyApplication()).getLocationInfo();
                    if (location == null) {
                        HttpRequest.getIntance().requestWeatherDataByLocation(MyApplication.getMyApplication(), "" + 32.298741, "" + 118.840485, this);
                    } else {
                        HttpRequest.getIntance().requestWeatherDataByLocation(MyApplication.getMyApplication(), "" + location.getAltitude(), "" + location.getLongitude(), this);
                    }
                } else {
                    HttpRequest.getIntance().requestWeatherDataByCityId(MyApplication.getMyApplication(), city.getCityId(), this);
                }
            }
        }
    }

    public void unRegisterCityChangeView(ICityChangeView iCityChangeView) {
        if (iCityChangeViewList.contains(iCityChangeView)) {
            iCityChangeViewList.remove(iCityChangeView);
        }
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public void setDataList(String tag, List<CityManagerBean> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(tag, strJson).commit();
    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public List<CityManagerBean> getDataList(String tag) {
        List<CityManagerBean> datalist = new ArrayList<CityManagerBean>();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<CityManagerBean>>() {
        }.getType());
        return datalist;
    }

    @Override
    public synchronized void handleWeatherResult(int cityId, Response<WeatherBean> response) {
        WeatherBean weatherBean = response.body();
        weatherBeanMap.put(cityId, weatherBean);
        Log.e(TAG, "cityId " + cityId + " weatherBeanMap size is " + weatherBeanMap.size());
        //说明所有的请求都已经完成
        if (weatherBeanMap.size() == cityList.size()) {
            for (ICityChangeView iCityChangeView : iCityChangeViewList) {
                iCityChangeView.onCityChange(cityList, weatherBeanMap);
            }
        }
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
