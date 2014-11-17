package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.DAYNAMES;
import static com.andraft.conpas.Screens.Constants.FONfill;
import static com.andraft.conpas.Screens.Constants.PaintPressed;
import static com.andraft.conpas.Screens.Constants.Res;
import static com.andraft.conpas.Screens.Constants.Silence;
import static com.andraft.conpas.Screens.Constants.WhiteRamca;
import static com.andraft.conpas.Screens.Constants.h;
import static com.andraft.conpas.Screens.Constants.w;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;
import com.andraft.models.ScheduleModel;

public class Schedule extends Screen implements Screen.Back {
	private int Silense[][][] = null;
	private Paint paintgradient = new Paint(Paint.ANTI_ALIAS_FLAG);
	private RectF bigDigitals[] = new RectF[2];
	private RectF daysRectf[] = new RectF[7];
	private RectF buttonsDownRectf[] = new RectF[4];
	private RectF buttonsUpRectf[] = new RectF[4];
	private RectF aciveDay = null, pressedRectf = null;
	private int fontSizeWeek;
	private ScheduleModel save;
	private ArrayList<ScheduleModel> saveList;
	private Checking checking;

	public Schedule() {
		super(R.string.schedule);
		this.setBackReadyCb(this);
		Silense = Constants.Silence;
		checking = Checking.getInstance(Constants.context);

		final Integer StrokeWidth = (int) Res.getInteger(R.integer.stokewidth);

		int maxCharsIndaynames = 3;
		for (String s : DAYNAMES)
			maxCharsIndaynames = Math.max(maxCharsIndaynames, s.length());
		fontSizeWeek = w / (7 * maxCharsIndaynames + 1);
		WhiteTextSmall.setTextSize(fontSizeWeek);

		final int wBigRectf = w / 2 - StrokeWidth * 4;
		bigDigitals[0] = new RectF(StrokeWidth * 2, h / 2 - wBigRectf / 2
				+ BannerHeight() / 2, StrokeWidth * 2 + wBigRectf, h / 2
				+ wBigRectf / 2 + BannerHeight() / 2);
		bigDigitals[1] = new RectF(bigDigitals[0]);
		bigDigitals[1].offset(w / 2, 0);
		final float[] B = { 0.00f, 0.4f, 0.6f, 1f };
		final int[] C = { Color.WHITE, FONfill.getColor(), FONfill.getColor(),
				Color.WHITE };
		LinearGradient sh = new LinearGradient(0, bigDigitals[1].top, 0,
				bigDigitals[1].bottom, C, B, TileMode.CLAMP);
		paintgradient.setShader(sh);
		paintgradient.setAlpha(55);
		WhiteText.setTextSize(bigDigitals[0].width() / 2.8f);
		for (int i = 0; i < daysRectf.length; i++) {
			daysRectf[i] = new RectF(i * w / 7 + StrokeWidth,
					BannerHeight() * 1.2f, (i + 1) * w / 7 - StrokeWidth,
					BannerHeight() * 1.2f + fontSizeWeek * 2);
		}
		final float bw = bigDigitals[0].width() / 2 - StrokeWidth * 4;
		for (int i = 0; i < buttonsDownRectf.length; i++) {
			buttonsDownRectf[i] = new RectF(0, 0, bw, bw);
			buttonsUpRectf[i] = new RectF(0, 0, bw, bw);
		}

		for (int i = 0; i < bigDigitals.length; i++) {
			buttonsUpRectf[i].offset(bigDigitals[i].left, bigDigitals[i].top
					- bw - StrokeWidth * 2);
			buttonsUpRectf[i + 2].offset(bigDigitals[i].right - bw,
					bigDigitals[i].top - bw - StrokeWidth * 2);
			buttonsDownRectf[i].offset(bigDigitals[i].left,
					bigDigitals[i].bottom + StrokeWidth * 2);
			buttonsDownRectf[i + 2].offset(bigDigitals[i].right - bw,
					bigDigitals[i].bottom + StrokeWidth * 2);
		}

		aciveDay = (daysRectf[Calendar.DAY_OF_WEEK - 1]);

	}

