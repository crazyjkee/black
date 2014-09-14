package com.andraft.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andraft.blacklist.R;

public class PhoneAdapter extends ArrayAdapter<Phone> {

	

	    Context context; 
	    int layoutResourceId;    
	    Phone data[] = null;
	    
	    public PhoneAdapter(Context context, int layoutResourceId, Phone[] data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        PhoneHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new PhoneHolder();
	            holder.name = (TextView)row.findViewById(R.id.txtName);
	            holder.number = (TextView)row.findViewById(R.id.txtNumber);
	            
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (PhoneHolder)row.getTag();
	        }
	        
	        Phone phone = data[position];
	        holder.name.setText(phone.name);
	        holder.number.setText(phone.number);
	        row.setBackgroundColor(phone.color);
	        
	        return row;
	    }
	    
	    static class PhoneHolder
	    {
	       TextView name;
	        TextView number;
	    }
	
}