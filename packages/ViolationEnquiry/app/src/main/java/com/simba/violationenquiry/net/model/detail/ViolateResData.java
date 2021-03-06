/**
 * Copyright 2020 bejson.com
 */
package com.simba.violationenquiry.net.model.detail;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class ViolateResData implements Serializable {

    private String amountsum;
    private String code;
    private String cph;
    private String msg;
    private String scoresum;
    private List<ViolateResDetail> violateResDataList;
    private String violatesum;
    private String updatetime;
    /**
     * 是否是从缓存中取出来
     */
    private boolean isCache = false;

    private boolean isShowToast=false;

    public ViolateResData() {
    }

    public boolean isShowToast() {
        return isShowToast;
    }

    public void setShowToast(boolean showToast) {
        isShowToast = showToast;
    }

    public ViolateResData(boolean isCache) {
        this.isCache = isCache;
    }

    public ViolateResData(boolean isCache, boolean isShowToast) {
        this.isCache = isCache;
        this.isShowToast = isShowToast;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public String getAmountsum() {
        return amountsum;
    }

    public void setAmountsum(String amountsum) {
        this.amountsum = amountsum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getScoresum() {
        return scoresum;
    }

    public void setScoresum(String scoresum) {
        this.scoresum = scoresum;
    }

    public List<ViolateResDetail> getViolateResDataList() {
        return violateResDataList;
    }

    public void setViolateResDataList(List<ViolateResDetail> violateResDataList) {
        this.violateResDataList = violateResDataList;
    }

    public String getViolatesum() {
        return violatesum;
    }

    public void setViolatesum(String violatesum) {
        this.violatesum = violatesum;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}