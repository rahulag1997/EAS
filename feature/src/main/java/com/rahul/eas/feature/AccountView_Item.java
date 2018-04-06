package com.rahul.eas.feature;

class AccountView_Item
{
    String id;
    String name;
    Float debit;
    Float credit;
    Float balance;
    String type;

    AccountView_Item(String id, String name, Float debit, Float credit, Float balance, String type)
    {
        this.id = id;
        this.name = name;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.type = type;
    }
}
