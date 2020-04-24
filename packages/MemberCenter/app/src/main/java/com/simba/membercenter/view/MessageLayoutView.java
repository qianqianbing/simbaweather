package com.simba.membercenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simba.membercenter.R;
import com.simba.membercenter.bean.MessageBean;

public class MessageLayoutView extends RelativeLayout implements View.OnClickListener {
    private static String TAG = "MessageLayoutView";
    private Button bt_select;
    private ImageView iv_shortcut;
    private TextView tv_message_title, tv_message_description;
    private MessageBean messageBean;
    public MessageLayoutView(Context context) {

        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_message_item, this, true);
    }

    public MessageLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("TAG", " MessageLayoutView init 2");
        LayoutInflater.from(context).inflate(R.layout.layout_message_item, this, true);
        bt_select = findViewById(R.id.bt_select);
        bt_select.setOnClickListener(this);
        iv_shortcut = findViewById(R.id.iv_shortcut);
        tv_message_title = findViewById(R.id.tv_message_title);
        tv_message_description = findViewById(R.id.tv_message_description);
    }

    public MessageLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_message_item, this, true);

    }

    public void setMessageInfo(MessageBean messageBean, boolean isEdit){
        this.messageBean = messageBean;
        bt_select.setSelected(messageBean.isSelected());
        tv_message_title.setText(messageBean.getMessageTitle());
        tv_message_description.setText(messageBean.getMessageDescription());

        bt_select.setVisibility(isEdit ? VISIBLE : GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_select:
                if(bt_select.isSelected()){
                    Log.e(TAG, "setselect false");
                    bt_select.setSelected(false);
                    messageBean.setSelected(false);
                }else {
                    Log.e(TAG, "setselect true");
                    bt_select.setSelected(true);
                    messageBean.setSelected(true);
                }

                break;
        }
    }
}
