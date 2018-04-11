package com.rahul.eas.feature;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Accounts_Item.class, Transaction_Item.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract AccountsDAO accounts_dao();
    public abstract TransactionsDAO transactions_dao();
}
