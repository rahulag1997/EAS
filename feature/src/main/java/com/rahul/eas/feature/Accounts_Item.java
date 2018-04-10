package com.rahul.eas.feature;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
class Accounts_Item
{
    @PrimaryKey
    Integer id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "location")
    String location;

    @ColumnInfo(name = "balance")
    Float balance;

    @ColumnInfo(name = "debit")
    Float debit;

    @ColumnInfo(name = "credit")
    Float credit;

    @ColumnInfo(name = "type")
    String type;


    Accounts_Item(Integer id, String name, String location, Float debit, Float credit, Float balance, String type)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.type = type;
    }

}
