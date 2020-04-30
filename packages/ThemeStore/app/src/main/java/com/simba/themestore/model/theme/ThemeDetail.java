package com.simba.themestore.model.theme;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/29
 * @Desc :
 */
public class ThemeDetail {
    private List<ThemeDetailBean> data;

    public ThemeDetail(List<ThemeDetailBean> data) {
        this.data = data;
    }

    public List<ThemeDetailBean> getData() {
        return data;
    }

    public void setData(List<ThemeDetailBean> data) {
        this.data = data;
    }
}
