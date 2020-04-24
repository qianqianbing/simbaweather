package com.simba.base.message.protocol.cloud.parser;

import android.content.Context;
import android.util.Log;

import com.simba.base.define.ModelDefine;
import com.simba.base.message.R;
import com.simba.base.message.bean.MemeberMsgData;
import com.simba.base.message.contant.MessageDef;
import com.simba.base.message.protocol.BaseParser;
import com.simba.base.message.protocol.cloud.SimbaCloudCmd;
import com.simba.base.message.protocol.cloud.SimbaCloudUtils;
import com.simba.base.message.util.DataUtils;
import com.simba.base.message.util.N;
import com.simba.base.service.data.DataWrapper;

/**
 * 会员中心消息解析器
 */
public class MemberMsgParser extends BaseParser {

    public MemberMsgParser(Context context) {
        super(context);
    }

    @Override
    public void parse(byte[] cmdBytes) {

        byte msgOwner = cmdBytes[4];
        byte msgLevel = cmdBytes[5];

        int len = DataUtils.getUnsignedByte(cmdBytes[7]);
        String msg = new String(cmdBytes, 8, len);
        Log.i(TAG, "msg="+msg);

        switch (msgOwner){
            case SimbaCloudCmd.MessageOwner.NOTIFICATION:
                N.tapNotification(context, "会员中心", msg, R.drawable.ic_member);
                break;
            case SimbaCloudCmd.MessageOwner.App:
            default:
                final MemeberMsgData data = new MemeberMsgData("", "", msg, "");
                final DataWrapper dataWrapper = new DataWrapper(data, MemeberMsgData.CODE);
                mCallBack.cache(dataWrapper);

                if(!ModelDefine.MEMBERCENTER_PACKAGENAME.equals(N.getTopActivityPackageName(context))){
                    // 会员中心不在前台， 则以通知提示
                    N.tapNotification(context, "会员中心", msg, R.drawable.ic_member, ModelDefine.MEMBERCENTER_PACKAGENAME, ModelDefine.MEMBERCENTER_MAIN_ACTIVITY);
                }else{
                    mCallBack.handleCallback(dataWrapper);
                }
                break;
        }
    }

    @Override
    public boolean check(byte[] cmdBytes) {
        return SimbaCloudUtils.getCmdType(cmdBytes) == MessageDef.Message.MEMBER_MSG;
    }
}
