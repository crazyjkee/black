package com.andraft.conpas.Screens;

import java.text.DateFormatSymbols;
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
import com.andraft.blacklist.MainActivity;
import com.andraft.blacklist.R;
import com.andraft.blacklist.ecrans;

public class Constants {
	public static Resources Res;
	private static Bitmap ICONS = null;
	public static Paint WhiteRamca = new Paint(Paint.ANTI_ALIAS_FLAG); 
	 public static Paint FONfill = new Paint(Paint.ANTI_ALIAS_FLAG); 
	public static Paint PaintPressed = new Paint(Paint.ANTI_ALIAS_FLAG);  
	public static int h, w;
	public static final String[] DAYNAMES = new DateFormatSymbols()
			.getShortWeekdays();
 
	public static HashMap<String, String> hNumber = new HashMap<String, String>();
	 
	static {
		// PaintPressed.setStyle(Style.STROKE );
		
		PaintPressed.setAlpha(20);
		WhiteRamca.setStyle(Style.STROKE);
		WhiteRamca.setColor(Color.WHITE);
		WhiteRamca.setTextAlign(Align.CENTER);
		
		FONfill.setStyle(Style.FILL);
		FONfill.setTextAlign(Align.CENTER);
		hNumber.put("+7950122121", "Гавнюк");

	}

	public static void init(Resources res, Rect rect) {
		w = rect.width();
		h = rect.height(); 
		if (ICONS != null)	ICONS.recycle();
		Res=res;
		
		FONfill.setStyle(Style.FILL);
		PaintPressed.setShadowLayer(res.getInteger(R.integer.stokewidth), res.getInteger(R.integer.stokewidth), res.getInteger(R.integer.stokewidth),
				Color.GRAY);
		FONfill.setColor(res.getColor(R.color.fon));
		ICONS = BitmapFactory.decodeResource(res, R.drawable.icons); 
		FONfill.setTextSize(res.getInteger(R.integer.fontsize)*1.1f);;
		MainActivity.main = new Main();
		MainActivity.setActiveScreen(ecrans.main);  
		WhiteRamca.setStrokeWidth(res.getInteger(R.integer.stokewidth));;
	}

	public static void destroy() {
		ICONS.recycle();
		ICONS = null;
	}

	public enum ico {
		linesConvert, linesTruba, bluHren, roundCrest,roundOk,roundPlus,roundRight,roundUp,roundDown,
		bluRoundLeft,shedule,konvert,whiteList,blackList,roundMinus,truba
	}
	
	public static void DrowIcon(Canvas canvas, ico ico,float f,float g,boolean smallTruelargeFalse) {
		  final RectF r = (smallTruelargeFalse)?
			  new RectF(0,0,Res.getInteger(R.integer.smallIconWidth),Res.getInteger(R.integer.smallIconWidth)):
			  new RectF(0,0,Res.getInteger(R.integer.largeIconWidth) ,Res.getInteger(R.integer.largeIconWidth) );
		  r.offsetTo(f-r.width()/2, g-r.width()/2);
		canvas.drawBitmap(ICONS,
				new Rect(ico.ordinal() * ICONS.getHeight(), 0, (ico.ordinal() + 1)
						* ICONS.getHeight(), ICONS.getHeight()), r, null);
	}
 
	 
 
}
