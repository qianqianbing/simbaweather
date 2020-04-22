package com.simba.themestore.model.personal;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public class PersonalThemeBean implements Serializable {
    private boolean isChecked = false;

    public PersonalThemeBean(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public PersonalThemeBean() {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
