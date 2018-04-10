package com.rahul.eas.feature;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;

public class Accounts extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        FloatingActionButton floatingActionButton= findViewById(R.id.fab);
        floatingActionButton.hide();
    }

    public void Bank(View view) {
        startActivity(new Intent(getApplicationContext(), AccountsView.class).putExtra(getString(R.string.Extra_AccountsView_Type),0));
    }

    public void Creditor(View view) {
        startActivity(new Intent(getApplicationContext(), AccountsView.class).putExtra(getString(R.string.Extra_AccountsView_Type),3));
    }

    public void Debtor(View view) {
        startActivity(new Intent(getApplicationContext(), AccountsView.class).putExtra(getString(R.string.Extra_AccountsView_Type),4));
    }
}
