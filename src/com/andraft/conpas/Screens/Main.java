package com.andraft.conpas.Screens;

import com.andraft.blacklist.R;
import android.content.Context;
import android.view.MotionEvent;

public class Main extends Screen{
  
	 
	private String label, opt, sms, call, list_sms, list_call, list_white,
			list_black, schedule;
 

	public  Main(Context context) { 
		label = context.getString(R.string.app_name);
		opt = context.getString(R.string.main_opt);
		sms = context.getString(R.string.sms);
		call = context.getString(R.string.call);
		list_sms = context.getString(R.string.main_list_sms);
		list_call = context.getString(R.string.main_list_call);
		list_white = context.getString(R.string.main_white_list);
		list_black = context.getString(R.string.main_black_list);
		schedule = context.getString(R.string.main_schedule);
		 
	}
	@Override
	public boolean OnTouch(MotionEvent event) { 
		 
		 
		return true;
	}

	 
	 
}
