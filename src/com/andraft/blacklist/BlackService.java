package com.andraft.blacklist;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.andraft.utils.CallLogUtils;
import com.android.internal.telephony.ITelephony;

public class BlackService extends Service {

	private CallLogUtils calls;

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
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
		Log.d("myLogs", "onCreate service");
		calls = new CallLogUtils(this);
		this.getApplicationContext()
				.getContentResolver()
				.registerContentObserver(
						android.provider.CallLog.Calls.CONTENT_URI, true,
						new MyContentObserver(new Handler()));

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);

		registerReceiver(receiver, filter);
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
			Log.d("myLogs", "MyContentObserver.onChange(" + selfChange + ")");
			calls.DeleteNumFromCallLog("+79507638332");
			super.onChange(selfChange);

			// here you call the method to fill the list
		}
	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		private TelephonyManager tmgr;

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("android.intent.action.PHONE_STATE")) {
				Log.d("myLogs", "PHONE_STATE");
				try {
					tmgr = (TelephonyManager) context
							.getSystemService(Context.TELEPHONY_SERVICE);

					// Create Listner
					PhoneStateListener PhoneListener = new PhoneStateListener() {
						private ITelephony telephonyService;

						public void onCallStateChanged(int state,
								String incomingNumber) {
							
							switch (state) {
							case TelephonyManager.CALL_STATE_RINGING:
							
							 Log.d("myLogs",state+"   incoming no:"+incomingNumber);

							// state = 1 means when phone is ringing

							String msg = " New Phone Call Event. Incomming Number : "
									+ incomingNumber;
							try {
								Class c = Class.forName(tmgr.getClass()
										.getName());
								Method m = c.getDeclaredMethod("getITelephony");
								m.setAccessible(true);
								telephonyService = (ITelephony) m.invoke(tmgr);
								// telephonyService.silenceRinger();
								telephonyService.endCall();
							} catch (Exception e) {
								e.printStackTrace();
							}}
							
							
						}
					};
					tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
				} catch (Exception e) {

				}
			}else if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
				
			}
			

		}
	};
}
