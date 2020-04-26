package com.simba.themestore.model.personal;

import com.simba.themestore.model.AbstractChoose;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public class PersonalThemeBean extends AbstractChoose implements Serializable {

    public PersonalThemeBean(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public PersonalThemeBean() {
    }

}
