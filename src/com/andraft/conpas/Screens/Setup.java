package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.h;
import static com.andraft.conpas.Screens.Constants.w;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.colors;

public class Setup extends Screen {
	private RectF[] rects = new RectF[3];
	private static final int HEIGHT = 60;
	private String[] text = new String[3];
	private RectF switcher,grayr,bluer,greenr;
	private Paint greyp,bluep,greenp,testp; //rgb bluep : 133,211,231; rgb greenp: 110,211,148 rgb grayp: 140,157,155
	SharedPreferences prefs;
	public Setup() {
		super(R.string.setup, Constants.FONwhitefill);
		init();
		 prefs = Constants.context.getSharedPreferences(
			      "com.andraft.blacklist", Context.MODE_PRIVATE);
	}
	private void init(){
		switcher = new RectF(w/8,h-HEIGHT*1.5f,7*w/8,h);
		testp = new Paint();
		testp.setAntiAlias(true);
		testp.setColor(Color.RED);
		grayr = new RectF(switcher.left+5,switcher.top+5,switcher.width()/3-5+switcher.left,switcher.bottom-5);
		bluer = new RectF(switcher.width()/3+5+switcher.left,switcher.top+5,2*switcher.width()/3-5+switcher.left,switcher.bottom-5);
		greenr = new RectF(2*switcher.width()/3+5+switcher.left,switcher.top+5,switcher.width()-5+switcher.left,switcher.bottom-5);
		greyp = new Paint();
		greyp.setColor(Color.rgb(140,157,155));
		greyp.setAntiAlias(true);
		bluep=new Paint();
		bluep.setAntiAlias(true);
		bluep.setColor(Color.rgb(133,211,231));
		greenp = new Paint();
		greenp.setAntiAlias(true);
		greenp.setColor(Color.rgb(110,211,148));
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
				canvas.drawText(
						Constants.Res.getString(R.string.setup_pass),
						rects[0].centerX(),
						rects[0].centerY(),
						this.WhiteText);
				canvas.drawText(
						Constants.Res.getString(R.string.coming),
						rects[0].centerX(),
						rects[0].bottom
								
								- (Math.abs(WhiteTextSmall.ascent()
										+ Math.abs(WhiteTextSmall.descent()))),
						this.WhiteTextSmall);
			}else{
				canvas.drawText(text[i],rects[i].centerX(), rects[i].centerY(), this.WhiteText);
			}
		}
		
		canvas.drawRect(switcher, testp);
		canvas.drawRect(grayr, greyp);
		canvas.drawRect(greenr, greenp);
		canvas.drawRect(bluer, bluep);

	}

	@Override
	boolean onTouch(MotionEvent event) {
if(event.getAction()==MotionEvent.ACTION_DOWN)
	return true;
if(event.getAction()==MotionEvent.ACTION_UP){
	if(grayr.contains(event.getX(),event.getY())){
		Constants.init(colors.gray,false);
		prefs.edit().putString(Constants.color_pref, colors.gray.name()).commit();
	}
	if(bluer.contains(event.getX(),event.getY())){
		Constants.init(colors.blue,false);
		prefs.edit().putString(Constants.color_pref, colors.blue.name()).commit();;
	}
	if(greenr.contains(event.getX(),event.getY())){
		Constants.init(colors.green,false);
		prefs.edit().putString(Constants.color_pref, colors.green.name()).commit();
	}
	
return false;}
		return false;
	}

}
