package com.simba.membercenter.accountDB;

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


    @Property(nameInDb = "LODIN_STATE")
    private Boolean loginState;

    @Property(nameInDb = "LODIN_ID")
    private int loginId;

    @Property(nameInDb = "REAL_NAME_STATE")
    private Boolean realNameState;

    @Keep
    public DeviceStateBean(int deviceId) {
        this.deviceId = deviceId;
        activationState = false;
        loginState = false;
        loginId = -1;
        realNameState = false;
    }

    @Generated(hash = 760690140)
    public DeviceStateBean(Long id, int deviceId, Boolean activationState,
            Boolean loginState, int loginId, Boolean realNameState) {
        this.id = id;
        this.deviceId = deviceId;
        this.activationState = activationState;
        this.loginState = loginState;
        this.loginId = loginId;
        this.realNameState = realNameState;
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

    public Boolean getLoginState() {
        return loginState;
    }

    public void setLoginState(Boolean loginState) {
        this.loginState = loginState;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public Boolean getRealNameState() {
        return realNameState;
    }

    public void setRealNameState(Boolean realNameState) {
        this.realNameState = realNameState;
    }
}
