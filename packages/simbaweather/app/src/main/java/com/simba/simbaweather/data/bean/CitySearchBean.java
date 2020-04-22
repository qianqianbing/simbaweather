package com.simba.simbaweather.data.bean;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/20 10:34
 */
public class CitySearchBean {

    /**
     * code : 0
     * data : {"city":{"city":"string","district":"string","id":"string","nation":"string","province":"string"},"weatherList":[{"aqi":"string","aqiValue":"string","condition":"string","conditionId":"string","date":"string","dayStr":"string","humidity":"string","temp":"string","tempDay":"string","tempNight":"string","uviStatus":"string","washCarStatus":"string","windDir":"string","windLevel":"string"}],"weatherToday":{"aqi":"string","aqiValue":"string","condition":"string","conditionId":"string","date":"string","dayStr":"string","humidity":"string","temp":"string","tempDay":"string","tempNight":"string","uviStatus":"string","washCarStatus":"string","windDir":"string","windLevel":"string"}}
     * message : string
     * success : true
     */

    private int code;
    private DataBean data;
    private String message;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * city : {"city":"string","district":"string","id":"string","nation":"string","province":"string"}
         * weatherList : [{"aqi":"string","aqiValue":"string","condition":"string","conditionId":"string","date":"string","dayStr":"string","humidity":"string","temp":"string","tempDay":"string","tempNight":"string","uviStatus":"string","washCarStatus":"string","windDir":"string","windLevel":"string"}]
         * weatherToday : {"aqi":"string","aqiValue":"string","condition":"string","conditionId":"string","date":"string","dayStr":"string","humidity":"string","temp":"string","tempDay":"string","tempNight":"string","uviStatus":"string","washCarStatus":"string","windDir":"string","windLevel":"string"}
         */

        private CityBean city;
        private WeatherTodayBean weatherToday;
        private List<WeatherListBean> weatherList;


        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public WeatherTodayBean getWeatherToday() {
            return weatherToday;
        }

        public void setWeatherToday(WeatherTodayBean weatherToday) {
            this.weatherToday = weatherToday;
        }

        public List<WeatherListBean> getWeatherList() {
            return weatherList;
        }

        public void setWeatherList(List<WeatherListBean> weatherList) {
            this.weatherList = weatherList;
        }

        public static class CityBean {
            /**
             * city : string
             * district : string
             * id : string
             * nation : string
             * province : string
             */

            private String city;
            private String district;
            private String id;
            private String nation;
            private String province;


            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }
        }

        public static class WeatherTodayBean {
            /**
             * aqi : string
             * aqiValue : string
             * condition : string
             * conditionId : string
             * date : string
             * dayStr : string
             * humidity : string
             * temp : string
             * tempDay : string
             * tempNight : string
             * uviStatus : string
             * washCarStatus : string
             * windDir : string
             * windLevel : string
             */

            private String aqi;
            private String aqiValue;
            private String condition;
            private String conditionId;
            private String date;
            private String dayStr;
            private String humidity;
            private String temp;
            private String tempDay;
            private String tempNight;
            private String uviStatus;
            private String washCarStatus;
            private String windDir;
            private String windLevel;


            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getAqiValue() {
                return aqiValue;
            }

            public void setAqiValue(String aqiValue) {
                this.aqiValue = aqiValue;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public String getConditionId() {
                return conditionId;
            }

            public void setConditionId(String conditionId) {
                this.conditionId = conditionId;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayStr() {
                return dayStr;
            }

            public void setDayStr(String dayStr) {
                this.dayStr = dayStr;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getTempDay() {
                return tempDay;
            }

            public void setTempDay(String tempDay) {
                this.tempDay = tempDay;
            }

            public String getTempNight() {
                return tempNight;
            }

            public void setTempNight(String tempNight) {
                this.tempNight = tempNight;
            }

            public String getUviStatus() {
                return uviStatus;
            }

            public void setUviStatus(String uviStatus) {
                this.uviStatus = uviStatus;
            }

            public String getWashCarStatus() {
                return washCarStatus;
            }

            public void setWashCarStatus(String washCarStatus) {
                this.washCarStatus = washCarStatus;
            }

            public String getWindDir() {
                return windDir;
            }

            public void setWindDir(String windDir) {
                this.windDir = windDir;
            }

            public String getWindLevel() {
                return windLevel;
            }

            public void setWindLevel(String windLevel) {
                this.windLevel = windLevel;
            }
        }

        public static class WeatherListBean {
            /**
             * aqi : string
             * aqiValue : string
             * condition : string
             * conditionId : string
             * date : string
             * dayStr : string
             * humidity : string
             * temp : string
             * tempDay : string
             * tempNight : string
             * uviStatus : string
             * washCarStatus : string
             * windDir : string
             * windLevel : string
             */

            private String aqi;
            private String aqiValue;
            private String condition;
            private String conditionId;
            private String date;
            private String dayStr;
            private String humidity;
            private String temp;
            private String tempDay;
            private String tempNight;
            private String uviStatus;
            private String washCarStatus;
            private String windDir;
            private String windLevel;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getAqiValue() {
                return aqiValue;
            }

            public void setAqiValue(String aqiValue) {
                this.aqiValue = aqiValue;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public String getConditionId() {
                return conditionId;
            }

            public void setConditionId(String conditionId) {
                this.conditionId = conditionId;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayStr() {
                return dayStr;
            }

            public void setDayStr(String dayStr) {
                this.dayStr = dayStr;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getTempDay() {
                return tempDay;
            }

            public void setTempDay(String tempDay) {
                this.tempDay = tempDay;
            }

            public String getTempNight() {
                return tempNight;
            }

            public void setTempNight(String tempNight) {
                this.tempNight = tempNight;
            }

            public String getUviStatus() {
                return uviStatus;
            }

            public void setUviStatus(String uviStatus) {
                this.uviStatus = uviStatus;
            }

            public String getWashCarStatus() {
                return washCarStatus;
            }

            public void setWashCarStatus(String washCarStatus) {
                this.washCarStatus = washCarStatus;
            }

            public String getWindDir() {
                return windDir;
            }

            public void setWindDir(String windDir) {
                this.windDir = windDir;
            }

            public String getWindLevel() {
                return windLevel;
            }

            public void setWindLevel(String windLevel) {
                this.windLevel = windLevel;
            }
        }
    }
}
