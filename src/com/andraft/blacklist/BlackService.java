package com.andraft.blacklist;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.andraft.conpas.Screens.Constants;
import com.andraft.utils.CallLogUtils;
import com.android.internal.telephony.ITelephony;

public class BlackService extends Service {
	private final String ACTION_SMS = "android.provider.Telephony.SMS_RECEIVED";
	private CallLogUtils calls;
	private Checking checking;
	private boolean phone_state = true;
	private TelephonyManager tmgr;

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		if (tmgr != null) {
			tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_NONE);
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Constants.service = true;
		Log.d("myLogs", "onCreate BlackService");
		calls = new CallLogUtils(Constants.context);
		checking = Checking.getInstance(Constants.context);
		this.getApplicationContext()
				.getContentResolver()
				.registerContentObserver(
						android.provider.CallLog.Calls.CONTENT_URI, true,
						new MyContentObserver(new Handler()));

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		// filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);

		registerReceiver(receiver, filter);
		try {
			tmgr = (TelephonyManager) Constants.context
					.getSystemService(Context.TELEPHONY_SERVICE);

			// Create Listner

			tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		} catch (Exception e) {

		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	class MyContentObserver extends ContentObserver {
		private boolean number = false;

		public MyContentObserver(Handler h) {
			super(h);
		}

		@Override
		public boolean deliverSelfNotifications() {
			return number;
		}

		// Журнал
		@Override
		public void onChange(boolean selfChange) {

			super.onChange(selfChange);

			// here you call the method to fill the list
		}
	}

	PhoneStateListener PhoneListener = new PhoneStateListener() {

		private ITelephony telephonyService;

		public void onCallStateChanged(int state, String incomingNumber) {

			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				String anotherNum = null;
				if (incomingNumber.substring(0, 1).equals("+"))
					anotherNum = "8"
							+ incomingNumber.substring(2,
									incomingNumber.length());
				else
					anotherNum = "+7"
							+ incomingNumber.substring(1,
									incomingNumber.length());

				Log.d("myLogs", state + "   incoming no:" + incomingNumber);

				// state = 1 means when phone is ringing

				String msg = " New Phone Call Event. Incomming Number : "
						+ incomingNumber;
				try {
					Class c = Class.forName(tmgr.getClass().getName());
					Method m = c.getDeclaredMethod("getITelephony");
					m.setAccessible(true);
					telephonyService = (ITelephony) m.invoke(tmgr);
					// telephonyService.silenceRinger();
					if (Constants.BLOCK_ALL_CALLS
							&& !(checking.getDb().isNumberBlackTrueWhiteFalse(
									incomingNumber, false)
							|| checking.getDb().isNumberBlackTrueWhiteFalse(
									anotherNum, false))) {

						checking.getDb().isNumberPlusCountBlack(incomingNumber);
						endCall(telephonyService);
						Log.d("myLogs", "block all calls");
						break;
					} else if (Constants.BLOCK_HIDDEN_CALLS) {
						if (checking.getDb().isNumberUnknown(incomingNumber)) {
							Log.d("myLogs", "block hidden calls");
							checking.getDb().isNumberPlusCountBlack(incomingNumber);
							endCall(telephonyService);
						}
					}
					
					if (breakAllCalls()) {
						if (!(checking.getDb().isNumberBlackTrueWhiteFalse(
								incomingNumber, false)
								|| checking.getDb()
										.isNumberBlackTrueWhiteFalse(
												anotherNum, false))) {
							Log.d("myLogs", "break all calls");
							checking.getDb().isNumberPlusCountBlack(incomingNumber);
							endCall(telephonyService);

						}
					} else if (checking.getDb().isNumberBlackTrueWhiteFalse(
							incomingNumber, true)
							|| checking.getDb().isNumberBlackTrueWhiteFalse(
									anotherNum, true)) {
						Log.d("myLogs", "!break all calls");
						checking.getDb().isNumberPlusCountBlack(incomingNumber);
						calls.DeleteNumFromCallLog(incomingNumber);
						endCall(telephonyService);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	};

	private void endCall(ITelephony telephonyService) {
		if (Constants.BUSY_MODE)
			telephonyService.endCall();
		else if (Constants.SILENT_MODE)
			telephonyService.silenceRinger();
	}

	private boolean breakAllCalls() {
		Date date = new Date(); // given date
		Calendar calendar = Calendar.getInstance(); // creates a new
													// calendar
													// instance
		// calendar.setTime(date); // assigns calendar to given date
		// Log.d("myLogs", calendar.get(Calendar.DAY_OF_WEEK) - 1 +
		// " DAY OF WEEK");
		// Log.d("myLogs", calendar.get(Calendar.HOUR_OF_DAY) + ""); // gets
		// hour
		// in 24h
		// format

		calendar.set(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				(Constants.Silence[calendar.get(Calendar.DAY_OF_WEEK) - 1][0][0]),
				Constants.Silence[calendar.get(Calendar.DAY_OF_WEEK) - 1][0][1]);
		long from = calendar.getTimeInMillis();
		calendar.set(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				(Constants.Silence[calendar.get(Calendar.DAY_OF_WEEK) - 1][1][0]),
				Constants.Silence[calendar.get(Calendar.DAY_OF_WEEK) - 1][1][1]);
		long to = calendar.getTimeInMillis();
		calendar.clear();
		calendar.setTime(date);

		if (System.currentTimeMillis() >= from
				&& System.currentTimeMillis() <= to)
			return true;
		else
			return false;
	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && intent.getAction() != null
					&& ACTION_SMS.compareToIgnoreCase(intent.getAction()) == 0) {
				Log.d("myLogs", "Проскочил 1111");

				Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
				SmsMessage[] messages = new SmsMessage[pduArray.length];
				for (int i = 0; i < pduArray.length; i++) {
					messages[i] = SmsMessage
							.createFromPdu((byte[]) pduArray[i]);
				}
				String sms_from = messages[0].getDisplayOriginatingAddress();
				StringBuilder bodyText = new StringBuilder();
				for (int i = 0; i < messages.length; i++) {
					bodyText.append(messages[i].getMessageBody());
				}
				Log.d("myLogs", "from:" + sms_from + "\n bodytext:" + bodyText);

			}
		}

	};
}
