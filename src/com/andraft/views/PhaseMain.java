package com.andraft.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PhaseMain extends View {
	public static interface nextCallback {
		public void onNextCallback(boolean opt, boolean circle, boolean tele,
				boolean sms, boolean black, boolean white);
	}

	private Context _context;
	nextCallback nextReadyCb = null;
	private Paint pmRect, lText, pmText,plRect,pOpt,pSms,pTele,pStrokeCircle;
	private Rect mRect, lRect, indRect, indSms, indCall, smsRect, teleRect,
			smsListRect, teleListRect, blackRect, spamRect, circleRect,
			optRect;
	private Rect indSmsCount,indCallCount,optSms1,optSms2,optCall1,optCall2,listSms,listCall,textShed,textSpam,textBlack;//TextRect
	private float x;
	private float y;
	private boolean bopt = false, btele = false, bsms = false, bspam = false,
			bblack = false, bcircle = false;
	private String label = "Blacklist lite";

	public PhaseMain(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public PhaseMain(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public PhaseMain(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public void setNextReadyCallback(nextCallback cb) {
		nextReadyCb = cb;
	}

	private void init(Context context) {
		_context = context;
		mRect = new Rect();
		indRect = new Rect();
		indSms = new Rect();
		indCall = new Rect();
		smsListRect = new Rect();
		spamRect = new Rect();
		teleListRect = new Rect();
		teleRect = new Rect();
		blackRect = new Rect();
		smsRect = new Rect();
		circleRect = new Rect();
		optRect = new Rect();
		lRect = new Rect();
		pmRect = new Paint();
		pmRect.setColor(Color.parseColor("#58cae7"));
		pmText = new Paint();
		pmText.setColor(Color.WHITE);
		plRect = new Paint();
		plRect.setColor(Color.WHITE);
		lText = new Paint();
		lText.setColor(Color.BLACK);
		lText.setTextAlign(Align.CENTER);
		lText.setTextSize(20);
		pOpt = new Paint();
		pOpt.setColor(Color.DKGRAY);
		pSms = new Paint();
		pTele = new Paint();
		pSms.setColor(Color.RED);
		pTele.setColor(Color.GREEN);
		pStrokeCircle = new Paint();
		pStrokeCircle.setColor(Color.WHITE);
		pStrokeCircle.setStrokeWidth(3);
		pStrokeCircle.setStyle(Paint.Style.STROKE);
		
		indSmsCount=new Rect();
		indCallCount = new Rect();
		optSms1 = new Rect();
		optSms2 = new Rect();
		optCall1 = new Rect();
		optCall2 = new Rect();
		listSms = new Rect();
		listCall = new Rect();
		textShed=new Rect();
		textSpam = new Rect();
		textBlack = new Rect();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		lRect.set(0, 0, canvas.getWidth(), 50);
		canvas.drawRect(lRect, plRect);
		int numOfChars = lText.breakText(label,true,canvas.getWidth(),null);
	    int start = (label.length()-numOfChars)/2;
	    canvas.drawText(label,start,start+numOfChars,lRect.exactCenterX(),lRect.exactCenterY(),lText);
		optRect.set(lRect.right - lRect.bottom, 0, lRect.right, lRect.bottom);
		canvas.drawRect(optRect, pOpt);
		
		indRect.set(0, lRect.bottom+4, canvas.getWidth(), lRect.bottom+50);
		canvas.drawRect(indRect, pmRect);
		

		mRect.set(0, indRect.bottom, canvas.getWidth(),
				canvas.getHeight() - 100);
		
		smsRect.set(0,mRect.top+4,mRect.width()/2-2,mRect.top+4+mRect.height()/2-2);
		canvas.drawRect(smsRect, pmRect);
		
		teleRect.set(canvas.getWidth()/2+2, mRect.top+4, canvas.getWidth(), mRect.top+4+mRect.height()/2-2);
		canvas.drawRect(teleRect, pmRect);
		
		smsListRect.set(0,smsRect.bottom+4,mRect.width()/2-2,mRect.bottom-4);
		canvas.drawRect(smsListRect, pmRect);
		
		teleListRect.set(canvas.getWidth()/2+2,smsRect.bottom+4,canvas.getWidth(),mRect.bottom-4);
		canvas.drawRect(teleListRect, pmRect);

		spamRect.set(0,mRect.bottom,canvas.getWidth()/2-2,canvas.getHeight()-2);
		canvas.drawRect(spamRect, pmRect);

		blackRect.set(canvas.getWidth()/2+2, mRect.bottom, canvas.getWidth(),
				canvas.getHeight()-2);
		canvas.drawRect(blackRect, pmRect);

		circleRect.set(mRect.width()/2-spamRect.width()/2,mRect.centerY()-spamRect.width()/2,
				mRect.width()/2+spamRect.width()/2, mRect.centerY()+spamRect.width()/2);

		canvas.drawCircle(mRect.centerX(), circleRect.centerY(),
				circleRect.height() / 2,pmRect);
		canvas.drawCircle(mRect.centerX(), circleRect.centerY(),
				circleRect.height() / 2,pStrokeCircle);
		
		optSms1.set(circleRect.left+5, circleRect.top, circleRect.left-circleRect.left/4, circleRect.top+smsRect.height()/4);
		canvas.drawRect(optSms1, pSms);
		optSms2.set(circleRect.left/4+10, optSms1.bottom+10, circleRect.left-circleRect.left/4-10, optSms1.bottom+10+optSms1.height());
        canvas.drawRect(optSms2, pSms);
	}

	public void onTouchDown(float downX, float downY) {
		bopt = false;
		btele = false;
		bsms = false;
		bspam = false;
		bblack = false;
		bcircle = false;
		Log.d("myLogs", "downX:" + downX + ", downY:" + downY);
		Log.d("myLogs", circleRect.contains((int) downX, (int) downY) + "");
		Log.d("myLogs", circleRect.centerX() + "");

		if (circleRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs", "circle contains");
			bcircle = true;
			this.invalidate();
			return;
		}
		if (teleRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs", "tele contains");
			btele = true;
			this.invalidate();
		}
		if (smsRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs", "sms contains");
			bsms = true;
			this.invalidate();
		}
		if (blackRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs", "black contains");
			bblack = true;
			this.invalidate();
		}
		if (spamRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs", "spam contains");
			bspam = true;
			this.invalidate();
		}
		if (optRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs", "option contains");
			bopt = true;
			this.invalidate();
		}
	}

	public void onTouchUp(float upx, float upy) {

		if (optRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs", "option contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(true, false, false, false, false,
						false);
		} else {
			bopt = false;
			this.invalidate();
		}
		if (circleRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs", "circle contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, true, false, false, false,
						false);
			return;
		} else {
			bcircle = false;
			this.invalidate();
		}
		if (teleRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs", "tele contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, false, true, false, false,
						false);
		} else {
			btele = false;
			this.invalidate();
		}
		if (smsRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs", "sms contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, false, false, true, false,
						false);
		} else {
			bsms = false;
			this.invalidate();
		}
		if (spamRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs", "white contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, false, false, false, false,
						true);
		} else {
			bspam = false;
			this.invalidate();
		}
		if (blackRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs", "black contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, false, false, false, true,
						false);
		} else {
			bblack = false;
			this.invalidate();
		}

	}

}
