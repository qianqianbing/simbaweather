package com.simba.themestore.model.personal;

import com.simba.themestore.model.AbstractChoose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
@Entity(nameInDb = "WallPaperDTO")
public class PersonalWallPaperBean extends AbstractChoose   {

    @Id(autoincrement = true)
    private long id;

    @Generated(hash = 1448019806)
    public PersonalWallPaperBean(long id) {
        this.id = id;
    }

    @Generated(hash = 504386714)
    public PersonalWallPaperBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
