package com.andraft.views;
 
import static com.andraft.conpas.Constants.*; 

import com.andraft.blacklist.Ecran;
import  com.andraft.conpas.Constants; 

import java.text.DateFormatSymbols; 
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.LinkedList;  

import com.andraft.conpas.Constants.ico; 

import android.view.MotionEvent;
import android.view.View.OnTouchListener; 
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas; 
import android.graphics.Paint; 
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SilenseChoiceView extends View  {
	public static SilenseChoiceView instance=null ;
	private int fontdays ;
	private static final String[] DAYNAMES = new DateFormatSymbols().getShortWeekdays();;
	private RectF bigDigitals[] = new RectF[2];
	private RectF daysRectf[] = new RectF[7];
	private RectF buttonsDownRectf[] = new RectF[4];
	private RectF buttonsUpRectf[] = new RectF[4];
	private static Paint whiteText=new Paint(PaintRamca); 
	private static LinkedList<RectF> aciveList=new LinkedList <RectF>();
	public SilenseChoiceView(Context context) {
		super(context);
		  Constants.init(this.getResources()); 
	} 

	public SilenseChoiceView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		 Constants.init(this.getResources()); 
	}

	public SilenseChoiceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 Constants.init(this.getResources());
		
	} 
	  
	private void init(int w, int h) { 
		instance=this;
		com.andraft.blacklist.MainActivity.setEcran(Ecran.conpas);
		final int wBigRectf = w / 2 - STROKEWIDTH * 8;
		bigDigitals[0] = new RectF(STROKEWIDTH * 2, h / 2 - wBigRectf / 2,
				STROKEWIDTH * 2 + wBigRectf, h / 2 + wBigRectf / 2);
		bigDigitals[1] = new RectF(bigDigitals[0]);
		bigDigitals[1].offset(w / 2+STROKEWIDTH*3 , 0); 
		 	final float bw= bigDigitals[0].width()/2 ; 
		int maxCharsIndaynames = 1;
		for (String s : DAYNAMES)
			maxCharsIndaynames = Math.max(maxCharsIndaynames, s.length());
		fontdays = w / (7 * maxCharsIndaynames + 1);
		 
		for (int i = 0; i < daysRectf.length; i++) {
			daysRectf[i] = new RectF(i * w / 7 + STROKEWIDTH * 2, STROKEWIDTH*2, (i + 1)
					* w / 7 - STROKEWIDTH * 2, fontdays * 2);
		}
		
		for (int i = 0; i < buttonsDownRectf.length; i++) {
			buttonsDownRectf[i]=new RectF(0,0,bw,bw);
			buttonsUpRectf[i]=new RectF(0,0,bw,bw);
		}
		
		for (int i = 0; i < bigDigitals.length; i++) {
			buttonsUpRectf[i].offset(bigDigitals[i].left,bigDigitals[i].top-bw);
			buttonsUpRectf[i+2].offset(bigDigitals[i].right-bw,bigDigitals[i].top-bw);
			buttonsDownRectf[i].offset(bigDigitals[i].left,bigDigitals[i].bottom);
			buttonsDownRectf[i+2].offset(bigDigitals[i].right-bw,bigDigitals[i].bottom);
		} 
		Calendar.getInstance(); 
		 aciveList.add(daysRectf[Calendar.DAY_OF_WEEK-1 ]);
	}

	@SuppressLint({ "DrawAllocation", "WrongCall" })
	protected void onDraw(Canvas canvas) {
		init(canvas.getWidth(),canvas.getHeight()) ;
		super.onDraw(canvas); 
		canvas.drawColor( FON);
		 
 		for (int i = 0; i < daysRectf.length; i++) {
 			Paint p=new Paint(getFontPaint(fontdays));
 			if(aciveList.contains(daysRectf[i])){ 
 				p.setStyle(Style.FILL_AND_STROKE);  
 				canvas.drawRoundRect(daysRectf[i], STROKEWIDTH * 2,
 						STROKEWIDTH * 2, p);
 				p.setColor(FON);
 				canvas.drawText(DAYNAMES[i + 1], daysRectf[i].centerX(),
 						daysRectf[i].centerY()+whiteText.getTextSize()/3,p);
 			}
 			else {canvas.drawText(DAYNAMES[i + 1], daysRectf[i].centerX(),
					daysRectf[i].centerY()+whiteText.getTextSize()/3,p);
			canvas.drawRoundRect(daysRectf[i], STROKEWIDTH * 2,
					STROKEWIDTH * 2, p);}
		} 	 
 		final int fonttime=(int) (bigDigitals[0].width()/2.8f) ; 
		for (int i = 0; i <bigDigitals.length; i++) {
			canvas.drawRoundRect(bigDigitals[i], STROKEWIDTH * 4,
					STROKEWIDTH * 4, getFontPaint(fonttime));
			canvas.drawText(formatTime(0,i), bigDigitals[i].centerX(),
					bigDigitals[i].centerY()+whiteText.getTextSize()/3,whiteText); 
			int j=0;
			canvas.drawText(FromTo[i], bigDigitals[i].centerX(),
					canvas.getHeight()-fontdays*2,getFontPaint(fontdays)); 
			while(j<3){ 
			canvas.drawRoundRect(buttonsUpRectf[i+j], STROKEWIDTH * 4,
					STROKEWIDTH * 4,whiteText);
			canvas.drawRoundRect(buttonsDownRectf[i+j], STROKEWIDTH * 4,
					STROKEWIDTH * 4,whiteText);
			 Constants.DrowIcon(canvas,ico.up,(buttonsUpRectf[i+j]));
			 Constants.DrowIcon(canvas,ico.down,(buttonsDownRectf[i+j]));
				j=j+2;	
			} 
		} 
	}
private static	Paint getFontPaint(int fontSize){
	 whiteText.setTextSize(fontSize);
	return whiteText;}
private static String formatTime(int day,int fromTo){
	final NumberFormat formatter = new DecimalFormat("00"); 
		return   formatter.format(Constants.Silense[day][fromTo][0])
				+ ':'+
				formatter.format(Constants.Silense[day][fromTo][0]);
} 
 
public  boolean onTouch(MotionEvent event) {
	Log.d("sss"+event.getX(),"sss"+event.getPressure());
	 for(RectF r:daysRectf){
		 if(r.contains(event.getX(),event.getY())) {
			 Log.i("r.contains"+event.getX(),"r.contains"+event.getPressure());
			if( aciveList.contains(r)) aciveList.remove(r) ;
			else  aciveList.add(r);
			SilenseChoiceView.this.invalidate();
			return false;} 
	 }
	return false;
}
}
