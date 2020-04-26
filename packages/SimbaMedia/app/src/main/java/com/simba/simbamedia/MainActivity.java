package com.simba.simbamedia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.simba.simbamedia.test.KwinterfaceActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KwinterfaceActivity.go2Activty(MainActivity.this);
            }
        });

    }

}
