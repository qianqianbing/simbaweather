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
@Entity(nameInDb = "ThemeDTO")
public class PersonalThemeBean extends AbstractChoose  {


    @Id(autoincrement = true)
    private long id;

    private String name;

    @Generated(hash = 24642812)
    public PersonalThemeBean(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1900821006)
    public PersonalThemeBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
