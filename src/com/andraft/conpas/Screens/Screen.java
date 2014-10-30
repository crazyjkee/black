package com.andraft.conpas.Screens;

import com.andraft.blacklist.MainActivity;
import com.andraft.blacklist.R;
import com.andraft.blacklist.ecrans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.MotionEvent;
import static com.andraft.conpas.Screens.Constants.*;

public abstract class Screen {
	ico iconca = ico.bluRoundLeft;
	private RectF BannerIcon, ShieldIcon;

	abstract boolean onTouch(MotionEvent event);

	private int label;
	Paint WhiteText = new Paint(WhiteRamca), WhiteTextSmall;
	Paint fon = FONfill;

	public Screen(int label) {
		this.label = label;
		init();
	}
		
	
	
	public Screen(int label,Paint fon){
		this.fon=fon;
		this.label=label;
		init();
		
	}
	
	private void init(){
		BannerIcon = new RectF(0, 0, Res.getInteger(R.integer.fontsize) * 1.4f,
				Res.getInteger(R.integer.fontsize) * 1.4f);
		if (label == R.string.app_name) {
			ShieldIcon = new RectF(0,0,w,BannerIcon.bottom);
			BannerIcon.offsetTo(
					w - Constants.Res.getInteger(R.integer.smallIconWidth)-5, 0);

		}
		WhiteText.setTextSize(Res.getInteger(R.integer.fontsize));
		WhiteText.setStyle(Style.FILL);
		WhiteText.setLinearText(true);
		WhiteTextSmall = new Paint(WhiteText);
		WhiteTextSmall.setTextSize(Res.getInteger(R.integer.fontsize) * 0.6f);
	
	}

	public boolean touch(MotionEvent event) {
		if (BannerIcon.contains(event.getX(), event.getY())) {
			if (iconca == com.andraft.conpas.Screens.Constants.ico.bluRoundLeft)
				MainActivity.setActiveScreen(ecrans.main);
			else
				MainActivity.setActiveScreen(ecrans.setup);
			return false;
		} else
			return onTouch(event);
	};

	float BannerHeight() {
		return Res.getInteger(R.integer.fontsize) * 1.45f;
	}

	public void OnDraw(Canvas canvas) {
		canvas.drawPaint(fon);
		canvas.drawRect(0, 0, w, BannerHeight(), WhiteText);
		Constants.DrowIcon(canvas, iconca, BannerIcon.centerX(),
				BannerIcon.centerY(), true);
		if (ShieldIcon != null) {
			Constants.DrowIcon(canvas, ico.shield, ShieldIcon.centerX(),
					ShieldIcon.centerY(), true);
		}
		if(label!=R.string.app_name)
		canvas.drawText(Res.getString(label), w / 2,
				Res.getInteger(R.integer.fontsize) * 1.2f, FONfill);
		canvas.clipRect(0, BannerHeight(), w, h);
	}
}
