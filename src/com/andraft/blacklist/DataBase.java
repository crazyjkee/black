package com.andraft.blacklist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.andraft.models.NumberModel;
import com.andraft.models.NumberOptionsModel;
import com.andraft.models.ScheduleModel;
import com.andraft.models.SmsModel;
import com.andraft.models.SmsOptionsModel;

public class DataBase extends SQLiteOpenHelper {
	private static String LOG = "myLogs";
	private static final String DATABASE_NAME = "blacklist";
	private static final int DATABASE_VERSION = 1;
	public static String TABLE_NUMBERS = "numbers";
	public static String TABLE_SMS = "sms";
	public static String TABLE_NUMBERS_OPTIONS = "options_numbers";
	public static String TABLE_SMS_OPTIONS = "options_sms";
	public static String TABLE_SCHEDULE = "schedule";

	// common column
	public static final String KEY_ID = "_id";
	public static final String NUM = "num";

	// numbers column

	public static final String BOOL = "bool";// 0 - white, 1 - black, 2 -
												// unknown
	public static final String NAME = "name";
	public static final String COUNT_BLACK = "count_black";

	// sms column
	public static final String TEXT = "text";

	// numbers options column
	public static final String BLOCK_ALL_CALLS = "block_all_calls";
	public static final String SILENT_MODE = "silent_mode";
	public static final String BUSY_MODE = "busy_mode";

	// universal option
	public static final String BLOCK_HIDDEN_NUMBERS = "block_hidden_numbers";
	public static final String BLOCK_NOTIFICATIONS = "block_notifications";

	// sms options column
	public static final String BLOCK_ALL_SMS = "block_all_sms";

	// schedule column
	public static final String DAY = "day";
	public static final String TOHOURS = "tohours";
	public static final String TOMINUTES = "tominutes";
	public static final String FROMHOURS = "fromhours";
	public static final String FROMMINUTES = "fromminutes";

	// Number table create statement
	private static final String CREATE_TABLE_NUMBERS = "CREATE TABLE "
			+ TABLE_NUMBERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + NUM
			+ " TEXT," + BOOL + " INTEGER," + NAME + " TEXT," + COUNT_BLACK
			+ " INTEGER" + ");";

	// SMS table create statement
	private static final String CREATE_TABLE_SMS = "CREATE TABLE " + TABLE_SMS
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + BOOL + " INTEGER,"
			+ TEXT + " TEXT," + NUM + " TEXT NOT NULL," + COUNT_BLACK
			+ " INTEGER" + ");";

	private static final String CREATE_TABLE_NUMBERS_OPTIONS = "CREATE TABLE "
			+ TABLE_NUMBERS_OPTIONS + "(" + KEY_ID + " INTEGER,"
			+ BLOCK_ALL_CALLS + " INTEGER," + BLOCK_HIDDEN_NUMBERS
			+ " INTEGER," + BLOCK_NOTIFICATIONS + " INTEGER," + SILENT_MODE
			+ " INTEGER," + BUSY_MODE + " ,INTEGER);";

	private static final String CREATE_TABLE_SMS_OPTIONS = "CREATE TABLE "
			+ TABLE_SMS_OPTIONS + "(" + KEY_ID + " INTEGER," + BLOCK_ALL_SMS
			+ " INTEGER," + BLOCK_HIDDEN_NUMBERS + " INTEGER,"
			+ BLOCK_NOTIFICATIONS + " INTEGER," + SILENT_MODE + " INTEGER,"
			+ BUSY_MODE + " ,INTEGER);";

