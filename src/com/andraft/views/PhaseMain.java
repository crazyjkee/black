package com.andraft.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
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
	private Paint pmRect, plRect, lText, ptRect, pbRect, pwRect, psRect,
			pcRect, pCircle, pOpt;
	private Rect mRect, lRect, teleRect, blackRect, whiteRect, smsRect,
			circleRect, optRect;
	private float x;
	private float y;
	private boolean bopt = false, btele = false, bsms = false, bwhite = false,
			bblack = false, bcircle = false;

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
		teleRect = new Rect();
		blackRect = new Rect();
		whiteRect = new Rect();
		smsRect = new Rect();
		circleRect = new Rect();
		optRect = new Rect();
		pOpt = new Paint();
		pOpt.setColor(Color.DKGRAY);
		ptRect = new Paint();
		ptRect.setColor(Color.BLUE);
		pbRect = new Paint();
		pbRect.setColor(Color.BLACK);
		pwRect = new Paint();
		pwRect.setColor(Color.WHITE);
		psRect = new Paint();
		psRect.setColor(Color.MAGENTA);
		pmRect = new Paint();
		pmRect.setColor(Color.GREEN);
		pcRect = new Paint();
		pcRect.setColor(Color.RED);
		pcRect.setStyle(Style.STROKE);
		pcRect.setStrokeWidth(5.0f);
		pCircle = new Paint();
		pCircle.setColor(Color.GREEN);
		mRect = new Rect();
		plRect = new Paint();
		plRect.setAntiAlias(true);
		plRect.setColor(Color.BLUE);
		lText = new Paint();
		lText.setAntiAlias(true);
		lText.setTextSize(15);
		lText.setColor(Color.WHITE);
		lRect = new Rect();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d("myLogs",
				"width:" + canvas.getWidth() + ", height:" + canvas.getHeight());

		lRect.set(0, 0, canvas.getWidth(), 50);
		canvas.drawRect(lRect, plRect);
		canvas.drawText("Label", canvas.getWidth()/2, lRect.bottom / 2, lText);

		optRect.set(lRect.right - lRect.height(), 0, lRect.right, lRect.bottom);
		canvas.drawRect(optRect, pOpt);

		mRect.set(0, lRect.bottom * 2, canvas.getWidth(),
				canvas.getHeight() - 100);
		canvas.drawRect(mRect, pmRect);

		teleRect.set(0, mRect.top, mRect.centerX(), mRect.centerY());
		canvas.drawRect(teleRect, ptRect);

		smsRect.set(mRect.centerX(), mRect.top, mRect.right, mRect.centerY());
		canvas.drawRect(smsRect, psRect);

		whiteRect.set(0, mRect.centerY(), mRect.centerX(), mRect.bottom);
		canvas.drawRect(whiteRect, pwRect);

		blackRect.set(mRect.centerX(), mRect.centerY(), mRect.right,
				mRect.bottom);
		canvas.drawRect(blackRect, pbRect);

		circleRect.set(mRect.centerX() / 2, mRect.top + mRect.centerY() / 2,
				mRect.right - mRect.centerX() / 2, mRect.bottom);
		canvas.drawRect(circleRect, pcRect);

		canvas.drawCircle(mRect.centerX(), circleRect.centerY(),
				circleRect.height() / 2, pCircle);

		
	}

	public void onTouchDown(float downX, float downY) {
		bopt = false;
		btele = false;
		bsms = false;
		bwhite = false; 
		bblack = false;
		bcircle = false;
		Log.d("myLogs","downX:"+downX+", downY:"+downY);
		Log.d("myLogs",circleRect.contains((int) downX, (int) downY)+"");
		Log.d("myLogs",circleRect.centerX()+"");
		
		if (circleRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs","circle contains");
			bcircle = true;
			this.invalidate();
			return;
		}
		if (teleRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs","tele contains");
			btele = true;
			this.invalidate();
		}
		if (smsRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs","sms contains");
			bsms = true;
			this.invalidate();
		}
		if (blackRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs","black contains");
			bblack = true;
			this.invalidate();
		}
		if (whiteRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs","white contains");
			bwhite = true;
			this.invalidate();
		}
		if (optRect.contains((int) downX, (int) downY)) {
			Log.d("myLogs","option contains");
			bopt = true;
			this.invalidate();
		}
	}
	public void onTouchUp(float upx,float upy) {
	
		if (optRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs","option contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(true, false, false, false,false,false);
		} else {
			bopt = false;
			this.invalidate();
		}
		if (circleRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs","circle contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, true, false, false,false,false);
			return;
		} else {
			bcircle = false;
			this.invalidate();
		}
		if (teleRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs","tele contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, false, true, false,false,false);
		} else {
			btele = false;
			this.invalidate();
		}
		if (smsRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs","sms contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, false, false, true,false,false);
		} else {
			bsms = false;
			this.invalidate();
		}
		if (whiteRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs","white contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, false, false, false,false,true);
		} else {
			bwhite = false;
			this.invalidate();
		}
		if (blackRect.contains((int) upx, (int) upy)) {
			Log.d("myLogs","black contains");
			if (nextReadyCb != null)
				nextReadyCb.onNextCallback(false, false, false, false,true,false);
		} else {
			bblack = false;
			this.invalidate();
		}

	}

}
