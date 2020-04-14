package com.simba.violationenquiry.utils;

import com.simba.base.utils.ACache;
import com.simba.violationenquiry.MyApplication;
import com.simba.violationenquiry.net.model.detail.ViolateResData;

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

    /**
     * @param carID
     * @param resData
     */
    public static void saveCarInfoDetail(String carID, ViolateResData resData) {
        ACache.get(MyApplication.sContext).put(carID, resData, CACHE_TIME);
    }

    /**
     * @param carID
     * @return
     */
    public static ViolateResData getCarInfoDetail(String carID) {
        return (ViolateResData) ACache.get(MyApplication.sContext).getAsObject(carID);
    }

    public static void clear() {
        ACache.get(MyApplication.sContext).clear();
    }
}
