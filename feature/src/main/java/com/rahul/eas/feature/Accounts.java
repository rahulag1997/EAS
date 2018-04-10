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
        startActivity(new Intent(getApplicationContext(), Bank.class));
    }
}
