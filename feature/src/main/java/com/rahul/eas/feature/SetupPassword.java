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

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_password);

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
                save();
                return false;
            }
        });

        sharedPreferences = this.getSharedPreferences(getString(R.string.SharedPreferencesName), Context.MODE_PRIVATE);
        checkBox.setChecked(sharedPreferences.getBoolean(getString(R.string.Key_UseFingerprint),false));
    }

    public void Save(View view) {
        save();
    }

    private void save() {
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

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(getString(R.string.Key_PasswordValue), pass1);
        editor.putBoolean(getString(R.string.Key_UseFingerprint), checkBox.isChecked());
        editor.apply();

        startActivity(new Intent(this, LoginScreen.class));

        Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
