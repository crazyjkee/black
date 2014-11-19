package com.andraft.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.andraft.blacklist.BlackService;

public class BooterReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		 if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
		Log.d("myLogs","BOOT SERVICE");
		Intent service = new Intent(context, BlackService.class);
		context.startService(service);
		 }
	}

}
