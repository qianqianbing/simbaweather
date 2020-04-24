package com.simba.membercenter.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by luojunjie on 2020/4/16.
 * 用来存储设备的激活和登陆状态，不完善可能修改
 */
@Entity
public class DeviceStateBean {

    @Id(autoincrement = true)
    private Long id;

    //设备deviceID
    @Property(nameInDb = "DEVICE_ID")
    private int deviceId;

    @Property(nameInDb = "ACTIVATION_STATE")
    private Boolean activationState;

    @Keep
    public DeviceStateBean(int deviceId) {
        this.deviceId = deviceId;
        activationState = false;
    }

    @Generated(hash = 459209631)
    public DeviceStateBean(Long id, int deviceId, Boolean activationState) {
        this.id = id;
        this.deviceId = deviceId;
        this.activationState = activationState;
    }

    @Generated(hash = 1094771735)
    public DeviceStateBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean getActivationState() {
        return activationState;
    }

    public void setActivationState(Boolean activationState) {
        this.activationState = activationState;
    }

}
