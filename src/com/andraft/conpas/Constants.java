package com.andraft.conpas;
 
import com.andraft.blacklist.R; 
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

public class Constants {
public static  int  FON ;
private static RectF CashedRectf= new RectF(); 
public static  Paint PaintRamca=new Paint(Paint.ANTI_ALIAS_FLAG); 
public static final int STROKEWIDTH=2,BUTTONWIDTH=36;
private static int BitmapHeight=0;
public static Bitmap  ICONS=null;
public static String[] FromTo=new String[2];

public static int Silense[][][] =null;
 static{ PaintRamca.setStyle(Style.STROKE);
 PaintRamca.setColor(Color.WHITE); 
 PaintRamca.setTextAlign(Align.CENTER);; 
 }
 
public static void init (Resources res ) {
	if(Silense==null){
		Silense=new int [7][2][2];
			for (int i = 0; i < 7; i++) 
			for (int j = 0; i < 2; i++)
			for (int k = 0; i < 2; i++)
				Silense[i][j][k]=0;
		 
	}
	if(ICONS!=null) ICONS.recycle();
		 FON=res.getColor(R.color.fon);
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
	if(dst.width()>halfW*2){
		CashedRectf.set(dst.centerX()-halfW , dst.centerY()-halfW , dst.centerX()+halfW ,dst.centerY()+halfW );
	} 
	else{
		final float w=Math.min(dst.width(), dst.height());
		CashedRectf.set(0,0, w, w);
		CashedRectf.offset(dst.centerX()-w/2, dst.centerY()-w/2);
	}
	canvas.drawBitmap(ICONS,new Rect(ico.ordinal()*BitmapHeight,0,(ico.ordinal()+1)*BitmapHeight,BitmapHeight)  ,
			CashedRectf,null);
}
 
}
 
 