package com.simba.simbaweather.data.bean;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/10 10:15
 */
public class WeatherBean {


    /**
     * message : null
     * code : 200
     * data : {"city":{"currentid":"1056","nation":"中国","province":"江苏省","city":"南京市","district":"六合区"},"weatherToday":{"dayStr":"明天","date":"04 / 11","condition":"多云","conditionId":"8","temp":"17","tempDay":"10","tempNight":"4","aqi":"良","aqiValue":"64","windDir":"东风","windLevel":"4","humidity":"62","uviStatus":"弱","washCarStatus":"不宜"},"weatherList":[{"dayStr":"周日","date":"04 / 12","condition":"晴","conditionId":"0","temp":null,"tempDay":"20","tempNight":"6","aqi":null,"aqiValue":null,"windDir":null,"windLevel":null,"humidity":null,"uviStatus":null,"washCarStatus":null},{"dayStr":"周一","date":"04 / 13","condition":"晴","conditionId":"0","temp":null,"tempDay":"19","tempNight":"8","aqi":null,"aqiValue":null,"windDir":null,"windLevel":null,"humidity":null,"uviStatus":null,"washCarStatus":null},{"dayStr":"周二","date":"04 / 14","condition":"晴","conditionId":"0","temp":null,"tempDay":"22","tempNight":"9","aqi":null,"aqiValue":null,"windDir":null,"windLevel":null,"humidity":null,"uviStatus":null,"washCarStatus":null}]}
     * success : true
     */


        /**
         * city : {"currentid":"1056","nation":"中国","province":"江苏省","city":"南京市","district":"六合区"}
         * weatherToday : {"dayStr":"明天","date":"04 / 11","condition":"多云","conditionId":"8","temp":"17","tempDay":"10","tempNight":"4","aqi":"良","aqiValue":"64","windDir":"东风","windLevel":"4","humidity":"62","uviStatus":"弱","washCarStatus":"不宜"}
         * weatherList : [{"dayStr":"周日","date":"04 / 12","condition":"晴","conditionId":"0","temp":null,"tempDay":"20","tempNight":"6","aqi":null,"aqiValue":null,"windDir":null,"windLevel":null,"humidity":null,"uviStatus":null,"washCarStatus":null},{"dayStr":"周一","date":"04 / 13","condition":"晴","conditionId":"0","temp":null,"tempDay":"19","tempNight":"8","aqi":null,"aqiValue":null,"windDir":null,"windLevel":null,"humidity":null,"uviStatus":null,"washCarStatus":null},{"dayStr":"周二","date":"04 / 14","condition":"晴","conditionId":"0","temp":null,"tempDay":"22","tempNight":"9","aqi":null,"aqiValue":null,"windDir":null,"windLevel":null,"humidity":null,"uviStatus":null,"washCarStatus":null}]
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
             * currentid : 1056
             * nation : 中国
             * province : 江苏省
             * city : 南京市
             * district : 六合区
             */

            private String currentid;
            private String nation;
            private String province;
            private String city;
            private String district;
            private String cityid;

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public String getCurrentid() {
                return currentid;
            }

            public void setCurrentid(String currentid) {
                this.currentid = currentid;
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
        }

        public static class WeatherTodayBean {
            /**
             * dayStr : 明天
             * date : 04 / 11
             * condition : 多云
             * conditionId : 8
             * temp : 17
             * tempDay : 10
             * tempNight : 4
             * aqi : 良
             * aqiValue : 64
             * windDir : 东风
             * windLevel : 4
             * humidity : 62
             * uviStatus : 弱
             * washCarStatus : 不宜
             */

            private String dayStr;
            private String date;
            private String condition;
            private String conditionId;
            private String temp;
            private String tempDay;
            private String tempNight;
            private String aqi;
            private String aqiValue;
            private String windDir;
            private String windLevel;
            private String humidity;
            private String uviStatus;
            private String washCarStatus;

            public String getDayStr() {
                return dayStr;
            }

            public void setDayStr(String dayStr) {
                this.dayStr = dayStr;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
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

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
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
        }

        public static class WeatherListBean {
            /**
             * dayStr : 周日
             * date : 04 / 12
             * condition : 晴
             * conditionId : 0
             * temp : null
             * tempDay : 20
             * tempNight : 6
             * aqi : null
             * aqiValue : null
             * windDir : null
             * windLevel : null
             * humidity : null
             * uviStatus : null
             * washCarStatus : null
             */

            private String dayStr;
            private String date;
            private String condition;
            private String conditionId;
            private Object temp;
            private String tempDay;
            private String tempNight;
            private Object aqi;
            private Object aqiValue;
            private Object windDir;
            private Object windLevel;
            private Object humidity;
            private Object uviStatus;
            private Object washCarStatus;

            public String getDayStr() {
                return dayStr;
            }

            public void setDayStr(String dayStr) {
                this.dayStr = dayStr;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
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

            public Object getTemp() {
                return temp;
            }

            public void setTemp(Object temp) {
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

            public Object getAqi() {
                return aqi;
            }

            public void setAqi(Object aqi) {
                this.aqi = aqi;
            }

            public Object getAqiValue() {
                return aqiValue;
            }

            public void setAqiValue(Object aqiValue) {
                this.aqiValue = aqiValue;
            }

            public Object getWindDir() {
                return windDir;
            }

            public void setWindDir(Object windDir) {
                this.windDir = windDir;
            }

            public Object getWindLevel() {
                return windLevel;
            }

            public void setWindLevel(Object windLevel) {
                this.windLevel = windLevel;
            }

            public Object getHumidity() {
                return humidity;
            }

            public void setHumidity(Object humidity) {
                this.humidity = humidity;
            }

            public Object getUviStatus() {
                return uviStatus;
            }

            public void setUviStatus(Object uviStatus) {
                this.uviStatus = uviStatus;
            }

            public Object getWashCarStatus() {
                return washCarStatus;
            }

            public void setWashCarStatus(Object washCarStatus) {
                this.washCarStatus = washCarStatus;
            }
        }

}
