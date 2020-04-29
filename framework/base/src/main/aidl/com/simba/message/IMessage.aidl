package com.simba.message;

import com.simba.message.IMessageCallback;

interface IMessage {

    void registerCallback(IMessageCallback cb);

    void unregisterCallback(IMessageCallback cb);

}