	private String formatTime(int fromTo) {
		final NumberFormat formatter = new DecimalFormat("00");
		return formatter.format(Silense[getDayOfWeek()][fromTo][0]) + ':'
				+ formatter.format(Silense[getDayOfWeek()][fromTo][1]);
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
		super.OnDraw(canvas);
		checkToAndFromTime();
		final String fromTo[] = { Res.getString(R.string.from),
				Res.getString(R.string.to) };

		for (int i = 0; i < daysRectf.length; i++) {
			if (aciveDay == daysRectf[i]) {
				final Paint p = new Paint(WhiteTextSmall);
				p.setStyle(Style.FILL_AND_STROKE);
				canvas.drawRoundRect(daysRectf[i], fontSizeWeek, fontSizeWeek,
						p);
				p.setColor(FONfill.getColor());
				canvas.drawText(DAYNAMES[i + 1], daysRectf[i].centerX(),
						daysRectf[i].centerY() + fontSizeWeek / 3, p);
			} else {
				canvas.drawRoundRect(daysRectf[i], fontSizeWeek, fontSizeWeek,
						WhiteRamca);
				canvas.drawText(DAYNAMES[i + 1], daysRectf[i].centerX(),
						daysRectf[i].centerY() + fontSizeWeek / 3,
						WhiteTextSmall);
			}
		}

		for (int i = 0; i < bigDigitals.length; i++) {

			canvas.drawRoundRect(bigDigitals[i], fontSizeWeek, fontSizeWeek,
					paintgradient);
			canvas.drawRoundRect(bigDigitals[i], fontSizeWeek, fontSizeWeek,
					WhiteRamca);
			canvas.drawText(formatTime(i), bigDigitals[i].centerX(),
					bigDigitals[i].centerY() + WhiteText.getTextSize() / 3,
					WhiteText);
			canvas.drawText(fromTo[i], bigDigitals[i].centerX(),
					canvas.getHeight() - fontSizeWeek, WhiteTextSmall);
			for (int j = 0; j < 3; j = j + 2) {

				canvas.drawRoundRect(buttonsUpRectf[i + j], fontSizeWeek,
						fontSizeWeek, WhiteRamca);
				canvas.drawRoundRect(buttonsDownRectf[i + j], fontSizeWeek,
						fontSizeWeek, WhiteRamca);
				Constants.DrowIcon(canvas, ico.roundUp,
						buttonsUpRectf[i + j].centerX(),
						buttonsUpRectf[i + j].centerY(), true);
				Constants
						.DrowIcon(canvas, ico.roundDown,
								buttonsDownRectf[i + j].centerX(),
								buttonsDownRectf[i + j].centerY(), true);
			}
		}

		if (pressedRectf != null) {
			canvas.drawRoundRect(pressedRectf, fontSizeWeek, fontSizeWeek,
					PaintPressed);
		}

	}

	@Override
	public boolean onTouch(MotionEvent event) {
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
					// Log.d("dd" + Silense[getDayOfWeek()][rect - 2][1], "dd"
					// + Silense[getDayOfWeek()][rect - 2][0]);
					if (Silense[getDayOfWeek()][rect - 2][1] == 0)
						Silense[getDayOfWeek()][rect - 2][1] = 59;
					else
						Silense[getDayOfWeek()][rect - 2][1]--;
				} else {
					if (Silense[getDayOfWeek()][rect][0] == 0)
						Silense[getDayOfWeek()][rect][0] = 24;
					Silense[getDayOfWeek()][rect][0]--;
				}
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

	@Override
	public void onBack() {
		saveList = new ArrayList<ScheduleModel>();
		for (int i = 0; i < 7; i++) {
			save = new ScheduleModel();
			save.setDay(i);
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 2; k++) {
					if (j == 0 && k == 0)
						save.setFromHour(Silence[i][j][k]);
					else if (j == 0 && k == 1)
						save.setFromMinute(Silence[i][j][k]);
					else if (j == 1 && k == 0)
						save.setToHour(Silence[i][j][k]);
					else if (j == 1 && k == 1)
						save.setToMinute(Silence[i][j][k]);
				}
			saveList.add(save);
		}
		checking.getDb().updateSchedule(saveList);
		Constants.initSchedule();
	}
}
