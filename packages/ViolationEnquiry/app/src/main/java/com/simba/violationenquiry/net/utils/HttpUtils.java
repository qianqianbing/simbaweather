package com.simba.violationenquiry.net.utils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.simba.violationenquiry.net.callback.JsonConvert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * @param <T>
 */
public class HttpUtils<T> {
    public final static String TAG = HttpUtils.class.getSimpleName();
    private Context context;
    private String httpUrl;

    /**
     * @param cxt
     * @param httpUrl
     */
    public HttpUtils(Context cxt, String httpUrl) {
        this.context = cxt;
        if (!httpUrl.startsWith("http")) {
            httpUrl = HttpParameters.BASE_URL + httpUrl;
        }
        this.httpUrl = httpUrl;

    }


    /**
     * @param type
     * @return
     * @throws Exception
     */
    public T get(Type type) throws Exception {
        Call<T> call = OkGo.<T>get(httpUrl)
                .tag(context)
                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }

    /**
     * @param params
     * @return
     * @throws Exception
     */
    public T get(HashMap<String, String> params, Type type) throws Exception {
        Call<T> call = OkGo.<T>get(httpUrl)
                .tag(context)
                .params(params)
                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }

    /**
     * @param type
     * @return
     * @throws Exception
     */
    public T post(Type type) throws Exception {

        Call<T> call = OkGo.<T>post(httpUrl)
                .tag(context)
                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }

    public T post(Type type, RequestBody body) throws Exception {

        Call<T> call = OkGo.<T>post(httpUrl)
                .tag(context)

                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }


    public T post(JSONObject json, Type type) throws Exception {

        Call<T> call = OkGo.<T>post(httpUrl)
                .tag(context)
                .upJson(json)
                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }

    public T post(String json, Type type) throws Exception {

        Call<T> call = OkGo.<T>post(httpUrl)
                .tag(context)
                .upJson(json)
                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }

    public T post(String json) throws Exception {

        Call<T> call = OkGo.<T>post(httpUrl)
                .tag(context)
                .upJson(json)
                .converter(new JsonConvert<T>())
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }

    public T post(HashMap<String, String> params, Type type) throws Exception {
        Call<T> call = OkGo.<T>post(httpUrl)
                .tag(context)
                .isSpliceUrl(true)
                .params(params)
                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }

    public T post(JSONObject json, HashMap<String, String> params, Type type) throws Exception {

        Call<T> call = OkGo.<T>post(httpUrl)
                .tag(context)
                .isSpliceUrl(true)
                .params(params)
                .upJson(json)
                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }


    /**
     * @param params
     * @return
     * @throws Exception
     */
    public Response<JSONObject> getJSONObject(HashMap<String, String> params) throws Exception {
        Call<JSONObject> call = OkGo.<JSONObject>get(httpUrl)
                .tag(context)
                .params(params)
                .converter(new JsonConvert<JSONObject>(JSONObject.class))
                .adapt();
        Response<JSONObject> response = call.execute();
        if (response.isSuccessful()) {
            return response;
        }
        throw new IllegalStateException(response.getException());
    }

    /**
     * @param params
     * @return
     * @throws Exception
     */
    public Response<JSONArray> getJSONArray(HashMap<String, String> params) throws Exception {
        Call<JSONArray> call = OkGo.<JSONArray>get(httpUrl)
                .tag(context)
                .params(params)
                .converter(new JsonConvert<JSONArray>(JSONArray.class))
                .adapt();
        Response<JSONArray> response = call.execute();
        if (response.isSuccessful()) {
            return response;
        }
        throw new IllegalStateException(response.getException());
    }

    /**
     * @param mediaID
     * @return
     * @throws Exception
     */
    public File getMedia(String mediaID) throws Exception {

        GetRequest<File> getRequest = OkGo.<File>get(httpUrl + "/" + mediaID)
                .tag(context)
                .converter(new FileConvert());
        Call<File> call = getRequest.adapt();
        Response<File> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }


    /**
     * @param file
     * @return
     * @throws Exception
     */
    public T uploadFile(File file, String token, Type type) throws Exception {

        Call<T> call = OkGo.<T>post(httpUrl)
                .tag(context)
                .params("file", file)
                .params("Authorization", token)
                .converter(new JsonConvert<T>(type))
                .adapt();
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IllegalStateException(response.getException());
    }


}
