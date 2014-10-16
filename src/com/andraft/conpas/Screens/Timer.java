package com.andraft.conpas.Screens;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar; 
import com.andraft.conpas.Screens.Constants.ico; 
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint; 
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.Log;
import android.view.MotionEvent;
import static com.andraft.conpas.Screens.Constants.*;

public class Timer extends Screen {
	private int Silense[][][] = null;
	private int fontdays = 0;
	private Paint paintgradient = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static final String[] DAYNAMES = new DateFormatSymbols()
			.getShortWeekdays();;
	private RectF bigDigitals[] = new RectF[2];
	private RectF daysRectf[] = new RectF[7];
	private RectF buttonsDownRectf[] = new RectF[4];
	private RectF buttonsUpRectf[] = new RectF[4];
	private static Paint whiteText = new Paint(PaintWhiteRamca);
	private RectF aciveDay = null, pressedRectf = null;

	public Timer() {
		if (Silense == null) {
			// days fromto chasminuts
			Silense = new int[7][2][2];
			for (int i = 0; i < 7; i++)
				for (int j = 0; i < 2; i++)
					for (int k = 0; i < 2; i++)
						Silense[i][j][k] = 0;
		}

		final int wBigRectf = w / 2 - STROKEWIDTH * 8;
		bigDigitals[0] = new RectF(STROKEWIDTH * 2, h / 2 - wBigRectf / 2,
				STROKEWIDTH * 2 + wBigRectf, h / 2 + wBigRectf / 2);
		bigDigitals[1] = new RectF(bigDigitals[0]);
		bigDigitals[1].offset(w / 2 + STROKEWIDTH * 3, 0);
		final float[] B = { 0.00f, 0.4f, 0.6f, 1f };
		final int[] C = { Color.WHITE, PaintFON.getColor(),
				PaintFON.getColor(), Color.WHITE };
		LinearGradient sh = new LinearGradient(0, bigDigitals[1].top, 0,
				bigDigitals[1].bottom, C, B, TileMode.CLAMP);
		paintgradient.setShader(sh);
		paintgradient.setAlpha(55);
		final float bw = bigDigitals[0].width() / 2;
		int maxCharsIndaynames = 1;
		for (String s : DAYNAMES)
			maxCharsIndaynames = Math.max(maxCharsIndaynames, s.length());
		fontdays = w / (7 * maxCharsIndaynames + 1);
		for (int i = 0; i < daysRectf.length; i++) {
			daysRectf[i] = new RectF(i * w / 7 + STROKEWIDTH * 2,
					STROKEWIDTH * 2, (i + 1) * w / 7 - STROKEWIDTH * 2,
					fontdays * 2);
		}

		for (int i = 0; i < buttonsDownRectf.length; i++) {
			buttonsDownRectf[i] = new RectF(0, 0, bw, bw);
			buttonsUpRectf[i] = new RectF(0, 0, bw, bw);
		}

		for (int i = 0; i < bigDigitals.length; i++) {
			buttonsUpRectf[i].offset(bigDigitals[i].left, bigDigitals[i].top
					- bw);
			buttonsUpRectf[i + 2].offset(bigDigitals[i].right - bw,
					bigDigitals[i].top - bw);
			buttonsDownRectf[i].offset(bigDigitals[i].left,
					bigDigitals[i].bottom);
			buttonsDownRectf[i + 2].offset(bigDigitals[i].right - bw,
					bigDigitals[i].bottom);
		}
		Calendar.getInstance();
		aciveDay = (daysRectf[Calendar.DAY_OF_WEEK - 1]);

	}

	 

	private String formatTime(int fromTo) {
		final NumberFormat formatter = new DecimalFormat("00");
		return formatter.format( Silense[getDayOfWeek()][fromTo][0])
				+ ':'
				+ formatter
						.format( Silense[getDayOfWeek()][fromTo][1]);
	}

	private void checkToAndFromTime() {
		final String from = formatTime(0).replaceAll(":", "");
		final String to = formatTime(1).replaceAll(":", "");
		if (Integer.parseInt(to) < Integer.parseInt(from)) {
			Silense[getDayOfWeek()][1][0] = Silense[getDayOfWeek()][0][0];
			Silense[getDayOfWeek()][1][1] = Silense[getDayOfWeek()][0][1];
		}
	}

	private int getDayOfWeek() {
		return Arrays.asList(daysRectf).indexOf(aciveDay);
	}

