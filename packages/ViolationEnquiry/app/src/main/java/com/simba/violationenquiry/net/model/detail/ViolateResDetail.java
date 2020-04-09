/**
 * Copyright 2020 bejson.com
 */
package com.simba.violationenquiry.net.model.detail;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class ViolateResDetail implements Serializable {

    private String citycode;
    private String collectunit;
    private String decisionno;
    private String handleno;
    private String handletag;
    private String handletagval;
    private String monitorno;
    private String onlineprocess;
    private String onlineprocessval;
    private String violateaddress;
    private String violateamount;
    private String violatecity;
    private String violatecode;
    private String violatemotion;
    private String violatescore;
    private String violatetime;

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCollectunit() {
        return collectunit;
    }

    public void setCollectunit(String collectunit) {
        this.collectunit = collectunit;
    }

    public String getDecisionno() {
        return decisionno;
    }

    public void setDecisionno(String decisionno) {
        this.decisionno = decisionno;
    }

    public String getHandleno() {
        return handleno;
    }

    public void setHandleno(String handleno) {
        this.handleno = handleno;
    }

    public String getHandletag() {
        return handletag;
    }

    public void setHandletag(String handletag) {
        this.handletag = handletag;
    }

    public String getHandletagval() {
        return handletagval;
    }

    public void setHandletagval(String handletagval) {
        this.handletagval = handletagval;
    }

    public String getMonitorno() {
        return monitorno;
    }

    public void setMonitorno(String monitorno) {
        this.monitorno = monitorno;
    }

    public String getOnlineprocess() {
        return onlineprocess;
    }

    public void setOnlineprocess(String onlineprocess) {
        this.onlineprocess = onlineprocess;
    }

    public String getOnlineprocessval() {
        return onlineprocessval;
    }

    public void setOnlineprocessval(String onlineprocessval) {
        this.onlineprocessval = onlineprocessval;
    }

    public String getViolateaddress() {
        return violateaddress;
    }

    public void setViolateaddress(String violateaddress) {
        this.violateaddress = violateaddress;
    }

    public String getViolateamount() {
        return violateamount;
    }

    public void setViolateamount(String violateamount) {
        this.violateamount = violateamount;
    }

    public String getViolatecity() {
        return violatecity;
    }

    public void setViolatecity(String violatecity) {
        this.violatecity = violatecity;
    }

    public String getViolatecode() {
        return violatecode;
    }

    public void setViolatecode(String violatecode) {
        this.violatecode = violatecode;
    }

    public String getViolatemotion() {
        return violatemotion;
    }

    public void setViolatemotion(String violatemotion) {
        this.violatemotion = violatemotion;
    }

    public String getViolatescore() {
        return violatescore;
    }

    public void setViolatescore(String violatescore) {
        this.violatescore = violatescore;
    }

    public String getViolatetime() {
        return violatetime;
    }

    public void setViolatetime(String violatetime) {
        this.violatetime = violatetime;
    }
}