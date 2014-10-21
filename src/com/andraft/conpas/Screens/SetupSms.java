package com.andraft.conpas.Screens;

import java.util.ArrayList;
import java.util.List;

import com.andraft.blacklist.R;

import android.view.MotionEvent;

public class SetupSms extends Screen {
private static List <String> testList=new ArrayList<String>();
static{
	for (int i = 0; i <50; i++) {
		testList.add("asa" + System.currentTimeMillis());
	}
	}
	public SetupSms() {
		super(R.string.setup_sms);
		 
	}

	@Override
	boolean onTouch(MotionEvent event) {
		
		return false;
	}
	

}
