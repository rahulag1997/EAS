package com.rahul.eas.feature;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AccountsView extends BaseActivity {

    // Private Variables
    private ArrayList<Accounts_Item> data;
    private Accounts_List_Adapter adapter;
    private Integer acc_type;
    private String acc_type_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        acc_type = getIntent().getIntExtra(getString(R.string.Extra_AccountsView_Type), 0);
        String[] acc_types = this.getResources().getStringArray(R.array.Acc_Types);
        acc_type_str = acc_types[acc_type];

        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(acc_type_str);


        setFAB();
        data = new ArrayList<>();

        ListView listView = findViewById(R.id.list);
        adapter = new Accounts_List_Adapter(this, data);
        listView.setAdapter(adapter);
    }

    private void setFAB() {
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewAccount.class).putExtra(getString(R.string.Extra_NewAcc_Type),acc_type));
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getData();
    }

    private void getData() {
        data.clear();

        MyDatabase my_database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "Accounts").allowMainThreadQueries().build();
        AccountsDAO accountsDAO = my_database.accounts_dao();
        data.addAll(accountsDAO.getAllTypeAccounts(acc_type_str));

        adapter.notifyDataSetChanged();
        Float total = 0.0f, c_total = 0.0f, d_total = 0.0f;
        for (int i=0; i<data.size();i++)
        {
            Accounts_Item item = data.get(i);
            total+=item.balance;
            c_total+=item.credit;
            d_total+=item.debit;

        }
        DecimalFormat dec_format=new DecimalFormat("#");
        ((TextView)findViewById(R.id.accounts_footer_btotal_tv)).setText(dec_format.format(total));
        ((TextView)findViewById(R.id.accounts_footer_ctotal_tv)).setText(dec_format.format(c_total));
        ((TextView)findViewById(R.id.accounts_footer_dtotal_tv)).setText(dec_format.format(d_total));

    }
}
