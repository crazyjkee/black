package com.andraft.views; 
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View; 
import static com.andraft.conpas.Screens.Constants.*;
public class PhaseMain extends View {
	 
	private String label, opt, sms, call, list_sms, list_call, list_white,
	list_black, schedule;
	private RectF baner  ;
	public PhaseMain(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context); 
	}

	public PhaseMain(Context context) {
		super(context);
		init(context); 
	} 
	private void init(Context context) { 
		res=context.getResources();
		
	}
	private Resources res;
	private void init(Rect r,Resources res) {  
		 label =res.getString(R.string.app_name);
		opt = res.getString(R.string.main_opt);
		sms = res.getString(R.string.sms);
		call = res.getString(R.string.call);
		list_sms = res.getString(R.string.main_list_sms);
		list_call = res.getString(R.string.main_list_call);
		list_white = res.getString(R.string.main_white_list);
		list_black =res.getString(R.string.main_black_list);
		schedule =res.getString(R.string.main_schedule);
		Constants.init(res, r);
		baner=new RectF(0,0,w, (h-w)/4);
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas); 
		if( w==0) init(  canvas.getClipBounds(), res);
		canvas.drawColor(PaintFON.getColor()) ; 
		Paint p =new Paint();
		p.setColor(Color.WHITE);
		canvas.drawRect(baner, p );
		 Constants.DrowIcon(canvas, ico.opt, new RectF(102,102,155,155));
		canvas.drawText("text",baner.centerX(), baner.centerY(),PaintFON);
		
		
	}}