package com.simba.message;

import com.simba.service.callbacks.IServiceDataCallback;
import com.simba.service.data.DataWrapper;

interface IMessage {

    void registerCallback(IServiceDataCallback cb);

    void unregisterCallback(IServiceDataCallback cb);

    DataWrapper getData(int dataType);

}