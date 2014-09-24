package com.andraft.blacklist;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.andraft.models.NumberModel;
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
	private static final String NUM = "num";
	private static final String BOOL = "bool";
	private static final String NAME = "name";
	private static final String COUNT_BLACK = "count_black";

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
		if (db.TableNumberIsEmpty()) {
			SQLiteDatabase db1 = db.getWritableDatabase();
			String TABLE_NUMBERS = "numbers";
			contacts = calllog.readAllContacts();
			for (Entry<String, String> e : contacts.entrySet()) {
				ContentValues values = new ContentValues();
				values.put(NUM, e.getKey());
				values.put(BOOL, 2);
				values.put(NAME, e.getValue());
				values.put(COUNT_BLACK, 0);

				// insert row
				db1.insert(TABLE_NUMBERS, null, values);
			}

		}
	}

	public CallLogUtils getCalllog() {
		return calllog;
	}

	public DataBase getDb() {
		return db;
	}

	public HashMap<String, String> list_minus_phone() {
		minus_number = calllog.readAll();
		list = db.getAllNumbers();
		for (NumberModel num : list) {
			if (minus_number.containsKey(num.getNum()))
				minus_number.remove(num.getNum());
		}
		if (newka != null) {
			for (Entry<String, String> n : getNewka().entrySet())
				minus_number.put(n.getKey(), n.getValue());

			newka.clear();
		}
		return minus_number;
	}

	public HashMap<String, String> getNewka() {
		return newka;
	}

	public HashMap<String, String> addUnknNumber(String number) {
		newka = new HashMap<String, String>();
		newka.put(number, "unknown");
		return newka;

	}

	public HashMap<String, String> sms_minus_contacts() {
		minus_sms = smslog.readAllSms();
		contacts = calllog.readAllContacts();
		for (Entry<String, String> c : contacts.entrySet()) {
			if (minus_sms.containsKey(c.getKey())) {
				minus_sms.remove(c.getKey());
			}
		}

		return minus_sms;
	}

	public HashMap<String, String> addRegSms(String Sms) {
		newsms = new HashMap<String, String>();

		// newka.put(number, "unknown");
		return newsms;
	}

	public HashMap<String, String> getRegSms() {
		return newsms;
	}

}
