package com.simba.membercenter.view;

import com.simba.membercenter.DB.AccountBean;

/**
 * @description:
 * @author: luojunjie
 * @createDate: 2020/4/19 16:04
 */

// 设备是否已经激活的回调
public interface IUserInfoView {

    void onLoadSucceed(AccountBean accountBean);
    void onLoadFailed();

}
