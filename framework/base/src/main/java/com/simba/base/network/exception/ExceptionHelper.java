package com.simba.base.network.exception;

import android.net.ParseException;
import android.util.MalformedJsonException;

import com.google.gson.JsonParseException;
import com.lzy.okgo.exception.HttpException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/18
 * @Desc :异常信息封装
 */
public class ExceptionHelper {
    //客户端报错
    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int ANALYTIC_CLIENT_DATA_ERROR = 1002;//解析(客户端)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时
    public static final int UNKNOWN_HOST_EXCEPTION = 1005;//网络连接超时

    /**
     * 统一封装一下异常信息
     *
     * @param e
     * @return
     */
    public static ClientException handleException(Throwable e) {
        ClientException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpExc = (HttpException) e;
            ex = new ClientException(e, httpExc.code());
            ex.setMsg("网络错误，请稍后再试");  //均视为网络错误
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException serverExc = (ServerException) e;
            ex = new ClientException(serverExc, serverExc.getCode());
            ex.setMsg(serverExc.getMsg());
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            ex = new ClientException(e, ANALYTIC_SERVER_DATA_ERROR);
            ex.setMsg("客户端异常，请稍后再试");
            return ex;
        } else if (e instanceof ConnectException) {//连接网络错误
            ex = new ClientException(e, CONNECT_ERROR);
            ex.setMsg("网络连接错误，请稍后再试");
            return ex;
        } else if (e instanceof SocketTimeoutException) {//网络超时
            ex = new ClientException(e, TIME_OUT_ERROR);
            ex.setMsg("网络连接超时，请稍后再试");
            return ex;
        } else if (e instanceof UnknownHostException) {//网络异常
            ex = new ClientException(e, UNKNOWN_HOST_EXCEPTION);
            ex.setMsg("网络异常，请检查您的网络连接");
            return ex;
        } else {  //未知错误
            ex = new ClientException(e, UN_KNOWN_ERROR);
            ex.setMsg("系统异常，请稍后再试");
            return ex;
        }
    }

}