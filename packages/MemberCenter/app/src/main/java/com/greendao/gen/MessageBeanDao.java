package com.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.simba.membercenter.DB.MessageBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MESSAGE_BEAN".
*/
public class MessageBeanDao extends AbstractDao<MessageBean, Long> {

    public static final String TABLENAME = "MESSAGE_BEAN";

    /**
     * Properties of entity MessageBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MessageId = new Property(1, int.class, "messageId", false, "MESSAGE_ID");
        public final static Property UserName = new Property(2, String.class, "userName", false, "USER_NAME");
        public final static Property Time = new Property(3, int.class, "time", false, "TIME");
        public final static Property MessageTitle = new Property(4, String.class, "messageTitle", false, "MESSAGE_TITLE");
        public final static Property MessageDescription = new Property(5, String.class, "messageDescription", false, "MESSAGE_DESCRIPTION");
    }


    public MessageBeanDao(DaoConfig config) {
        super(config);
    }
    
    public MessageBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MESSAGE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"MESSAGE_ID\" INTEGER NOT NULL ," + // 1: messageId
                "\"USER_NAME\" TEXT," + // 2: userName
                "\"TIME\" INTEGER NOT NULL ," + // 3: time
                "\"MESSAGE_TITLE\" TEXT," + // 4: messageTitle
                "\"MESSAGE_DESCRIPTION\" TEXT);"); // 5: messageDescription
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MESSAGE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MessageBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMessageId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
        stmt.bindLong(4, entity.getTime());
 
        String messageTitle = entity.getMessageTitle();
        if (messageTitle != null) {
            stmt.bindString(5, messageTitle);
        }
 
        String messageDescription = entity.getMessageDescription();
        if (messageDescription != null) {
            stmt.bindString(6, messageDescription);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MessageBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMessageId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
        stmt.bindLong(4, entity.getTime());
 
        String messageTitle = entity.getMessageTitle();
        if (messageTitle != null) {
            stmt.bindString(5, messageTitle);
        }
 
        String messageDescription = entity.getMessageDescription();
        if (messageDescription != null) {
            stmt.bindString(6, messageDescription);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MessageBean readEntity(Cursor cursor, int offset) {
        MessageBean entity = new MessageBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // messageId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userName
            cursor.getInt(offset + 3), // time
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // messageTitle
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // messageDescription
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MessageBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMessageId(cursor.getInt(offset + 1));
        entity.setUserName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.getInt(offset + 3));
        entity.setMessageTitle(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMessageDescription(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MessageBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MessageBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MessageBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
