package com.andraft.conpas.Screens;

import com.andraft.blacklist.R;

import android.view.MotionEvent;

public class ListOfContacts extends Screen {

	public ListOfContacts() {
		super(R.string.list_of_contacts);
		 
	}

	@Override
	boolean onTouch(MotionEvent event) {
		
		return false;
	}

}
