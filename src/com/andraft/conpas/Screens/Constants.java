package com.andraft.conpas.Screens;

import java.text.DateFormatSymbols;
import java.util.List;

import android.content.Context;
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
import android.util.Log;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.MainActivity;
import com.andraft.blacklist.R;
import com.andraft.blacklist.ecrans;
import com.andraft.models.NumberOptionsModel;
import com.andraft.models.ScheduleModel;
import com.andraft.models.SmsOptionsModel;

public class Constants {
	private static Checking checking;
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
	public static boolean BLOCK_ALL_SMS, BLOCK_HIDDEN_SMS,
			BLOCK_NOTIFICATIONS_SMS;
	public static boolean BLOCK_ALL_CALLS, BLOCK_HIDDEN_CALLS,
			BLOCK_NOTIFICATION_CALLS, SILENT_MODE, BUSY_MODE;
	public static int Silence[][][] = new int[7][2][2];
	private static colors colorka = colors.blue;

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
		init(colorka, true);
		FONfill.setTextSize(res.getInteger(R.integer.fontsize) * 1.1f);

		FONwhitefill.set(FONfill);
		FONwhitefill.setColor(res.getColor(R.color.fon_white));
		MainActivity.main = new Main();
		MainActivity.setActiveScreen(ecrans.main);
		WhiteRamca.setStrokeWidth(res.getInteger(R.integer.stokewidth));
		;
		checking = Checking.getInstance(context);
		;
		initNumberOptions();
		initSmsOptions();
		initSchedule();
	}

	public static void initSchedule() {
		List<ScheduleModel> sch = checking.getDb().getSchedule();
		for (ScheduleModel sched : sch) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					if (i == 0 && j == 0)
						Silence[sched.getDay()][i][j] = sched.getFromHour();
					else if (i == 0 && j == 1)
						Silence[sched.getDay()][i][j] = sched.getFromMinute();
					else if (i == 1 && j == 0)
						Silence[sched.getDay()][i][j] = sched.getToHour();
					else if (i == 1 && j == 1)
						Silence[sched.getDay()][i][j] = sched.getToMinute();
				}

			}

		}

	}

	public static void initSmsOptions() {
		SmsOptionsModel sms = checking.getDb().getSmsOptionsModel(1);
		if (sms.isBlock_all_sms() == 1) {
			BLOCK_ALL_SMS = true;
			//Log.d("myLogs", "BLOCK_ALL_SMS true");
		} else {
			BLOCK_ALL_SMS = false;
			//Log.d("myLogs", "BLOCK_ALL_SMS false");
		}
		if (sms.isBlock_hidden_numbers_sms() == 1) {
			BLOCK_HIDDEN_SMS = true;
			//Log.d("myLogs", "BLOCK_HIDDEN_SMS true");
		} else {
			BLOCK_HIDDEN_SMS = false;
			//Log.d("myLogs", "BLOCK_HIDDEN_SMS false");
		}
		if (sms.isBlock_notifications_sms() == 1) {
			BLOCK_NOTIFICATIONS_SMS = true;
			//Log.d("myLogs", "BLOCK_NOTIFICATIONS_SMS true");
		} else {
			BLOCK_NOTIFICATIONS_SMS = false;
			//Log.d("myLogs", "BLOCK_NOTIFICATIONS_SMS false");
		}
	}

	public static void initNumberOptions() {
		NumberOptionsModel num = checking.getDb().getNumberOptionsModel(1);

		if (num.isBlock_all_calls() == 1) {
			BLOCK_ALL_CALLS = true;
			//Log.d("myLogs", "BLOCK_ALL_CALLS true");
		} else {
			BLOCK_ALL_CALLS = false;
			//Log.d("myLogs", "BLOCK_ALL_CALLS false");
		}
		if (num.isBlock_hidden_numbers_calls() == 1) {
			BLOCK_HIDDEN_CALLS = true;
			//Log.d("myLogs", "BLOCK_HIDDEN_CALLS true");
		} else {
			BLOCK_HIDDEN_CALLS = false;
			//Log.d("myLogs", "BLOCK_HIDDEN_CALLS false");
		}
		if (num.isBlock_notifications_calls() == 1) {
			BLOCK_NOTIFICATION_CALLS = true;
			//Log.d("myLogs", "BLOCK_NOTIFICATIONS_CALLS true");
		} else {
			BLOCK_NOTIFICATION_CALLS = false;
			//Log.d("myLogs", "BLOCK_NOTIFICATION_CALLS false");
		}
		if (num.isSilent_mode() == 1) {
			SILENT_MODE = true;
			//Log.d("myLogs", "SILENT_MODE true");
		} else {
			SILENT_MODE = false;
			//Log.d("myLogs", "SILENT_MODE false");
		}
		if (num.isBusy_mode() == 1) {
			BUSY_MODE = true;
			//Log.d("myLogs", "BUSY_MODE true");
		} else {
			BUSY_MODE = false;
			//Log.d("myLogs", "BUSY_MODE false");

		}
	}

	public static void init(colors color, boolean first) {
		if (FONfill.getColor() == getColor(color) && !first)
			return;
		switch (color) {
		case blue:
			colorka = colors.blue;
			FONfill.setColor(Res.getColor(R.color.fon_blue));
			ICONS = BitmapFactory.decodeResource(Res, R.drawable.icons);
			break;
		case gray:
			colorka = colors.gray;
			FONfill.setColor(Res.getColor(R.color.fon_gray));
			if(ICONS != null)
			ICONS = changeColor(ICONS, colors.gray);
			else{
				ICONS = BitmapFactory.decodeResource(Res, R.drawable.icons);
				ICONS = changeColor(ICONS, colors.gray);
			}
			break;
		case green:
			colorka = colors.green;
			FONfill.setColor(Res.getColor(R.color.fon_green));
			if(ICONS !=null)
			ICONS = changeColor(ICONS, colors.green);
			else{
				ICONS = BitmapFactory.decodeResource(Res, R.drawable.icons);
				ICONS = changeColor(ICONS, colors.green);
			}
			break;
		default:
			break;
		}

	}

	private static int getColor(colors color) {
		switch (color) {
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
		if(ICONS!=null){
		ICONS.recycle();
		ICONS = null;
		}
	}

	public enum ico {
		linesConvert, linesTruba, bluHren, roundCrest, roundOk, roundPlus, roundRight, roundUp, roundDown, bluRoundLeft, shedule, konvert, whiteList, blackList, roundMinus, truba, shield, write, stat
	}

	public enum colors {
		gray, blue, green
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

	private static Bitmap changeColor(Bitmap bitmap, colors color) {
		int orgWidth = bitmap.getWidth();
		int orgHeight = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(orgWidth, orgHeight,
				Bitmap.Config.ARGB_8888);
		int[] srcPixels = new int[orgWidth * orgHeight];
		int[] dstPixels = new int[orgWidth * orgHeight];
		int col;
		switch (color) {
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
