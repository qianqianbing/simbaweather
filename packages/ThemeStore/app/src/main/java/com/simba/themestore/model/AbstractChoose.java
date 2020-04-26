package com.simba.themestore.model;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/26
 * @Desc :
 */
public abstract class AbstractChoose {
    protected boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
