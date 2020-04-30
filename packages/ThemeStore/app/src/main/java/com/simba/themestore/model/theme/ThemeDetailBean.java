/**
 * Copyright 2020 bejson.com
 */
package com.simba.themestore.model.theme;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/26
 * @Desc :
 */
public class ThemeDetailBean implements Serializable {

    private String id;
    private String typeid;
    private String title;
    private String coverurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    @Override
    public String toString() {
        return "ThemeDetailBean{" +
                "id='" + id + '\'' +
                ", typeid='" + typeid + '\'' +
                ", title='" + title + '\'' +
                ", coverurl='" + coverurl + '\'' +
                '}';
    }
}