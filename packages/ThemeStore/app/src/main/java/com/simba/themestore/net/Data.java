package com.simba.themestore.net;

import com.google.gson.reflect.TypeToken;
import com.simba.base.network.model.GeneralResponse;
import com.simba.base.network.utils.Convert;
import com.simba.themestore.model.theme.ThemeDetailBean;
import com.simba.themestore.model.theme.ThemeMainListBean;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/29
 * @Desc :
 */
public class Data {
    private static final String THEME_LIST = "{\"message\":null,\"code\":200,\"data\":[{\"categoryName\":\"动漫\",\"skinNameList\":[{\"id\":\"2\",\"typeid\":\"10\",\"title\":\"蜡笔小新\",\"coverurl\":\"封面url\"}]},{\"categoryName\":\"风景\",\"skinNameList\":[{\"id\":\"1\",\"typeid\":\"9\",\"title\":\"桂林山水\",\"coverurl\":\"封面url\"},{\"id\":\"3\",\"typeid\":\"9\",\"title\":\"青藏高原\",\"coverurl\":\"封面url\"}]}],\"success\":true}";
    private static final String    THEME_BANNER ="{\"message\":null,\"code\":200,\"data\":[{\"id\":\"4\",\"typeid\":\"9\",\"title\":\"朱穆朗玛\",\"coverurl\":\"封面url\"},{\"id\":\"2\",\"typeid\":\"10\",\"title\":\"蜡笔小新\",\"coverurl\":\"封面url\"},{\"id\":\"1\",\"typeid\":\"9\",\"title\":\"桂林山水\",\"coverurl\":\"封面url\"}],\"success\":true}";
    public static List<ThemeMainListBean> getMainThemeList() {
        Type type = new TypeToken<GeneralResponse<List<ThemeMainListBean>>>() {
        }.getType();
        GeneralResponse<List<ThemeMainListBean>> generalResponse = Convert.fromJson(THEME_LIST, type);
        return generalResponse.data;
    }
    public static List<ThemeDetailBean> getThemeBannerData() {
        Type type = new TypeToken<GeneralResponse<List<ThemeDetailBean>>>() {
        }.getType();
        GeneralResponse<List<ThemeDetailBean>> generalResponse = Convert.fromJson(THEME_BANNER, type);
        return generalResponse.data;
    }
}
