package com.rahul.eas.feature;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Accounts_Item.class, version = 1)
public abstract class Accounts_Item_Database extends RoomDatabase {
    public abstract Accounts_Item_DAO accounts_item_dao();
}
