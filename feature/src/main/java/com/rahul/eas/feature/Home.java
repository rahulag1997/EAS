package com.rahul.eas.feature;

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
        // TODO Implement this
        // Init Cash, Sales, Purchase, Expense
        return;
        // Set Init to false
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(getString(R.string.Key_InitAccount), false);
//        editor.apply();
    }

    public void Accounts(View view) {
        startActivity(new Intent(getApplicationContext(), Accounts.class));
    }
}
