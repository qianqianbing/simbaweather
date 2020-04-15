package com.simba.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.simba.base.R;

import java.util.HashMap;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/08
 * 描    述：通用公共弹框类
 * 修订历史：
 * 使用方法：
 * 1.DialogUtil.buildProgress(this, "网络正在加载中！").show();
 * <p>
 * 2.DialogUtil.build(this)
 * .content("阿伐季艾莉！")
 * .show();
 * <p>
 * 3.DialogUtil.build(this)
 * .title("提示")
 * .content("阿里扩大解放了阿康点解啊点附近啊卢卡斯的附近啊领导分厘！")
 * .show();
 * <p>
 * 4.DialogUtil.build(this)
 * .title("提示")
 * .content("里扩大解放了阿康点解啊点附近啊卢卡斯的附近啊领导分厘卡决定付了款接撒点路口附近啊塑料口袋解放了卡降低森林砍伐季！")
 * .positiveText("确定")
 * .negativeText("取消")
 * .setDelayTimeShow(500) //延时500毫秒显示进度条
 * .show();
 * ================================================
 */
public class DialogUtil extends Dialog implements View.OnClickListener {

    Context context;
    TextView tv_title;
    View title_divider;
    TextView tv_content;
    TextView tv_negative;
    TextView tv_positive;
    ProgressBar progressBar;
    View divider_line1, divider_line2;
    LinearLayout append_layout;
    boolean autoDismiss = true;
    long delayTimeShow;//延迟指定时长显示
    long delayTimeDismiss;//延迟指定时长关闭
    int netWorkCount;

