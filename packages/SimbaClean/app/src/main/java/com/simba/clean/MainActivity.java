package com.simba.clean;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private ImageView iv_loading_memory;
    private ObjectAnimator animator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_main);
        iv_loading_memory = findViewById(R.id.iv_loading_memory);
        startMemoryLoadingAnim();
    }


    public void startMemoryLoadingAnim(){
        animator = ObjectAnimator.ofFloat(iv_loading_memory, "rotation", 0f, 360f);//旋转360度
        animator.setRepeatCount(-1);//无限循环
        animator.setDuration(2000);//设置持续时间
        animator.start();//动画开始
    }

    public void setFullScreen(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
