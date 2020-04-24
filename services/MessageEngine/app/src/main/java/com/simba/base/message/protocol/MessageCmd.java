package com.simba.base.message.protocol;

import com.simba.base.message.util.DataUtils;

/**
 * @author chefengyun
 */
public class MessageCmd {

    public static final String CHARSET = "ISO-8859-1";

    /**
     * App 回应 Cloud 消息已收到
     */
    public static final int MSG_STATUS_ACK = 100;


    public enum Num {
        ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
        SIXTEEN(16), SEVENTEEN(17), EIGHTEEN(18),
        TWENTY(20), TWENTY_TWO(22), TWENTY_THREE(23), TWENTY_FORE(24),
        FOURTY_ONE(41),
        FIFTY_SIX(56),
        EIGHTY_EIGHT(88),
        ONE_HUNDRED_AND_FIFTY_FOUR(154),
        ONE_HUNDRED_AND_FIFTY_FIVE(155);
        private byte value;

        Num(int value) {
            this.value = (byte) value;
        }

        public byte byteValue() {
            return value;
        }

        public int intValue() {
            // 大于127 使用int值时需要转换为无符号
            return DataUtils.getUnsignedByte(value);
        }

        @Override
        public String toString() {
            return DataUtils.byte2HexString(value);
        }
    }
}
