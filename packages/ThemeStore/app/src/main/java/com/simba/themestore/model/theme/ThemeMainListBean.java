
package com.simba.themestore.model.theme;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/26
 * @Desc :
 */
public class ThemeMainListBean implements Serializable {


    private String categoryID;
    private String categoryName;
    private List<ThemeDetailBean> skinNameList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ThemeDetailBean> getSkinNameList() {
        return skinNameList;
    }

    public void setSkinNameList(List<ThemeDetailBean> skinNameList) {
        this.skinNameList = skinNameList;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "ThemeMainListBean{" +
                "categoryName='" + categoryName + '\'' +
                ", skinNameList=" + skinNameList +
                '}';
    }
}