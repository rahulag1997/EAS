package com.rahul.eas.feature;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    // Private Variables
    // Views
    EditText curr_pass_et, new_pass_et, confirm_pass_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setup();
    }

    private void setup() {
        curr_pass_et = findViewById(R.id.change_curr_password_et);
        new_pass_et = findViewById(R.id.change_new_password_et);
        confirm_pass_et = findViewById(R.id.change_confirm_et);

        curr_pass_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                new_pass_et.requestFocus();
                return false;
            }
        });

        new_pass_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                confirm_pass_et.requestFocus();
                return false;
            }
        });

        confirm_pass_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                update();
                return false;
            }
        });
    }

    public void Update(View view) {
        update();
    }

    private void update() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String actual_password = sharedPreferences.getString("Password","");
        String current_password = curr_pass_et.getText().toString();
        String new_password = new_pass_et.getText().toString();
        String confirm_password = confirm_pass_et.getText().toString();

        if(!actual_password.equals(current_password)){
            curr_pass_et.setError("Incorrect Password");
            return;
        }

        if(new_password.equals("")){
            new_pass_et.setError("Password Cannot be Empty");
            return;
        }

        if(!new_password.equals(confirm_password)){
            confirm_pass_et.setError("Passwords do not Match");
            return;
        }

        editor.putString("Password", new_password);
        editor.apply();
        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
