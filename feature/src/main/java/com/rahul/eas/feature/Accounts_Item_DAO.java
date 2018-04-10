package com.rahul.eas.feature;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface Accounts_Item_DAO {
    @Query("SELECT * FROM accounts_item")
    List<Accounts_Item> getAllAccounts();

    @Query("SELECT * FROM accounts_item WHERE type=\'Bank\'")
    List<Accounts_Item> getAllBankAccounts();

    @Query("SELECT * FROM accounts_item WHERE type=\'Cash\'")
    List<Accounts_Item> getAllCashAccounts();

    @Query("SELECT * FROM accounts_item WHERE type=\'Expense\'")
    List<Accounts_Item> getAllExpenseAccounts();

    @Query("SELECT * FROM accounts_item WHERE type=\'Creditor\'")
    List<Accounts_Item> getAllCreditorAccounts();

    @Query("SELECT * FROM accounts_item WHERE type=\'Debtor\'")
    List<Accounts_Item> getAllDebtorAccounts();

    @Query("SELECT * FROM accounts_item WHERE type=\'Sales\'")
    List<Accounts_Item> getAllSalesAccounts();

    @Insert
    void insertAll(Accounts_Item... accounts_items);
}
