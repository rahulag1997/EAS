package com.rahul.eas.feature;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReceiptView extends BaseActivity {
    private Integer t_id, r_id;
    String date, from, to, amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_view);

        t_id = getIntent().getIntExtra(getString(R.string.Extra_AccountsView_TID), 0);


        getData();

        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle("Receipt No. "+r_id);
        display();

        setFAB();
    }

    private void setFAB() {
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditReceipt.class).putExtra("Id", t_id));
            }
        });
    }

    private void display() {
        View date_line = findViewById(R.id.receipt_date);
        TextView date_title = date_line.findViewById(R.id.tv_line_type_tv);
        date_title.setText(getString(R.string.date));
        TextView date_value = date_line.findViewById(R.id.tv_line_value_tv);
        date_value.setText(date);

        View from_line = findViewById(R.id.receipt_from);
        TextView from_title = from_line.findViewById(R.id.tv_line_type_tv);
        from_title.setText(getString(R.string.name));
        TextView from_value = from_line.findViewById(R.id.tv_line_value_tv);
        from_value.setText(from);

        View to_line = findViewById(R.id.receipt_to);
        TextView to_title = to_line.findViewById(R.id.tv_line_type_tv);
        to_title.setText(getString(R.string.to));
        TextView to_value = to_line.findViewById(R.id.tv_line_value_tv);
        to_value.setText(to);

        View amount_line = findViewById(R.id.receipt_amount);
        TextView amount_title = amount_line.findViewById(R.id.tv_line_type_tv);
        amount_title.setText(getString(R.string.amount));
        TextView amount_value = amount_line.findViewById(R.id.tv_line_value_tv);
        amount_value.setText(amount);


    }

    private void getData() {
        // TODO implement this
        date = "Date";
        from = "From";
        to = "To";
        amount = "amount";
        r_id = 0;
    }
}
