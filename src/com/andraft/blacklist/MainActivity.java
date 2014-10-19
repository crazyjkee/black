package com.andraft.blacklist;

import com.andraft.conpas.Screens.BlackList;
import com.andraft.conpas.Screens.ListOfContacts;
import com.andraft.conpas.Screens.ListOfNumbers;
import com.andraft.conpas.Screens.Schedule;
import com.andraft.conpas.Screens.Setup;
import com.andraft.conpas.Screens.SetupCalls;
import com.andraft.conpas.Screens.SetupSms;
import com.andraft.conpas.Screens.WhiteList;
import com.andraft.conpas.Screens.Constants;
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
	if(activeScreen==null)return main;
		return activeScreen;
}
public static Screen setup,main,setupSMS, setupCalls,
listOfNumbers, listOfContacts,schedule,
whiteList,blackList ; 
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.conpas_test_layout); 
		Constants.Res=this.getResources();
	}
 
	@Override
	protected void onDestroy() {
		super.onDestroy();
		 try {
			com.andraft.conpas.Screens.Constants.destroy();
		} finally {}  
	} 
	public static void setActiveScreen(ecrans screen){
		 
		switch (screen){
		case blackList:
			if(blackList==null)blackList=new BlackList();
			 activeScreen=blackList;
			break;
		case  whiteList:
			if(whiteList==null)whiteList=new  WhiteList();
			 activeScreen=whiteList;
			break;
		 
		case  main:
			
			 activeScreen=main; 
			break;
		case listOfContacts:if(listOfContacts==null)listOfContacts=new  ListOfContacts();
		 activeScreen=listOfContacts;
			break;
		case listOfNumbers:if(listOfNumbers==null)listOfNumbers=new  ListOfNumbers();
		 activeScreen=listOfNumbers;
			break;
		case schedule:if(schedule==null)schedule=new  Schedule();
		 activeScreen=schedule;
			break;
		case setup:if(setup==null)setup=new  Setup();
		 activeScreen=setup;
			break;
		case setupCalls:if(setupCalls==null)setupCalls=new  SetupCalls();
		 activeScreen= setupCalls;
			break;
		case setupSMS:if(setupSMS==null)setupSMS=new SetupSms();
		 activeScreen=setupSMS;
			break;
		 
		 }	 
		
	}
}
