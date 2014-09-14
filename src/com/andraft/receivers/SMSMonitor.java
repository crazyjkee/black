package com.andraft.receivers;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSMonitor extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
    	if (intent != null && intent.getAction() != null &&
    	        ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
    		Log.d("myLogs","Проскочил");
    		
    	    Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
    	    SmsMessage[] messages = new SmsMessage[pduArray.length];
    	    for (int i = 0; i < pduArray.length; i++) {
    	        messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
    	    }
    	    String sms_from = messages[0].getDisplayOriginatingAddress();
    	    StringBuilder bodyText = new StringBuilder();
    	    for (int i = 0; i < messages.length; i++) {
    	        bodyText.append(messages[i].getMessageBody());
    	    }
    	    Log.d("myLogs","from:"+sms_from+"\n bodytext:"+bodyText);
    	}
    	this.abortBroadcast();
    }
}