package com.simba.simbamedia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ARouter.init(getApplication());
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(MainActivity.this, KwinterfaceActivity.class);
                ARouter.getInstance()
                        .build("/module/musicfragment")
                        .navigation();
            }
        });

    }

}
