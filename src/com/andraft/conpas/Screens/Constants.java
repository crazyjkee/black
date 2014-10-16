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

import com.andraft.blacklist.Checker;
import com.andraft.blacklist.MainActivity;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;

public class Constants {
	public static Resources Res;
	private static RectF CashedRectf = new RectF();
	public static final int STROKEWIDTH = 2;
	private static int BitmapHeight = 0;
	public static Bitmap ICONS = null;
	public static RectF BannerIcon=new RectF();
	public static Paint WhiteRamca = new Paint(Paint.ANTI_ALIAS_FLAG);// white
																		// stroke
																		// textAligneCentr
	public static Paint FONfill = new Paint(Paint.ANTI_ALIAS_FLAG);// Fon
																	// fillAndstroke
																	// textAligneCentr
	public static Paint PaintPressed = new Paint(Paint.ANTI_ALIAS_FLAG);// Fon
																		// fillAndstroke
																		// textAligneCentr
	public static Paint WhiteText;// Fon fillAndstroke textAligneCentr
	public static int h, w, fontsize;
	public static final String[] DAYNAMES = new DateFormatSymbols()
			.getShortWeekdays();
 
	public static HashMap<String, String> hNumber = new HashMap<String, String>();
	 
	static {
		// PaintPressed.setStyle(Style.STROKE );
		PaintPressed.setShadowLayer(STROKEWIDTH * 2, STROKEWIDTH, STROKEWIDTH,
				Color.GRAY);
		PaintPressed.setAlpha(20);
		WhiteRamca.setStyle(Style.STROKE);
		WhiteRamca.setColor(Color.WHITE);
		WhiteRamca.setTextAlign(Align.CENTER);
		WhiteText = new Paint(WhiteRamca);
		WhiteText.setStyle(Style.FILL);
		FONfill.setStyle(Style.FILL_AND_STROKE);
		FONfill.setTextAlign(Align.CENTER);
		hNumber.put("+7950122121", "Гавнюк");

	}

	public static void init(Resources res, Rect rect) {
		w = rect.width();
		h = rect.height(); 
		if (ICONS != null)
			ICONS.recycle();
		FONfill.setColor(res.getColor(R.color.fon));
		ICONS = BitmapFactory.decodeResource(res, R.drawable.icons);
		BitmapHeight = ICONS.getHeight(); 
		int maxCharsIndaynames = 1;
		for (String s : DAYNAMES)
			maxCharsIndaynames = Math.max(maxCharsIndaynames, s.length());
		fontsize = w / (7 * maxCharsIndaynames + 1);
		FONfill.setTextSize(fontsize);
		MainActivity.mainScreen = new Main();
		MainActivity.setActiveScreen(Checker.phasemain); 
		BannerIcon=new RectF(0,0,getBannerWidth(),getBannerWidth());
	}

	public static void destroy() {
		ICONS.recycle();
		ICONS = null;
	}

	public enum ico {
		smslist, calllist, opt, cancel, ok, add, right, up, down, back_blue, timer, sms, white, black, delete
	}

	public static void DrowIcon(Canvas canvas, ico ico, RectF dst) {
		
		if (dst.width() >h/20)
			CashedRectf.set(dst.centerX() - h/20, dst.centerY() - h/20,
					dst.centerX() + h/20, dst.centerY() + h/20);

		else {
			final float w = Math.min(dst.width(), dst.height());
			CashedRectf.set(0, 0, w, w);
			CashedRectf.offset(dst.centerX() - w / 2, dst.centerY() - w / 2);
		}
		canvas.drawBitmap(ICONS,
				new Rect(ico.ordinal() * BitmapHeight, 0, (ico.ordinal() + 1)
						* BitmapHeight, BitmapHeight), CashedRectf, null);
	}
public static void DrowIcon(Canvas canvas, ico ico, int x, int y) {
	DrowIcon(canvas, ico, new RectF(x-h/10,y-h/10,x+h/10,y+h/10));
			
	}
	 static int getBannerWidth(){
		return (h-w)/8;}

	
}
