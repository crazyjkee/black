package com.andraft.blacklist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.andraft.views.PhaseMain;
import com.andraft.views.PhaseNumberBlack;
import com.andraft.views.PhaseSmsBlack;

enum Checker {
	phasemain, phaseBlackNumber, phaseBlackSms, phaseCall, phaseOpt, phaseTimer
}

public class MainActivity extends Activity implements PhaseMain.nextCallback,
		PhaseNumberBlack.blackCallback, PhaseSmsBlack.smsCallback,
		OnTouchListener {
	private PhaseMain phaseMain;
	private View dialog, phase;
	private RelativeLayout content;
	private Checker check = Checker.phasemain;
	private boolean second = false;
	private PhaseNumberBlack phaseBlack;
	private PhaseSmsBlack phaseSmsBlack;
	private Checking checking;

	private EditText num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		checking = Checking.getInstance(this);
		showDialog(1);
		Intent service = new Intent(this, BlackService.class);
		this.startService(service);

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog alertDialog = new Dialog(this, R.style.MyAlertDialogTheme);
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);
		LayoutInflater inflater = this.getLayoutInflater();
		// this is what I did to added the layout to the alert dialog
		dialog = inflater.inflate(R.layout.dailog, null);
		dialog.setOnTouchListener(this);
		content = (RelativeLayout) dialog.findViewById(R.id.content);

		phase = inflater.inflate(R.layout.main_phase, null);
		phaseMain = (PhaseMain) phase.findViewById(R.id.mainPhase);
		phaseMain.setNextReadyCallback(this);
		content.addView(phase);
		alertDialog.setContentView(dialog);
		// Showing Alert Message
		return alertDialog;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog1) {
		switch (check) {
		case phasemain:
			if (second) {
				Log.d("myLogs", "phasemain preparedialog");
				dialog = this.getLayoutInflater()
						.inflate(R.layout.dailog, null);
				dialog.setOnTouchListener(this);
				content = (RelativeLayout) dialog.findViewById(R.id.content);
				content.removeView(phase);
				phase = this.getLayoutInflater().inflate(R.layout.main_phase,
						null);
				phaseMain = (PhaseMain) phase.findViewById(R.id.mainPhase);
				phaseMain.setNextReadyCallback(this);

				content.addView(phase);
				dialog1.setContentView(dialog);
			}
			break;
		case phaseBlackNumber:
			dialog = this.getLayoutInflater().inflate(R.layout.dailog, null);
			dialog.setOnTouchListener(this);
			content = (RelativeLayout) dialog.findViewById(R.id.content);
			content.removeView(phase);
			phase = this.getLayoutInflater()
					.inflate(R.layout.black_phase, null);
			phaseBlack = (PhaseNumberBlack) phase.findViewById(R.id.blackPhase);
			phaseBlack.setBlackReadyCallback(this);
			content.addView(phase);
			dialog1.setContentView(dialog);

			break;
		case phaseBlackSms:
			dialog = this.getLayoutInflater().inflate(R.layout.dailog, null);
			dialog.setOnTouchListener(this);
			content = (RelativeLayout) dialog.findViewById(R.id.content);
			content.removeView(phase);
			phase = this.getLayoutInflater()
					.inflate(R.layout.sms_black_phase, null);
			phaseSmsBlack = (PhaseSmsBlack) phase.findViewById(R.id.blackSmsPhase);
			phaseSmsBlack.setSmsReadyCallback(this);
			content.addView(phase);
			dialog1.setContentView(dialog);
			break;
		case phaseCall:
			break;
		case phaseOpt:
			break;

		case phaseTimer:
			break;
		default:
			break;

		}
	}

	@Override
	public void onNextCallback(boolean opt, boolean circle, boolean tele,
			boolean sms, boolean black, boolean smsblack) {
		Log.d("myLogs", "opt:" + opt + ",circle:" + circle + ",tele:" + tele
				+ ",sms:" + sms + ",black:" + black + ",smsblack:" + smsblack);
		second = true;
		if (black) {
			Log.d("myLogs", "black");
			check = Checker.phaseBlackNumber;
			showDialog(100);
		}else if(smsblack){
			Log.d("myLogs","smsblack");
			check = Checker.phaseBlackSms;
			showDialog(100);
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) { // ACTION UP
			Log.d("myLogs", "MainActivity UP");
			switch (check) {
			case phasemain:
				phaseMain.onTouchUp(event.getX(), event.getY());
				Log.d("myLogs", "phaseFirst up");
				break;
			case phaseBlackNumber:
				phaseBlack.onTouchUp(event.getX(), event.getY());
				Log.d("myLogs", "phaseMain up");
				break;
			case phaseBlackSms:
				phaseSmsBlack.onTouchUp(event.getX(), event.getY());
				break;
			case phaseCall:
				break;
			case phaseOpt:
				break;

			case phaseTimer:
				break;
			default:
				break;
			}
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) { // ACTION DOWN
			Log.d("myLogs", "MainActivity DOWN");
			switch (check) {
			case phasemain:
				phaseMain.onTouchDown(event.getX(), event.getY());
				break;
			case phaseBlackNumber:
				phaseBlack.onTouchDown(event.getX(), event.getY());
				break;
			case phaseBlackSms:
				phaseSmsBlack.onTouchDown(event.getX(), event.getY());
				break;
			case phaseCall:
				break;
			case phaseOpt:
				break;

			case phaseTimer:
				break;
			default:
				break;
			}
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			Log.d("myLogs", "MainActivity MOVE");
			switch (check) {
			case phaseBlackNumber:
				phaseBlack.onMoveEvent(event.getX(), event.getY());
				break;
			case phaseBlackSms:
				phaseSmsBlack.onMoveEvent(event.getX(), event.getY());
				break;

			case phaseTimer:
				break;
				
			default:
				break;

			}

		}
		return super.onTouchEvent(event);

	}

	@Override
	public void onblackCallback(boolean back, boolean add, boolean list) {
		Log.d("myLogs", "onblackCallback");

		

		if (add) {
			AlertDialog.Builder customDialog = new AlertDialog.Builder(this);
			customDialog.setTitle("Add phone number");

			LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.add, null);

			num = (EditText) view.findViewById(R.id.numEdit);

			customDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							if (num.length() > 0) {
								Log.d("myLogs", "OK");
								checking.addUnknNumber(num.getText().toString()
										.trim());
								phaseBlack.invalidate();
							}

						}
					});

			customDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub

						}
					});

			customDialog.setView(view);
			customDialog.show();
		}

		if (back) {
			check = Checker.phasemain;
			showDialog(100);
		}

	}

	@Override
	public void onSmsCallback(boolean back, boolean add, boolean list) {
		if (back) {
			check = Checker.phasemain;
			showDialog(100);
		}
		

		

	}
}
