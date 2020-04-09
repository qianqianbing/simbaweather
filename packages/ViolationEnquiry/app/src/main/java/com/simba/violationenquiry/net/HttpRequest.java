package com.simba.violationenquiry.net;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.simba.base.network.OkGoUtil;
import com.simba.base.network.SimbaUrl;
import com.simba.base.network.model.SimpleResponse;
import com.simba.base.network.utils.Convert;
import com.simba.violationenquiry.MyApplication;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.net.model.detail.ViolateResData;
import com.simba.violationenquiry.utils.DataTest;

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
    public static void getCarInfoList(ResultCallBack<List<CarInfo>> callBack, Context cxt, String deviceID) {
        if (MyApplication.isDebug) {
            try {
                Thread.sleep(5000);
                // callBack.onDataLoadedFailure(new Exception());
                callBack.onLoaded(DataTest.getCarInfoList());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        Type type = new TypeToken<List<CarInfo>>() {
        }.getType();
        OkGoUtil<List<CarInfo>> communicator = new OkGoUtil<>(cxt, SimbaUrl.REQUEST_CAR_LIST + deviceID);
        try {
            List<CarInfo> carInfoList = communicator.post(type);
            callBack.onLoaded(carInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }

    }

    /**
     * 获取车辆违章详情
     *
     * @param cxt
     * @param carInfo
     * @return
     * @throws Exception
     */
    public static void getDetail(ResultCallBack<ViolateResData> callBack, Context cxt, CarInfo carInfo) {
        if (MyApplication.isDebug) {
            try {
                Thread.sleep(5000);
                // callBack.onDataLoadedFailure(new Exception());
                callBack.onLoaded(DataTest.getDetail());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        Type type = new TypeToken<ViolateResData>() {
        }.getType();
        OkGoUtil<ViolateResData> communicator = new OkGoUtil<>(cxt, SimbaUrl.REQUEST_CAR_DETAIL);
        String value = Convert.toJson(carInfo);
        try {
            ViolateResData violateResData = communicator.post(value, type);
            callBack.onLoaded(violateResData);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }
    }

    /**
     * 新增车辆信息
     *
     * @param cxt
     * @param carInfo
     * @return
     * @throws Exception
     */
    public static void add(ResultCallBack<SimpleResponse> callBack, Context cxt, CarInfo carInfo) {

        OkGoUtil<SimpleResponse> communicator = new OkGoUtil<>(cxt, SimbaUrl.REQUEST_ADD_CAR_INFO);
        String value = Convert.toJson(carInfo);
        try {
            SimpleResponse response = communicator.post(value);
            callBack.onLoaded(response);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }
    }

    public static void delete(ResultCallBack<SimpleResponse> callBack, Context cxt, String id) throws Exception {
        OkGoUtil<SimpleResponse> communicator = new OkGoUtil<>(cxt, SimbaUrl.REQUEST_DELETE_CAR);
        try {
            SimpleResponse response = communicator.post(id);
            callBack.onLoaded(response);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }
    }
}
