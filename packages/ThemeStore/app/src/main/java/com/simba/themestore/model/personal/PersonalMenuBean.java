package com.simba.themestore.model.personal;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class PersonalMenuBean implements Serializable {
    private String title;
    private int bgRes;
    private int iconRes;

    public PersonalMenuBean(String title, int bgRes, int iconRes) {
        this.title = title;
        this.bgRes = bgRes;
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBgRes() {
        return bgRes;
    }

    public void setBgRes(int bgRes) {
        this.bgRes = bgRes;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
