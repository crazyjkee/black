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
		Log.d("myLogs","CHECKING INIT");
		this.context = context;
		db = new DataBase(context);
		calllog = new CallLogUtils(context);
		this.smslog = new SmsLogUtils(context);
		init();
	}

	public void init() {
		
		SQLiteDatabase db1 = db.getWritableDatabase();
		contacts = calllog.readAllCalls();
		if (db.TableIsEmpty(DataBase.TABLE_NUMBERS))
			for (Entry<String, String> e : contacts_plus_calls().entrySet())
				addNumberValues(db1, e.getKey(), e.getValue());
		else
			for (Entry<String, String> e : contacts_plus_calls().entrySet())
				if (!db.isNumberInTable(e.getKey())) {
					Log.d("myLogs", "yes");
					addNumberValues(db1, e.getKey(), e.getValue());
				}

		if (db.TableIsEmpty(DataBase.TABLE_SMS))
			for (Entry<String, String> e : sms_minus_contacts().entrySet()) {
				// Log.d("myLogs", "key" + e.getKey() + " value:" +
				// e.getValue());
				addSmsValues(db1, e.getKey(), e.getValue());
			}
		else
			for (Entry<String, String> e : sms_minus_contacts().entrySet())
				if (!db.isSmsInTable(e.getKey())) {
					// Log.d("myLogs",
					// "key" + e.getKey() + " value:" + e.getValue());
					addSmsValues(db1, e.getKey(), e.getValue());
				}
		if (db.TableIsEmpty(DataBase.TABLE_NUMBERS_OPTIONS))
			addNumberOptionsValues(db1);
		if (db.TableIsEmpty(DataBase.TABLE_SMS_OPTIONS))
			addSmsOptionsValues(db1);
		if (db.TableIsEmpty(DataBase.TABLE_SCHEDULE))
			addScheduleValues(db1);

		db.close();
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

	private void addNumberOptionsValues(SQLiteDatabase db1) {
		Log.d("myLogs","insert NumberValues 1 row");
		ContentValues values = new ContentValues();
		values.put(DataBase.KEY_ID, 1);
		values.put(DataBase.BLOCK_ALL_CALLS, 0);
		values.put(DataBase.BLOCK_HIDDEN_NUMBERS, 0);
		values.put(DataBase.BLOCK_NOTIFICATIONS, 0);
		values.put(DataBase.SILENT_MODE, 0);
		values.put(DataBase.BUSY_MODE, 1);
		Log.d("myLogs","TABLE NUMBER OPTIONS INSERT:"+db1.insert(DataBase.TABLE_NUMBERS_OPTIONS, null, values));
	}

	private void addSmsOptionsValues(SQLiteDatabase db1) {
		Log.d("myLogs","insert SmsValues 1 row");
		ContentValues values = new ContentValues();
		values.put(DataBase.KEY_ID, 1);
		values.put(DataBase.BLOCK_ALL_SMS, 0);
		values.put(DataBase.BLOCK_HIDDEN_NUMBERS, 0);
		values.put(DataBase.BLOCK_NOTIFICATIONS, 0);
		Log.d("myLogs","TABLE SMS OPTIONS INSERT:"+db1.insert(DataBase.TABLE_SMS_OPTIONS, null, values));
	}
	
	private void addScheduleValues(SQLiteDatabase db1){
		Log.d("myLogs","insert ScheduleValues row");
		ContentValues values;
		for(int i=0;i<7;i++){
			values = new ContentValues();
			values.put(DataBase.DAY, i);
			values.put(DataBase.FROMHOURS, 0);
			values.put(DataBase.FROMMINUTES, 0);
			values.put(DataBase.TOHOURS, 0);
			values.put(DataBase.TOMINUTES, 0);
		Log.d("myLogs","TABLE SCHEDULE INSERT"+db1.insert(DataBase.TABLE_SCHEDULE, null, values));
		}
	}

	public DataBase getDb() {
		return db;
	}

	private HashMap<String, String> contacts_plus_calls() {
		minus_number = calllog.readAllCalls();
		contacts = calllog.readAllContacts();
		for (Entry<String, String> num : contacts.entrySet()) {
			String plus7 = "+7"+num.getKey().substring(1,num.getKey().length());
			if (!minus_number.containsKey(num.getKey())&&!minus_number.containsKey(plus7))
				minus_number.put(num.getKey(), num.getValue());
		}

		return minus_number;
	}



	private HashMap<String, String> sms_minus_contacts() {
		minus_sms = smslog.readAllSms();
		contacts = calllog.readAllContacts();
		for (Entry<String, String> c : contacts.entrySet()) {
			String plus7 = "+7"+c.getKey().substring(1,c.getKey().length());
			if (minus_sms.containsKey(c.getKey())||minus_sms.containsKey(plus7)) {
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
