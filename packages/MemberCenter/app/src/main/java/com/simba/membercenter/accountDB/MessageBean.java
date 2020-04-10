package com.simba.membercenter.accountDB;

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

    @Property(nameInDb = "TIME")
    private Long time;


    @Property(nameInDb = "MESSAGE_TITLE")
    private String messageTitle;


    @Property(nameInDb = "MESSAGE_DESCRIPTION")
    private String messageDescription ;

    @Transient
    private int tempUsageCount; // not persisted
    
    @Keep
    public MessageBean( Long time, String messageTitle,
            String messageDescription) {
        
        this.time = time;
        this.messageTitle = messageTitle;
        this.messageDescription = messageDescription;
    }

    @Keep
    public MessageBean() {
    }

    @Generated(hash = 127621942)
    public MessageBean(Long id, Long time, String messageTitle,
            String messageDescription) {
        this.id = id;
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

    public Long getTime() {        return time; }

    public void setTime(Long time) {
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
}
