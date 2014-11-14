package com.andraft.conpas.Screens;

import java.util.Arrays;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.MainActivity;
import com.andraft.blacklist.R;
import com.andraft.blacklist.ecrans;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import static com.andraft.conpas.Screens.Constants.*;

public class Main extends Screen {
	public static int blockedCountSms,blockedCountNumber;
	private RectF RectFs[] = new RectF[6];
	private ico icon[] = { ico.konvert, ico.truba, ico.linesConvert,
			ico.linesTruba, ico.whiteList, ico.blackList, ico.shedule };
	private int iconText[] = { R.string.setup_sms, R.string.setup_calls,
			R.string.list_of_numbers, R.string.list_of_contacts,
			R.string.white_list, R.string.black_list, R.string.schedule };
	private Point[] LargeIconsPosition = new Point[7];
	private Checking checking;

	public Main() {
		super(R.string.app_name);
		super.iconca = ico.bluHren;
		for (int i = 0; i < 4; i++) {
			final int dx = (i % 2 == 0) ? 0 : w / 2;
			final int dy = (i > 1) ? h / 2 : h / 2 - w / 2;
			RectFs[i] = new RectF(dx, dy, w / 2 + dx, dy + w / 2);
		}
		checking = Checking.getInstance(Constants.context);
		blockedCountSms = checking.getDb().getCountBlockSms();
		blockedCountNumber = checking.getDb().getCountBlockNumber();
		checking.getDb().close();

		RectFs[4] = new RectF(0, RectFs[3].bottom, w / 2,h);// RectFs[3].bottom
				//+ RectFs[3].height() / 2);
		RectFs[5] = new RectF(w / 2, RectFs[3].bottom, w,h); //RectFs[3].bottom
				//+ RectFs[3].height() / 2);
		LargeIconsPosition[0] = new Point(w / 4,
				(int) (RectFs[0].centerY() - Res
						.getInteger(R.integer.largeIconWidth) / 2.1f));
		LargeIconsPosition[1] = new Point(w / 4 * 3,
				(int) (RectFs[1].centerY() - Res
						.getInteger(R.integer.largeIconWidth) / 2.1f));
		LargeIconsPosition[2] = new Point(w / 4, (int) RectFs[2].centerY());
		LargeIconsPosition[3] = new Point(w / 4 * 3, (int) RectFs[3].centerY());
		LargeIconsPosition[4] = new Point(
				(int) (w / 2 - Res.getInteger(R.integer.largeIconWidth)),
				(int) RectFs[4].centerY());
		LargeIconsPosition[5] = new Point(
				(int) (w / 2 + Res.getInteger(R.integer.largeIconWidth)),
				(int) RectFs[4].centerY());
		LargeIconsPosition[6] = new Point(w / 2, (int) RectFs[0].bottom
				- Res.getInteger(R.integer.largeIconWidth) / 3);

		Log.d("myLogs",
				"largeIconWidth:" + Res.getInteger(R.integer.largeIconWidth)
						+ "smallIconWidth:"
						+ Res.getInteger(R.integer.smallIconWidth)
						+ " textsize" + Res.getInteger(R.integer.fontsize));
	}

	@Override
	public boolean onTouch(MotionEvent event) {
		MainActivity.setActiveScreen(ecrans.main);
		Log.i("w" + w * w / 4, "w" + w * w / 4);
		final int x = (int) (event.getX() - w / 2);
		final int y = (int) (event.getY() - h / 2);
		if (x * x + y * y < w * w / 25) {
			MainActivity.setActiveScreen(ecrans.schedule);
			return false;
		}
		for (RectF r : RectFs) {
			if (r.contains(event.getX(), event.getY())) {
				final int i = Arrays.asList(RectFs).indexOf(r);
				switch (i) {
				case 0:
					MainActivity.setActiveScreen(ecrans.setupSMS);
					break;
				case 1:
					MainActivity.setActiveScreen(ecrans.setupCalls);
					break;
				case 2:
					MainActivity.setActiveScreen(ecrans.listOfNumbers);
					break;
				case 3:
					MainActivity.setActiveScreen(ecrans.listOfContacts);
					break;
				case 4:
					MainActivity.setActiveScreen(ecrans.whiteList);
					break;
				case 5:
					MainActivity.setActiveScreen(ecrans.blackList);
					break;
				}
				return false;
			}
		}
		return false;
	}

	@Override
	public void OnDraw(Canvas canvas) {
		super.OnDraw(canvas);
		for (int j = 0; j < 6; j++)
			canvas.drawRect(RectFs[j], WhiteRamca);
		canvas.drawCircle(w / 2, h / 2, w / 5, FONfill);
		canvas.drawCircle(w / 2, h / 2, w / 5, WhiteRamca);
		for (int j = 0; j < LargeIconsPosition.length; j++) {
			Constants.DrowIcon(canvas, icon[j], LargeIconsPosition[j].x,
					LargeIconsPosition[j].y, false);
			if (j == 4) {
				WhiteTextSmall.setTextAlign(Align.RIGHT);
				canvas.drawText(
						Res.getString(iconText[j]),
						w / 2 - Res.getInteger(R.integer.largeIconWidth) * 1.7f,
						LargeIconsPosition[j].y + WhiteTextSmall.getTextSize()
								/ 2, WhiteTextSmall);
			} else if (j == 5) {
				WhiteTextSmall.setTextAlign(Align.LEFT);
				canvas.drawText(
						Res.getString(iconText[j]),
						w / 2 + Res.getInteger(R.integer.largeIconWidth) * 1.7f,
						LargeIconsPosition[j].y + WhiteTextSmall.getTextSize()
								/ 2, WhiteTextSmall);

			} else {
				WhiteTextSmall.setTextAlign(Align.CENTER);
				canvas.drawText(
						Res.getString(iconText[j]),
						LargeIconsPosition[j].x,
						LargeIconsPosition[j].y
								+ Res.getInteger(R.integer.largeIconWidth),
						WhiteTextSmall);

			}
		}

		WhiteText.setTextAlign(Align.RIGHT);
		final int sw = Res.getInteger(R.integer.smallIconWidth) / 2;
		float mt = w / 2 - sw;
		final float dy = (RectFs[1].top + BannerHeight()) / 2;
		canvas.drawText(" " + blockedCountSms, mt,
				dy + Res.getInteger(R.integer.smallIconWidth) / 3, WhiteText);
		Constants.DrowIcon(canvas, ico.konvert,
				mt - sw - WhiteText.measureText(" " + blockedCountSms), dy,
				true);
		WhiteText.setTextAlign(Align.LEFT);
		canvas.drawText(" " + blockedCountNumber, w / 2 + sw * 3,
				dy + Res.getInteger(R.integer.smallIconWidth) / 3, WhiteText);
		Constants.DrowIcon(canvas, ico.truba, w / 2 + sw * 2, dy, true);
		canvas.drawRect(new RectF(0, RectFs[5].bottom, w, h), WhiteText);
	}
}
