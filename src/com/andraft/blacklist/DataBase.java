package com.andraft.blacklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.andraft.models.NumberModel;
import com.andraft.models.SmsModel;

public class DataBase extends SQLiteOpenHelper {
	private static String LOG = "myLogs";
	private static final String DATABASE_NAME = "blacklist";
	private static final int DATABASE_VERSION = 1;
	public static String TABLE_NUMBERS = "numbers";
	public static String TABLE_SMS = "sms";

	// common column
	public static final String KEY_ID = "_id";
	public static final String NUM = "num";

	// numbers column

	public static final String BOOL = "bool";//0 - white, 1 - black, 2 - unknown
	public static final String NAME = "name";
	public static final String COUNT_BLACK = "count_black";

	// sms column
	public static final String TEXT = "text";

	// Number table create statement
	private static final String CREATE_TABLE_NUMBERS = "CREATE TABLE "
			+ TABLE_NUMBERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + NUM
			+ " TEXT," + BOOL + " INTEGER," + NAME + " TEXT," + COUNT_BLACK
			+ " INTEGER" + ");";

	// SMS table create statement
	private static final String CREATE_TABLE_SMS = "CREATE TABLE " + TABLE_SMS
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + BOOL + " INTEGER,"
			+ TEXT + " TEXT," + NUM + " TEXT NOT NULL," +COUNT_BLACK+" INTEGER"+ ");";

	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("myLogs", "onCreateDatabase");
		db.execSQL(CREATE_TABLE_NUMBERS);
		db.execSQL(CREATE_TABLE_SMS);

	}

	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUMBERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
		onCreate(db);
	}

	public long createNumber(NumberModel number) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NUM, number.getNum());
		values.put(BOOL, number.getBool());
		values.put(NAME, number.getName());
		values.put(COUNT_BLACK, number.getCount_black());
		// insert row
		long number_id = db.insert(TABLE_NUMBERS, null, values);
		Log.d("myLogs", "number_id:" + number_id);
		return number_id;
	}

	public long createSms(SmsModel sms) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TEXT, sms.getText());
		values.put(BOOL, sms.getBool());
		values.put(NUM, sms.getNum());

		// insert row
		long sms_id = db.insert(TABLE_SMS, null, values);

		return sms_id;
	}

	public NumberModel getNumber(long number_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_NUMBERS + " WHERE "
				+ KEY_ID + " = " + number_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		NumberModel number = new NumberModel();
		number.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		number.setNum(c.getString(c.getColumnIndex(NUM)));
		number.setBool(c.getInt(c.getColumnIndex(BOOL)));
		number.setName(c.getString(c.getColumnIndex(NAME)));
		number.setCount_black(c.getInt(c.getColumnIndex(COUNT_BLACK)));

		return number;
	}

	public List<NumberModel> getAllNumbers() {
		List<NumberModel> numbers = new ArrayList<NumberModel>();
		String selectQuery = "SELECT  * FROM " + TABLE_NUMBERS;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				NumberModel number = new NumberModel();
				number.setNum(c.getString(c.getColumnIndex(NUM)));
				number.setBool(c.getInt(c.getColumnIndex(BOOL)));
				number.setName(c.getString(c.getColumnIndex(NAME)));
				number.setCount_black(c.getInt(c.getColumnIndex(COUNT_BLACK)));

				// adding to todo list
				numbers.add(number);
			} while (c.moveToNext());
		}
		c.close();

		return numbers;
	}

	public List<SmsModel> getAllSms() {
		List<SmsModel> sms = new ArrayList<SmsModel>();
		String selectQuery = "SELECT  * FROM " + TABLE_SMS;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				SmsModel t = new SmsModel();
				t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				t.setNum(c.getString(c.getColumnIndex(NUM)));
				t.setText(c.getString(c.getColumnIndex(TEXT)));
				t.setCount_black(c.getInt((c.getColumnIndex(COUNT_BLACK))));

				// adding to tags list
				sms.add(t);
			} while (c.moveToNext());
		}
		c.close();
		return sms;
	}

	public int updateNumber(NumberModel number, int bool) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(NUM, number.getNum());
		values.put(BOOL, bool);
		values.put(NAME, number.getName());
		values.put(COUNT_BLACK, number.getCount_black());

		// updating row
		return db.update(TABLE_NUMBERS, values, NUM + " = ?",
				new String[] { String.valueOf(number.getNum()) });
	}

	public int updateSms(SmsModel sms,int bool) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NUM, sms.getNum());
		values.put(TEXT, sms.getText());
		values.put(COUNT_BLACK, sms.getCount_black());
		values.put(BOOL, bool);

		// updating row
		return db.update(TABLE_SMS, values, NUM + " = ?",
				new String[] { String.valueOf(sms.getNum()) });
	}

	public void deleteNumber(String number) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NUMBERS, NUM + " = ?", new String[] { number });
	}

	public void deleteSms(String number) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_SMS, NUM + " = ?", new String[] { number });
	}

	public void clearAllNumbers() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from " + TABLE_NUMBERS);
	}

	public void clearAllSms() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from " + TABLE_SMS);

	}

	public List<NumberModel> getWhereNumber(int bool) {
		List<NumberModel> numbers = new ArrayList<NumberModel>();
		String selectQuery = "SELECT  * FROM " + TABLE_NUMBERS + " WHERE "
				+ BOOL + "=" + bool + " ORDER BY " + NAME + " ASC";

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				NumberModel number = new NumberModel();
				number.setNum(c.getString(c.getColumnIndex(NUM)));
				number.setBool(c.getInt(c.getColumnIndex(BOOL)));
				number.setName(c.getString(c.getColumnIndex(NAME)));
				number.setCount_black(c.getInt(c.getColumnIndex(COUNT_BLACK)));

				// adding to todo list
				numbers.add(number);
			} while (c.moveToNext());
		}

		return numbers;
	}
	
	public List<SmsModel> getWhereSms(int bool) {
		List<SmsModel> sms = new ArrayList<SmsModel>();
		String selectQuery = "SELECT  * FROM " + TABLE_SMS + " WHERE "
				+ BOOL + "=" + bool + " ORDER BY " + NUM + " ASC";

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				SmsModel smsm = new SmsModel();
				smsm.setNum(c.getString(c.getColumnIndex(NUM)));
				smsm.setBool(c.getInt(c.getColumnIndex(BOOL)));
                smsm.setText(c.getString(c.getColumnIndex(TEXT)));
				// adding to todo list
				sms.add(smsm);
			} while (c.moveToNext());
		}

		return sms;
	
	}

	public boolean isNumberInTable(String num) {
		String selectQuery = "SELECT * FROM " + TABLE_NUMBERS + " WHERE " + NUM
				+ "=" + NUM;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		return c.moveToFirst();
	}

	public boolean isSmsInTable(String num) {
		String selectQuery = "SELECT * FROM " + TABLE_SMS + " WHERE " + NUM
				+ "=" + "\""+num+"\"";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		return c.moveToFirst();
	}

	public boolean TableIsEmpty(String table) {
		String countQuery = "SELECT  * FROM " + table;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cnt = cursor.getCount();
		cursor.close();
		if (cnt > 0)
			return false;
		else
			return true;

	}

	

}