	private static final String CREATE_TABLE_SCHEDULE = "CREATE TABLE "
			+ TABLE_SCHEDULE + "(" + DAY + " INTEGER," + FROMHOURS
			+ " INTEGER," + FROMMINUTES + " INTEGER," + TOHOURS + " INTEGER,"
			+ TOMINUTES + " INTEGER);";

	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("myLogs", "onCreateDatabase");
		db.execSQL(CREATE_TABLE_NUMBERS);
		db.execSQL(CREATE_TABLE_SMS);
		db.execSQL(CREATE_TABLE_NUMBERS_OPTIONS);
		db.execSQL(CREATE_TABLE_SMS_OPTIONS);
		db.execSQL(CREATE_TABLE_SCHEDULE);

	}

	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("myLogs", "onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUMBERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);

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

	/*public NumberModel getNumber(long number_id) {
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
*/
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

	public List<ScheduleModel> getSchedule() {
		List<ScheduleModel> sched = new ArrayList<ScheduleModel>();
		String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				ScheduleModel s = new ScheduleModel();
				s.setDay(c.getInt((c.getColumnIndex(DAY))));
				s.setFromHour(c.getInt(c.getColumnIndex(FROMHOURS)));
				s.setFromMinute(c.getInt(c.getColumnIndex(FROMMINUTES)));
				s.setToHour(c.getInt(c.getColumnIndex(TOHOURS)));
				s.setToMinute(c.getInt(c.getColumnIndex(TOMINUTES)));
				sched.add(s);
			} while (c.moveToNext());
		}
		c.close();
		return sched;

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

	public int updateSms(SmsModel sms, int bool) {
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

	public void updateNumbersOptions(NumberOptionsModel num) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_ID, num.getId());
		values.put(BLOCK_ALL_CALLS, num.isBlock_all_calls());
		values.put(BLOCK_HIDDEN_NUMBERS, num.isBlock_hidden_numbers_calls());
		values.put(BLOCK_NOTIFICATIONS, num.isBlock_notifications_calls());
		values.put(SILENT_MODE, num.isSilent_mode());
		values.put(BUSY_MODE, num.isBusy_mode());
		Log.d("myLogs",
				"updateNumbersOptions:"
						+ db.update(TABLE_NUMBERS_OPTIONS, values, KEY_ID
								+ " = ?",
								new String[] { String.valueOf(num.getId()) }));

	}

	public void updateSmsOptions(SmsOptionsModel sms) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_ID, sms.getId());
		values.put(BLOCK_ALL_SMS, sms.isBlock_all_sms());
		values.put(BLOCK_HIDDEN_NUMBERS, sms.isBlock_hidden_numbers_sms());
		values.put(BLOCK_NOTIFICATIONS, sms.isBlock_notifications_sms());
		Log.d("myLogs",
				"updateSmsOptions:BLOCK_ALL_CALLS:" + sms.isBlock_all_sms()
						+ " ,BLOCK_HIDDEN_NUMBERS:"
						+ sms.isBlock_hidden_numbers_sms());
		Log.d("myLogs",
				"updateSmsOptions:"
						+ db.update(TABLE_SMS_OPTIONS, values, KEY_ID + " = ?",
								new String[] { String.valueOf(sms.getId()) }));

	}

	public void updateSchedule(ArrayList<ScheduleModel> ArraySched) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values;
		for (ScheduleModel sched : ArraySched) {
			values = new ContentValues();
			values.put(DAY, sched.getDay());
			values.put(FROMHOURS, sched.getFromHour());
			values.put(FROMMINUTES, sched.getFromMinute());
			values.put(TOHOURS, sched.getToHour());
			values.put(TOMINUTES, sched.getToMinute());
			Log.d("myLogs",
					"updateSchedule:"
							+ db.update(TABLE_SCHEDULE, values, DAY + " = ?",
									new String[] { String.valueOf(sched
											.getDay()) }));
		}
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
        c.close();
		return numbers;
	}

	public List<SmsModel> getWhereSms(int bool) {
		List<SmsModel> sms = new ArrayList<SmsModel>();
		String selectQuery = "SELECT  * FROM " + TABLE_SMS + " WHERE " + BOOL
				+ "=" + bool + " ORDER BY " + NUM + " ASC";

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
        c.close();
		return sms;
	}

	public NumberOptionsModel getNumberOptionsModel(int key_id) {
		String selectQuery = "SELECT  * FROM " + TABLE_NUMBERS_OPTIONS
				+ " WHERE " + KEY_ID + "=" + key_id;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			NumberOptionsModel num = new NumberOptionsModel();
			num.setId(c.getInt(c.getColumnIndex(KEY_ID)));
			num.setBlock_all_calls(c.getInt(c.getColumnIndex(BLOCK_ALL_CALLS)));
			Log.d("myLogs",
					"getCallOption: isBlockAllCalls:"
							+ c.getInt(c.getColumnIndex(BLOCK_ALL_CALLS)));
			num.setBlock_hidden_numbers_calls(c.getInt(c
					.getColumnIndex(BLOCK_HIDDEN_NUMBERS)));
			Log.d("myLogs",
					"getCallOption: isBlockHiddenNumbers:"
							+ c.getInt(c.getColumnIndex(BLOCK_HIDDEN_NUMBERS)));
			num.setBlock_notifications_calls(c.getInt(c
					.getColumnIndex(BLOCK_NOTIFICATIONS)));
			num.setSilent_mode(c.getInt(c.getColumnIndex(SILENT_MODE)));
			Log.d("myLogs",
					"getCallOption: SILENT_MODE:"
							+ c.getInt(c.getColumnIndex(SILENT_MODE)));
			num.setBusy_mode(c.getInt(c.getColumnIndex(BUSY_MODE)));
			Log.d("myLogs",
					"getCallOption: BUSY_MODE:"
							+ c.getInt(c.getColumnIndex(BUSY_MODE)));
			c.close();
			
			return num;
		} else {
			c.close();
			Log.d("myLogs", "getNumber null");
			return null;
		}
	}

	public SmsOptionsModel getSmsOptionsModel(int key_id) {
		String selectQuery = "SELECT  * FROM " + TABLE_SMS_OPTIONS + " WHERE "
				+ KEY_ID + "=" + key_id;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			SmsOptionsModel sms = new SmsOptionsModel();
			sms.setId(c.getInt(c.getColumnIndex(KEY_ID)));
			sms.setBlock_all_sms(c.getInt(c.getColumnIndex(BLOCK_ALL_SMS)));
			Log.d("myLogs",
					"getSmsOption: isBlockAllSms:"
							+ c.getInt(c.getColumnIndex(BLOCK_ALL_SMS)));
			sms.setBlock_hidden_numbers_sms(c.getInt(c
					.getColumnIndex(BLOCK_HIDDEN_NUMBERS)));
			Log.d("myLogs",
					"getSmsOption: isBlockHiddenSms:"
							+ c.getInt(c.getColumnIndex(BLOCK_HIDDEN_NUMBERS)));
			sms.setBlock_notifications_sms(c.getInt(c
					.getColumnIndex(BLOCK_NOTIFICATIONS)));
            c.close();
			return sms;
		} else {
			c.close();
			Log.d("myLogs", "getSms null");
			return null;
		}

	}

	public boolean isNumberInTable(String num) {
		String selectQuery = "SELECT * FROM " + TABLE_NUMBERS + " WHERE " + NUM
				+ "=" + "\"" + num + "\"";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.moveToFirst()){
			c.close();
		return true;
		}else{
			c.close();
			return false;
		}
	}

	public boolean isSmsInTable(String num) {
		String selectQuery = "SELECT * FROM " + TABLE_SMS + " WHERE " + NUM
				+ "=" + "\"" + num + "\"";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		return c.moveToFirst();
	}

	public int getCountBlockNumber() {
		int i = 0;
		String selectQuery = "SELECT  * FROM " + TABLE_NUMBERS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			i+=c.getInt(c.getColumnIndex(COUNT_BLACK));
		} else {
			Log.d("myLogs", "getCount_black null");
			return 0;
		}
		c.close();

		return i;
	}
	
	public int getCountBlockSms() {
		int i = 0;
		String selectQuery = "SELECT  * FROM " + TABLE_SMS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			i+=c.getInt(c.getColumnIndex(COUNT_BLACK));
		} else {
			Log.d("myLogs", "getCount_black null");
			return 0;
		}
		c.close();

		return i;
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
