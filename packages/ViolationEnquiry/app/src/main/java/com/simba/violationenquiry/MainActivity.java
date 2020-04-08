package com.simba.violationenquiry;

import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.lzy.okgo.OkGo;
import com.simba.violationenquiry.base.BaseActivity;
import com.simba.violationenquiry.dialog.SinglePickerManager;
import com.simba.violationenquiry.net.HttpRequest;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.ui.SectionsPagerAdapter;
import com.simba.violationenquiry.ui.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc : 主界面
 */
public class MainActivity extends BaseActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<CarInfo> mData;
    private NoScrollViewPager viewPager;
    private ImageView ivAdd;
    private TabLayout tabs;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        ivAdd = findViewById(R.id.iv_add_car_info);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddNewCarActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mData, this);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    public void add(View view) {
        startActivity(AddNewCarActivity.class);
//        mData.add(new CarInfo("苏A20V3W"));
//        sectionsPagerAdapter.notifyDataSetChanged();
    }

    public void delete(View view) {
        List<CarInfo> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new CarInfo("苏A20V3" + i));
        }
        SinglePickerManager singlePickerManager = new SinglePickerManager(this, data);
        singlePickerManager.show();

    }

    private void loadData() {

        Observable.create(new ObservableOnSubscribe<List<CarInfo>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<CarInfo>> emitter) throws Exception {

                HttpRequest.getCarInfoList(new ResultCallBack<List<CarInfo>>() {
                    @Override
                    public void onLoaded(List<CarInfo> wrapper) {
                        emitter.onNext(wrapper);
                    }

                    @Override
                    public void onDataLoadedFailure(Exception e) {
                        emitter.onError(new Exception(e.getMessage()));
                    }
                }, mContext, "deviceID");


            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        mDisposable = disposable;

                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CarInfo>>() {
                    @Override
                    public void accept(List<CarInfo> empSchedules) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


}