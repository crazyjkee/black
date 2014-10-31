package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.w;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;

public class SetupSms extends Screen {
	private static final int HEIGHT = 60;
	private RectF[] rects = new RectF[4];
	private String[] text = new String[6];

	public SetupSms() {
		super(R.string.setup_sms, Constants.FONwhitefill);
		init();
	}

	private void init() {
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new RectF(0, BannerHeight() + i * HEIGHT, w,
					BannerHeight() + (i + 1) * HEIGHT);
		}
		text[0] = Constants.Res.getString(R.string.block_all_sms);
		text[1] = Constants.Res.getString(R.string.block_hidden_numbers);
		text[2] = Constants.Res.getString(R.string.notice);
		text[3] = Constants.Res.getString(R.string.spam_filter);
		text[4] = Constants.Res.getString(R.string.version);
		text[5] = Constants.Res.getString(R.string.coming);

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

			if (i == 2) {
				canvas.drawText(text[i], rects[i].centerX(),
						rects[i].centerY(), this.WhiteText);
				canvas.drawText(
						text[4],
						rects[i].centerX(),
						rects[i].bottom

								- (Math.abs(WhiteTextSmall.ascent()
										+ Math.abs(WhiteTextSmall.descent()))),
						this.WhiteTextSmall);
				Constants.DrowIcon(canvas, ico.roundCrest, rects[i].width() - 3
						* Constants.Res.getInteger(R.integer.smallIconWidth)
						/ 2, rects[i].centerY(), true);
			} else if (i == rects.length - 1) {
				canvas.drawText(text[i], rects[i].centerX(),
						rects[i].centerY(), this.WhiteText);
				canvas.drawText(
						text[5],
						rects[i].centerX(),
						rects[i].bottom

								- (Math.abs(WhiteTextSmall.ascent()
										+ Math.abs(WhiteTextSmall.descent()))),
						this.WhiteTextSmall);

			} else {
				canvas.drawText(text[i], rects[i].centerX(),
						rects[i].centerY(), this.WhiteText);
				Constants.DrowIcon(canvas, ico.roundCrest, rects[i].width() - 3
						* Constants.Res.getInteger(R.integer.smallIconWidth)
						/ 2, rects[i].centerY(), true);
			}
		}
	}

	@Override
	boolean onTouch(MotionEvent event) {

		return false;
	}

}
