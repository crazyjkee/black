package com.andraft.models;

public class ScheduleModel {
	private int day;
	private int fromHour;
	private int fromMinute;
	private int toHour;
	private int toMinute;

	public ScheduleModel(int day, int fromHour, int fromMinute, int toHour,
			int toMinute) {
		super();
		this.day = day;
		this.fromHour = fromHour;
		this.fromMinute = fromMinute;
		this.toHour = toHour;
		this.toMinute = toMinute;
	}

	public ScheduleModel() {
		// TODO Auto-generated constructor stub
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getFromHour() {
		return fromHour;
	}

	public void setFromHour(int fromHour) {
		this.fromHour = fromHour;
	}

	public int getFromMinute() {
		return fromMinute;
	}

	public void setFromMinute(int fromMinute) {
		this.fromMinute = fromMinute;
	}

	public int getToHour() {
		return toHour;
	}

	public void setToHour(int toHour) {
		this.toHour = toHour;
	}

	public int getToMinute() {
		return toMinute;
	}

	public void setToMinute(int toMinute) {
		this.toMinute = toMinute;
	}

}
