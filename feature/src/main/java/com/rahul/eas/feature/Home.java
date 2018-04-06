package com.rahul.eas.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

public class Home extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton floatingActionButton= findViewById(R.id.fab);
        floatingActionButton.hide();
    }

    public void Accounts(View view) {
        startActivity(new Intent(getApplicationContext(), Accounts.class));
    }
}
