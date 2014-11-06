package com.andraft.models;

public class SmsOptionsModel {

	private int id;
	private int block_all_sms;
	private int block_hidden_numbers_sms;
	private int block_notifications_sms;

	public SmsOptionsModel(int id, int block_all_sms,
			int block_hidden_numbers_sms, int block_notifications_sms) {
		super();
		this.id = id;
		this.block_all_sms = block_all_sms;
		this.block_hidden_numbers_sms = block_hidden_numbers_sms;
		this.block_notifications_sms = block_notifications_sms;
	}

	public SmsOptionsModel(int block_all_sms,
			int block_hidden_numbers_sms, int block_notifications_sms) {
		super();
		this.block_all_sms = block_all_sms;
		this.block_hidden_numbers_sms = block_hidden_numbers_sms;
		this.block_notifications_sms = block_notifications_sms;
	}

	public SmsOptionsModel() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int isBlock_all_sms() {
		return(block_all_sms);
	}

	public void setBlock_all_sms(int block_all_sms) {
		this.block_all_sms = block_all_sms;
	}

	public int isBlock_hidden_numbers_sms() {
		return(block_hidden_numbers_sms);
	}

	public void setBlock_hidden_numbers_sms(int block_hidden_numbers_sms) {
		this.block_hidden_numbers_sms = block_hidden_numbers_sms;
	}

	public int isBlock_notifications_sms() {
		return( block_notifications_sms);
	}

	public void setBlock_notifications_sms(int block_notifications_sms) {
		this.block_notifications_sms = block_notifications_sms;
	}

}
