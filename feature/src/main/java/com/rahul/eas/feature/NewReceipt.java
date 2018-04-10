package com.rahul.eas.feature;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class NewReceipt extends BaseActivity {
    // Private Variables
    // Views
    private TextView date_tv;
    private EditText amount_et;
    private AutoCompleteTextView from_actv, to_actv;

    private ArrayList<String> from_list, to_list;
    private ArrayList<Integer> from_list_id, to_list_id;
    private ArrayAdapter<String> from_adapter, to_adapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String from;
    private String date;
    private String amount;
    private Integer from_id, to_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_receipt);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.hide();

        setup();
    }

    private void setup() {
        sharedPreferences = this.getSharedPreferences(getString(R.string.SharedPreferencesName), Context.MODE_PRIVATE);

        date_tv = findViewById(R.id.new_receipt_date_tv);

        amount_et = findViewById(R.id.new_receipt_amount_et);

        amount_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                submit();
                return false;
            }
        });

        from_actv = findViewById(R.id.new_receipt_name_actv);

        from_actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                to_actv.requestFocus();
            }
        });


        to_actv = findViewById(R.id.new_receipt_to_actv);

        to_actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                amount_et.requestFocus();
            }
        });

        from_list = new ArrayList<>();
        from_list_id = new ArrayList<>();
        to_list = new ArrayList<>();
        to_list_id = new ArrayList<>();

        from_adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, from_list);
        from_actv.setAdapter(from_adapter);
        to_adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, to_list);
        to_actv.setAdapter(to_adapter);

        getData();

    }

    private void getData() {
        //TODO Implement This
        from_adapter.notifyDataSetChanged();
        to_adapter.notifyDataSetChanged();
    }

    public void setDate(View view)    {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        String day=Integer.toString(dayOfMonth);
                        String month=Integer.toString(monthOfYear+1);
                        String yearS=Integer.toString(year);
                        if(dayOfMonth<10)
                            day="0"+day;
                        if(monthOfYear<9)
                            month="0"+month;
                        if(year<10)
                            yearS="200"+yearS;
                        if(year<100)
                            yearS="20"+yearS;

                        date_tv.setText((day + "-" + month + "-" + yearS));
                        date_tv.setError(null);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void Submit(View view) {
        submit();
    }

    private void submit() {
        from = from_actv.getText().toString();
        String to = to_actv.getText().toString();
        date = date_tv.getText().toString();
        amount = amount_et.getText().toString();

        if(date.equals("")){
            date_tv.setError("Required");
            return;
        }

        if(!from_list.contains(from)){
            from_actv.setError("Not in Data");
            return;
        }
        from_id = from_list_id.get(from_list.indexOf(from));

        if(!to_list.contains(to)){
            to_actv.setError("Not in Data");
            return;
        }
        to_id = to_list_id.get(to_list.indexOf(to));

        if(amount.equals("")){
            amount_et.setError("Required");
            return;
        }

        if(!sharedPreferences.getBoolean("Confirm", true)){
            addNewReceipt();
            return;
        }

        View customDialogView = View.inflate(this, R.layout.confirm_dialog,null);
        final CheckBox cb = customDialogView.findViewById(R.id.cb);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm?");
        builder.setView(customDialogView);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                if(cb.isChecked())
                {
                    Toast.makeText(getApplicationContext(),"Confirmation Disabled",Toast.LENGTH_SHORT).show();
                    editor=sharedPreferences.edit();
                    editor.putBoolean("Confirm", false);
                    editor.apply();

                }
                addNewReceipt();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void addNewReceipt() {
        Integer t_id = sharedPreferences.getInt(getString(R.string.Key_T_Count), 1);
        Integer r_id = sharedPreferences.getInt(getString(R.string.Key_R_Count), 1);
        editor.putInt(getString(R.string.Key_T_Count), t_id+1);
        editor.putInt(getString(R.string.Key_R_Count), r_id+1);
        editor.apply();
        Transaction_Item item = new Transaction_Item(t_id, from_id, from, to_id, "Receipt No." + r_id, Float.parseFloat(amount), "Receipt", date);
        //TODO Add item into database
        //TODO Update Balances

    }
}
