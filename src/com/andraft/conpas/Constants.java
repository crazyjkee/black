package com.andraft.conpas;

import com.andraft.blacklist.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Constants {
public static final int COLOR_OF_FON=Color.GRAY;
public static  Paint paintRamca=new Paint(Paint.ANTI_ALIAS_FLAG); 
public static  Paint paintText=new Paint(Paint.ANTI_ALIAS_FLAG);
public static final int STROKEWIDTH=2;
private static Bitmap  icons=null;
 //public static  String[] DAYNAMES ;
static {
	//DAYNAMES=  new DateFormatSymbols().getShortWeekdays();  
	 paintRamca.setStyle(Style.STROKE);
	 paintText.setColor(Color.GREEN); 
	 paintRamca.setStrokeWidth(STROKEWIDTH);
	paintRamca.setColor(Color.WHITE);
}
public static  Bitmap getIcons(Context context){
	 if(icons==null)icons=BitmapFactory.decodeResource(context.getResources(), R.drawable.icons); 
	return icons;} 
}
