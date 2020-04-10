package com.simba.violationenquiry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.simba.base.network.model.SimpleResponse;
import com.simba.violationenquiry.base.MyBaseActivity;
import com.simba.violationenquiry.event.AddCarInfoEvent;
import com.simba.violationenquiry.net.HttpRequest;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.ui.view.ProvincesKeyBoardView;
import com.simba.violationenquiry.utils.KeyBoardListener;
import com.simba.violationenquiry.utils.KeyboardHelper;
import com.simba.violationenquiry.utils.PopupHelper;
import com.simba.violationenquiry.utils.ResourceUtils;
import com.simba.violationenquiry.utils.VinUtil;

import org.greenrobot.eventbus.EventBus;

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
 * @Desc : 新增车辆信息
 */
public class AddNewCarActivity extends MyBaseActivity implements View.OnClickListener {
    public final static String CAR_INFO = "CAR_INFO";
    private Button submit;
    private ImageView ivClose;
    private TextView tvProvinces;
    private EditText etPlateNo;
    private EditText etVIN;
    private EditText etEngineNo;
    private ProvincesKeyBoardView provincesKeyBoardView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addnewcar;
    }

    @Override
    protected void initView() {
        submit = findViewById(R.id.btn_submit);

        ivClose = findViewById(R.id.iv_close);
        tvProvinces = findViewById(R.id.tv_provinces);
        etPlateNo = findViewById(R.id.et_car_plate_no);
        etVIN = findViewById(R.id.et_vin);
        etEngineNo = findViewById(R.id.et_engine_no);


        provincesKeyBoardView = new ProvincesKeyBoardView(this, tvProvinces);
        tvProvinces.setOnClickListener(this);
        submit.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            CarInfo carInfo = (CarInfo) bundle.getSerializable(CAR_INFO);
            if (carInfo != null) {
                if (!TextUtils.isEmpty(carInfo.getPlateno()) && carInfo.getPlateno().length() > 0) {
                    String keyChar = String.valueOf(carInfo.getPlateno().charAt(0));
                    tvProvinces.setText(keyChar);
                    etPlateNo.setText(carInfo.getPlateno().substring(1));
                }
                etVIN.setText(carInfo.getVin());
                etEngineNo.setText(carInfo.getEngineno());
            }
        }
        KeyBoardListener.setListener(this, new KeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                dismissProvincesKeyBoard();
            }

            @Override
            public void keyBoardHide(int height) {
            }
        });
    }

    /**
     * 提交
     */
    private void submit() {
        if (check()) {
            Observable.create(new ObservableOnSubscribe<SimpleResponse>() {
                @Override
                public void subscribe(final ObservableEmitter<SimpleResponse> emitter) throws Exception {
                    CarInfo carInfo = new CarInfo("deviceid",
                            etEngineNo.getText().toString(),
                            tvProvinces.getText().toString() + etPlateNo.getText().toString(),
                            etVIN.getText().toString());
                    HttpRequest.add(new ResultCallBack<SimpleResponse>() {
                        @Override
                        public void onLoaded(SimpleResponse wrapper) {
                            emitter.onNext(wrapper);
                        }

                        @Override
                        public void onDataLoadedFailure(Exception e) {
                            emitter.onError(e);
                        }
                    }, mContext, carInfo);
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
                                showToast("绑定成功");
                                finish();
                                EventBus.getDefault().post(new AddCarInfoEvent(true));
                            } else {
                                showToast("绑定失败，请重试");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            dismissProgressDialog();
                            showToast("绑定失败，请重试");
                        }
                    });


        }
    }

    /**
     * 输入校验
     *
     * @return
     */
    private boolean check() {
        if (etPlateNo.getText().toString().length() > 0 && etPlateNo.getText().toString().length() < 6) {
            etPlateNo.setError(ResourceUtils.getString(R.string.please_input_right_plate_no_info));
            return false;
        }
        if (!VinUtil.isValidVin(etVIN.getText().toString())) {
            etVIN.setError(ResourceUtils.getString(R.string.please_input_right_vin));
            return false;
        }
        if (etEngineNo.getText().toString().length() == 0) {
            etEngineNo.setError(ResourceUtils.getString(R.string.please_input_right_engine_no));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close: {
                finish();
            }
            break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.tv_provinces: {
                if (KeyboardHelper.isKeyboardVisible(this)) {
                    KeyboardHelper.hideKeyboard(etPlateNo);
                }
                PopupHelper.showToActivity(this, provincesKeyBoardView);
            }

            break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return dismissProvincesKeyBoard();
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean dismissProvincesKeyBoard() {
        if (provincesKeyBoardView.isShown()) {
            PopupHelper.dismiss(this);
            return true;
        }
        return false;
    }
}
