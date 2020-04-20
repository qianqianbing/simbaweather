package com.simba.simbaweather.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gyf.immersionbar.ImmersionBar;
import com.simba.simbaweather.R;
import com.simba.simbaweather.ui.activity.frag.Cinema_Frag;
import com.simba.simbaweather.ui.activity.frag.Home_Frag;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fly)
    FrameLayout fly;
    @BindView(R.id.btn_sy)
    RadioButton btnSy;
    @BindView(R.id.btn_yy)
    RadioButton btnYy;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private Cinema_Frag cinema_frag;
    private Home_Frag home_frag;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                .fullScreen(true)
                .transparentNavigationBar()
                .init();
        home_frag = new Home_Frag();
        cinema_frag = new Cinema_Frag();
        manager = getSupportFragmentManager();

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(home_frag);
        list.add(cinema_frag);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fly, home_frag).
                add(R.id.fly, cinema_frag).
                show(home_frag).hide(cinema_frag);
        transaction.commit();
    }


    @OnClick({R.id.btn_sy, R.id.btn_yy})
    public void onViewClicked(View view) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.btn_sy:
                transaction.show(home_frag).hide(cinema_frag);
                break;
            case R.id.btn_yy:
                transaction.show(cinema_frag).hide(home_frag);
                break;
        }
    }
}
