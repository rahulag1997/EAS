package com.rahul.eas.feature;

class AccountView_Item
{
    int t_id;
    String name;
    Float debit;
    Float credit;
    Float balance;
    String type;

    AccountView_Item(int t_id, String name, Float debit, Float credit, Float balance, String type)
    {
        this.t_id = t_id;
        this.name = name;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.type = type;
    }
}
