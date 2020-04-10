package com.simba.membercenter.presenter;

import com.greendao.gen.MessageBeanDao;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.view.IMessageView;

public class MessagePresenter {
    IMessageView mMessageView;
    private MessageBeanDao mMessageBeanDao;

    private static MessagePresenter mMessagePresenter ;
    public static MessagePresenter getInstance (){
        if(mMessagePresenter  == null){
            mMessagePresenter = new MessagePresenter();
        }
        return mMessagePresenter;
    }

    private MessagePresenter() {
        mMessageBeanDao = MyApplication.getmApplication().getDaoSession().getMessageBeanDao();
    }

    public void registerMessageView(IMessageView messageView){
        mMessageView = messageView;
    }

    public void unRegisterMessageView(){
        mMessageView = null;
    }

    public void getMessageList(){
        if(mMessageView != null ){
            mMessageView.onLoadAllMessage(mMessageBeanDao.loadAll());
        }
    }
}
