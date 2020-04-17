package com.simba.base.DeviceAccountManager;

import android.app.Activity;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

import com.simba.base.R;

public class JumpToMemberCenterActivity extends Activity  {
    private Button mBtJump;
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setContentView(R.layout.activity_jumptomembercenter);


    }
}