	@Override
	public void OnDraw(Canvas canvas) {
		//super.OnDraw(canvas);
		 
		 checkToAndFromTime();
		canvas.drawColor(PaintFON.getColor()); 
		 whiteText.setTextSize(fontdays);
		 Paint p=new Paint(whiteText);
		 p.setStyle(Style.FILL_AND_STROKE);
		 p.setColor(PaintFON.getColor());
		for (int i = 0; i < daysRectf.length; i++) { 
			if (aciveDay == daysRectf[i]) { 
				canvas.drawRoundRect(daysRectf[i], STROKEWIDTH * 2,
						STROKEWIDTH * 2, p);
				
				canvas.drawText(DAYNAMES[i + 1], daysRectf[i].centerX(),
						daysRectf[i].centerY() + whiteText.getTextSize() / 3, p); 
			} else { 
				canvas.drawRoundRect(daysRectf[i], STROKEWIDTH * 2,
						STROKEWIDTH * 2,whiteText);
				canvas.drawText(DAYNAMES[i + 1], daysRectf[i].centerX(),
						daysRectf[i].centerY() + whiteText.getTextSize() / 3,
						whiteText);
			}
		} 
		for (int i = 0; i < bigDigitals.length; i++) {
			whiteText.setTextSize( bigDigitals[0].width() / 2.8f);
			canvas.drawRoundRect(bigDigitals[i], STROKEWIDTH * 4,
					STROKEWIDTH * 4, paintgradient);
			canvas.drawRoundRect(bigDigitals[i], STROKEWIDTH * 4,
					STROKEWIDTH * 4, whiteText);
			canvas.drawText(formatTime(i), bigDigitals[i].centerX(),
					bigDigitals[i].centerY() + whiteText.getTextSize() / 3,
					whiteText);
			whiteText.setTextSize( bigDigitals[0].width() / 2.8f);
			canvas.drawText(FromTo[i], bigDigitals[i].centerX(),
					canvas.getHeight() - fontdays * 2, whiteText);
			for (int j = 0; j < 3; j = j + 2) {
				canvas.drawRoundRect(buttonsUpRectf[i + j], STROKEWIDTH * 4,
						STROKEWIDTH * 4, whiteText);
				canvas.drawRoundRect(buttonsDownRectf[i + j], STROKEWIDTH * 4,
						STROKEWIDTH * 4, whiteText);
				Constants.DrowIcon(canvas, ico.up, (buttonsUpRectf[i + j]));
				Constants.DrowIcon(canvas, ico.down, (buttonsDownRectf[i + j]));
			}
		}
		if (pressedRectf != null) {
			canvas.drawRoundRect(pressedRectf, STROKEWIDTH, STROKEWIDTH,
					PaintPressed);
		}
	}
 

	@Override
	public boolean OnTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			pressedRectf = null;
			return true;
		}
		for (RectF r : daysRectf) {
			if (r.contains(event.getX(), event.getY())) {
				aciveDay = r;
				pressedRectf = r;
				return true;
			}
		}
		for (RectF r : buttonsDownRectf) {
			if (r.contains(event.getX(), event.getY())) {
				pressedRectf = r;
				int rect = Arrays.asList(buttonsDownRectf).indexOf(r);
				if (rect > 1) {
					Log.d("dd"+Silense[getDayOfWeek()][rect - 2][1], "dd"+Silense[getDayOfWeek()][rect - 2][0]); 
					if (Silense[getDayOfWeek()][rect - 2][1] == 0)	Silense[getDayOfWeek()][rect - 2][1] = 59;
					else Silense[getDayOfWeek()][rect - 2][1]--;
				} else {
					if (Silense[getDayOfWeek()][rect][0] == 0)
						Silense[getDayOfWeek()][rect][0] = 24;
					Silense[getDayOfWeek()][rect][0]--;
				}
				//Log.d("dd"+Silense[getDayOfWeek()][rect - 2][1], "dd"+Silense[getDayOfWeek()][rect - 2][0]);
				return true;
			}
		}
		for (RectF r : buttonsUpRectf) {
			pressedRectf = r;
			if (r.contains(event.getX(), event.getY())) {
				final int rect = Arrays.asList(buttonsUpRectf).indexOf(r);
				if (rect > 1) {
					if (Silense[getDayOfWeek()][rect - 2][1] == 59)
						Silense[getDayOfWeek()][rect - 2][1] = 0;
					else
						Silense[getDayOfWeek()][rect - 2][1]++;
				} else {
					if (Silense[getDayOfWeek()][rect][0] == 23)
						Silense[getDayOfWeek()][rect][0] = 0;
					else
						Silense[getDayOfWeek()][rect][0]++;
				}
				return true;
			}
		}
		pressedRectf = null;
		return true;
	} 
}
