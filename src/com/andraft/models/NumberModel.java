package com.andraft.models;

public class NumberModel {

	private int id;
	private String num;
	private int bool; //0 - white , 1 - black , 2 - unknown
	private String name;
	private int count_black;
	
	
	public NumberModel(int id, String num, int bool, 
			String name, int count_black) {
		super();
		this.id = id;
		this.num = num;
		this.bool = bool;
		this.count_black = count_black;
	}
	
	public NumberModel(String num, int bool, String name, int count_black) {
		super();
		this.num = num;
		this.bool = bool;
		this.name = name;
		this.count_black = count_black;
	}

	public NumberModel() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount_black() {
		return count_black;
	}
	public void setCount_black(int count_black) {
		this.count_black = count_black;
	}
	
}
