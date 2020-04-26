package com.simba.message.protocol.cloud;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.simba.message.contant.MessageDef;
import com.simba.message.manager.ParserManager;
import com.simba.message.protocol.BaseParser;
import com.simba.message.protocol.IProtocol;
import com.simba.message.protocol.MessageCmd;
import com.simba.message.protocol.cloud.parser.MemberInfoParser;
import com.simba.message.protocol.cloud.parser.MemberMsgParser;
import com.simba.message.protocol.cloud.parser.NewsParser;
import com.simba.message.util.DataUtils;
import com.simba.base.os.SystemProperties;

import java.util.ArrayList;

/**
 * 注：车机端TCP连接访问云端的Socket服务器，IP地址192.168.100.1，端口号20000。
 *
 * @author chefengyun
 */
public class SimbaCloudProtocol implements IProtocol {

    private final String TAG = this.getClass().getSimpleName();

    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void permissionGranted(Context context) {
    }

    @Override
    public String getServerIP() {
        String ip = SystemProperties.get("debug.cloud.ip", "192.168.12.2");
        Log.i(TAG, "SERVER_IP " + ip);
        return ip;
    }

    @Override
    public int getServerPort() {
        return 60000;
    }

    @Override
    public int getServerTimeout() {
        // 10s
        return 10 * 1000;
    }

    @Override
    public int getCmdType(byte[] cmdBytes) {
        return SimbaCloudUtils.getCmdType(cmdBytes);
    }

    @Override
    public void queryIfNeed(ArrayList<byte[]> cmds){
    }

    byte BCC = DataUtils.bcc(SimbaCloudCmd.UpDownLink.ACK, MessageDef.Message.DEFAULT, SimbaCloudCmd.MessageOwner.DEFAULT);
    byte[] ACK_CMD = new byte[]{SimbaCloudCmd.HEADER, SimbaCloudCmd.HEADEREX, SimbaCloudCmd.UpDownLink.ACK, MessageDef.Message.DEFAULT, SimbaCloudCmd.MessageOwner.DEFAULT, /*消息等级*/0x0, /*消息长度高位*/0x0, /*消息长度低位*/0x0, BCC};

    @Override
    public byte[] getDefaultCmd() {
        return ACK_CMD;
    }

    @Override
    public byte[] getAckCmd() {
        return ACK_CMD;
    }

    @Override
    public int getAckInterval() {
        // 100ms flag++
        // flag % 50 = 0
        // 5s 发送一次心跳
        return 50;
    }

    @Override
    public byte[] getSendCmd(Message msg) {
        String dataStr = null;
        switch (msg.what){
            case MessageCmd.MSG_STATUS_ACK:


                break;
            default:
                Log.e(TAG, "Not Support msg " + msg.what);
                break;
        }

        if(dataStr != null){
            // TODO append cmd
        }

        return null;
    }

    @Override
    public ArrayList<byte[]> checkCmd(byte[] rCmd) {
        return SimbaCloudUtils.checkCmd(rCmd);
    }

    @Override
    public void log(byte[] cmdBytes, boolean read) {
        String tag = read ? "cloud2app" : "app2cloud";
        Log.d(tag, DataUtils.bytes2HexString(cmdBytes));
    }

    @Override
    public void initialParsers(BaseParser.ParseCallback cacheCallback) {
        initMemberMsgParser(cacheCallback);
        initMemberInfoParser(cacheCallback);
        initNewsParser(cacheCallback);
    }

    private void initMemberMsgParser(BaseParser.ParseCallback cacheCallback){
        final MemberMsgParser parser = new MemberMsgParser(context);
        parser.registerCallback(cacheCallback);
        ParserManager.get().addParser(MessageDef.Message.MEMBER_MSG, parser);
    }

    private void initMemberInfoParser(BaseParser.ParseCallback cacheCallback){
        final MemberInfoParser parser = new MemberInfoParser(context);
        parser.registerCallback(cacheCallback);
        ParserManager.get().addParser(MessageDef.Message.MEMBER_INFO, parser);
    }

    private void initNewsParser(BaseParser.ParseCallback cacheCallback){
        final NewsParser parser = new NewsParser(context);
        parser.registerCallback(cacheCallback);
        ParserManager.get().addParser(MessageDef.Message.NEWS, parser);
    }

}
