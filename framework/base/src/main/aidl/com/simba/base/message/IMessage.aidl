package com.simba.base.message;

import com.simba.base.service.callbacks.IServiceDataCallback;
import com.simba.base.service.data.DataWrapper;

interface IMessage {

    // 设置回调
    void registerCallback(IServiceDataCallback cb);

    // 移除回调
    void unregisterCallback(IServiceDataCallback cb);

    DataWrapper getData(int dataType);

}