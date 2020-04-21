package com.simba.membercenter.DB;

import com.simba.base.dialog.model.KeyValue;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by admin on 2018/5/14.
 */
@Entity
public class AccountBean implements KeyValue {

    @Id
    private Long id;

    @Property(nameInDb = "USER_NAME")
    private String username;

    @Property(nameInDb = "NICK_NAME")
    private String nickname;

    @Property(nameInDb = "IS_LOGINED")
    private Boolean isLogined;

    @Property(nameInDb = "TOKEN")
    private String token;

    @Property(nameInDb = "PHONE")
    private String phone;

    @Property(nameInDb = "OPENID")
    private String openid;

    @Property(nameInDb = "HEADIMGURL")
    private String headimgurl;

    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    @Property(nameInDb = "SEX")
    private int sex;

    //是否为车主：0否 1是
    @Property(nameInDb = "OWNED")
    private int owned;

    @Keep
    public AccountBean(String username, String nickname,
                       Boolean isLogined) {
        this.username = username;
        this.nickname = nickname;
        this.isLogined = isLogined;
    }

    @Keep
    public AccountBean() {
    }

    @Generated(hash = 846407872)
    public AccountBean(Long id, String username, String nickname, Boolean isLogined,
            String token, String phone, String openid, String headimgurl, int sex,
            int owned) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.isLogined = isLogined;
        this.token = token;
        this.phone = phone;
        this.openid = openid;
        this.headimgurl = headimgurl;
        this.sex = sex;
        this.owned = owned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLogined() {
        return isLogined;
    }

    public void setLogined(Boolean logined) {
        isLogined = logined;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getKey() {
        return username;
    }

    @Override
    public String getValue() {
        return nickname;
    }

    @Override
    public String getHint() {
        return null;
    }



    public Boolean getIsLogined() {
        return this.isLogined;
    }

    public void setIsLogined(Boolean isLogined) {
        this.isLogined = isLogined;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getOwned() {
        return owned;
    }

    public void setOwned(int owned) {
        this.owned = owned;
    }
}
