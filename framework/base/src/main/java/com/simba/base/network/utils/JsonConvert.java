/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.simba.base.network.utils;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;
import com.simba.base.network.OkGoUtil;
import com.simba.base.network.model.GeneralResponse;
import com.simba.base.network.model.SimpleResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :
 */
public class JsonConvert<T> implements Converter<T> {

    private Type type;
    private Class<T> clazz;

    public JsonConvert() {
    }

    public JsonConvert(Type type) {
        this.type = type;
    }

    public JsonConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象，生成onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        if (type == null) {
            if (clazz == null) {
                // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型（有局限性，继承后就无法解析到）
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                return parseClass(response, clazz);
            }
        }

        if (type instanceof ParameterizedType) {
            return parseParameterizedType(response, (ParameterizedType) type);
        } else if (type instanceof Class) {
            return parseClass(response, (Class<?>) type);
        } else {
            return parseType(response, type);
        }
    }

    private T parseClass(Response response, Class<?> rawType) throws Exception {
        if (rawType == null) {
            return null;
        }
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        JsonReader jsonReader = new JsonReader(body.charStream());
        if (response.code() == 200) {
            if (rawType == String.class) {
                return (T) body.string();
            } else if (rawType == JSONObject.class) {
                return (T) new JSONObject(body.string());
            } else if (rawType == JSONArray.class) {
                return (T) new JSONArray(body.string());
            } else {
                T t = Convert.fromJson(jsonReader, rawType);
                response.close();
                return t;
            }
        } else {
            throw new IllegalStateException("数据异常");
//            try {
//                JSONObject jsonObject = new JSONObject(body.string());
//            //    throw new IllegalStateException(ResourceUtils.getString(R.string.error_code) + jsonObject.optString("error_code") + "\n" + ResourceUtils.getString(R.string.error_msg) + jsonObject.optString("error_message"));
//            } catch (JSONException e) {
//              //  throw new IllegalStateException(ResourceUtils.getString(R.string.error_msg) + body.string());
//            }
        }
    }

    private T parseType(Response response, Type type) throws Exception {
        if (type == null) {
            return null;
        }
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        JsonReader jsonReader = new JsonReader(body.charStream());

        // 泛型格式如下： new JsonCallback<任意JavaBean>(this)
        if (response.code() == 200) {
            T t = Convert.fromJson(jsonReader, type);
            response.close();
            return t;
        } else {
            //   {"error_code":10001, "error_message": "xxxxx"}
            throw new IllegalStateException("数据异常");
//            try {
//                JSONObject jsonObject = new JSONObject(body.string());
//                throw new IllegalStateException(ResourceUtils.getString(R.string.error_code) + jsonObject.optString("error_code") + "\n" + ResourceUtils.getString(R.string.error_msg) + jsonObject.optString("error_message"));
//            } catch (JSONException e) {
//                throw new IllegalStateException(ResourceUtils.getString(R.string.error_msg) + body.string());
//            }
        }
    }

    private T parseParameterizedType(Response response, ParameterizedType type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        Type rawType = type.getRawType();                     // 泛型的实际类型
        Type typeArgument = type.getActualTypeArguments()[0]; // 泛型的参数

        if (response.code() == OkGoUtil.SUCCESS_CODE) {

            if (rawType != GeneralResponse.class) {
                // 泛型格式如下： new JsonCallback<外层BaseBean<内层JavaBean>>(this)
                T t = Convert.fromJson(jsonReader, type);
                response.close();
                return t;
            } else {
                if (typeArgument == Void.class) {
                    // 泛型格式如下： new JsonCallback<LzyResponse<Void>>(this)
                    SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                    response.close();
                    //noinspection unchecked
                    return (T) simpleResponse.toGeneralResponse();
                } else {
                    // 泛型格式如下： new JsonCallback<LzyResponse<内层JavaBean>>(this)
                    GeneralResponse lzyResponse = Convert.fromJson(jsonReader, type);
                    response.close();
                    return (T) lzyResponse;
                }
            }
        } else {
            throw new IllegalStateException("数据异常");
//            try {
//                JSONObject jsonObject = new JSONObject(body.string());
//                throw new IllegalStateException(ResourceUtils.getString(R.string.error_code) + jsonObject.optString("error_code") + "\n" + ResourceUtils.getString(R.string.error_msg) + jsonObject.optString("error_message"));
//            } catch (JSONException e) {
//                throw new IllegalStateException(ResourceUtils.getString(R.string.error_msg) + body.string());
//            }
        }
    }
}
