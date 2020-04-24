package com.simba.base.dialog.model;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/24
 * @Desc :
 */
public class KeyValueBean implements KeyValue {
    private String value;

    public KeyValueBean() {
    }

    public KeyValueBean(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getHint() {
        return null;
    }
}
