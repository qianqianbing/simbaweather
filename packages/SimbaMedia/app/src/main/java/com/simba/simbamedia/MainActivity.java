package com.simba.simbamedia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(MainActivity.this, KwinterfaceActivity.class);
                ARouter.getInstance()
                        .build("/module/1")
                        .navigation(MainActivity.this, new NavCallback() {
                                @Override
                                public void onFound(Postcard postcard) {
                                    Log.d("ARouter", "找到了");
                                }

                                @Override
                                public void onLost(Postcard postcard) {
                                    Log.d("ARouter", "找不到了"+postcard.toString());
                                }

                                @Override
                                public void onArrival(Postcard postcard) {

                                    Log.d("ARouter", "跳转完了");
                                }

                                @Override
                                public void onInterrupt(Postcard postcard) {
                                    Log.d("ARouter", "被拦截了");
                                }

                        });
            }
        });



        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(MainActivity.this, KwinterfaceActivity.class);
                ARouter.getInstance()
                        .build("/app/hello")
                        .navigation(MainActivity.this, new NavCallback() {
                            @Override
                            public void onFound(Postcard postcard) {
                                Log.d("ARouter", "找到了");
                            }

                            @Override
                            public void onLost(Postcard postcard) {
                                Log.d("ARouter", "找不到了"+postcard.toString());
                            }

                            @Override
                            public void onArrival(Postcard postcard) {

                                Log.d("ARouter", "跳转完了");
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                Log.d("ARouter", "被拦截了");
                            }

                        });
            }
        });
    }

}
