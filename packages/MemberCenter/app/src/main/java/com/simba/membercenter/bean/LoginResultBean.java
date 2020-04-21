package com.simba.membercenter.bean;
/**
 * @description: 账号密码登陆后得到的token结果
 * @author: luojunjie
 * @createDate: 2020/4/19 16:04
 */

public class LoginResultBean {

        private String token;

        private String openId;

        private String userId;

        public String getToken() {
                return token;
        }

        public void setToken(String token) {
                this.token = token;
        }

        public String getOpenId() {
                return openId;
        }

        public void setOpenId(String openId) {
                this.openId = openId;
        }

        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }
}
