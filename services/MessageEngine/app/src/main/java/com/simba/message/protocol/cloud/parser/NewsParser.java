package com.simba.message.protocol.cloud.parser;

import android.content.Context;
import android.util.Log;

import com.simba.message.R;
import com.simba.message.protocol.BaseParser;
import com.simba.message.util.N;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * (网易)新闻消息解析器
 */
public class NewsParser extends BaseParser {

    public NewsParser(Context context) {
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
                Log.i(TAG, "title=" + title+", message="+message);

                N.show(context, title, message, R.drawable.icon_news_wy);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
