package com.simba.violationenquiry.net.model;


import com.simba.base.dialog.model.KeyValue;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :车辆信息
 */
public class CarInfo implements Serializable, KeyValue {
    /**
     * 创建时间
     */
    private String createdate;
    /**
     * 设备ID
     */
    private String deviceid;
    private String engineno;
    private String id;
    private String plateno;
    private String vin;

    public CarInfo() {
    }

    /**
     * @param deviceid
     * @param engineno
     * @param plateno
     * @param vin
     */
    public CarInfo(String deviceid, String engineno, String plateno, String vin) {
        this.deviceid = deviceid;
        this.engineno = engineno;
        this.plateno = plateno;
        this.vin = vin;
    }

    public CarInfo(String deviceid, String engineno, String id, String plateno, String vin) {

        this.deviceid = deviceid;
        this.engineno = engineno;
        this.id = id;
        this.plateno = plateno;
        this.vin = vin;
    }

    public CarInfo(String plateno) {
        this.plateno = plateno;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getEngineno() {
        return engineno;
    }

    public void setEngineno(String engineno) {
        this.engineno = engineno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String getKey() {
        return getId();
    }

    @Override
    public String getValue() {
        return getPlateno();
    }

    @Override
    public String getHint() {
        return null;
    }
}
