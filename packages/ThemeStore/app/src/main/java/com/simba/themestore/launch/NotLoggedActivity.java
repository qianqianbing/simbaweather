package com.simba.themestore.launch;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.simba.themestore.R;
import com.simba.themestore.base.MyBaseActivity;
import com.simba.themestore.view.ProgressButton;

public class NotLoggedActivity extends MyBaseActivity implements Runnable, View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_not_logged;
    }

    @Override
    protected void initView() {
        progressButton=findViewById(R.id.progress);
        progressButton.setOnClickListener(this);
      //  downLoad();
    }

    @Override
    protected void initData() {

    }

    ProgressButton progressButton;


    Thread downLoadThread;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressButton.setProgress(msg.arg1);

            if (msg.arg1 == 100) {
                progressButton.finishLoad();

            }
        }
    };


    public void reLoad(View view) {

        downLoadThread.interrupt();
        // 重新加载
        progressButton.reset();


        downLoad();
    }

    private void downLoad() {
        downLoadThread = new Thread(this);
        downLoadThread.start();
    }

    @Override
    public void onClick(View v) {
//        if (!progressButton.isFinish()) {
//            progressButton.toggle();
//
//
//            if (progressButton.isStop()) {
//                downLoadThread.interrupt();
//            } else {
//                downLoad();
//            }
//
//        }
    }

    @Override
    public void run() {
        try {
            while (!downLoadThread.isInterrupted()) {
                float progress = progressButton.getProgress();
                progress += 2;
                Thread.sleep(200);
                Message message = handler.obtainMessage();
                message.arg1 = (int) progress;
                handler.sendMessage(message);
                if (progress == 100) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start(View view) {
        startActivity(MainActivity.class);
    }
}
