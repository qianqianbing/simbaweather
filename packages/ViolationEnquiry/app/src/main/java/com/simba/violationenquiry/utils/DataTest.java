package com.simba.violationenquiry.utils;

import com.google.gson.reflect.TypeToken;
import com.simba.base.network.model.GeneralResponse;
import com.simba.base.network.utils.Convert;
import com.simba.violationenquiry.net.model.detail.ViolateResData;

import java.lang.reflect.Type;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/9
 * @Desc :
 */
public class DataTest {
    private final static String DETAIL_INFO = "{\"message\":null,\"code\":200,\"data\":{\"code\":\"0\",\"msg\":\"Success\",\"cph\":\"苏A2J6P5\",\"violatesum\":\"3\",\"amountsum\":\"350\",\"scoresum\":\"9\",\"updatetime\":\"2020-04-09 07:04:24\",\"violateResDataList\":[{\"violatetime\":\"2020-03-23 10:51:00\",\"violateaddress\":\"幕云路(幕旭西路至白云新寓小区路段)\",\"violatemotion\":\"机动车违反规定停放，妨碍其他车辆、行人通行的，驾驶人不在现场的\",\"violateamount\":\"50元\",\"violatescore\":\"0\",\"violatecity\":\"江苏\",\"citycode\":\"32\",\"violatecode\":\"10393\",\"collectunit\":\"南京市公安局\",\"decisionno\":\"\",\"monitorno\":\"3201077000069906\",\"handletagval\":\"未处理\",\"handletag\":\"1\",\"handleno\":\"c575c50a45780014625f51341138c87d\",\"onlineprocessval\":\"是\",\"onlineprocess\":\"1\"},{\"violatetime\":\"2020-03-12 01:21:47\",\"violateaddress\":\"湖东路石泉路口\",\"violatemotion\":\"机动车违反禁止标线指示的\",\"violateamount\":\"100元\",\"violatescore\":\"3\",\"violatecity\":\"江苏\",\"citycode\":\"32\",\"violatecode\":\"13451\",\"collectunit\":\"南京市公安局江宁分局\",\"decisionno\":\"\",\"monitorno\":\"3201217001928778\",\"handletagval\":\"未处理\",\"handletag\":\"1\",\"handleno\":null,\"onlineprocessval\":\"否\",\"onlineprocess\":null},{\"violatetime\":\"2019-02-17 11:45:00\",\"violateaddress\":\"天华西路 泰冯路路口\",\"violatemotion\":\"驾驶机动车违反道路交通信号灯通行的,在红灯、红色叉形灯或者箭头灯禁行时机动车继续通行的\",\"violateamount\":\"200元\",\"violatescore\":\"6\",\"violatecity\":\"江苏\",\"citycode\":\"32\",\"violatecode\":\"16252\",\"collectunit\":\"\",\"decisionno\":\"\",\"monitorno\":\"3201290080150900\",\"handletagval\":\"未处理\",\"handletag\":\"1\",\"handleno\":null,\"onlineprocessval\":\"否\",\"onlineprocess\":null}]},\"success\":true}";

    public static ViolateResData getDetail() {
        Type type = new TypeToken<GeneralResponse<ViolateResData>>() {
        }.getType();

        GeneralResponse<ViolateResData> response = Convert.fromJson(DETAIL_INFO, type);
        return response.data;
    }

}
