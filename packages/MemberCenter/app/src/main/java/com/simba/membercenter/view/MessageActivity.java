package com.simba.membercenter.view;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.simba.base.base.BaseActivity;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.R;
import com.simba.membercenter.accountDB.MessageBean;
import com.simba.membercenter.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageActivity extends BaseActivity implements  IMessageView{
    private static String TAG = "MainActivity";
    private ViewPager vp_message;
    private MessagePagerAdapter messagePagerAdapter = new MessagePagerAdapter();



    public static void startAcivity(){
        Intent intent = new Intent(MyApplication.getmApplication().getApplicationContext(), MessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getmApplication().getApplicationContext().startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        vp_message = findViewById(R.id.vp_message);
    }

    @Override
    protected void initData() {
       MessagePresenter.getInstance().registerMessageView(this);
       MessagePresenter.getInstance().getMessageList();
        vp_message.setAdapter(messagePagerAdapter );
    }

    @Override
    protected void onDestroy() {
        MessagePresenter.getInstance().unRegisterMessageView();
        super.onDestroy();
    }

    @Override
    public void onLoadAllMessage(List<MessageBean> messageBeanList) {
        Log.e(TAG, "messageBeanList size " + messageBeanList.size());
        messagePagerAdapter.setMessageBeanList(messageBeanList);
        messagePagerAdapter.notifyDataSetChanged();
    }

    class MessagePagerAdapter extends PagerAdapter implements View.OnClickListener{
        private List<MessageBean> messageBeanList;
        private List<View> mViewList;

        public void setMessageBeanList(List<MessageBean> messageBeanList) {
            this.messageBeanList = messageBeanList;
            initViewList();
        }

        private void initViewList(){
            mViewList = new ArrayList<>();
            for (int i = 0; i < messageBeanList.size() / 4 + 1 ; i ++ ){
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.layout_message,null,false);
                TextView tv_message_1 = view.findViewById(R.id.tv_message_1);
                TextView tv_message_2 = view.findViewById(R.id.tv_message_2);
                TextView tv_message_3 = view.findViewById(R.id.tv_message_3);
                TextView tv_message_4 = view.findViewById(R.id.tv_message_4);

                tv_message_1.setTag(i * 4);
                tv_message_1.setText(messageBeanList.get(i * 4).getMessageTitle());

                if(i * 4 + 1 < messageBeanList.size()){
                    tv_message_2.setTag(i * 4 + 1);
                    tv_message_2.setText(messageBeanList.get(i * 4 + 1).getMessageTitle());
                }
                if(i * 4 + 2 < messageBeanList.size()){
                    tv_message_3.setTag(i * 4 + 2);
                    tv_message_3.setText(messageBeanList.get(i * 4 + 2).getMessageTitle());
                }
                if(i * 4 + 3 < messageBeanList.size()){
                    tv_message_4.setTag(i * 4 + 3);
                    tv_message_4.setText(messageBeanList.get(i * 4 + 3).getMessageTitle());
                }
                tv_message_1.setOnClickListener(this);
                tv_message_2.setOnClickListener(this);
                tv_message_3.setOnClickListener(this);
                tv_message_4.setOnClickListener(this);
                mViewList.add(view);
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
}
