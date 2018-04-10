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

class Accounts_List_Adapter extends BaseAdapter {
    private ArrayList<Accounts_Item> data;
    private final LayoutInflater layoutInflater;
    private final Context context;

    Accounts_List_Adapter(Context context, ArrayList<Accounts_Item> data)    {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
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

    public void updateData(ArrayList<Accounts_Item> new_data){
        this.data = new_data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)    {
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.accounts_item, null);
        }
        final Accounts_Item item = (Accounts_Item) getItem(position);
        TextView sno_tv = convertView.findViewById(R.id.accounts_item_sno);
        TextView name_tv = convertView.findViewById(R.id.accounts_item_name);
        TextView debit_tv = convertView.findViewById(R.id.accounts_item_debit);
        TextView credit_tv = convertView.findViewById(R.id.accounts_item_credit);
        TextView balance_tv = convertView.findViewById(R.id.accounts_item_balance);

        DecimalFormat dec_format = new DecimalFormat("#");

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
                Intent i=new Intent(context, AccountView.class);
                i.putExtra(context.getString(R.string.Extra_AccView_Name), item.name);
                i.putExtra(context.getString(R.string.Extra_AccView_Id), item.id);
                (context).startActivity(i);
            }
        });
        return convertView;
    }
}

