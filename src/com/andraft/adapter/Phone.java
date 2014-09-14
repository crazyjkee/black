package com.andraft.adapter;

import android.graphics.Color;

public class Phone {
	public String name;
	public String number;
	public int color;

	public Phone() {
		super();
	}

	public Phone(String name, String number, int color) {
		super();
		this.name = name;
		this.number = number;
		if(color==0)
		this.color = Color.WHITE;
		else if(color==1)
			this.color = Color.BLACK;
		else if(color==2)
			this.color=Color.GREEN;

	}

}
