package com.simba.membercenter.view;

import com.simba.membercenter.accountDB.MessageBean;

import java.util.List;

/**
 * @description:
 * @author: luojunjie
 * @createDate: 2020/4/19 16:04
 */

// 消息增删的view
public interface IMessageView {

    void onLoadAllMessage(List<MessageBean> messageBeanList);

}
