package com.simba.membercenter;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.greendao.gen.MessageBeanDao;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.UI.Popupwindow.GlobalPopupWindow;
import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.base.network.ConstantDefine;
import com.simba.base.network.JsonCallback;
import com.simba.membercenter.DB.MessageBean;
import com.simba.membercenter.bean.WeCharUrlBean;
import com.simba.membercenter.presenter.LocalAccountManager;
import com.simba.membercenter.ui.popupwindow.DeviceActivationPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import static com.simba.base.network.ConstantDefine.QRTYPE_ACTIVATION;
import static com.simba.base.network.SimbaUrl.ACCOUNT_GET_QRCODE;

/**
 * @description: 会员中心管理的service，因为要随着系统启动就工作，而且还要提供已登陆账号的信息给其他应用使用，所以使用service来实现
 * @author: luojunjie
 * @createDate: 2020/4/19 16:04
 */



public class MemberCenterService extends Service  {
    private static String TAG = "MemberCenterService";
    private BroadcastReceiver messageReceiver ;
    private MessageBeanDao messageBeanDao;
    //消息中心发送消息给会员中心，测试代码
    private static String MessageIntent = "com.simba.messagecenter";

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initMessageReceiver();
        messageBeanDao = MyApplication.getMyApplication().getDaoSession().getMessageBeanDao();

        //service 启动后首先检测设备是否激活，未激活需要弹框提示
        if(! DeviceAccountManager.getInstance(this).getDeviceActivation()){
            //获取激活二维码
            requestQRCode();
        }

        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 开机启动，需要判断设备是否已经激活
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private void showActivationDialog(String url) {
        final GlobalPopupWindow deviceActivationPopupWindow = new DeviceActivationPopupWindow(url);
        deviceActivationPopupWindow.showPopupWindow(MyApplication.getMyApplication().getApplicationContext());
    }

    private void initMessageReceiver(){
        messageReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MessageIntent);
        registerReceiver(messageReceiver,intentFilter);
    }

    //测试代码，生成消息的逻辑
    int messageTime = 100;
    class MessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MessageIntent)){
                Log.e(TAG, "insert message ");

                messageBeanDao.insert(new MessageBean(LocalAccountManager.getIntance().getUserName(), messageTime, "标题" + messageTime , "描述 " + messageTime));
                messageTime ++ ;
            }
        }
    }

    private void requestQRCode() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantDefine.ACTION, QRTYPE_ACTIVATION);
            jsonObject.put(ConstantDefine.CALLBACKURL, ConstantDefine.WeChatURL);
            jsonObject.put(ConstantDefine.DEVICEID, DeviceAccountManager.getInstance(MyApplication.getMyApplication().getApplicationContext()).getDeviceId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

         OkGo.<WeCharUrlBean>post(ACCOUNT_GET_QRCODE)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<WeCharUrlBean>() {
                    @Override
                    public void onSuccess(Response<WeCharUrlBean> response) {
                        if (isCode200()) {
                            WeCharUrlBean weCharUrl = response.body();
                            Log.e(TAG, "weCharUrl " + weCharUrl.getUrl());
                            showActivationDialog(weCharUrl.getUrl());
                        }
                    }
                });

    }
}
