package com.simba.violationenquiry.utils;

import com.simba.base.utils.ACache;
import com.simba.violationenquiry.MyApplication;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.net.model.detail.ViolateResData;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/14
 * @Desc :缓存
 */
public class CacheHelper {
    /**
     * 缓存时间
     */
    public static final int CACHE_TIME = 6 * 60 * 60;

    public static final String CACHE_CAR_LIST = "CACHE_CAR_LIST";

    /**
     * @param carID
     * @param resData
     */
    public static void saveCarInfoDetail(String carID, ViolateResData resData) {
        ACache.get(MyApplication.sContext).put(carID, resData);
    }

    /**
     * @param carID
     * @return
     */
    public static ViolateResData getCarInfoDetail(String carID) {
        return (ViolateResData) ACache.get(MyApplication.sContext).getAsObject(carID);
    }


    public static void saveCarInfoList(ArrayList<CarInfo> carInfoList) {
        ACache.get(MyApplication.sContext).put(CACHE_CAR_LIST, carInfoList);
    }

    /**
     * @return
     */
    public static List<CarInfo> getCarInfoList() {
        return (List<CarInfo>) ACache.get(MyApplication.sContext).getAsObject(CACHE_CAR_LIST);
    }

    public static void clear() {
        ACache.get(MyApplication.sContext).clear();
    }

    public static void saveIsFirst(boolean isFirst) {
        ACache.get(MyApplication.sContext).put("IS_FIRST", String.valueOf(isFirst));
    }

    public static boolean getIsFirst() {
        String value = ACache.get(MyApplication.sContext).getAsString("IS_FIRST");
        return value == null || "true".equalsIgnoreCase(value);
    }

    public static void saveMustRefresh(String id, boolean mustRefresh) {
        ACache.get(MyApplication.sContext, "MUST_REFRESH").put(id, String.valueOf(mustRefresh));
    }

    public static boolean getMustRefresh(String id) {
        String value = ACache.get(MyApplication.sContext, "MUST_REFRESH").getAsString(id);
        return value == null || "true".equalsIgnoreCase(value);
    }

    public static void clearMustRefresh() {
        ACache.get(MyApplication.sContext, "MUST_REFRESH").clear();
    }
}
