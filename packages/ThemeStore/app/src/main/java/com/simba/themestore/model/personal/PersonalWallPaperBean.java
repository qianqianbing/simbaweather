package com.simba.themestore.model.personal;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public class PersonalWallPaperBean implements Serializable {
    private boolean isChecked = false;

    public PersonalWallPaperBean(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public PersonalWallPaperBean() {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


}
