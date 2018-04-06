package com.rahul.eas.feature;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Bank extends BaseActivity {

    // Private Variables
    private ArrayList<Accounts_Item> data;
    Accounts_List_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        setFAB();

        data=new ArrayList<>();

        ListView listView = findViewById(R.id.list);
        adapter = new Accounts_List_Adapter(this, data);
        listView.setAdapter(adapter);
    }

    private void setFAB() {
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewAccount.class).putExtra("Type",0));
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        data.clear();
        getData();
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        // TODO Get Data
    }
}
