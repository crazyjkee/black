package com.andraft.adapter;

import android.graphics.Color;

public class Sms {
	public String number;
	public String body;
	public int color;

	public Sms() {
		super();
	}

	public Sms(String number, String body, int color) {
		super();
		this.body = body;
		this.number = number;
		if (color == 1)
			this.color = Color.BLACK;
		else if (color == 2)
			this.color = Color.GREEN;

	}
}
