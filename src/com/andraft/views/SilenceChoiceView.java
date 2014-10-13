package com.andraft.views;


import static com.andraft.conpas.Constants.COLOR_OF_FON;
import static com.andraft.conpas.Constants.STROKEWIDTH;
import static com.andraft.conpas.Constants.getIcons;
import static com.andraft.conpas.Constants.paintRamca;
import static com.andraft.conpas.Constants.paintText;

import java.text.DateFormatSymbols;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SilenceChoiceView extends View {
	Context context;
	private Rect src, dst;
	private RectF daysRectf[] = new RectF[7];
	private RectF bigDigitals[] = new RectF[2];
	private RectF seecbarRectf = null;
	private int fontsize;
	private String[] DAYNAMES;

	public SilenceChoiceView(Context context) {
		super(context);
		this.context = context;
		initRects();
	}

	private void initRects() {
		// TODO Auto-generated method stub
		src = new Rect();
		dst = new Rect();
		
	}

	public SilenceChoiceView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initRects();
	}

	public SilenceChoiceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initRects();

	}



	private void init(int w, int h) {
		final int wBigRectf = w / 2 - STROKEWIDTH * 8;
		bigDigitals[0] = new RectF(STROKEWIDTH * 2, h / 2 - wBigRectf / 2,
				STROKEWIDTH * 2 + wBigRectf, h / 2 + wBigRectf / 2);
		bigDigitals[1] = new RectF(bigDigitals[0]);
		bigDigitals[1].offset(w / 2, 0);
		seecbarRectf = new RectF(STROKEWIDTH, h - 80, w - STROKEWIDTH, h - 22);
		DAYNAMES = new DateFormatSymbols().getShortWeekdays();
		int maxCharsIndaynames = 1;
		for (String s : DAYNAMES)
			maxCharsIndaynames = Math.max(maxCharsIndaynames, s.length());
		fontsize = w / (7 * maxCharsIndaynames + 2);
		paintText.setTextSize(fontsize);
		for (int i = 0; i < daysRectf.length; i++) {
			daysRectf[i] = new RectF(i * w / 7 + STROKEWIDTH * 2, 0, (i + 1)
					* w / 7 - STROKEWIDTH * 2, fontsize * 2);
		}
	}

	@SuppressLint({ "DrawAllocation", "WrongCall" })
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (seecbarRectf == null)
			init(canvas.getWidth(), canvas.getHeight());
		src.set(36, 0, 72, 36);
		dst.set(100, 100, 200, 200);
		canvas.drawColor(COLOR_OF_FON);
		for (int i = 0; i < daysRectf.length; i++) {
			canvas.drawText(DAYNAMES[i + 1], daysRectf[i].left + fontsize / 2,
					daysRectf[i].centerY() + fontsize / 2, paintText);
			canvas.drawRoundRect(daysRectf[i], STROKEWIDTH * 2,
					STROKEWIDTH * 2, paintRamca);
		}
		canvas.drawRoundRect(bigDigitals[0], STROKEWIDTH * 4, STROKEWIDTH * 4,
				paintRamca);
		canvas.drawRoundRect(bigDigitals[1], STROKEWIDTH * 4, STROKEWIDTH * 4,
				paintRamca);
		canvas.drawRoundRect(seecbarRectf, STROKEWIDTH * 4, STROKEWIDTH * 4,
				paintRamca);
		canvas.drawBitmap(getIcons(context), src,
				dst, null);
		// canvas.drawBitmap(icons, new Rect( 0, 0,55,55),new Rect(0,0,55,55),
		// null );
	}
}
