package com.simba.message.protocol.cloud.parser;

import android.content.Context;
import android.util.Log;

import com.simba.base.define.ModelDefine;
import com.simba.message.R;
import com.simba.message.bean.MemeberMsgData;
import com.simba.message.protocol.BaseParser;
import com.simba.message.util.N;
import com.simba.service.data.DataWrapper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 会员中心消息解析器
 */
public class MemberMsgParser extends BaseParser {

    public MemberMsgParser(Context context) {
        super(context);
    }

    @Override
    public void parse(int cmdType, String jsonData) {
        Log.i(TAG, jsonData);

        try {
            JSONObject obj = new JSONObject(jsonData);

            int code = obj.getInt("code");
            if(code == 200){
                String title = obj.getString("title");
                String message = obj.getString("message");

                final MemeberMsgData data = new MemeberMsgData("", title, message, "");
                final DataWrapper dataWrapper = new DataWrapper(data, MemeberMsgData.CODE);
                mCallBack.cache(dataWrapper);

                String topPackage = N.getTopActivityPackageName(context);
                if(ModelDefine.MEMBERCENTER_PACKAGENAME.equals(topPackage) || "com.shuiyes.appstore".equals(topPackage)){
                    mCallBack.handleCallback(dataWrapper);
                }else{
                    // 会员中心不在前台， 则以通知提示
                    N.show(context, title, message, R.drawable.ic_member, ModelDefine.MEMBERCENTER_PACKAGENAME, ModelDefine.MEMBERCENTER_MAIN_ACTIVITY);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
