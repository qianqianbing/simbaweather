package com.simba.message.protocol.cloud.parser;

import android.content.Context;
import android.util.Log;

import com.simba.message.R;
import com.simba.message.contant.MessageDef;
import com.simba.message.protocol.BaseParser;
import com.simba.message.protocol.cloud.SimbaCloudCmd;
import com.simba.message.protocol.cloud.SimbaCloudUtils;
import com.simba.message.util.DataUtils;
import com.simba.message.util.N;

/**
 * (网易)新闻消息解析器
 */
public class NewsParser extends BaseParser {

    public NewsParser(Context context) {
        super(context);
    }

    @Override
    public void parse(byte[] cmdBytes) {

        byte msgOwner = cmdBytes[4];
        byte msgLevel = cmdBytes[5];

        int titleLen = DataUtils.getUnsignedByte(cmdBytes[8]);
        String title = new String(cmdBytes, 8 + 1, titleLen);

        int msgOffset = 8 + 1 + titleLen;
        int messageLen = DataUtils.getUnsignedByte(cmdBytes[msgOffset]);
        String message = new String(cmdBytes, msgOffset + 1, messageLen);
        Log.i(TAG, "title=" + title+", message="+message);

        switch (msgOwner){
            case SimbaCloudCmd.MessageOwner.NOTIFICATION:
                N.tapNotification(context, title, message, R.drawable.ic_news_wy);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean check(byte[] cmdBytes) {
        return SimbaCloudUtils.getCmdType(cmdBytes) == MessageDef.Message.NEWS;
    }
}
