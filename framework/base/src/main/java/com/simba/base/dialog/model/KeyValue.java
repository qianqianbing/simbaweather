package com.simba.base.dialog.model;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc : dialog 专用
 */
public interface KeyValue {
    /**
     * 选中返回的值
     *
     * @return
     */
    String getKey();

    /**
     * dialog显示的值
     *
     * @return
     */
    String getValue();

    String getHint();
}
