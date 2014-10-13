package com.andraft.blacklist;

import com.andraft.conpas.Screens.Screen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
  
@SuppressLint("InflateParams")
public class MainActivity extends Activity  { 
private static com.andraft.conpas.Screens.Screen activeScreen;
	

	public static com.andraft.conpas.Screens.Screen getActiveScreen() {
	if(activeScreen==null)return mainScreen;
		return activeScreen;
}
private static Screen mainScreen,timerScreen,blackNumberScreen, blackSmsScreen, callScreen, phaseOpt; 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.conpas_test_layout); 
		mainScreen=new com.andraft.conpas.Screens.Main();
		
		 activeScreen= mainScreen;
	}
 
	@Override
	protected void onDestroy() {
		super.onDestroy();
		 try {
			com.andraft.conpas.Screens.Constants.destroy();
		} finally {}  
	} 
	public static void setActiveScreen(Checker Checker){
		switch (Checker){
		case phaseBlackNumber:
			break;
		case phaseBlackSms:
			break;
		case phaseCall:
			break;
		case phaseOpt:
			break;
		case phaseTimer:
			if(timerScreen==null)timerScreen=new com.andraft.conpas.Screens.Timer();
			MainActivity.activeScreen=timerScreen;
			break;
		case phasemain:
			break;
		default:
			break;}	 
		 
	}
}
