package com.simba.message.protocol;

import android.content.Context;
import android.os.Message;

import java.util.ArrayList;

/**
 * @author chefengyun
 */
public interface IProtocol {

    void init(Context context);

    void permissionGranted(Context context);

    String getServerIP();

    int getServerPort();

    int getServerTimeout();

    int getCmdType(byte[] cmdBytes);

    int getCmdLength(byte[] cmdBytes);

    int getCmdStart();

    byte[] getDefaultCmd();

    byte[] getAckCmd();

    int getAckInterval();

    void queryIfNeed(ArrayList<byte[]> cmds);

    ArrayList<byte[]> checkCmd(byte[] rCmd);

    void log(byte[] cmdBytes, boolean read);

    byte[] getSendCmd(Message msg);

    void initialParsers(BaseParser.ParseCallback cacheCallback);

}
