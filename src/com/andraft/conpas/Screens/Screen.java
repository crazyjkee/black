package com.andraft.conpas.Screens;
 
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

public abstract class Screen {
	 
	 
public abstract boolean OnTouch(MotionEvent event);
 
public   void OnDraw(Canvas canvas){
	 
	canvas.drawColor(Color.BLUE);
};
  
}
