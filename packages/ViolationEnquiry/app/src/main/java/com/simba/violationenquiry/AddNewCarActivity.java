package com.simba.violationenquiry;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parkingwang.keyboard.KeyboardInputController;
import com.parkingwang.keyboard.OnInputChangedListener;
import com.parkingwang.keyboard.PopupKeyboard;
import com.parkingwang.keyboard.view.InputView;
import com.simba.violationenquiry.base.BaseActivity;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc : 新增车辆信息
 */
public class AddNewCarActivity extends BaseActivity {
    private InputView mInputView;
    private PopupKeyboard mPopupKeyboard;
    private Button newLicensePlate;
    private Button submit;
    private EditText etVIN;

    @Override
    protected int initLayout() {
        return R.layout.activity_addnewcar;
    }

    @Override
    protected void initView() {
        newLicensePlate = findViewById(R.id.iv_new);
        mInputView = findViewById(R.id.input_view);
        submit = findViewById(R.id.btn_submit);
        etVIN = findViewById(R.id.et_vin);
        // 创建弹出键盘
        mPopupKeyboard = new PopupKeyboard(this);
        // 弹出键盘内部包含一个KeyboardView，在此绑定输入两者关联。
        mPopupKeyboard.attach(mInputView, this);

        // 隐藏确定按钮
        mPopupKeyboard.getKeyboardEngine().setHideOKKey(false);

        //     KeyboardInputController提供一个默认实现的新能源车牌锁定按钮
        mPopupKeyboard.getController()
                .setDebugEnabled(true)

                .bindLockTypeProxy(new KeyboardInputController.ButtonProxyImpl(newLicensePlate) {
                    @Override
                    public void onNumberTypeChanged(boolean isNewEnergyType) {
                        super.onNumberTypeChanged(isNewEnergyType);
//                        if (isNewEnergyType) {
//                            lockTypeButton.setTextColor(getResources().getColor(android.R.color.holo_green_light));
//                        } else {
//                            lockTypeButton.setTextColor(getResources().getColor(android.R.color.black));
//                        }
                    }
                });
        mPopupKeyboard.getController().addOnInputChangedListener(new OnInputChangedListener() {
            @Override
            public void onChanged(String number, boolean isCompleted) {
                if (isCompleted) {
                    mPopupKeyboard.dismiss(AddNewCarActivity.this);
                }
            }

            @Override
            public void onCompleted(String number, boolean isAutoCompleted) {
                mPopupKeyboard.dismiss(AddNewCarActivity.this);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etVIN.setError("请输入正确的车架号");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 默认选中第一个车牌号码输入框
        //  mInputView.performFirstFieldView();
    }

    @Override
    protected void initData() {

    }
}
