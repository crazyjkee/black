package com.andraft.models;

public class SmsModel {

	private int id;
	private String num;
	private String text;
	private int bool;
	private int count_black;

	public SmsModel(String num, String text) {
		super();
		this.num = num;
		this.text = text;
	}

	public SmsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SmsModel(String num, String text, int bool,int count_black) {
		super();
		this.num = num;
		this.text = text;
		this.bool = bool;
		this.count_black= count_black;
	}



	public SmsModel(int id, String num, String text, int bool) {
		super();
		this.id = id;
		this.num = num;
		this.text = text;
		this.bool = bool;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getBool() {
		return bool;
	}

	public void setBool(int bool) {
		this.bool = bool;
	}

	public int getCount_black() {
		return count_black;
	}

	public void setCount_black(int count_black) {
		this.count_black = count_black;
	}
}
