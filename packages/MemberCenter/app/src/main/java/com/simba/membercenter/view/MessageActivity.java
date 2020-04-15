package com.simba.membercenter.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.simba.base.base.BaseActivity;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.R;
import com.simba.membercenter.accountDB.MessageBean;
import com.simba.membercenter.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends Activity implements  IMessageView, View.OnClickListener{

    private static String TAG = "MainActivity";
    private ViewPager vp_message;
    private RelativeLayout rl_back, rl_nomessage;
    private TextView tv_back_title;
    private MessagePagerAdapter messagePagerAdapter = new MessagePagerAdapter();
    private LinearLayout ll_viewGroup;
    private ImageView[] imageViews;
    private Button bt_select_all,bt_delete,bt_edit;

    private boolean editState = false;
    private List<MessageBean> messageBeanList;

    public static void startAcivity(){
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), MessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_message);
        vp_message = findViewById(R.id.vp_message);
        rl_nomessage = findViewById(R.id.rl_nomessage);
        rl_back = findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        bt_select_all = findViewById(R.id.bt_select_all);
        bt_select_all.setOnClickListener(this);
        bt_delete = findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(this);
        bt_edit = findViewById(R.id.bt_edit);
        bt_edit.setOnClickListener(this);

        tv_back_title = findViewById(R.id.tv_back_title);
        tv_back_title.setText(R.string.message);

        ll_viewGroup = findViewById(R.id.ll_viewGroup);

        MessagePresenter.getInstance().registerMessageView(this);
        MessagePresenter.getInstance().getMessageList();
        vp_message.setAdapter(messagePagerAdapter );
        vp_message.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageViews.length; i++) {
                    if (i == position) {
                        //表示当前图片
                        imageViews[i].setBackgroundResource(R.drawable.icon_banner_selected);
                    } else {
                        imageViews[i].setBackgroundResource(R.drawable.icon_banner_unselected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        MessagePresenter.getInstance().unRegisterMessageView();
        super.onDestroy();
    }

    @Override
    public void onLoadAllMessage(List<MessageBean> messageBeanList) {
        if(messageBeanList == null || messageBeanList.size() == 0){
            vp_message.setVisibility(View.GONE);
            rl_nomessage.setVisibility(View.VISIBLE);
            return;
        }
        vp_message.setVisibility(View.VISIBLE);
        rl_nomessage.setVisibility(View.GONE);
        Log.e(TAG, "messageBeanList size " + messageBeanList.size());
        messagePagerAdapter.setMessageBeanList(messageBeanList);
        messagePagerAdapter.notifyDataSetChanged();
        initPointer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_select_all:
                if(bt_select_all.getText().toString().equals("全选")){
                    for(MessageBean messageBean : messageBeanList){
                        messageBean.setSelected(true);
                    }
                    bt_select_all.setText(R.string.select_none);
                }else {
                    for(MessageBean messageBean : messageBeanList){
                        messageBean.setSelected(false);
                    }
                    bt_select_all.setText(R.string.select_all);
                }

                messagePagerAdapter.initViewList();
                messagePagerAdapter.notifyDataSetChanged();
                break;
            case R.id.bt_delete:
                int count = 0;
                for (MessageBean messageBean : messageBeanList){
                    if (messageBean.isSelected()){
                        count ++ ;
                    }
                }
                showDeleteDialog(count);
                break;
            case R.id.bt_edit:
                if(editState){
                    editState = false;
                    rl_back.setVisibility(View.VISIBLE);
                    bt_select_all.setVisibility(View.GONE);
                    bt_delete.setVisibility(View.GONE);
                    bt_edit.setText(R.string.edit);
                }else {
                    editState = true;
                    rl_back.setVisibility(View.GONE);
                    bt_select_all.setVisibility(View.VISIBLE);
                    bt_delete.setVisibility(View.VISIBLE);
                    bt_edit.setText(R.string.cancel);
                }
                break;
        }
    }

    private void showDeleteDialog(int number){

            final AlertDialog.Builder builder=new AlertDialog.Builder(this);  //建立一个对象
            builder.setTitle("是否确认删除"+ number +"条消息");
            //正面的按钮
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MessagePresenter.getInstance().deleteMessage(messageBeanList);
                }
            });
            //反面的按钮
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();

    }
    class MessagePagerAdapter extends PagerAdapter implements View.OnClickListener{


        private List<View> mViewList;

        public void setMessageBeanList(List<MessageBean> messageBeans) {
            messageBeanList = messageBeans;
            initViewList();
        }

        public void initViewList(){
            mViewList = new ArrayList<>();
            for (int i = 0;   i < messageBeanList.size() / 4 + 1 ; i ++ ){
                Log.e(TAG, "init view  " +  i );
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.layout_message,null,false);
                MessageLayoutView ml_message0 = view.findViewById(R.id.ml_message0);
                MessageLayoutView ml_message1 = view.findViewById(R.id.ml_message1);
                MessageLayoutView ml_message2 = view.findViewById(R.id.ml_message2);
                MessageLayoutView ml_message3 = view.findViewById(R.id.ml_message3);
                if( i < messageBeanList.size() / 4){
                    ml_message0.setMessageInfo(messageBeanList.get( i*4 ));
                    ml_message0.setVisibility(View.VISIBLE);

                    ml_message1.setMessageInfo(messageBeanList.get( i*4 + 1));
                    ml_message1.setVisibility(View.VISIBLE);

                    ml_message2.setMessageInfo(messageBeanList.get( i*4 + 2));
                    ml_message2.setVisibility(View.VISIBLE);

                    ml_message3.setMessageInfo(messageBeanList.get( i*4 + 3));
                    ml_message3.setVisibility(View.VISIBLE);

                }else {
                    ml_message0.setMessageInfo(messageBeanList.get( i*4 ));
                    ml_message0.setVisibility(View.VISIBLE);
                    if(messageBeanList.size() % 4 > 1){
                        ml_message1.setMessageInfo(messageBeanList.get( i*4 + 1));
                        ml_message1.setVisibility(View.VISIBLE);
                    }
                    if(messageBeanList.size() % 4 > 2){
                        ml_message2.setMessageInfo(messageBeanList.get( i*4 + 2));
                        ml_message2.setVisibility(View.VISIBLE);
                    }
                    if(messageBeanList.size() % 4 > 3){
                        ml_message3.setMessageInfo(messageBeanList.get( i*4 + 3));
                        ml_message3.setVisibility(View.VISIBLE);
                    }
                }

                mViewList.add(view);
                if((i + 1)* 4 == messageBeanList.size()){
                    break;
                }
            }
        }

        @Override
        public int getCount() {
            return messageBeanList.size() / 4 + 1;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public void onClick(View v) {
            int index = (int)v.getTag();
            Log.e(TAG, "message click index " + index);
            messageBeanList.remove(index);
            initViewList();
            notifyDataSetChanged();
        }
    }

    private void initPointer() {
        //有多少个界面就new多长的数组
        ll_viewGroup.removeAllViews();
        imageViews = new ImageView[messagePagerAdapter.getCount()];
        for (int i = 0; i < imageViews.length; i++) {
            //new新的ImageView控件
            ImageView imageView = new ImageView(this);
            //设置控件的宽高
           // imageView.setLayoutParams(new ViewGroup.LayoutParams(25, 25));
            //设置控件的padding属性

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(lp);
            imageViews[i] = imageView;
            //初始化第一个page页面的图片的原点为选中状态
            if (i == 0) {
                //表示当前图片
                imageViews[i].setBackgroundResource(R.drawable.icon_banner_selected);
                /**
                 * 在java代码中动态生成ImageView的时候
                 * 要设置其BackgroundResource属性才有效
                 * 设置ImageResource属性无效
                 */
            } else {
                imageViews[i].setBackgroundResource(R.drawable.icon_banner_unselected);
            }
            //把new出来的ImageView控件添加到线性布局中
            ll_viewGroup.addView(imageViews[i]);
        }
    }
}
