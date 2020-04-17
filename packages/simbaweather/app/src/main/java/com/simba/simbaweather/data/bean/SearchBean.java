package com.simba.simbaweather.data.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/17 11:12
 */
public class SearchBean {

    /**
     * message : null
     * code : 200
     * data : [{"id":"131497","nation":null,"province":"台湾","city":"台南市","district":"山上區 (Shanshang)"},{"id":"131301","nation":null,"province":"台湾","city":"台东县","district":"池上鄉 (Chihshang)"},{"id":"131215","nation":null,"province":"台湾","city":"嘉义县","district":"水上鄉 (Shueishang)"},{"id":"131120","nation":null,"province":"台湾","city":"Shanglin Village","district":"Shanglin Village"}]
     * success : true
     */

    private Object message;
    private int code;
    private boolean success;
    private List<DataBean> data;


    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 131497
         * nation : null
         * province : 台湾
         * city : 台南市
         * district : 山上區 (Shanshang)
         */

        private String id;
        private Object nation;
        private String province;
        private String city;
        private String district;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getNation() {
            return nation;
        }

        public void setNation(Object nation) {
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
}
