package com.andraft.conpas.Screens;
 
import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import com.andraft.blacklist.R;

public class Constants {
 
private static RectF CashedRectf= new RectF(); 
 
public static final int STROKEWIDTH=2 ;
private static int BitmapHeight=0;
public static Bitmap  ICONS=null;
public static String[] FromTo=new String[2]; 
public static  Paint PaintWhiteRamca=new Paint(Paint.ANTI_ALIAS_FLAG);//white stroke textAligneCentr
public static  Paint PaintFON=new Paint(Paint.ANTI_ALIAS_FLAG);//Fon fillAndstroke textAligneCentr
public static  Paint PaintPressed=new Paint(Paint.ANTI_ALIAS_FLAG);//Fon fillAndstroke textAligneCentr
public static int  h,w;

public static HashMap<String,String> hNumber = new HashMap<String,String>();
 static{
	 //PaintPressed.setStyle(Style.STROKE );
	 PaintPressed.setShadowLayer(STROKEWIDTH*2 ,STROKEWIDTH,STROKEWIDTH, Color.GRAY); 
	 PaintPressed.setAlpha(20);
	 PaintWhiteRamca.setStyle(Style.STROKE);
 PaintWhiteRamca.setColor(Color.WHITE); 
 PaintWhiteRamca.setTextAlign(Align.CENTER); 
 PaintFON.setStyle(Style.FILL_AND_STROKE);
 PaintFON.setTextAlign(Align.CENTER); 
 hNumber.put("+7950122121", "Гавнюк");
 
 
 }
 
public static void init (Resources res ,Rect rect) {
	 w=rect.width();
     h=rect.height(); 
	if(ICONS!=null) ICONS.recycle(); 
	PaintFON.setColor(res.getColor(R.color.fon) );
	ICONS=BitmapFactory.decodeResource( res, R.drawable.icons);
	BitmapHeight=ICONS.getHeight(); 
	FromTo[0]=res.getString(R.string.from);
	FromTo[1]=res.getString(R.string.to);
}
public static void destroy( ) {
	ICONS.recycle();
	ICONS=null;
}
public enum ico{smslist,calllist,opt,cancel,ok,add,right,up,down,left,timer,sms,white,black,delete}
  
public static void DrowIcon(Canvas canvas,ico ico,RectF dst){
	final float halfW=ICONS.getHeight()/2;
	if(dst.width()>halfW*2)
		CashedRectf.set(dst.centerX()-halfW , dst.centerY()-halfW , dst.centerX()+halfW ,dst.centerY()+halfW );
	 
	else{
		final float w=Math.min(dst.width(), dst.height());
		CashedRectf.set(0,0, w, w);
		CashedRectf.offset(dst.centerX()-w/2, dst.centerY()-w/2);
	}
	canvas.drawBitmap(ICONS,new Rect(ico.ordinal()*BitmapHeight,0,(ico.ordinal()+1)*BitmapHeight,BitmapHeight)  ,
			CashedRectf,null);
}}
 
 