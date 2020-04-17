package com.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.simba.membercenter.DB.AccountBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ACCOUNT_BEAN".
*/
public class AccountBeanDao extends AbstractDao<AccountBean, Long> {

    public static final String TABLENAME = "ACCOUNT_BEAN";

    /**
     * Properties of entity AccountBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, int.class, "userId", false, "USERID");
        public final static Property NickName = new Property(2, String.class, "nickName", false, "NICK_NAME");
        public final static Property IsLogined = new Property(3, Boolean.class, "isLogined", false, "IS_LOGINED");
    }


    public AccountBeanDao(DaoConfig config) {
        super(config);
    }
    
    public AccountBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCOUNT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USERID\" INTEGER NOT NULL ," + // 1: userId
                "\"NICK_NAME\" TEXT," + // 2: nickName
                "\"IS_LOGINED\" INTEGER);"); // 3: isLogined
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCOUNT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AccountBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        Boolean isLogined = entity.getIsLogined();
        if (isLogined != null) {
            stmt.bindLong(4, isLogined ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AccountBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        Boolean isLogined = entity.getIsLogined();
        if (isLogined != null) {
            stmt.bindLong(4, isLogined ? 1L: 0L);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AccountBean readEntity(Cursor cursor, int offset) {
        AccountBean entity = new AccountBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nickName
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0 // isLogined
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AccountBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getInt(offset + 1));
        entity.setNickName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsLogined(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AccountBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AccountBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AccountBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
