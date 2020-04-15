package com.simba.membercenter.accountDB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by admin on 2018/5/14.
 */
@Entity
public class AccountBean {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "USERID")
    private Long userId;


    @Property(nameInDb = "NICK_NAME")
    private String nickName;


    @Property(nameInDb = "IS_LOGINED")
    private Boolean isLogined;


    @Keep
    public AccountBean(Long userId, String nickName,
                       Boolean isLogined) {
        this.userId = userId;
        this.nickName = nickName;
        this.isLogined = isLogined;
    }

    @Keep
    public AccountBean() {
    }

    @Generated(hash = 864824447)
    public AccountBean(Long id, Long userId, String nickName, Boolean isLogined) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.isLogined = isLogined;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getLogined() {
        return isLogined;
    }

    public void setLogined(Boolean logined) {
        isLogined = logined;
    }

    public Boolean getIsLogined() {
        return this.isLogined;
    }

    public void setIsLogined(Boolean isLogined) {
        this.isLogined = isLogined;
    }
}
