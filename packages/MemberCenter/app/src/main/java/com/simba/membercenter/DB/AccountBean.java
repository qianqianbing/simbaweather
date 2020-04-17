package com.simba.membercenter.DB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by admin on 2018/5/14.
 */
@Entity
public class AccountBean {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "USERID")
    private int userId;


    @Property(nameInDb = "NICK_NAME")
    private String nickName;


    @Property(nameInDb = "IS_LOGINED")
    private Boolean isLogined;


    @Keep
    public AccountBean(int userId, String nickName,
                       Boolean isLogined) {
        this.userId = userId;
        this.nickName = nickName;
        this.isLogined = isLogined;
    }

    @Keep
    public AccountBean() {
    }

    @Generated(hash = 1544391652)
    public AccountBean(Long id, int userId, String nickName, Boolean isLogined) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
