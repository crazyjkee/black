package com.andraft.conpas.Screens;

import com.andraft.blacklist.R;

import android.view.MotionEvent;

public class ListOfNumbers extends ListOfContacts {
private static String Messages[]={"9555555 asasas","8888888 asasasa","7222222 asasas"};
	public ListOfNumbers() {
		super(R.string.list_of_numbers,Messages);
		 
	}

	@Override
	boolean onTouch(MotionEvent event) {
		return super.onTouch(event);
		 
	}

}
