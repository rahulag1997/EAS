package com.rahul.eas.feature;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

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
    }

    public void SAVE(View view)
    {
        editor.putBoolean("Fingerprint",fingerprint_cb.isChecked());
        editor.apply();
        finish();

    }
}
