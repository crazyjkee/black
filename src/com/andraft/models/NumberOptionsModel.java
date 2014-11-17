package com.andraft.models;

public class NumberOptionsModel {

	private int id;
	private int block_all_calls;
	private int block_hidden_numbers_calls;
	private int block_notifications_calls;
	private int silent_mode;
	private int busy_mode;

	public NumberOptionsModel(int id, int block_all_calls,
			int block_hidden_numbers_calls, int block_notifications_calls,
			int silent_mode, int busy_mode) {
		this.id = id;
		this.block_all_calls = block_all_calls;
		this.block_hidden_numbers_calls = block_hidden_numbers_calls;
		this.block_notifications_calls = block_notifications_calls;
		this.silent_mode = silent_mode;
		this.busy_mode = busy_mode;
	}

	public NumberOptionsModel() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int isBlock_all_calls() {
		return (block_all_calls);
	}

	public void setBlock_all_calls(int block_all_calls) {
		this.block_all_calls = block_all_calls;
	}

	public int isBlock_hidden_numbers_calls() {
		return (block_hidden_numbers_calls);
	}

	public void setBlock_hidden_numbers_calls(int block_hidden_numbers_calls) {
		this.block_hidden_numbers_calls = block_hidden_numbers_calls;
	}

	public int isBlock_notifications_calls() {
		return (block_notifications_calls);
	}

	public void setBlock_notifications_calls(int block_notifications_calls) {
		this.block_notifications_calls = block_notifications_calls;
	}

	public int isSilent_mode() {
		return (silent_mode);

	}

	public void setSilent_mode(int silent_mode) {
		this.silent_mode = silent_mode;
	}

	public int isBusy_mode() {

		return busy_mode;
	}

	public void setBusy_mode(int busy_mode) {
		this.busy_mode = busy_mode;
	}

}
