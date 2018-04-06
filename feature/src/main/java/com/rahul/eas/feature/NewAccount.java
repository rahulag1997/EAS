package com.rahul.eas.feature;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewAccount extends BaseActivity {
    private SharedPreferences sharedPreferences;
    EditText name_et, amount_et, location_et;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.hide();

        setup();
    }

    private void setup() {

        //need for SHOW_DIALOG and SHOW_REPEAT
        sharedPreferences=this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        View name_line = findViewById(R.id.new_account_name_line);
        TextView name_tv = name_line.findViewById(R.id.et_line_tv);
        name_tv.setText("Name");
        name_et = name_line.findViewById(R.id.et_line_et);
        name_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                location_et.requestFocus();
                return false;
            }
        });

        View location_line = findViewById(R.id.new_account_location_line);
        TextView location_tv = location_line.findViewById(R.id.et_line_tv);
        location_tv.setText("Location");
        location_et = location_line.findViewById(R.id.et_line_et);
        location_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                amount_et.requestFocus();
                return false;
            }
        });

        View amount_line = findViewById(R.id.new_account_amount_line);
        TextView amount_tv = amount_line.findViewById(R.id.et_line_number_tv);
        amount_tv.setText("Opening Balance");
        amount_et = amount_line.findViewById(R.id.et_line_number_et);
        amount_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                submit();
                return false;
            }
        });



        String[] acc_types = this.getResources().getStringArray(R.array.Acc_Types);
        spinner = findViewById(R.id.account_type_spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, acc_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //For selecting default type
        if(getIntent().hasExtra("Type"))
        {
            spinner.setSelection(getIntent().getIntExtra("Type",0));
        }
    }

    public void Submit(View view) {
        submit();
    }

    private void submit() {
        int id = sharedPreferences.getInt("Account_Number",1);
        String name = name_et.getText().toString();
        String location = location_et.getText().toString();
        String ob = amount_et.getText().toString();
        Float ob_val;
        Float debit = 0.0f;
        Float credit = 0.0f;
        String type = spinner.getSelectedItem().toString();
        // TODO Check Name Existence
//        if(exists){
//            name_et.setError("Already Exists");
//            return;
//        }

        if(location.equals(""))
            location = "None";

        if(ob.equals(""))
            ob_val = 0.0f;
        else
            ob_val = Float.parseFloat(ob);

        // TODO Add into account
        Accounts_Item item = new Accounts_Item(Integer.toString(id), name, location, debit, credit, ob_val, type);

        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("Account_Numebr", id+1);
        Toast.makeText(this, "Account Added Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }
}
