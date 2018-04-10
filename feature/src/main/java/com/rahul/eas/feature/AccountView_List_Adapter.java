package com.rahul.eas.feature;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

class AccountView_List_Adapter extends BaseAdapter
{
    private final ArrayList<AccountView_Item> data;
    private final LayoutInflater layoutInflater;
    private final Context context;
    AccountView_List_Adapter(Context context, ArrayList<AccountView_Item> data)
    {
        this.context=context;
        this.data=data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()    {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null)
        {
            convertView=layoutInflater.inflate(R.layout.account_view_item, parent, false);
        }
        final AccountView_Item item = (AccountView_Item) getItem(position);

        TextView sno_tv = convertView.findViewById(R.id.accounts_view_item_sno);
        TextView name_tv = convertView.findViewById(R.id.account_view_item_name);
        TextView debit_tv = convertView.findViewById(R.id.account_view_item_debit);
        TextView credit_tv = convertView.findViewById(R.id.account_view_item_credit);
        TextView balance_tv = convertView.findViewById(R.id.account_view_item_balance);

        DecimalFormat dec_format=new DecimalFormat("#");

        sno_tv.setText(dec_format.format(position+1));
        name_tv.setText(item.name);
        debit_tv.setText(dec_format.format(item.debit));
        credit_tv.setText(dec_format.format(item.credit));
        balance_tv.setText(dec_format.format(item.balance));

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch (item.type)
                {
                    case "OB" :
                        break;
                    // TODO COMPLETE THIS SHIT
//                    case "Payment" :
//                        context.startActivity(new Intent(context,PaymentView.class).putExtra("PMT_NUM",item.num));
//                        break;
//                    case "Receipt":
//                        context.startActivity(new Intent(context,ReceiptView.class).putExtra("RPT_NUM",item.num));
//                        break;
//                    case "Expense":
//                        context.startActivity(new Intent(context,ExpenseView.class).putExtra("EXP_NUM",item.num));
//                        break;
//                    case "Bill":
//                        context.startActivity(new Intent(context,BillView.class).putExtra("BILL_NUM",item.num));
//                        break;
//                    case "Purchase" :
//                        context.startActivity(new Intent(context,PurchaseView.class).putExtra("PUR_NUM",item.num));
//                        break;
                }
            }
        });
        return convertView;
    }
}
