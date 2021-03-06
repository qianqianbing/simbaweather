package com.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.simba.membercenter.bean.DeviceStateBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEVICE_STATE_BEAN".
*/
public class DeviceStateBeanDao extends AbstractDao<DeviceStateBean, Long> {

    public static final String TABLENAME = "DEVICE_STATE_BEAN";

    /**
     * Properties of entity DeviceStateBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property DeviceId = new Property(1, int.class, "deviceId", false, "DEVICE_ID");
        public final static Property ActivationState = new Property(2, Boolean.class, "activationState", false, "ACTIVATION_STATE");
    }


    public DeviceStateBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DeviceStateBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEVICE_STATE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"DEVICE_ID\" INTEGER NOT NULL ," + // 1: deviceId
                "\"ACTIVATION_STATE\" INTEGER);"); // 2: activationState
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEVICE_STATE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DeviceStateBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getDeviceId());
 
        Boolean activationState = entity.getActivationState();
        if (activationState != null) {
            stmt.bindLong(3, activationState ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DeviceStateBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getDeviceId());
 
        Boolean activationState = entity.getActivationState();
        if (activationState != null) {
            stmt.bindLong(3, activationState ? 1L: 0L);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DeviceStateBean readEntity(Cursor cursor, int offset) {
        DeviceStateBean entity = new DeviceStateBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // deviceId
            cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0 // activationState
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DeviceStateBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDeviceId(cursor.getInt(offset + 1));
        entity.setActivationState(cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DeviceStateBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DeviceStateBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DeviceStateBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
