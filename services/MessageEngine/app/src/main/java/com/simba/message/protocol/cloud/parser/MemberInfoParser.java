package com.simba.message.protocol.cloud.parser;

import android.content.Context;
import android.util.Log;

import com.simba.message.bean.MemeberInfoData;
import com.simba.message.protocol.BaseParser;
import com.simba.service.data.DataWrapper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 会员信息 解析器
 */
public class MemberInfoParser extends BaseParser {

    public MemberInfoParser(Context context) {
        super(context);
    }

    @Override
    public void parse(int cmdType, String jsonData) {
        Log.i(TAG, jsonData);

        try {
            JSONObject obj = new JSONObject(jsonData);

            int code = obj.getInt("code");
            if(code == 200){
                JSONObject dataObj = obj.getJSONObject("data");
                String token = dataObj.getString("token");
                String openid = dataObj.getString("openid");
                String userid = dataObj.getString("userId");
                String message = obj.getString("message");

                final MemeberInfoData data = new MemeberInfoData(token, openid, userid, message);
                final DataWrapper dataWrapper = new DataWrapper(data, MemeberInfoData.CODE);
                mCallBack.handleCallback(dataWrapper);
                mCallBack.cache(dataWrapper);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

/*

{
	"message": null,
	"code": 200,
	"data": {
		"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1OTAzMDEyNDgsInVzZXJJZCI6IjEiLCJzdXBwb3J0IjpmYWxzZX0.YFT_mV6MEemccIzypGmnpbr-jKFjicAki8LE5GasBCXN0xS7BgxZpZtbRhvzSpGFszirTNzxKc-GzOrXMqex8A",
		"openid": null,
		"userId": null
	},
	"success": true
}

*/