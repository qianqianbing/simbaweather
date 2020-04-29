package com.simba.message;

interface IMessageCallback {
     void onChange(int msgType, in String jsonData);
     void onSocketStateChange(boolean state);
}