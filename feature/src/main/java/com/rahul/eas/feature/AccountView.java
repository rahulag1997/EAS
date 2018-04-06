package com.rahul.eas.feature;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AccountView extends BaseActivity {

    // Private Variables
    private String account_id;
    private ArrayList<AccountView_Item> data;
    AccountView_List_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_view);
        FloatingActionButton floatingActionButton= findViewById(R.id.fab);
        floatingActionButton.hide();

        account_id = getIntent().getStringExtra("Id");
        String name = getIntent().getStringExtra("Name");
        
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(name);

        data=new ArrayList<>();
        ListView list = findViewById(R.id.list);
        adapter = new AccountView_List_Adapter(this, data);
        list.setAdapter(adapter);
        getData();

    }

    private void getData() {
        //TODO GET DATA
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