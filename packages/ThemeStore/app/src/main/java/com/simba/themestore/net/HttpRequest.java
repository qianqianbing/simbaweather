package com.simba.themestore.net;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.simba.base.network.OkGoUtil;
import com.simba.base.network.SimbaUrl;
import com.simba.base.network.model.GeneralResponse;
import com.simba.base.network.model.callback.ResultCallBack;
import com.simba.themestore.model.PageInfo;
import com.simba.themestore.model.theme.ThemeDetail;
import com.simba.themestore.model.theme.ThemeDetailBean;
import com.simba.themestore.model.theme.ThemeMainList;
import com.simba.themestore.model.theme.ThemeMainListBean;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc :网络请求Utils
 */
public class HttpRequest {


    /**
     * @param callBack
     * @param cxt
     * @param vehicleTypeId
     * @param pageNum
     */
    public static void getMainThemeList(ResultCallBack<ThemeMainList> callBack, Context cxt, String vehicleTypeId, int pageNum) {

        try {
            Thread.sleep(2000);
            callBack.onLoaded(new ThemeMainList(Data.getMainThemeList()));
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Type type = new TypeToken<GeneralResponse<List<ThemeMainListBean>>>() {
            }.getType();
            OkGoUtil<GeneralResponse<List<ThemeMainListBean>>> communicator = new OkGoUtil<>(cxt, SimbaUrl.MAIN_THEME_LIST);
            JSONObject json = new JSONObject();
            json.put("vehicleTypeId", vehicleTypeId);
            json.put("pageNum", pageNum);
            json.put("pageSize", PageInfo.PAGE_SIZE);

            GeneralResponse<List<ThemeMainListBean>> response = communicator.post(json, type);
            callBack.onLoaded(new ThemeMainList(response.data));
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }

    }

    /**
     * @param callBack
     * @param cxt
     * @param vehicleTypeId
     */
    public static void getThemeBannerData(ResultCallBack<ThemeDetail> callBack, Context cxt, String vehicleTypeId) {
        try {
            Thread.sleep(2000);
            callBack.onLoaded(new ThemeDetail(Data.getThemeBannerData()));
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Type type = new TypeToken<GeneralResponse<List<ThemeDetailBean>>>() {
            }.getType();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("vehicleTypeId", vehicleTypeId);
            OkGoUtil<GeneralResponse<List<ThemeDetailBean>>> communicator = new OkGoUtil<>(cxt, SimbaUrl.MAIN_THEME_BANNER);
            GeneralResponse<List<ThemeDetailBean>> response = communicator.post(jsonObject, type);
            callBack.onLoaded(new ThemeDetail(response.data));
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }
    }

    /**
     * @param callBack
     * @param cxt
     * @param typeID
     */
    public static void getThemeTypeList(ResultCallBack<List<ThemeDetailBean>> callBack, Context cxt, String typeID,int pageNum) {
        try {
            Thread.sleep(2000);
            callBack.onLoaded(Data.getThemeBannerData());
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Type type = new TypeToken<GeneralResponse<List<ThemeDetailBean>>>() {
            }.getType();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeid", typeID);
            jsonObject.put("pageNum", pageNum);
            jsonObject.put("pageSize", PageInfo.PAGE_SIZE);
            OkGoUtil<GeneralResponse<List<ThemeDetailBean>>> communicator = new OkGoUtil<>(cxt, SimbaUrl.MAIN_THEME_TYPE_LIST);
            GeneralResponse<List<ThemeDetailBean>> response = communicator.post(jsonObject, type);
            callBack.onLoaded(response.data);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }
    }

    /**
     * @param callBack
     * @param cxt
     * @param themeID
     */
    public static void getThemeDetail(ResultCallBack<ThemeDetailBean> callBack, Context cxt, String themeID) {
        try {
            Type type = new TypeToken<GeneralResponse<ThemeDetailBean>>() {
            }.getType();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("themeId", themeID);
            OkGoUtil<GeneralResponse<ThemeDetailBean>> communicator = new OkGoUtil<>(cxt, SimbaUrl.MAIN_THEME_DETAIL);
            GeneralResponse<ThemeDetailBean> response = communicator.post(jsonObject, type);
            callBack.onLoaded(response.data);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataLoadedFailure(e);
        }
    }
}
