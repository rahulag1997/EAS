package com.rahul.eas.feature;

import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean initialise = sharedPreferences.getBoolean("Init", true);
        if(initialise)
            init();

    }

    private void init() {
        // TODO Implement this
        // Init Cash, Sales, Purchase, Expense
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Init", false);
    }

    public void Accounts(View view) {
        startActivity(new Intent(getApplicationContext(), Accounts.class));
    }
}
