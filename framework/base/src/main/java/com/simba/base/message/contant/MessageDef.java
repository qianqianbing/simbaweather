package com.simba.base.message.contant;

/**
 * @author chefengyun
 */
public interface MessageDef {

    interface Message {
        byte DEFAULT = (byte) 0xb0;

        /**
         * 会员中心消息推送
         */
        byte MEMBER_MSG = DEFAULT + 1;

        /**
         * 会员信息消息推送
         */
        byte MEMBER_INFO = MEMBER_MSG + 1;

        /**
         * (网易)新闻消息推送
         */
        byte NEWS = MEMBER_INFO + 1;
    }

}
