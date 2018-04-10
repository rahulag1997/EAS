package com.rahul.eas.feature;

class Accounts_Item
{
    Integer id;
    String name;
    private String location;
    Float balance;
    Float debit;
    Float credit;
    private String type;

    Accounts_Item(Integer id, String name, Float debit, Float credit, Float balance)
    {
        this.id = id;
        this.name = name;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
    }

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
