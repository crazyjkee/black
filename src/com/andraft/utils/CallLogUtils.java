package com.andraft.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract;
import android.util.Log;

import com.andraft.blacklist.Checking;

public class CallLogUtils {
	private ContentResolver resolver;
	private Context context;
	int i = 0;
	private HashMap<String, String> contacts, all;

	public CallLogUtils(Context context) {
		super();
		this.resolver = context.getContentResolver();
		this.context = context;
	}

	String[] strFields = { android.provider.CallLog.Calls.NUMBER,
			android.provider.CallLog.Calls.TYPE,
			android.provider.CallLog.Calls.CACHED_NAME,
			android.provider.CallLog.Calls.CACHED_NUMBER_TYPE,
			android.provider.CallLog.Calls.CACHED_NAME };
	String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
	String[] number;

	public HashMap<String, String> readAllContacts() {
		contacts = new HashMap<String, String>();
		Cursor phones = resolver.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

			contacts.put(phoneNumber, name);
			// Log.d("myLogs","name:"+name+" phoneNumber:"+phoneNumber);
		}
		phones.close();
	
		return contacts;
	}

	public HashMap<String, String> readAllCalls() {
		all = new HashMap<String, String>();
		all.put("212121", "OIIIII");
		all.put("122121", "NE MOGY");
		
		Cursor cursor = context.getContentResolver().query(
				CallLog.Calls.CONTENT_URI, null, null, null, null);// CallLog.Calls.NUMBER
																	// + "," +
																	// CallLog.Calls.DATE
																	// +
																	// " DESC");
		int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int iname = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		while (cursor.moveToNext()) {
			String name = cursor.getString(iname);
			String phoneNumber = cursor.getString(number);
			if (name == null) {
				// Log.d("myLogs","name==null");
				all.put(phoneNumber, "unknown");
			} else
				all.put(phoneNumber, name);
		}
		cursor.close();
		
		return all;
	}

	public int DeleteNumFromCallLog(String strNum) {

		number = new String[] { strNum };
		Cursor cursor = context.getContentResolver().query(
				CallLog.Calls.CONTENT_URI, null,
				CallLog.Calls.NUMBER + " = ? ", number,
				CallLog.Calls.DATE + " DESC");

		try {
			String strUriCalls = "content://call_log/calls";
			Uri UriCalls = Uri.parse(strUriCalls);

			Log.d("myLogs", "moveToFirst:" + cursor.moveToFirst());

			if (null != resolver && cursor.moveToFirst()) {

				i = resolver.delete(UriCalls, CallLog.Calls.NUMBER + "=?",
						new String[] { strNum });
				markCallLogRead();

				Log.d("myLogs", "delete:" + strNum + "bool:" + i);
			} else {
				Log.d("myLogs", "null");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return i;
	}

	public void markCallLogRead() {
		ContentValues values = new ContentValues();
		values.put(Calls.NEW, 0);
		values.put(Calls.IS_READ, 1);
		StringBuilder where = new StringBuilder();
		where.append(Calls.NEW);
		where.append(" = 1 AND ");
		where.append(Calls.TYPE);
		where.append(" = ?");
		context.getContentResolver().update(Calls.CONTENT_URI, values,
				where.toString(),
				new String[] { Integer.toString(Calls.MISSED_TYPE) });
	}

}
