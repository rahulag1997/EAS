package com.rahul.eas.feature;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetupPassword extends AppCompatActivity {

    // Private Variables
    // Views
    private EditText password_et, confirm_et;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_password);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle("Setup Password");

        setup();
    }

    private void setup() {
        password_et = findViewById(R.id.create_password_et);
        confirm_et = findViewById(R.id.create_confirm_et);
        checkBox = findViewById(R.id.create_cb);

        password_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                confirm_et.requestFocus();
                return false;
            }
        });

        confirm_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                create();
                return false;
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        checkBox.setChecked(sharedPreferences.getBoolean("Fingerprint",false));
    }

    public void Create(View view) {
        create();
    }

    private void create() {
        String pass1 = password_et.getText().toString();
        String pass2 = confirm_et.getText().toString();


        if(pass1.equals("")){
            // Empty Password
            password_et.setError("Password cannot be empty");
            password_et.requestFocus();
            return;
        }

        if(!pass1.equals(pass2)){
            // Passwords do not match
            confirm_et.setError("Passwords do not match");
            confirm_et.requestFocus();
            return;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Password",pass1);
        editor.putBoolean("Fingerprint",checkBox.isChecked());
        editor.apply();

        startActivity(new Intent(this,LoginScreen.class));

        Toast.makeText(this, "Account Created Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }
}
