package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.w;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;

public class SetupCalls extends Screen {
	private static final int HEIGHT = 60;
	private RectF[] rects = new RectF[4];
	private String[] text = new String[6];
	private RectF[] rectShow = new RectF[2];
	private String[] textShow = new String[2];
	private boolean show = false;

	public SetupCalls() {
		super(R.string.setup_calls, Constants.FONwhitefill);
		init();
	}

	private void init() {
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new RectF(0, BannerHeight() + i * HEIGHT, w,
					BannerHeight() + (i + 1) * HEIGHT);
		}
		for (int i = 0; i < rectShow.length; i++) {
			rectShow[i] = new RectF(0, rects[rects.length - 1].bottom + i
					* HEIGHT, w, rects[rects.length - 1].bottom + (i + 1)
					* HEIGHT);
		}
		text[0] = Constants.Res.getString(R.string.block_all_calls);
		text[1] = Constants.Res.getString(R.string.block_hidden_numbers);
		text[2] = Constants.Res.getString(R.string.notice);
		text[3] = Constants.Res.getString(R.string.call_forwarding);
		text[4] = Constants.Res.getString(R.string.notice);
		text[5] = Constants.Res.getString(R.string.version);
		textShow[0] = Constants.Res.getString(R.string.silent);
		textShow[1] = Constants.Res.getString(R.string.busy);
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
						text[5],
						rects[i].centerX(),
						rects[i].bottom

								- (Math.abs(WhiteTextSmall.ascent()
										+ Math.abs(WhiteTextSmall.descent()))),
						this.WhiteTextSmall);
				Constants.DrowIcon(canvas, ico.roundCrest, rects[i].width() - 3
						* Constants.Res.getInteger(R.integer.smallIconWidth)
						/ 2, rects[i].centerY(), true);
			} else {
				canvas.drawText(text[i], rects[i].centerX(),
						rects[i].centerY(), this.WhiteText);
			}
			if(i==rects.length-1){
				Constants.DrowIcon(canvas, ico.roundDown, rects[i].width()-3*Constants.Res.getInteger(R.integer.smallIconWidth)/2, rects[i].centerY(), true);
			}else{
				Constants.DrowIcon(canvas, ico.roundCrest, rects[i].width()-3*Constants.Res.getInteger(R.integer.smallIconWidth)/2, rects[i].centerY(), true);
			}
		}

		if (show)
			for (int i = 0; i < rectShow.length; i++) {
				canvas.drawLine(0, rectShow[i].top, w, rectShow[i].top,
						Constants.WhiteRamca);
				canvas.drawRect(rectShow[i], Constants.FONfill);
				canvas.drawLine(0, rectShow[i].bottom, w, rectShow[i].bottom,
						Constants.WhiteRamca);
				canvas.drawText(textShow[i], rectShow[i].centerX(),
						rectShow[i].centerY(), this.WhiteText);
			}

	}

	@Override
	boolean onTouch(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN)
			return true;
		if(event.getAction()==MotionEvent.ACTION_UP){
			if(rects[rects.length-1].contains(event.getX(),event.getY())){
				if(!show)
					show = true;
				else
					show = false;
			}
			return false;
		}
		return false;
	}

}
