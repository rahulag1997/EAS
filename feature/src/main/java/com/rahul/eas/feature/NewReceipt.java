package com.rahul.eas.feature;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
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

    private ArrayList<Accounts_Item> from_list, to_list;
    private ArrayList<String> from_list_name, to_list_name;
    private ArrayList<Integer> from_list_id, to_list_id;
    private ArrayAdapter<String> from_adapter, to_adapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String from;
    private String date;
    private String amount;
    private Integer from_id, to_id;

    private MyDatabase my_database;
    private AccountsDAO accountsDAO;
    private TransactionsDAO transactionsDAO;


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
        from_list_name = new ArrayList<>();
        to_list = new ArrayList<>();
        to_list_id = new ArrayList<>();
        to_list_name = new ArrayList<>();

        from_adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, from_list_name);
        from_actv.setAdapter(from_adapter);
        to_adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, to_list_name);
        to_actv.setAdapter(to_adapter);

        my_database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "Accounts").allowMainThreadQueries().build();
        accountsDAO = my_database.accounts_dao();
        transactionsDAO  = my_database.transactions_dao();

        getData();

    }

    private void getData() {
        from_list = (ArrayList<Accounts_Item>) my_database.accounts_dao().getAllTypeAccounts("Debtor");
        to_list = (ArrayList<Accounts_Item>) my_database.accounts_dao().getAllTypesAccounts(new String[] {"Cash","Bank"});
        from_list_name.clear();
        to_list_name.clear();

        for (int i = 0; i < from_list.size(); i++) {
            from_list_name.add(from_list.get(i).name);
            from_list_id.add(from_list.get(i).id);
        }
        for (int i = 0; i < to_list.size(); i++) {
            to_list_name.add(to_list.get(i).name);
            to_list_id.add(to_list.get(i).id);
        }
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
        if(!from_list_name.contains(from)) {
            from_actv.setError("Not in Data");
            return;
        }
        from_id = from_list_id.get(from_list_name.indexOf(from));

        if(!to_list_name.contains(to)){
            to_actv.setError("Not in Data");
            return;
        }
        to_id = to_list_id.get(to_list_name.indexOf(to));

        if(amount.equals("")){
            amount_et.setError("Required");
            return;
        }

        if(!sharedPreferences.getBoolean(getString(R.string.Key_ConfirmDialog), true)){
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
                    editor.putBoolean(getString(R.string.Key_ConfirmDialog), false);
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
        editor = sharedPreferences.edit();
        Integer t_id = sharedPreferences.getInt(getString(R.string.Key_T_Count), 1);
        Integer r_id = sharedPreferences.getInt(getString(R.string.Key_R_Count), 1);
        editor.putInt(getString(R.string.Key_T_Count), t_id+1);
        editor.putInt(getString(R.string.Key_R_Count), r_id+1);
        editor.apply();
        Transaction_Item item = new Transaction_Item(t_id, from_id, from, to_id, "Receipt No." + r_id, Float.parseFloat(amount), "Receipt", date);
        transactionsDAO.insertAll(item);

        // Update

        accountsDAO.incrementDebit(Float.parseFloat(amount), from_id);
        accountsDAO.decrementBalance(Float.parseFloat(amount), from_id);

        accountsDAO.incrementCredit(Float.parseFloat(amount), to_id);
        accountsDAO.incrementBalance(Float.parseFloat(amount), to_id);
        Toast.makeText(getApplicationContext(), "Receipt Added Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }
}
