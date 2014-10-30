package com.andraft.blacklist;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andraft.models.NumberModel;
import com.andraft.models.SmsModel;
import com.andraft.utils.CallLogUtils;
import com.andraft.utils.SmsLogUtils;

public class Checking {

	private DataBase db;
	private Context context;
	private CallLogUtils calllog;
	private SmsLogUtils smslog;
	private static volatile Checking instance;
	private int count = 0;
	private HashMap<String, String> minus_number, newka, newsms, minus_sms,
			contacts;
	private List<NumberModel> list;

	public static Checking getInstance(Context context) {
		Checking localInstance = instance;
		if (localInstance == null) {
			synchronized (Checking.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new Checking(context);
				}
			}
		}
		return localInstance;
	}

	private Checking(Context context) {
		this.context = context;
		db = new DataBase(context);
		calllog = new CallLogUtils(context);
		this.smslog = new SmsLogUtils(context);
		init();
	}

	private void init() {
		SQLiteDatabase db1 = db.getWritableDatabase();
		contacts = calllog.readAllCalls();
		if (db.TableIsEmpty(DataBase.TABLE_NUMBERS))
			for (Entry<String, String> e : contacts_plus_calls().entrySet())
				addNumberValues(db1, e.getKey(), e.getValue());
		else
			for (Entry<String, String> e : contacts_plus_calls().entrySet())
				if (!db.isNumberInTable(e.getKey())){
					Log.d("myLogs","yes");
					addNumberValues(db1, e.getKey(), e.getValue());}

		if (db.TableIsEmpty(DataBase.TABLE_SMS))
			for (Entry<String, String> e : sms_minus_contacts().entrySet()) {
				//Log.d("myLogs", "key" + e.getKey() + " value:" + e.getValue());
				addSmsValues(db1, e.getKey(), e.getValue());
			}
		else
			for (Entry<String, String> e : sms_minus_contacts().entrySet())
				if (!db.isSmsInTable(e.getKey())) {
					//Log.d("myLogs",
					//		"key" + e.getKey() + " value:" + e.getValue());
					addSmsValues(db1, e.getKey(), e.getValue());
				}

		db1.close();
	}

	private void addNumberValues(SQLiteDatabase db1, String number, String name) {
		ContentValues values = new ContentValues();
		values.put(DataBase.NUM, number);
		values.put(DataBase.BOOL, 2);
		values.put(DataBase.NAME, name);
		values.put(DataBase.COUNT_BLACK, 0);
		// insert row
		db1.insert(DataBase.TABLE_NUMBERS, null, values);
	}

	private void addSmsValues(SQLiteDatabase db1, String number, String text) {
		ContentValues values = new ContentValues();
		values.put(DataBase.BOOL, 2);
		values.put(DataBase.TEXT, text);
		values.put(DataBase.NUM, number);
		values.put(DataBase.COUNT_BLACK, 0);
		// insert row
		db1.insert(DataBase.TABLE_SMS, null, values);
	}

	public DataBase getDb() {
		return db;
	}

	private HashMap<String, String> contacts_plus_calls() {
		minus_number = calllog.readAllCalls();
		contacts = calllog.readAllContacts();
		for (Entry<String,String> num : contacts.entrySet()) {
			if (!minus_number.containsKey(num.getKey()))
				minus_number.put(num.getKey(), num.getValue());
		}
		
		return minus_number;
	}

	private HashMap<String, String> getNewka() {
		return newka;
	}

	private HashMap<String, String> sms_minus_contacts() {
		minus_sms = smslog.readAllSms();
		contacts = calllog.readAllContacts();
		for (Entry<String, String> c : contacts.entrySet()) {
			if (minus_sms.containsKey(c.getKey())) {
				minus_sms.remove(c.getKey());
			}
		}

		return minus_sms;
	}

	public List<NumberModel> getCalls(int bool) {
		return db.getWhereNumber(bool);

	}

	public List<SmsModel> getSms(int bool) {

		return db.getWhereSms(bool);

	}

}
