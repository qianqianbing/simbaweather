package com.simba.violationenquiry;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.simba.base.UI.view.NoScrollViewPager;
import com.simba.base.dialog.picker.MultiplePickerManager;
import com.simba.base.network.model.SimpleResponse;
import com.simba.base.network.model.callback.ResultCallBack;
import com.simba.violationenquiry.add.AddNewCarActivity;
import com.simba.violationenquiry.base.MyBaseActivity;
import com.simba.violationenquiry.event.AddCarInfoEvent;
import com.simba.violationenquiry.net.HttpRequest;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.ui.SectionsPagerAdapter;
import com.simba.violationenquiry.utils.CacheHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class MainActivity extends MyBaseActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<CarInfo> mData;
    private NoScrollViewPager viewPager;
    private ImageView ivAdd;
    private TabLayout tabs;
    private RelativeLayout rlEmpty;
    private LinearLayout optionLayout;

    //    private static final int PERMISSIONS_REQUEST_CODE = 10;
//    private static final String[] PERMISSIONS_REQUIRED = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Button btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (CacheHelper.getIsFirst()) {
            startActivity(AddNewCarActivity.class);
            CacheHelper.saveIsFirst(false);
        }
        CacheHelper.clearMustRefresh();
//        if (allPermissionsGranted()) {
//
//        } else {
//            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE);
//        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSIONS_REQUEST_CODE) {
//            if (allPermissionsGranted()) {
//
//            } else {
//                showToast("未授权");
//                finish();
//            }
//        }
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        ivAdd = findViewById(R.id.iv_add_car_info);
        rlEmpty = findViewById(R.id.rl_empty);
        optionLayout = findViewById(R.id.ll_option);
        btnAdd = findViewById(R.id.btn_add_car_info);
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

        loadData();
    }

    public void add(View view) {
        startActivity(AddNewCarActivity.class);
    }

    public void delete(View view) {

        final MultiplePickerManager pickerManager = new MultiplePickerManager(this, mData);
        pickerManager.show();
        pickerManager.setTitle(R.string.vehicle_management);
        pickerManager.setOnConfirmListener(new MultiplePickerManager.onConfirmClickListener() {
            @Override
            public void onClick(SparseBooleanArray checkedItemPositions, int count) {
                deleteCar(checkedItemPositions, pickerManager);
            }
        });


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
                }, mContext, "1");


            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable = disposable;
                        showProgressDialog();
                        optionLayout.setVisibility(View.INVISIBLE);
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
                        setEmptyViewVisibility(false);
                        optionLayout.setVisibility(View.VISIBLE);
                        mData = carInfoList;
                        checkAdd();
                        sectionsPagerAdapter.refresh(mData);
                        viewPager.setCurrentItem(0);


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissProgressDialog();
                        setEmptyViewVisibility(true);
                    }
                });
    }

    private void deleteCar(final SparseBooleanArray checkedItemPositions, final MultiplePickerManager singlePickerManager) {
        Observable.create(new ObservableOnSubscribe<SimpleResponse>() {
            @Override
            public void subscribe(final ObservableEmitter<SimpleResponse> emitter) throws Exception {

                HttpRequest.delete(new ResultCallBack<SimpleResponse>() {
                    @Override
                    public void onLoaded(SimpleResponse wrapper) {
                        emitter.onNext(wrapper);
                    }

                    @Override
                    public void onDataLoadedFailure(Exception e) {
                        emitter.onError(e);
                    }
                }, mContext, checkedItemPositions, mData);
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable = disposable;
                        showProgressDialog();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(SimpleResponse response) throws Exception {
                        dismissProgressDialog();
                        if (response.success) {
                            singlePickerManager.dismiss();
                            showToast(R.string.delete_success);
                            loadData();

                        } else {
                            showToast(R.string.delete_fail);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissProgressDialog();
                        showToast(R.string.delete_fail);
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

    private void checkAdd() {
        if (mData != null && mData.size() >= 10) {
            btnAdd.setEnabled(false);
        } else {
            btnAdd.setEnabled(true);
        }
    }
 /*   private boolean allPermissionsGranted() {
        for (String permission : PERMISSIONS_REQUIRED) {
            if (ContextCompat.checkSelfPermission(getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }*/
}