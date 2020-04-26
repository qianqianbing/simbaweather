package com.simba.message.protocol.cloud.parser;

import android.content.Context;
import android.util.Log;

import com.simba.message.bean.MemeberInfoData;
import com.simba.message.contant.MessageDef;
import com.simba.message.protocol.BaseParser;
import com.simba.message.protocol.cloud.SimbaCloudCmd;
import com.simba.message.protocol.cloud.SimbaCloudUtils;
import com.simba.message.util.DataUtils;
import com.simba.service.data.DataWrapper;

/**
 * 会员信息 解析器
 */
public class MemberInfoParser extends BaseParser {

    public MemberInfoParser(Context context) {
        super(context);
    }

    @Override
    public void parse(byte[] cmdBytes) {

        byte msgOwner = cmdBytes[4];
        byte msgLevel = cmdBytes[5];

        int len = DataUtils.getUnsignedByte(cmdBytes[7]);
        String token = new String(cmdBytes, 8, len);
        Log.i(TAG, "token="+token);

        switch (msgOwner){
            case SimbaCloudCmd.MessageOwner.App:
            default:
                final MemeberInfoData data = new MemeberInfoData("", token, "");
                final DataWrapper dataWrapper = new DataWrapper(data, MemeberInfoData.CODE);
                mCallBack.handleCallback(dataWrapper);
                mCallBack.cache(dataWrapper);
                break;
        }
    }

    @Override
    public boolean check(byte[] cmdBytes) {
        return SimbaCloudUtils.getCmdType(cmdBytes) == MessageDef.Message.MEMBER_INFO;
    }
}
