package com.andraft.conpas.Screens;
 
import com.andraft.blacklist.Checker;
import com.andraft.blacklist.MainActivity;
import com.andraft.blacklist.R;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import static com.andraft.conpas.Screens.Constants.*;
public abstract class Screen {
  com.andraft.conpas.Screens.Constants.ico iconca=com.andraft.conpas.Screens.Constants.ico.back_blue ;
 
  abstract boolean onTouch(MotionEvent event);
public   boolean touch(MotionEvent event){
	if(BannerIcon.contains(event.getX(),event.getY())){
		if(iconca==com.andraft.conpas.Screens.Constants.ico.back_blue){
			MainActivity.setActiveScreen(Checker.phasemain);
			
		}
		return true;
	} 
	else return onTouch(event);
};
 
public   void OnDraw(Canvas canvas){ 
	canvas.drawPaint(Constants.FONfill);
	canvas.drawRect(new RectF(0,0,w,  getBannerWidth()), Constants.WhiteText);
	Constants.DrowIcon(canvas, iconca, BannerIcon);
	canvas.drawText(Res.getString(R.string.app_name),w/2, getBannerWidth(),FONfill);
	 
} 
}
