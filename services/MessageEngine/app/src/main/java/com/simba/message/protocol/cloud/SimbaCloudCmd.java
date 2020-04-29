package com.simba.message.protocol.cloud;

/**
 * <p>
 * 表 3-1 消息体数据包结构和定义
 * 起始字节     定义            数据类型      描述及要求
 * 0-1          起始符          BYTE2         固定为 0x0D、0x0E。
 * 2            上下行标识      BYTE          定义详见表 3-2
 * 3-4          消息类型        BYTE2         如 会员中心消息推送，设备激活推送，应用商城上架新品推送等。 注：数据采用大端存储，如：300 -> 0x012c -> {0x01 0x2c}
 * 5-6          消息长度        BYTE2         消息体的 总字节数，固定为 2 字节， 有效值范围为 0-65535。
 * N            消息体          BYTE[n]       依据消息类型定义的Json字符串，数据编码 UTF-8，具体Json格式详见后续说明。
 * N+1          校验码          BYTE          采用 BCC（异或校验法），校验范围从上下行标识的第一字节开始，和后一字节异或， 直到校验码前一字节为止， 校验码占用一个字节
 * </p>
 *
 * <p>
 * 表 3-2 上下行标识 {@link UpDownLink}
 * 标识码       定义                  消息发起方   说明
 * 0xa1         上行                  Vehicle      车机给云端发送消息
 * 0xa2         下行                  Cloud        云端给车机下发消息
 * 0xa3         心跳保活              Vehicle      车机给云端发送心跳
 * 0xa4         消息反馈              Vehicle      车机给云端发送消息反馈
 * </p>
 *
 * @author chefengyun
 *
 */
public interface SimbaCloudCmd {

    /**
     * cmd 最小长度
     * header  2
     * updown  1
     * type    2
     * length  2
     * BCC     1
     */
    int MIN_LEN = 8;

    byte HEADER = 0x0d;
    byte HEADEREX = 0x0e;

    /**
     * 上下行
     */
    interface UpDownLink {
        byte App2Cloud = (byte) 0xa1;
        byte Cloud2App = (byte) 0xa2;
        byte ACK = (byte) 0xa3;
    }

    /**
     * 消息归属
     */
    interface MessageOwner {
        byte DEFAULT = (byte) 0xc0;
        byte VOICE = (byte) 0xc1;
        byte NOTIFICATION = (byte) 0xc2;
        byte AI_CARD = (byte) 0xc3;
        byte DIALOG = (byte) 0xc4;
        byte WIDGET = (byte) 0xc5;
        byte App = (byte) 0xc6;
        byte RESPONSE = (byte) 0xc7;
    }

}
