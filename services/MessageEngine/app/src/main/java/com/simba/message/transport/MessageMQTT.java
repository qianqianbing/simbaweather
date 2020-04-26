package com.simba.message.transport;

/**
 * @author chefengyun
 */
public class MessageMQTT implements IMessageTransport {

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public void disconnect() {
    }

    @Override
    public byte[] readCmd() {
        return null;
    }

    @Override
    public boolean writeCmd(byte[] cmdString) {
        return false;
    }
}
