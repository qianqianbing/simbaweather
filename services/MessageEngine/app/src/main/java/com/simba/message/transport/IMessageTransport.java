package com.simba.message.transport;

/**
 * @author chefengyun
 */
public interface IMessageTransport {

    boolean isConnected();

    boolean connect();

    void disconnect();

    byte[] readCmd();

    boolean writeCmd(byte[] cmdString);

}
