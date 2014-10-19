package com.andraft.conpas.Screens;

import com.andraft.blacklist.R;

import android.view.MotionEvent;

public class ListOfNumbers extends Screen {

	public ListOfNumbers() {
		super(R.string.list_of_numbers);
		 
	}

	@Override
	boolean onTouch(MotionEvent event) {
		
		return false;
	}

}
