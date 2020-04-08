package com.simba.base.network;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.Response;
import com.simba.base.network.utils.JsonConvert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/08
 * 描    述：okgo3.0 初始化辅助，常用方法封装类
 * 修订历史：
 * ================================================
 */
public class OkGoUtil<T> {
    public final static String TAG = OkGoUtil.class.getSimpleName();
    private Context context;
    private String httpUrl;
    /**
     * @param cxt
     * @param httpUrl
     */
    public OkGoUtil(Context cxt, String httpUrl) {
        this.context = cxt;
        if (!httpUrl.startsWith("http")) {
            httpUrl = SimbaUrl.BASE_HOST + httpUrl;
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

    public static <T> void get(String url, Object tag, Map<String, String> map, JsonCallback<T> callback) {
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }


    public static <T> void post(String url, Object tag, Map<String, String> map, JsonCallback<T> callback) {
        OkGo.<T>post(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }

    /**
     * okgo 初始化
     *
     * @param application application 对象
     * @param debug       是否输出网络请求的log信息
     */
    public static void init(Application application, boolean debug) {
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (debug) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
                //log打印级别，决定了log显示的详细程度
                loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
                //log颜色级别，决定了log在控制台显示的颜色
                loggingInterceptor.setColorLevel(Level.INFO);
                builder.addInterceptor(loggingInterceptor);
            }

            //全局的读取超时时间
            builder.readTimeout(20000, TimeUnit.MILLISECONDS);
            //全局的写入超时时间
            builder.writeTimeout(20000, TimeUnit.MILLISECONDS);
            //全局的连接超时时间
            builder.connectTimeout(20000, TimeUnit.MILLISECONDS);

            //使用sp保持cookie，如果cookie不过期，则一直有效
            builder.cookieJar(new CookieJarImpl(new SPCookieStore(application)));
//            //使用数据库保持cookie，如果cookie不过期，则一直有效
//            builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
//            //使用内存保持cookie，app退出后，cookie消失
//            builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));


            //方法一：信任所有证书,不安全有风险
            HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
//            //方法二：自定义信任规则，校验服务端证书
//            HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
//            //方法三：使用预埋证书，校验服务端证书（自签名证书）
//            HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
//            //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//            HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
            builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

//            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//            builder.hostnameVerifier(new SafeHostnameVerifier());


            OkGo.getInstance().init(application)                       //必须调用初始化
                    .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                    .setCacheMode(CacheMode.DEFAULT)               //全局统一缓存模式，默认不使用缓存，可以不传
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                    .setRetryCount(3);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                    .addCommonHeaders(headers)                      //全局公共头
//                    .addCommonParams(params);                       //全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return OkGo.getInstance().getContext();
    }

    private static X509TrustManager safeTrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                for (X509Certificate certificate : chain) {
                    certificate.checkValidity(); //检查证书是否过期
                }
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };


    private static HostnameVerifier safeHostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //示例
            if ("yourhostname".equals(hostname)) {
                return true;
            } else {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify(hostname, session);
            }
        }
    };
}
