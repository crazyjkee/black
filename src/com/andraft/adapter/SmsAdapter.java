package com.andraft.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andraft.adapter.SmsAdapter.SmsHolder;
import com.andraft.blacklist.R;

public class SmsAdapter extends ArrayAdapter<Sms> {
	Context context; 
    int layoutResourceId;    
    Sms data[] = null;
    
    public SmsAdapter(Context context, int layoutResourceId, Sms[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SmsHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new SmsHolder();
            holder.body = (TextView)row.findViewById(R.id.txtNumber);
            holder.number = (TextView)row.findViewById(R.id.txtName);
            
            
            row.setTag(holder);
        }
        else
        {
            holder = (SmsHolder)row.getTag();
        }
        
        Sms Sms = data[position];
        holder.body.setText(Sms.body);
        holder.number.setText(Sms.number);
        row.setBackgroundColor(Sms.color);
        
        return row;
    }
    
    static class SmsHolder
    {
        TextView number;
        TextView body;
    }


}
