package com.simba.message.protocol.cloud;

/**
 * <p>
 * 表 3-1 消息体数据包结构和定义
 * 起始字节     定义            数据类型      描述及要求
 * 0-1          起始符          BYTE2         固定为 0x0D、0x0E。
 * 2            上下行标识      BYTE          定义详见表 3-2
 * 3            消息类型        BYTE          如 会员中心消息推送，设备激活推送，应用商城上架新品推送
 * 4            消息归属        BYTE          定义详见表 3-3
 * 5            消息等级        BYTE          0 - 10? 如 高等级消息覆盖低等级消息，按实际需求定义
 * 6-7          消息长度        BYTE2         消息体的 总字节数，固定为 2 字节， 有效值范围为 0-65535。注：数据采用大端存储，如：300 -> 0x012c -> {0x01 0x2c}
 * N            消息体          BYTE[n]       依据消息类型定义，具体格式详见第 4 章说明。
 * N+1          校验码          BYTE          采用 BCC（异或校验法），校验范围从上下行标识的第一字节开始，和后一字节异或， 直到校验码前一字节为止， 校验码占用一个字节
 * </p>
 *
 * <p>
 * 表 3-2 上下行标识 {@link UpDownLink}
 * 标识码       定义                  消息发起方
 * 0xa1         上行                  Vehicle
 * 0xa2         下行                  Cloud
 * 0xa3         心跳保活              Vehicle
 * </p>
 *
 * <p>
 * 表 3-3 消息归属 {@link MessageOwner}
 * 标识码       定义                  说明
 * 0xc1         语音消息              表示语音提醒消息
 * 0xc2         通知消息              表示通知消息
 * 0xc3         AI卡片                表示 AI 卡片展示消息(不在主界面，则转化为通知消息？)
 * 0xc4         弹出框                表示霸屏弹出框消息
 * 0xc5         Widget                表示 Widget 控件展示消息
 * 0xc6         App                   表示透传给 App 模块自己处理
 * 0xc7         消息反馈              表示车机端已收到云端消息，回应握手消息
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
     * owner   1
     * type    1
     * level   1
     * length  2
     * BCC     1
     */
    int MIN_LEN = 9;

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
