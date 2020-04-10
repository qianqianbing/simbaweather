package com.simba.violationenquiry.event;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/9
 * @Desc : 添加成功返回msg
 */
public class AddCarInfoEvent implements Serializable {
    public boolean success;

    public AddCarInfoEvent(boolean success) {
        this.success = success;
    }
}
