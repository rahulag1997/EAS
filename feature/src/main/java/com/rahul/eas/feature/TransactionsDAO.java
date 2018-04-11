package com.rahul.eas.feature;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface TransactionsDAO {
    @Insert
    void insertAll(Transaction_Item... transaction_items);

    @Query("SELECT * FROM transaction_item WHERE p1_id=:p_id or p2_id=:p_id")
    List<Transaction_Item> getAllTransactions(Integer p_id);


}
