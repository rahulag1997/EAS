package com.rahul.eas.feature;

class Transaction_Item
{
    String t_id;
    String p1_id;
    String p2_id;
    String p1_name;
    String p2_name;
    Float amount;
    String type;
    String date;

    Transaction_Item(String t_id, String p1_id, String p2_id, String p1_name, String p2_name, Float amount, String type, String date)
    {
        this.t_id = t_id;
        this.p1_id = p1_id;
        this.p2_id = p2_id;
        this.p1_name = p1_name;
        this.p2_name = p2_name;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

}
