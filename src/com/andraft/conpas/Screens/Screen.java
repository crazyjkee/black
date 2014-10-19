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
   ico iconca= ico.bluRoundLeft ;
    RectF BannerIcon=new RectF();
  abstract boolean onTouch(MotionEvent event);
  private int label;
   Paint  WhiteText=new Paint(WhiteRamca),WhiteTextSmall;
  public Screen(int label){
	  this.label=label;
	  BannerIcon=new RectF(0,0,Res.getInteger(R.integer.fontsize)*1.4f,Res.getInteger(R.integer.fontsize) *1.4f);
	  if(label==R.string.app_name) BannerIcon.offsetTo(w-2* Constants.Res.getInteger(R.integer.smallIconWidth),0);
	  WhiteText.setTextSize(Res.getInteger(R.integer.fontsize));
	  WhiteText.setStyle(Style.FILL);
	  WhiteText.setLinearText(true);
	  WhiteTextSmall=new Paint(WhiteText);
	  WhiteTextSmall.setTextSize(Res.getInteger(R.integer.fontsize)*0.6f);
  }
public   boolean touch(MotionEvent event){
	if(BannerIcon.contains(event.getX(),event.getY())){
		if(iconca==com.andraft.conpas.Screens.Constants.ico.bluRoundLeft) 
			MainActivity.setActiveScreen(ecrans.main);
		else	
			MainActivity.setActiveScreen(ecrans.setup);
		return false;
	} 
	else return onTouch(event);
};
 
public   void OnDraw(Canvas canvas){ 
	 canvas.drawPaint(FONfill);
	canvas.drawRect(new RectF(0,0,w,  Res.getInteger(R.integer.fontsize)*1.45f), WhiteText);
	 Constants.DrowIcon(canvas, iconca, BannerIcon.centerX(),BannerIcon.centerY(),true);
	canvas.drawText(Res.getString(label),w/2, Res.getInteger(R.integer.fontsize)*1.2f,FONfill); 
} 
}
