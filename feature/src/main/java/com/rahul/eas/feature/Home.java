package com.rahul.eas.feature;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

public class Home extends BaseActivity {
    // Private Variables
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton floatingActionButton= findViewById(R.id.fab);
        floatingActionButton.hide();

        sharedPreferences = getSharedPreferences(getString(R.string.SharedPreferencesName), MODE_PRIVATE);
        boolean initialise = sharedPreferences.getBoolean(getString(R.string.Key_InitAccount), true);
        if(initialise)
            init();

    }

    private void init() {
        MyDatabase my_database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "Accounts").allowMainThreadQueries().build();
        AccountsDAO accountsDAO = my_database.accounts_dao();

        Accounts_Item cash = new Accounts_Item(1, "Cash", "-", 0.0f, 0.0f, 0.0f, "Cash");
        Accounts_Item sales = new Accounts_Item(2, "Sales", "-", 0.0f, 0.0f, 0.0f, "Sales");
        Accounts_Item purchase = new Accounts_Item(3, "Purchase", "-", 0.0f, 0.0f, 0.0f, "Purchase");
        Accounts_Item expense = new Accounts_Item(4, "Expense", "-", 0.0f, 0.0f, 0.0f, "Expense");
        accountsDAO.insertAll(cash, sales, purchase, expense);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.Key_InitAccount), false);
        editor.putInt(getString(R.string.Key_A_Count), 5);
        editor.apply();
    }

    public void Accounts(View view) {
        startActivity(new Intent(getApplicationContext(), Accounts.class));
    }
}
