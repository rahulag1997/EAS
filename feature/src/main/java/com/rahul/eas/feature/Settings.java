package com.rahul.eas.feature;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Settings extends BaseActivity {

    // Private Variables
    // Views
    private CheckBox fingerprint_cb;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        FloatingActionButton floatingActionButton= findViewById(R.id.fab);
        floatingActionButton.hide();

        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setup();
    }

    private void setup() {
        fingerprint_cb = findViewById(R.id.settings_fingerprint_cb);

        fingerprint_cb.setChecked(sharedPreferences.getBoolean("Fingerprint",false));

        fingerprint_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("Fingerprint",fingerprint_cb.isChecked());
                editor.apply();
            }
        });
    }

//    public void SAVE(View view)
//    {
//        editor.putBoolean("Fingerprint",fingerprint_cb.isChecked());
//        editor.apply();
//        finish();
//
//    }

    public void ChangePass(View view) {
        startActivity(new Intent(this, ChangePassword.class));
    }

    public void ClearData(View view) {
        //TODO Implement this
    }
}
