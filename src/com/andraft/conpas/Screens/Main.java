package com.andraft.conpas.Screens;

import com.andraft.blacklist.Checker;
import com.andraft.blacklist.MainActivity;

import android.graphics.Canvas; 
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import static com.andraft.conpas.Screens.Constants.*;

public class Main extends Screen{
	private RectF RectFs[]=new RectF [6];
	private com.andraft.conpas.Screens.Constants.ico icon[]={ico.calllist,ico.calllist,ico.opt,ico.sms,ico.timer,ico.down,ico.opt};
  public Main( ) {
	  super.iconca=ico.smslist;
	  
	 for (int i = 0; i < 4; i++) {
		 final int dx=(i%2==0)?0:w/2;
		 final int dy=(i>1)?h/2-w/2:h/2 ;
		 RectFs[i]= new RectF(dx,dy,w/2+dx,dy+w/2);		 
	}
	 RectFs[4]= new RectF(0,h-h/10,w/2 , h);
	 RectFs[5]= new RectF(w/2,h-h/10,w ,h ); 
	 
 } 	
  
	@Override
	public boolean onTouch(MotionEvent event) { 
		MainActivity.setActiveScreen(Checker.phaseTimer);
		final float x=event.getX()-w/2;
		final float y=event.getY()-h/2; 
		if(x*x  + y*y < w*w/16)Log.i("попал в центр","попал в центр");
		return true;
	}
	@Override
	public void OnDraw(Canvas canvas) { 
		 super.OnDraw(canvas);  
			 for (int j = 0; j < 6; j++) {
				canvas.drawRect(RectFs[j], WhiteRamca);
				Constants.DrowIcon(canvas, icon[j], RectFs[j]);
			} 
		 canvas.drawCircle(w/2, h/2, w/4, FONfill);
		 canvas.drawCircle(w/2, h/2, w/4, WhiteRamca); 
		 Constants.DrowIcon(canvas, ico.white, w/2,h/2);
	}
}
