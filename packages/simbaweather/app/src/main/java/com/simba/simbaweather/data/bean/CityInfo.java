package com.simba.simbaweather.data.bean;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/22 19:44
 * 描    述：城市信息模型
 * 修订历史：
 * ================================================
 */
public class CityInfo {
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
