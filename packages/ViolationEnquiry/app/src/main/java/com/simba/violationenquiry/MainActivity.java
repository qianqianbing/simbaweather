package com.simba.violationenquiry;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.simba.violationenquiry.base.BaseActivity;
import com.simba.violationenquiry.dialog.CommonDialog;
import com.simba.violationenquiry.dialog.SinglePickerManager;
import com.simba.violationenquiry.event.AddCarInfoEvent;
import com.simba.violationenquiry.net.HttpRequest;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.ui.SectionsPagerAdapter;
import com.simba.violationenquiry.ui.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private RelativeLayout rlEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        ivAdd = findViewById(R.id.iv_add_car_info);
        rlEmpty = findViewById(R.id.rl_empty);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddNewCarActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mData, this);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        //  loadData();
    }

    public void add(View view) {
        mData = new ArrayList<>();
        mData.add(new CarInfo("苏A20V3W"));
        sectionsPagerAdapter.refresh(mData);
        //  startActivity(AddNewCarActivity.class);
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
                        showProgressDialog();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CarInfo>>() {
                    @Override
                    public void accept(List<CarInfo> carInfoList) throws Exception {
                        dismissProgressDialog();
                        if (carInfoList == null || carInfoList.size() == 0) {
                            setEmptyViewVisibility(true);
                            return;
                        }
                        mData = carInfoList;
                        sectionsPagerAdapter.refresh(mData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissProgressDialog();
                        setEmptyViewVisibility(true);
                    }
                });
    }


    private void setEmptyViewVisibility(boolean visible) {
        rlEmpty.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAdd(AddCarInfoEvent carInfoEvent) {
        if (carInfoEvent.success) {
            loadData();
        }

    }

}