    HashMap<String, EditLine> ecgDialogETHashMap = new HashMap<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());


    protected SingleButtonCallback onPositiveCallback;
    protected SingleButtonCallback onNegativeCallback;
    protected SingleButtonCallback onNeutralCallback;
    protected SingleButtonCallback onAnyCallback;
    protected SingleButtonCallback onDismissCallback;
    protected SingleButtonCallback onKeyBackCallback;
    protected SingleButtonCallback onDelayTimeDismissCallback;


    public interface SingleButtonCallback {
        void onClick(DialogUtil dialogUtil, DialogAction dialogAction);
    }

    private DialogUtil(Context context) {
        super(context, R.style.base_BaseDialog);
        this.context = context;
        setContentView(R.layout.base_dialog);
        tv_title = ((TextView) findViewById(R.id.tv_title));
        title_divider = ((View) findViewById(R.id.title_divider));
        progressBar = ((ProgressBar) findViewById(R.id.progress));
        tv_content = ((TextView) findViewById(R.id.tv_content));
        append_layout = ((LinearLayout) findViewById(R.id.content_layout));
        tv_negative = ((TextView) findViewById(R.id.tv_negative));
        tv_positive = ((TextView) findViewById(R.id.tv_positive));
        divider_line1 = findViewById(R.id.divider_line1);
        divider_line2 = findViewById(R.id.divider_line2);
        tv_negative.setTag(DialogAction.NEGATIVE);
        tv_positive.setTag(DialogAction.POSITIVE);
        tv_negative.setOnClickListener(this);
        tv_positive.setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    //普通信息弹框
    public static DialogUtil build(Context context) {
        return new DialogUtil(context);
    }

    //进度条弹框
    public static DialogUtil buildProgress(Context context, int contentId) {
        return buildProgress(context, context.getString(contentId));
    }

    //进度条弹框
    public static DialogUtil buildProgress(Context context, String content) {
        DialogUtil dialogUtil = new DialogUtil(context);
        dialogUtil.progress(0);
        dialogUtil.content(content);
        dialogUtil.setCanceledOnTouchOutside(false);
//        ecgDialog.disableKeyBack();
        return dialogUtil;
    }

    public DialogUtil title(String titleName) {
        tv_title.setText(titleName);
        tv_title.setVisibility(View.VISIBLE);
        title_divider.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogUtil title(int titleNameId) {
        tv_title.setText(titleNameId);
        tv_title.setVisibility(View.VISIBLE);
        title_divider.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogUtil titleBgColor(int color) {
        int padding = DensityUtil.dip2px(context, 15);
        tv_title.setPadding(padding, padding, padding, padding);
        tv_title.setBackgroundColor(color);
        return this;
    }

    public DialogUtil titleBgColorRes(int colorId) {
        int padding = DensityUtil.dip2px(context, 15);
        tv_title.setPadding(padding, padding, padding, padding);
        tv_title.setBackgroundResource(colorId);
        return this;
    }

    public DialogUtil titleColor(int color) {
        tv_title.setTextColor(color);
        return this;
    }

    public DialogUtil titleColorRes(int colorId) {
        ColorStateList csl = (ColorStateList) context.getResources().getColorStateList(colorId);
        if (csl != null) {
            tv_title.setTextColor(csl);
        }
        return this;
    }

    public DialogUtil content(String content) {
        tv_content.setText(content);
        tv_content.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogUtil content(int contentResId) {
        tv_content.setText(contentResId);
        tv_content.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogUtil contentColor(int color) {
        tv_content.setTextColor(color);
        return this;
    }

    public DialogUtil contentColorRes(int colorId) {
        ColorStateList csl = (ColorStateList) context.getResources().getColorStateList(colorId);
        if (csl != null) {
            tv_content.setTextColor(csl);
        }
        return this;
    }


    public DialogUtil positiveText(String positiveText) {
        tv_positive.setText(positiveText);
        tv_positive.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogUtil positiveText(int positiveTextId) {
        tv_positive.setText(positiveTextId);
        tv_positive.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogUtil positiveTextColor(int color) {
        tv_positive.setTextColor(color);
        return this;
    }

    public DialogUtil positiveTextColorRes(int colorId) {
        ColorStateList csl = (ColorStateList) context.getResources().getColorStateList(colorId);
        if (csl != null) {
            tv_positive.setTextColor(csl);
        }
        return this;
    }

    public DialogUtil negativeText(String negativeText) {
        tv_negative.setText(negativeText);
        tv_negative.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogUtil negativeText(int negativeTextId) {
        tv_negative.setText(negativeTextId);
        tv_negative.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogUtil negativeTextColor(int color) {
        tv_negative.setTextColor(color);
        return this;
    }

    public DialogUtil negativeTextColorRes(int colorId) {
        ColorStateList csl = (ColorStateList) context.getResources().getColorStateList(colorId);
        if (csl != null) {
            tv_negative.setTextColor(csl);
        }
        return this;
    }

    //按钮点击后自动关闭窗口
    public DialogUtil autoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
        return this;
    }

    //点击对话框周边是否取消对话框
    public DialogUtil setCancelOnTouchOutside(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        return this;
    }

    //禁止返回键 dismiss掉 dialog
    public DialogUtil disableKeyBack() {
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                }
                return false;
            }
        });
        return this;
    }

    //追加自定义视图
    public DialogUtil appendView(View view) {
        append_layout.addView(view);
        return this;
    }

    //追加自定义视图
    public DialogUtil appendView(View view, ViewGroup.LayoutParams layoutParams) {
        append_layout.addView(view, layoutParams);
        return this;
    }

//    //追加自定义视图
//    public EcgDialog appendView(View view, int gravity) {
//        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) append_layout.getLayoutParams();
//        p.gravity = gravity;
//        append_layout.addView(view, p);
//        return this;
//    }

    //自定义视图
    public DialogUtil view(View view) {
        setContentView(view);
        return this;
    }

    //添加编辑视图
    public DialogUtil appendEditLine(final EditLine EditLine) {

        View view = LayoutInflater.from(context).inflate(R.layout.base_dialog_edit_text, null);

        EditText editText = ((EditText) view.findViewById(R.id.et_edit));

        final View et_line = view.findViewById(R.id.et_line);

        if (!TextUtils.isEmpty(EditLine.getName()))
            ((TextView) view.findViewById(R.id.tv_title)).setText(EditLine.getName());

        if (!TextUtils.isEmpty(EditLine.getEndName()))
            ((TextView) view.findViewById(R.id.tv_end)).setText(EditLine.getEndName());

        if (!TextUtils.isEmpty(EditLine.getHint()))
            editText.setHint(EditLine.getHint());

        if (!TextUtils.isEmpty(EditLine.getDefaultValue()))
            editText.setText(EditLine.getDefaultValue());

        if (EditLine.getInputType() != -1)
            editText.setInputType(EditLine.getInputType());

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    et_line.setBackgroundResource(R.color.base_simbacolor);
                else
                    et_line.setBackgroundResource(R.color.base_day_common_line);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                EditLine.setValue(s.toString());
            }
        });

        view.findViewById(R.id.et_edit).setEnabled(EditLine.isEditEnable());
        view.setTag(EditLine);

        append_layout.addView(view);

        ecgDialogETHashMap.put(EditLine.getName(), EditLine);
        return this;
    }

    public HashMap<String, EditLine> getEditLine() {
        return ecgDialogETHashMap;
    }


    public DialogUtil progress(int maxProgress) {
        progressBar.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public void show() {

        if (isShowing())
            return;
        netWorkCount++;

        if (delayTimeShow != 0) {
            //有延迟显示 计划
            mHandler.postDelayed(delayedShowRunnable, delayTimeShow);
        } else {
            //直接显示
            myShow();
        }
    }

    //真正显示的方法
    private void myShow() {
        try {

            if (!TextUtils.isEmpty(tv_positive.getText()) || !TextUtils.isEmpty(tv_negative.getText())) {
                divider_line1.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(tv_positive.getText()) && !TextUtils.isEmpty(tv_negative.getText())) {
                divider_line2.setVisibility(View.VISIBLE);
            }

            //设置对话框消失监听器
            if (onDismissCallback != null)
                setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onDismissCallback.onClick(DialogUtil.this, DialogAction.DISMISS);
                    }
                });

            //显示
            if (!isShowing())
                super.show();

            //有延迟消失任务 先显示 指定 时长后 自动消失
            if (delayTimeDismiss != 0) {
                mHandler.postDelayed(delayedDismissRunable, delayTimeDismiss);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置延迟 time 后，显示dialog
     *
     * @param time
     */
    public DialogUtil setDelayTimeShow(long time) {
        delayTimeShow = time;
        return this;
    }

    /**
     * 显示固定毫秒值后dismiss
     *
     * @param time
     */
    public DialogUtil setDelayTimeDismiss(long time) {
        delayTimeDismiss = time;
        return this;
    }

    /**
     * 取消固定毫秒后dismiss计划
     */
    public DialogUtil cancelDelayDismissPlan() {
        if (delayTimeDismiss != 0) {
            delayTimeDismiss = 0;
            mHandler.removeCallbacks(delayedDismissRunable);
        }
        return this;
    }

    /**
     * 取消固定毫秒后显示计划
     */
    public DialogUtil cancelDelayShowPlan() {
        if (delayTimeShow != 0) {
            delayTimeShow = 0;
            mHandler.removeCallbacks(delayedShowRunnable);
        }
        return this;
    }

    /**
     * 延时 消失对话框 runnable
     */
    private Runnable delayedDismissRunable = new Runnable() {
        @Override
        public void run() {
            delayTimeDismiss = 0;
            dismiss();
            if (onDelayTimeDismissCallback != null)
                onDelayTimeDismissCallback.onClick(DialogUtil.this, DialogAction.SHOW_TIME_OUT);

        }
    };

    /**
     * 延时 显示对话框 runnable
     */
    private Runnable delayedShowRunnable = new Runnable() {
        @Override
        public void run() {
            delayTimeShow = 0;
            myShow();
        }
    };

    //callback
    public DialogUtil onPositive(@NonNull SingleButtonCallback callback) {
        this.onPositiveCallback = callback;
        return this;
    }

    public DialogUtil onNegative(@NonNull SingleButtonCallback callback) {
        this.onNegativeCallback = callback;
        return this;
    }

//    public EcgDialog onNeutral(@NonNull SingleButtonCallback callback) {
//        this.onNeutralCallback = callback;
//        return this;
//    }

    public DialogUtil onAny(@NonNull SingleButtonCallback callback) {
        this.onAnyCallback = callback;
        return this;
    }

    public DialogUtil onDismiss(@NonNull SingleButtonCallback callback) {
        this.onDismissCallback = callback;
        return this;
    }

    public DialogUtil onKeyBack(@NonNull SingleButtonCallback callback) {
        this.onKeyBackCallback = callback;

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (onKeyBackCallback != null)
                        onKeyBackCallback.onClick(DialogUtil.this, DialogAction.KEY_BACK);
                    dismiss();
                }
                return true;
            }
        });

        return this;
    }

    /**
     * 设置showtime 时间到超时的回调
     * 与showTime(*)一起使用
     *
     * @param callback
     * @return
     */
    public DialogUtil onDelayTimeDismiss(@NonNull SingleButtonCallback callback) {
        this.onDelayTimeDismissCallback = callback;
        return this;
    }

    @Override
    public final void onClick(View v) {
        DialogAction tag = (DialogAction) v.getTag();
        switch (tag) {
            case POSITIVE: {
                if (onPositiveCallback != null) {
                    onPositiveCallback.onClick(this, tag);
                }
                if (autoDismiss) {
                    dismiss();
                }
                break;
            }
            case NEGATIVE: {
                if (onNegativeCallback != null) {
                    onNegativeCallback.onClick(this, tag);
                }
                if (autoDismiss) {
                    cancel();
                }
                break;
            }
            case NEUTRAL: {
                break;
            }
        }
        if (onAnyCallback != null) {
            onAnyCallback.onClick(this, tag);
        }
    }

    @Override
    public void dismiss() {
        try {
            //移除延时显示 计划
            cancelDelayShowPlan();

            //移除延时关闭 计划
            cancelDelayDismissPlan();

            netWorkCount--;
            if (isShowing() && netWorkCount == 0)
                super.dismiss();

        } catch (Exception e) {

        }
    }


    /**
     * 弹框事件类型
     */
    public enum DialogAction {
        POSITIVE,   //确定
        NEUTRAL,    //中立
        NEGATIVE,    //否定
        DISMISS,   //消失
        SHOW_TIME_OUT,    //顯示超時
        KEY_BACK    //返回键
    }

    /**
     * 输入框模型
     */
    public class EditLine {
        private String name;
        private String endName;
        private String hint;
        private String value = "";
        private String defaultValue;
        private int inputType = -1;
        private boolean editEnable = true;

        public EditLine(String name, String endName, String hint, String defaultValue) {
            this.name = name;
            this.endName = endName;
            this.hint = hint;
            this.defaultValue = defaultValue;
            value = defaultValue;
        }

        public EditLine() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEndName() {
            return endName;
        }

        public void setEndName(String endName) {
            this.endName = endName;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.value = defaultValue;
            this.defaultValue = defaultValue;
        }

        public boolean isEditEnable() {
            return editEnable;
        }

        public void setEditEnable(boolean editEnable) {
            this.editEnable = editEnable;
        }

        public int getInputType() {
            return inputType;
        }

        public void setInputType(int inputType) {
            this.inputType = inputType;
        }
    }
}
