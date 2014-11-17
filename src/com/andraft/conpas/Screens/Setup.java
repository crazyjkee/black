package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.h;
import static com.andraft.conpas.Screens.Constants.w;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.net.Uri;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.colors;
import com.andraft.conpas.Screens.Constants.ico;

public class Setup extends Screen {
	private RectF[] rects = new RectF[3];
	private static final int HEIGHT = 60;
	private String[] text = new String[3];
	private RectF switcher, grayr, bluer, greenr;
	private Paint greyp, bluep, greenp, switchp; // rgb bluep : 133,211,231; rgb
	private Checking checking; // greenp: 110,211,148 rgb
								// grayp: 162,193,190
	private RectF[] icons = new RectF[3];

	public Setup() {
		super(R.string.setup, Constants.FONwhitefill);
		init();
	}

	private void init() {
		checking = Checking.getInstance(Constants.context);
		switcher = new RectF(w / 5, h - (2 * w / 5) / 3 - 20, 4 * w / 5, h);
		switchp = new Paint();
		switchp.set(Constants.FONfill);
		switchp.setAntiAlias(true);
		switchp.setStyle(Style.STROKE);
		switchp.setStrokeWidth(3);

		grayr = new RectF(switcher.left + 5, switcher.top + 5, switcher.width()
				/ 3 - 5 + switcher.left, switcher.bottom - 5);
		bluer = new RectF(switcher.width() / 3 + 5 + switcher.left,
				switcher.top + 5, 2 * switcher.width() / 3 - 5 + switcher.left,
				switcher.bottom - 5);
		greenr = new RectF(2 * switcher.width() / 3 + 5 + switcher.left,
				switcher.top + 5, switcher.width() - 5 + switcher.left,
				switcher.bottom - 5);
		greyp = new Paint();
		greyp.setColor(Color.rgb(140, 157, 155));
		greyp.setAntiAlias(true);
		bluep = new Paint();
		bluep.setAntiAlias(true);
		bluep.setColor(Color.rgb(133, 211, 231));
		greenp = new Paint();
		greenp.setAntiAlias(true);
		greenp.setColor(Color.rgb(110, 211, 148));
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new RectF(0, BannerHeight() + i * HEIGHT, w,
					BannerHeight() + (i + 1) * HEIGHT);
		}
		text[0] = Constants.Res.getString(R.string.setup_pass);
		text[1] = Constants.Res.getString(R.string.comment);
		text[2] = Constants.Res.getString(R.string.clear_stat);
	}

	@Override
	public void OnDraw(Canvas canvas) {
		super.OnDraw(canvas);
		for (int i = 0; i < rects.length; i++) {
			canvas.drawLine(0, rects[i].top, w, rects[i].top,
					Constants.WhiteRamca);
			canvas.drawRect(rects[i], Constants.FONfill);
			canvas.drawLine(0, rects[i].bottom, w, rects[i].bottom,
					Constants.WhiteRamca);
			if (i == 0) {
				canvas.drawText(Constants.Res.getString(R.string.setup_pass),
						rects[0].centerX(), rects[0].centerY(), this.WhiteText);
				canvas.drawText(
						Constants.Res.getString(R.string.coming),
						rects[0].centerX(),
						rects[0].bottom

								- (Math.abs(WhiteTextSmall.ascent()
										+ Math.abs(WhiteTextSmall.descent()))),
						this.WhiteTextSmall);
			} else {
				canvas.drawText(text[i], rects[i].centerX(), rects[i].centerY()
						+ (WhiteText.descent() - WhiteText.ascent()) / 4,
						this.WhiteText);
			}
		}
		Constants.DrowIcon(
				canvas,
				ico.write,
				rects[1].width()
						- Constants.Res.getInteger(R.integer.smallIconWidth),
				rects[1].centerY(), true);
		Constants.DrowIcon(
				canvas,
				ico.stat,
				rects[2].width()
						- Constants.Res.getInteger(R.integer.smallIconWidth),
				rects[2].centerY(), true);
		canvas.drawRoundRect(switcher, 6, 6, switchp);
		canvas.drawRect(grayr, greyp);
		canvas.drawRect(greenr, greenp);
		canvas.drawRect(bluer, bluep);

	}

	@Override
	boolean onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			return true;
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (rects[1].contains(event.getX(), event.getY())) {
				final String appPackageName = Constants.context
						.getPackageName();
				try {
					Constants.context.startActivity(new Intent(
							Intent.ACTION_VIEW, Uri
									.parse("market://details?id="
											+ appPackageName)));
				} catch (android.content.ActivityNotFoundException anfe) {
					Constants.context
							.startActivity(new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("http://play.google.com/store/apps/details?id="
											+ appPackageName)));
				}
			}
			if (rects[2].contains(event.getX(), event.getY())) {
				checking.getDb().updateCountBlackSmsAndNumbers();
				checking.getDb().close();
			}
			if (grayr.contains(event.getX(), event.getY())) {
				switchp.setColor(Constants.Res.getColor(R.color.fon_gray));
				Constants.init(colors.gray, false);

			}
			if (bluer.contains(event.getX(), event.getY())) {
				switchp.setColor(Constants.Res.getColor(R.color.fon_blue));
				Constants.init(colors.blue, false);

			}
			if (greenr.contains(event.getX(), event.getY())) {
				switchp.setColor(Constants.Res.getColor(R.color.fon_green));
				Constants.init(colors.green, false);

			}

			return false;
		}
		return false;
	}

}
