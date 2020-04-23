package com.simba.violationenquiry.add;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.simba.base.mvp.BaseMActivity;
import com.simba.violationenquiry.R;
import com.simba.violationenquiry.add.contract.AddCarInfoContract;
import com.simba.violationenquiry.add.presenter.AddCarPresenter;
import com.simba.violationenquiry.event.AddCarInfoEvent;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.ui.view.ProvincesKeyBoardView;
import com.simba.violationenquiry.utils.AlphabetReplaceMethod;
import com.simba.violationenquiry.utils.AppUtils;
import com.simba.violationenquiry.utils.KeyBoardListener;
import com.simba.violationenquiry.utils.KeyboardHelper;
import com.simba.violationenquiry.utils.PopupHelper;
import com.simba.violationenquiry.utils.VinUtil;

import org.greenrobot.eventbus.EventBus;


/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc : 新增车辆信息
 */
public class AddNewCarActivity extends BaseMActivity<AddCarPresenter> implements View.OnClickListener, AddCarInfoContract.AddView {
    public final static int PLATE_CODE = 0;
    public final static int VIN_CODE = 1;
    public final static int ENGINE_CODE = 2;
    public final static String CAR_INFO = "CAR_INFO";
    private Button submit;
    private ImageView ivClose;
    private TextView tvProvinces;
    private EditText etPlateNo;
    private EditText etVIN;
    private EditText etEngineNo;
    private ProvincesKeyBoardView provincesKeyBoardView;
    private CarInfo newCarInfo;
    private String carID;

    private TextView tvErrorPlate;
    private TextView tvErrorVin;
    private TextView tvErrorEngine;

    private View vErrorPlate;
    private View vErrorVin;
    private View vErrorEngine;

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

        etPlateNo.setTransformationMethod(new AlphabetReplaceMethod());
        etVIN.setTransformationMethod(new AlphabetReplaceMethod());
        etEngineNo.setTransformationMethod(new AlphabetReplaceMethod());

        tvErrorPlate = findViewById(R.id.tv_plate_error);
        tvErrorVin = findViewById(R.id.tv_vin_error);
        tvErrorEngine = findViewById(R.id.tv_engine_error);

        vErrorPlate = findViewById(R.id.view_plate);
        vErrorVin = findViewById(R.id.view_vin);
        vErrorEngine = findViewById(R.id.view_engine);


        tvProvinces.setOnClickListener(this);
        submit.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        etPlateNo.addTextChangedListener(new AfterTextChangedListener(PLATE_CODE));
        etVIN.addTextChangedListener(new AfterTextChangedListener(VIN_CODE));
        etEngineNo.addTextChangedListener(new AfterTextChangedListener(ENGINE_CODE));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        carID = "";
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            CarInfo carInfo = (CarInfo) bundle.getSerializable(CAR_INFO);
            if (carInfo != null) {
                carID = carInfo.getId();
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
        provincesKeyBoardView = new ProvincesKeyBoardView(this, tvProvinces);

        check();

    }

    @Override
    protected AddCarPresenter initPresenter() {
        return new AddCarPresenter();
    }

    /**
     * 提交
     */
    private void submit() {

        newCarInfo = new CarInfo("1",
                AppUtils.getUpperValue(etEngineNo), carID,
                tvProvinces.getText().toString() + AppUtils.getUpperValue(etPlateNo),
                AppUtils.getUpperValue(etVIN));

        getP().onAdd(newCarInfo);

    }

    /**
     * 输入校验
     *
     * @return
     */
    private void check() {
        boolean flag = etPlateNo.getText().toString().length() < 6 || !VinUtil.isValidVin(etVIN.getText().toString()) || etEngineNo.getText().toString().length() == 0;
        submit.setEnabled(!flag);
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
            boolean flag = dismissProvincesKeyBoard();
            if (flag) {
                return true;
            }
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

    @Override
    public void onAddSuccess() {
        finish();
        EventBus.getDefault().post(new AddCarInfoEvent(true));
    }

    @Override
    public void onAddFail(String value) {
        showToast(value);
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private class AfterTextChangedListener implements TextWatcher {
        int code;

        public AfterTextChangedListener(int code) {
            this.code = code;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (code) {
                case PLATE_CODE: {
                    if (etPlateNo.getText().toString().length() < 6) {
                        setErrorPlate(true);
                    } else {
                        setErrorPlate(false);
                    }
                }
                break;
                case VIN_CODE: {
                    if (!VinUtil.isValidVin(etVIN.getText().toString())) {
                        setErrorVin(true);
                    } else {
                        setErrorVin(false);
                    }
                }
                break;
                case ENGINE_CODE: {
                    if (etEngineNo.getText().toString().length() == 0) {
                        setErrorEngine(true);
                    } else {
                        setErrorEngine(false);
                    }
                }
                break;
                default:
                    break;
            }
            check();
        }
    }


    private void setErrorPlate(boolean isShow) {
        tvErrorPlate.setVisibility(isShow ? View.VISIBLE : View.GONE);
        vErrorPlate.setVisibility(!isShow ? View.VISIBLE : View.GONE);

    }

    private void setErrorVin(boolean isShow) {
        tvErrorVin.setVisibility(isShow ? View.VISIBLE : View.GONE);
        vErrorVin.setVisibility(!isShow ? View.VISIBLE : View.GONE);

    }

    private void setErrorEngine(boolean isShow) {
        tvErrorEngine.setVisibility(isShow ? View.VISIBLE : View.GONE);
        vErrorEngine.setVisibility(!isShow ? View.VISIBLE : View.GONE);
    }
}
