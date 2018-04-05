package com.rahul.eas.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void Exit(View view) {
        finish();
    }

    public void Login(View view) {
        startActivity(new Intent(this,LoginScreen.class));
    }

    public void Create(View view) {
        startActivity(new Intent(this,CreateAccount.class));
    }
}
