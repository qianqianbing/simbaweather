package com.simba.themestore.model.theme;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/29
 * @Desc :
 */
public class ThemeMainList {
   private List<ThemeMainListBean> data;

    public ThemeMainList(List<ThemeMainListBean> data) {
        this.data = data;
    }

    public List<ThemeMainListBean> getData() {
        return data;
    }

    public void setData(List<ThemeMainListBean> data) {
        this.data = data;
    }
}
