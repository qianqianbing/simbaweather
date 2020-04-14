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

    public static void saveCarInfoDetail(String carID, ViolateResData resData) {
        ACache.get(MyApplication.sContext).put(carID, resData);
    }

    public static ViolateResData getCarInfoDetail(String carID) {
        return (ViolateResData) ACache.get(MyApplication.sContext).getAsObject(carID);
    }
}
