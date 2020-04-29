package com.simba.themestore.launch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.simba.themestore.R;
import com.simba.themestore.base.MyBaseActivity;
import com.simba.themestore.view.ProgressButton;

public class NotLoggedActivity extends MyBaseActivity implements Runnable, View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_CODE = 10;
    private static final String[] PERMISSIONS_REQUIRED = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_not_logged;
    }

    @Override
    protected void initView() {
        progressButton = findViewById(R.id.progress);
        progressButton.setOnClickListener(this);
        //  downLoad();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    private boolean allPermissionsGranted() {
        for (String permission : PERMISSIONS_REQUIRED) {
            if (ContextCompat.checkSelfPermission(getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void initData() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE);
        }
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
