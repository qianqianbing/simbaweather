package com.simba.membercenter.DB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by admin on 2018/5/14.
 */
@Entity
public class MessageBean {

    @Id(autoincrement = true)
    private Long id;

    //消息id
    @Property(nameInDb = "MESSAGE_ID")
    private int messageId;

    //用户id，不同的用户id需要区分
    @Property(nameInDb = "USER_NAME")
    private String userName;

    @Property(nameInDb = "TIME")
    private int time;


    @Property(nameInDb = "MESSAGE_TITLE")
    private String messageTitle;


    @Property(nameInDb = "MESSAGE_DESCRIPTION")
    private String messageDescription ;

    @Transient
    private boolean isSelected; // not persisted
    
    @Keep
    public MessageBean(String userName, int time, String messageTitle,
            String messageDescription) {
        this.userName = userName;
        this.time = time;
        this.messageTitle = messageTitle;
        this.messageDescription = messageDescription;
    }

    @Keep
    public MessageBean() {
    }

    @Generated(hash = 218509621)
    public MessageBean(Long id, int messageId, String userName, int time,
            String messageTitle, String messageDescription) {
        this.id = id;
        this.messageId = messageId;
        this.userName = userName;
        this.time = time;
        this.messageTitle = messageTitle;
        this.messageDescription = messageDescription;
    }

    public String getMessageTitle() {
        return this.messageTitle;
    }
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public int getTime() {        return time; }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }



    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
