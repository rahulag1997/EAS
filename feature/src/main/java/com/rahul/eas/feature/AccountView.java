package com.rahul.eas.feature;

import android.arch.persistence.room.Room;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class AccountView extends BaseActivity {

    // Private Variables
    private Integer account_id;
    private ArrayList<AccountView_Item> data;
    private ArrayList<Transaction_Item> original_data;
    private AccountView_List_Adapter adapter;

    private MyDatabase my_database;
    private TransactionsDAO transactionsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_view);
        FloatingActionButton floatingActionButton= findViewById(R.id.fab);
        floatingActionButton.hide();

        account_id = getIntent().getIntExtra(getString(R.string.Extra_AccView_Id), 0);
        String name = getIntent().getStringExtra(getString(R.string.Extra_AccView_Name));
        String type = getIntent().getStringExtra(getString(R.string.Extra_AccView_Type));

        Log.d("ID",""+account_id);
        Log.d("Name", name);
        Log.d("type",type);
        
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(name);

        data=new ArrayList<>();
        original_data = new ArrayList<>();
        ListView list = findViewById(R.id.list);
        adapter = new AccountView_List_Adapter(this, data);
        list.setAdapter(adapter);

        my_database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "Accounts").allowMainThreadQueries().build();
        transactionsDAO  = my_database.transactions_dao();

        getData();

    }

    private void getData() {
        //TODO GET DATA
        original_data = (ArrayList<Transaction_Item>) transactionsDAO.getAllTransactions(account_id);
        Log.d("SIze",""+original_data.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart()
    {
        data.clear();
        getData();
        super.onRestart();
    }
}
