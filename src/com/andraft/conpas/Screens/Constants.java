package com.andraft.conpas.Screens;

import java.text.DateFormatSymbols;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
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
	public static Paint FONwhitefill = new Paint(Paint.ANTI_ALIAS_FLAG);
	public static Paint PaintPressed = new Paint(Paint.ANTI_ALIAS_FLAG);
	public static int h, w;
	public static final String[] DAYNAMES = new DateFormatSymbols()
			.getShortWeekdays();
	public static Context context;
	public static String color_pref = "com.andraft.blacklist.color";
	private SharedPreferences sharedPref = context.getSharedPreferences(
		      "com.andraft.blacklist", Context.MODE_PRIVATE);

	static {
		PaintPressed.setAlpha(20);
		WhiteRamca.setStyle(Style.STROKE);
		WhiteRamca.setColor(Color.WHITE);
		WhiteRamca.setTextAlign(Align.CENTER);
		FONfill.setStyle(Style.FILL);
		FONfill.setTextAlign(Align.CENTER);

	}

	public static void init(Resources res, Rect rect) {
		w = rect.width();
		h = rect.height();
		if (ICONS != null)
			ICONS.recycle();
		Res = res;

		FONfill.setStyle(Style.FILL);
		PaintPressed.setShadowLayer(res.getInteger(R.integer.stokewidth),
				res.getInteger(R.integer.stokewidth),
				res.getInteger(R.integer.stokewidth), Color.GRAY);
		init(colors.blue,true);
		FONfill.setTextSize(res.getInteger(R.integer.fontsize) * 1.1f);
		
		FONwhitefill.set(FONfill);
		FONwhitefill.setColor(res.getColor(R.color.fon_white));
		MainActivity.main = new Main();
		MainActivity.setActiveScreen(ecrans.main);
		WhiteRamca.setStrokeWidth(res.getInteger(R.integer.stokewidth));
		;
	}
	
	public static void init(colors color,boolean first){
		if(FONfill.getColor()==getColor(color)&&!first)
		    return;
		switch(color){
		case blue:
			FONfill.setColor(Res.getColor(R.color.fon_blue));
			ICONS = BitmapFactory.decodeResource(Res, R.drawable.icons);
			break;
		case gray:
			FONfill.setColor(Res.getColor(R.color.fon_gray));
			ICONS = changeColor(ICONS,colors.gray);
			break;
		case green:
			FONfill.setColor(Res.getColor(R.color.fon_green));
			ICONS = changeColor(ICONS,colors.green);
			break;
		default:
			break;
		}
		
	}
	
	private static int getColor(colors color){
		switch(color){
		case blue:
			return Res.getColor(R.color.fon_blue);
		case gray:
			return Res.getColor(R.color.fon_gray);
		case green:
			return Res.getColor(R.color.fon_green);
		
		default:
			break;
		
		}
		return Res.getColor(R.color.fon_blue);
		
	}

	public static void destroy() {
		ICONS.recycle();
		ICONS = null;
	}

	public enum ico {
		linesConvert, linesTruba, bluHren, roundCrest, roundOk, roundPlus, roundRight, roundUp, roundDown, bluRoundLeft, shedule, konvert, whiteList, blackList, roundMinus, truba, shield
	}
	
	public enum colors{
		gray,blue,green
	}

	public static void DrowIcon(Canvas canvas, ico ico, float f, float g,
			boolean smallTruelargeFalse) {
		final RectF r = (smallTruelargeFalse) ? new RectF(0, 0,
				Res.getInteger(R.integer.smallIconWidth),
				Res.getInteger(R.integer.smallIconWidth)) : new RectF(0, 0,
				Res.getInteger(R.integer.largeIconWidth),
				Res.getInteger(R.integer.largeIconWidth));
		r.offsetTo(f - r.width() / 2, g - r.width() / 2);
		canvas.drawBitmap(ICONS, new Rect(ico.ordinal() * ICONS.getHeight(), 0,
				(ico.ordinal() + 1) * ICONS.getHeight(), ICONS.getHeight()), r,
				null);
	}  

	private static Bitmap changeColor(Bitmap bitmap,colors color) {
		int orgWidth = bitmap.getWidth();
		int orgHeight = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(orgWidth, orgHeight,
				Bitmap.Config.ARGB_8888);
		int[] srcPixels = new int[orgWidth * orgHeight];
		int[] dstPixels = new int[orgWidth * orgHeight];
		int col;
		switch(color){
		case blue:
			col = Color.parseColor(Res.getString(R.color.fon_blue));
			break;
		case gray:
			col = Color.parseColor(Res.getString(R.color.fon_gray));
			break;
		case green:
			col = Color.parseColor(Res.getString(R.color.fon_green));
			break;
		default:
			col = Color.parseColor(Res.getString(R.color.fon_blue));
			break;
		
		}
		
		bitmap.getPixels(srcPixels, 0, orgWidth, 0, 0, orgWidth, orgHeight);

		for (int i = 0; i < srcPixels.length; i++) {
			if (Color.blue(srcPixels[i]) != 255
					&& Color.blue(srcPixels[i]) != 0) {
				dstPixels[i] = col;
			} else {
				dstPixels[i] = srcPixels[i];
			}

		}

		newBitmap.setPixels(dstPixels, 0, orgWidth, 0, 0, orgWidth, orgHeight);

		return newBitmap;

	}

}
