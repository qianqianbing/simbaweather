package com.simba.violationenquiry.net.callback;

/**
 * @Author : chenjianbo
 * @Date : 2020/3/2
 * @Desc :
 */
public interface ResultCallBack<T> {
    /**
     * 请求数据成功时
     *
     * @param wrapper 返回的数据集合包装对象
     */
    void onLoaded(T wrapper);

    /**
     * 请求数据异常
     *
     * @param e net error/db error
     */
    void onDataLoadedFailure(Exception e);
}
