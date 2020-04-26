package com.simba.message.bean;

import com.simba.service.data.DataWrapper;

public class Message {

    int id;
    byte messageOwner, messageType, messageLevel;
    DataWrapper dataWrapper;

    public Message(int id, byte messageOwner, byte messageType, byte messageLevel, DataWrapper dataWrapper) {
        this.id = id;
        this.messageOwner = messageOwner;
        this.messageType = messageType;
        this.messageLevel = messageLevel;
        this.dataWrapper = dataWrapper;
    }

    public int getId() {
        return id;
    }

    public byte getMessageOwner() {
        return messageOwner;
    }

    public byte getMessageType() {
        return messageType;
    }

    public byte getMessageLevel() {
        return messageLevel;
    }

    public DataWrapper getDataWrapper() {
        return dataWrapper;
    }
}
