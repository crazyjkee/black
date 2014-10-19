package com.andraft.conpas.Screens;

import com.andraft.blacklist.R;

import android.view.MotionEvent;

public class BlackList extends Screen {

	public BlackList(){
		super(R.string.black_list);
		 
	}

	@Override
	boolean onTouch(MotionEvent event) {
		return false;
	}

}
