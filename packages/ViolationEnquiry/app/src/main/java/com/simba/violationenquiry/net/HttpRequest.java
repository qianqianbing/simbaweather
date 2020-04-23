package com.simba.violationenquiry.net;

import android.content.Context;
import android.util.SparseBooleanArray;

import com.google.gson.reflect.TypeToken;
import com.simba.base.network.OkGoUtil;
import com.simba.base.network.SimbaUrl;
import com.simba.base.network.model.GeneralResponse;
import com.simba.base.network.model.SimpleResponse;
import com.simba.base.network.utils.Convert;
import com.simba.violationenquiry.MyApplication;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.net.model.detail.ViolateResData;
import com.simba.violationenquiry.utils.CacheHelper;
import com.simba.violationenquiry.utils.DataTest;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc :网络请求Utils
 */
public class HttpRequest {
    public static final String IS_CACHE = "IS_CACHE";

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
                Thread.sleep(2000);
                // callBack.onDataLoadedFailure(new Exception());
                callBack.onLoaded(DataTest.getCarInfoList());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        Type type = new TypeToken<GeneralResponse<List<CarInfo>>>() {
        }.getType();
        OkGoUtil<GeneralResponse<List<CarInfo>>> communicator = new OkGoUtil<>(cxt, SimbaUrl.REQUEST_CAR_LIST);
        try {
            GeneralResponse<List<CarInfo>> response = communicator.post(deviceID, type);
            if (response.data != null) {
                CacheHelper.saveCarInfoList((ArrayList<CarInfo>) response.data);
            }
            callBack.onLoaded(response.data);
        } catch (Exception e) {
            e.printStackTrace();
            List<CarInfo> carInfoList = CacheHelper.getCarInfoList();
            if (carInfoList != null) {
                callBack.onLoaded(carInfoList);
            } else {
                callBack.onDataLoadedFailure(e);
            }
        }

    }

    /**
     * 获取车辆违章详情
     *
     * @param callBack
     * @param cxt
     * @param carInfo
     * @param mustRefresh 是否必须刷新
     */
    public static void getDetail(ResultCallBack<ViolateResData> callBack, Context cxt, CarInfo carInfo, boolean mustRefresh) {
        if (MyApplication.isDebug) {
            try {
                Thread.sleep(2000);
                callBack.onDataLoadedFailure(new Exception());
                //  callBack.onLoaded(DataTest.getDetail());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        if (!mustRefresh) {//不是必须刷新 先取缓存

            ViolateResData violateResData = CacheHelper.getCarInfoDetail(carInfo.getId());
            if (violateResData != null) {
                if (violateResData.isCache()) {//是否是请求之后的null
                    callBack.onDataLoadedFailure(new Exception(IS_CACHE));
                } else {
                    callBack.onLoaded(violateResData);
                }
                return;
            }
        }
        Type type = new TypeToken<GeneralResponse<ViolateResData>>() {
        }.getType();
        OkGoUtil<GeneralResponse<ViolateResData>> communicator = new OkGoUtil<>(cxt, SimbaUrl.REQUEST_CAR_DETAIL);
        String value = Convert.toJson(carInfo);
        try {
            GeneralResponse<ViolateResData> response = communicator.post(value, type);
            //更新缓存

            if (response.data == null) {//没有查到数据，缓存一个标记位
                CacheHelper.saveCarInfoDetail(carInfo.getId(), new ViolateResData(true));
                callBack.onDataLoadedFailure(new Exception(""));
                return;
            }
            CacheHelper.saveCarInfoDetail(carInfo.getId(), response.data);
            callBack.onLoaded(response.data);
        } catch (Exception e) {//没有查到数据，缓存一个标记位
            e.printStackTrace();
            CacheHelper.saveCarInfoDetail(carInfo.getId(), new ViolateResData(true));
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
        Type type = new TypeToken<SimpleResponse>() {
        }.getType();
        OkGoUtil<SimpleResponse> communicator = new OkGoUtil<>(cxt, SimbaUrl.REQUEST_ADD_CAR_INFO);
        String value = Convert.toJson(carInfo);
        try {
            SimpleResponse response = communicator.post(value, type);
            callBack.onLoaded(response);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }
    }

    public static void delete(ResultCallBack<SimpleResponse> callBack, Context cxt, SparseBooleanArray checkedItemPositions, List<CarInfo> carInfoList) throws Exception {
        Type type = new TypeToken<SimpleResponse>() {
        }.getType();
        OkGoUtil<SimpleResponse> communicator = new OkGoUtil<>(cxt, SimbaUrl.REQUEST_DELETE_CAR);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0, j = carInfoList.size(); i < j; i++) {
            if (checkedItemPositions.get(i)) {
                jsonArray.put(carInfoList.get(i).getId());
            }
        }
        try {
            SimpleResponse response = communicator.post(jsonArray.toString(), type);
            callBack.onLoaded(response);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }
    }
}
