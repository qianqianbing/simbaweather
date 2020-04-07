package com.simba.violationenquiry.net;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.net.model.base.GeneralResponse;
import com.simba.violationenquiry.net.model.base.SimpleResponse;
import com.simba.violationenquiry.net.model.detail.ViolateResData;
import com.simba.violationenquiry.net.utils.Convert;
import com.simba.violationenquiry.net.utils.HttpParameters;
import com.simba.violationenquiry.net.utils.HttpUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc :
 */
public class HttpRequest {
    /**
     * 获取车辆列表
     *
     * @param cxt
     * @param deviceID
     * @return
     * @throws Exception
     */
    public static void getCarInfoList(ResultCallBack<List<CarInfo>> callBack,Context cxt, String deviceID) {
        Type type = new TypeToken<List<CarInfo>>() {
        }.getType();
        HttpUtils<List<CarInfo>> communicator = new HttpUtils<>(cxt, HttpParameters.REQUEST_CAR_LIST + deviceID);
        try {
            List<CarInfo> carInfoList = communicator.get(type);
            callBack.onLoaded(carInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }

    }

    public static GeneralResponse<ViolateResData> getDetail(Context cxt, CarInfo carInfo) throws Exception {
        Type type = new TypeToken<GeneralResponse<ViolateResData>>() {
        }.getType();
        HttpUtils<GeneralResponse<ViolateResData>> communicator = new HttpUtils<>(cxt, HttpParameters.REQUEST_CAR_DETAIL);
        String value = Convert.toJson(carInfo);
        return communicator.post(value, type);
    }

    public static SimpleResponse add(Context cxt, CarInfo carInfo) throws Exception {

        HttpUtils<SimpleResponse> communicator = new HttpUtils<>(cxt, HttpParameters.REQUEST_ADD_CAR_INFO);
        String value = Convert.toJson(carInfo);
        return communicator.post(value);
    }

    public static SimpleResponse delete(Context cxt, String id) throws Exception {
        HttpUtils<SimpleResponse> communicator = new HttpUtils<>(cxt, HttpParameters.REQUEST_DELETE_CAR);
        return communicator.post(id);
    }
}
