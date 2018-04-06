package com.rahul.eas.feature;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

public class Home extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton floatingActionButton= findViewById(R.id.fab);
        floatingActionButton.hide();
    }
}
