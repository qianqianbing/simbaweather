package com.simba.membercenter.bean;
/**
 * @description: 实名认证的结果
 * @author: luojunjie
 * @createDate: 2020/4/19 16:04
 */

public class UserCertificationBean {
        //实名认证：0否 1是
        private int certificated;

        public int getCertificated() {
                return certificated;
        }

        public void setCertificated(int certificated) {
                this.certificated = certificated;
        }
}
