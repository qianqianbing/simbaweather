/**
 * Copyright 2020 bejson.com
 */
package com.simba.violationenquiry.net.model.detail;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class ViolateResData {

    private String amountsum;
    private String code;
    private String cph;
    private String msg;
    private String scoresum;
    private List<ViolateResDetail> violateResDataList;
    private String violatesum;

    public void setAmountsum(String amountsum) {
        this.amountsum = amountsum;
    }

    public String getAmountsum() {
        return amountsum;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getCph() {
        return cph;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setScoresum(String scoresum) {
        this.scoresum = scoresum;
    }

    public String getScoresum() {
        return scoresum;
    }

    public void setViolateResDataList(List<ViolateResDetail> violateResDataList) {
        this.violateResDataList = violateResDataList;
    }

    public List<ViolateResDetail> getViolateResDataList() {
        return violateResDataList;
    }

    public void setViolatesum(String violatesum) {
        this.violatesum = violatesum;
    }

    public String getViolatesum() {
        return violatesum;
    }

}