package com.simba.themestore.model.personal;

import com.simba.themestore.model.AbstractChoose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
@Entity(nameInDb = "PersonalThemeDTO")
public class PersonalThemeBean extends AbstractChoose {


    @Id
    private String id;
    private String typeid;
    private String title;
    private String coverurl;
    private String vipID;

    @Generated(hash = 407168230)
    public PersonalThemeBean(String id, String typeid, String title,
                             String coverurl, String vipID) {
        this.id = id;
        this.typeid = typeid;
        this.title = title;
        this.coverurl = coverurl;
        this.vipID = vipID;
    }

    @Generated(hash = 1900821006)
    public PersonalThemeBean() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeid() {
        return this.typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverurl() {
        return this.coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getVipID() {
        return this.vipID;
    }

    public void setVipID(String vipID) {
        this.vipID = vipID;
    }


}
