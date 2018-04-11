package com.rahul.eas.feature;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
class Transaction_Item
{
    @PrimaryKey
    Integer t_id;

    @ColumnInfo(name = "p1_id")
    Integer p1_id;

    @ColumnInfo(name = "p1_name")
    String p1_name;

    @ColumnInfo(name = "p2_id")
    Integer p2_id;

    @ColumnInfo(name = "p2_name")
    String p2_name;

    @ColumnInfo(name = "amount")
    Float amount;

    @ColumnInfo(name = "type")
    String type;

    @ColumnInfo(name = "date")
    String date;

    Transaction_Item(Integer t_id, Integer p1_id, String p1_name, Integer p2_id, String p2_name, Float amount, String type, String date)
    {
        this.t_id = t_id;
        this.p1_id = p1_id;
        this.p1_name = p1_name;
        this.p2_id = p2_id;
        this.p2_name = p2_name;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

}
