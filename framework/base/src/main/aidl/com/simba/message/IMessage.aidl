package com.simba.message;

import com.simba.service.callbacks.IServiceDataCallback;
import com.simba.service.data.DataWrapper;

interface IMessage {

    // 设置回调
    void registerCallback(IServiceDataCallback cb);

    // 移除回调
    void unregisterCallback(IServiceDataCallback cb);

    DataWrapper getData(int dataType);

}