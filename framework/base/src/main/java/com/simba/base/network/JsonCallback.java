package com.simba.base.network;

import android.app.Activity;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.simba.base.R;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.utils.LogUtil;
import com.simba.base.utils.Toasty;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/08
 * 描    述：
 * 1.对json数据类型直接转换到javabean
 * 2.网络请求自动弹框
 * 3.对多个并发请求支持共享统一个等待框，以第一个请求发出后弹框，最后一个请求结束小时弹框。
 * 4.统一处理网络请求异常，使用者只需处理成功时的业务
 * 修订历史：
 * ================================================
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private final static Gson gson = new Gson();
    //进度条延时显示的时间
    private final static long DELAYED_TIME = 200;

    //请求的进度条
    private DialogUtil publicDialog;

    //请求的activtity对象
    private Activity activity;
    //是否显示逻辑错误提示
    private boolean showLogicErr = true;
    //是否显示网络错误、其他错误提示
    private boolean showErr = true;

    //请求的url 和参数
    private String requestUrl, requestParams;

    //http 的状态码
    private int httpCode;
    //响应中code的值
    private int responseCode;
    //响应中data的值
    private String responseData;
    //响应中message的值
    private String responseMessage;
    //响应的字符串
    private String response;

    /**
     * 无参构造，无须activity ，也不会显示dialog
     */
    public JsonCallback() {
        this(null, false);
    }

    /**
     * Activity  会显示等待框
     *
     * @param activity
     */
    public JsonCallback(Activity activity) {
        this(activity, true);
    }

    /**
     * @param activity     Activity 对象
     * @param isShowDialog 是否显示等待框
     */
    public JsonCallback(Activity activity, boolean isShowDialog) {
        this.activity = activity;
        if (isShowDialog)
            publicDialog = DialogUtil.buildProgress(activity, R.string.base_network_load_wait);
    }

    /**
     * @param activity     Activity 对象
     * @param processResId 进度条的描述
     */
    public JsonCallback(Activity activity, int processResId) {
        this.activity = activity;
        publicDialog = DialogUtil.buildProgress(activity, processResId);
    }

    /**
     * 级联并发请求
     * 这个方案可以在请求前就配置好，后面使用中每次传入相同的EcgDialog对象即可   示例见 使用说明
     *
     * @param activity
     * @param dialog   级联并发请求的进度条 可以通过 ecgConnectDialog.netWorkSize 设置并发的数量
     */
    public JsonCallback(Activity activity, DialogUtil dialog) {
        this.activity = activity;
        if (dialog != null)
            publicDialog = dialog;
    }

    /**
     * // 主要用于在所有请求之前添加公共的请求头或请求参数
     * // 例如登录授权的 token
     * // 使用的设备信息
     * // 可以随意添加,也可以什么都不传
     * // 还可以在这里对所有的参数进行加密，均在这里实现
     *
     * @param request
     */
    @Override
    public void onStart(Request<T, ? extends Request> request) {
        //添加请求头
//        request.headers(getHttpHeaders());

        //网络请求前显示对话框
        if (publicDialog != null) {
            publicDialog.setDelayTimeShow(DELAYED_TIME).show();
        }

        requestUrl = request.getUrl();
        requestParams = request.getParams().toString();
    }

    @Override
    public T convertResponse(okhttp3.Response theResponse) throws Throwable {

        httpCode = theResponse.code();

        if (theResponse.body() == null)
            throw new Exception("response.body() is null");

        response = theResponse.body().string();

        JSONObject json = new JSONObject(response);
        responseCode = json.optInt("code");
        responseData = json.optString("data");
        responseMessage = json.optString("message");
        T data = null;
        if (isCode200()) {
//            //过滤 data="",null,{},[]
//            if (TextUtils.isEmpty(responseData) || "{}".equals(responseData) || "[]".equals(responseData)) {
//                data = (T) gson.fromJson(this.response, BaseResult.class);
//            }
            //以下代码是通过泛型解析实际参数,泛型必须传
            //com.lzy.demo.callback.DialogCallback<com.lzy.demo.model.Login> 得到类的泛型，包括了泛型参数
            Type genType = getClass().getGenericSuperclass();
            //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值,当前类只有一个泛型，所以取出第一个，得到实际类型
            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];

            //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
            //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
            //有数据类型，表示有data
            data = gson.fromJson(this.response, type);
        }
        return data;
    }

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     */
    @Override
    public void onError(Response<T> response) {
        //输出错误日志
        if (response != null) {

            Throwable e = response.getException();
            LogUtil.e(e);

            //是否显示网络错误、其他错误提示
            if (showErr && activity != null) {

                String requestInfo = "Url:" + requestUrl + "；Params:" + requestParams;
                String responseInfo = "\nResponse:" + this.response;

                //处理网络异常类
                if (e instanceof JSONException) {
                    Toasty.error(activity, R.string.base_service_error1).show();
                } else if (e instanceof SocketTimeoutException ||
                        e instanceof ConnectException ||
                        e instanceof UnknownHostException) {
                    Toasty.error(activity, R.string.base_network_unavailable).show();
                } else {
                    Toasty.error(activity, R.string.base_service_error).show();
                }
            }
        }
    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (publicDialog != null) {
            publicDialog.dismiss();
        }
    }

    //设置是否显示接口返回的业务逻辑错误 toast,dialog信息 默认显示
    public JsonCallback<T> showLogicErr(boolean showLogicErr) {
        this.showLogicErr = showLogicErr;
        return this;
    }

    //是否显示网络错误、其他错误提示
    public JsonCallback<T> showErr(boolean showErr) {
        this.showErr = showErr;
        return this;
    }

    //获取 网络状态码
    public int getHttpCode() {
        return httpCode;
    }

    //判断code 是不是200；
    public boolean isCode200() {
        return responseCode == 200;
    }

    //获取 响应中code的值
    public int getResponseCode() {
        return responseCode;
    }

    //获取 响应中Response的值
    public String getResponse() {
        return response;
    }

    //获取 响应中data的值
    public String getResponseData() {
        return responseData;
    }

    //获取响应中message的值
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * 获取htttp头添加的信息，token，语言环境
     *
     * @return
     */
    public static HttpHeaders getHttpHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();

//        //添加 应用版本号
//        httpHeaders.put("app_version", EcgApplication.getVersionCode() + "");
//
//        //添加 应用语言环境
//        if (EcgUtil.isZh())
//            httpHeaders.put("lang", "zh");
//        else
//            httpHeaders.put("lang", "en");

        return httpHeaders;
    }

}