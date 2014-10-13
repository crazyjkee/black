package com.andraft.views;

import com.andraft.blacklist.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
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
	private Paint pmRect, lText, pmText, plRect, pOpt, pSms, pTele,
			pStrokeCircle, mText;
	private Rect mRect, lRect, indRect, indSms, indCall,indTextSms,indTextCall, smsRect, teleRect,
			smsListRect, teleListRect, blackRect, spamRect, optRect, optSms;
	private RectF rectInCircle, circleRect;
	private Rect indSmsCount, indCallCount, optSms1, optSms2, optCall,
			optCall1, optCall2, listSms, listCall, textShed, textWhite,
			textBlack;// TextRect
	private float x;
	private float y;
	private boolean bopt = false, btele = false, bsms = false, bspam = false,
			bblack = false, bcircle = false;
	private String label, opt, sms, call, list_sms, list_call, list_white,
			list_black, schedule;

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
		label = context.getString(R.string.app_name);
		opt = context.getString(R.string.main_opt);
		sms = context.getString(R.string.sms);
		call = context.getString(R.string.call);
		list_sms = context.getString(R.string.main_list_sms);
		list_call = context.getString(R.string.main_list_call);
		list_white = context.getString(R.string.main_white_list);
		list_black = context.getString(R.string.main_black_list);
		schedule = context.getString(R.string.main_schedule);
		mRect = new Rect();
		indRect = new Rect();
		indSms = new Rect();
		indCall = new Rect();
		indTextSms = new Rect();
		indTextCall = new Rect();
		smsListRect = new Rect();
		spamRect = new Rect();
		teleListRect = new Rect();
		teleRect = new Rect();
		blackRect = new Rect();
		smsRect = new Rect();
		circleRect = new RectF();
		rectInCircle = new RectF();
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
		mText = new Paint();
		mText.setColor(Color.WHITE);
		mText.setTextAlign(Align.CENTER);

		indSmsCount = new Rect();
		indCallCount = new Rect();
		optSms = new Rect();
		optSms1 = new Rect();
		optSms2 = new Rect();
		optCall = new Rect();
		optCall1 = new Rect();
		optCall2 = new Rect();
		listSms = new Rect();
		listCall = new Rect();
		textShed = new Rect();
		textWhite = new Rect();
		textBlack = new Rect();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		lRect.set(0, 0, canvas.getWidth(), 50);
		canvas.drawRect(lRect, plRect);
		int numOfChars = lText.breakText(label, true, canvas.getWidth(), null);
		int start = (label.length() - numOfChars) / 2;
		canvas.drawText(label, start, start + numOfChars, lRect.exactCenterX(),
				lRect.exactCenterY(), lText);
		optRect.set(lRect.right - lRect.bottom, 0, lRect.right, lRect.bottom);
		canvas.drawRect(optRect, pOpt);

		indRect.set(0, lRect.bottom + 4, canvas.getWidth(), lRect.bottom + 50);
		canvas.drawRect(indRect, pmRect);
		
		

		mRect.set(0, indRect.bottom, canvas.getWidth(),
				canvas.getHeight() - 100);

		smsRect.set(0, mRect.top + 4, mRect.width() / 2 - 2, mRect.top + 4
				+ mRect.height() / 2 - 2);
		canvas.drawRect(smsRect, pmRect);

		teleRect.set(canvas.getWidth() / 2 + 2, mRect.top + 4,
				canvas.getWidth(), mRect.top + 4 + mRect.height() / 2 - 2);
		canvas.drawRect(teleRect, pmRect);

		smsListRect.set(0, smsRect.bottom + 4, mRect.width() / 2 - 2,
				mRect.bottom - 4);
		canvas.drawRect(smsListRect, pmRect);

		teleListRect.set(canvas.getWidth() / 2 + 2, smsRect.bottom + 4,
				canvas.getWidth(), mRect.bottom - 4);
		canvas.drawRect(teleListRect, pmRect);

		spamRect.set(0, mRect.bottom, canvas.getWidth() / 2 - 2,
				canvas.getHeight() - 2);
		canvas.drawRect(spamRect, pmRect);

		blackRect.set(canvas.getWidth() / 2 + 2, mRect.bottom,
				canvas.getWidth(), canvas.getHeight() - 2);
		canvas.drawRect(blackRect, pmRect);

		circleRect
				.set(mRect.width() / 2 - spamRect.width() / 2
						/ (float) Math.sqrt(2),
						mRect.centerY() - spamRect.width() / 2
								/ (float) Math.sqrt(2),
						mRect.width() / 2 + spamRect.width() / 2
								/ (float) Math.sqrt(2),
						mRect.centerY() + spamRect.width() / 2
								/ (float) Math.sqrt(2));
		rectInCircle.set(
				mRect.width() / 2 - circleRect.height() / 2
						/ (float) Math.sqrt(2),
				mRect.centerY() - circleRect.height() / 2
						/ (float) Math.sqrt(2),
				mRect.width() / 2 + circleRect.height() / 2
						/ (float) Math.sqrt(2),
				mRect.centerY() + circleRect.height() / 2
						/ (float) Math.sqrt(2));

		canvas.drawCircle(mRect.centerX(), circleRect.centerY(),
				circleRect.height() / 2, pmRect);
		canvas.drawCircle(mRect.centerX(), circleRect.centerY(),
				circleRect.height() / 2, pStrokeCircle);
		
		
		indTextSms.set(canvas.getWidth()/2-5-(int)rectInCircle.width()/2, (int)(indRect.top+indRect.height()/4), canvas.getWidth()/2-5, (int)indRect.bottom-indRect.height()/4);
		canvas.drawRect(indTextSms, pTele);
		
		indSms.set(indTextSms.left+5-indRect.height(),indRect.top+5,indTextSms.left-5,indRect.bottom-5);
		canvas.drawRect(indSms, pTele);
		
		indCall.set(canvas.getWidth()/2+5, (int)(indRect.top+5), canvas.getWidth()/2+5+indSms.width(), (int)(indRect.top+5)+indRect.height()-10);
		canvas.drawRect(indCall, pTele);
		
		indTextCall.set(indCall.right+5, indTextSms.top, indCall.right+5+indTextSms.width(), indTextSms.bottom);
		canvas.drawRect(indTextCall, pTele);
		
		// canvas.drawRect(rectInCircle, pTele);
		optSms.set(smsRect.left + 15, (int) circleRect.top,
				(int) rectInCircle.left, smsRect.bottom - 5);
		// canvas.drawRect(optSms, pSms);
		optSms1.set(optSms.left, optSms.top, optSms.right,
				(int) rectInCircle.top);
		// canvas.drawRect(optSms1, pTele);
		optSms2.set(optSms.left + optSms.right - (int) circleRect.left,
				optSms1.bottom + 5, (int) circleRect.left, optSms1.bottom + 5
						+ optSms1.height());
		// canvas.drawRect(optSms2, pTele);

		optCall.set((int) rectInCircle.right, (int) circleRect.top,
				teleRect.right - 15, teleRect.bottom - 5);
		// canvas.drawRect(optCall, pSms);
		optCall1.set(optCall.left, optCall.top, optCall.right,
				(int) rectInCircle.top);
		// canvas.drawRect(optCall1, pTele);
		optCall2.set((int) circleRect.right, optCall1.bottom + 5, optCall.right
				+ (int) rectInCircle.right - (int) circleRect.right,
				optCall1.bottom + 5 + optCall1.height());
		// canvas.drawRect(optCall2, pTele);

		listSms.set(optSms1.left, smsListRect.bottom + optSms2.top
				- optSms.bottom - 5, optSms1.right, smsListRect.bottom
				+ optSms2.top - optSms.bottom + optSms2.height() - 5);
		// canvas.drawRect(listSms, pTele);

		listCall.set(optCall1.left, teleListRect.bottom + optCall2.top
				- optCall.bottom - 5, optCall1.right, teleListRect.bottom
				+ optCall2.top - optCall.bottom + optCall2.height() - 5);
		// canvas.drawRect(listCall, pTele);

		textWhite.set(optSms2.left + (spamRect.right - optSms2.right) / 2,
				spamRect.centerY() - optSms2.height() / 2,
				(int) circleRect.left + (spamRect.right - optSms2.right) / 2,
				spamRect.centerY() + optSms2.height() / 2);
		// canvas.drawRect(textWhite, pTele);

		textBlack.set(optCall2.left + (blackRect.right - optCall2.right) / 2
				- 5, blackRect.centerY() - optCall2.height() / 2,
				optCall2.right + (blackRect.right - optCall2.right) / 2 - 5,
				blackRect.centerY() + optCall2.height() / 2);
		// canvas.drawRect(textBlack, pTele);

		textShed.set((int) rectInCircle.left, (int) rectInCircle.bottom
				- textBlack.height(), (int) rectInCircle.right,
				(int) rectInCircle.bottom);
		// canvas.drawRect(textShed, pTele);

		setTextSize(opt, mText, optSms1.width());
		canvas.drawText(opt, optSms1.centerX(),
				optSms1.centerY() + optSms1.height() / 2, mText);

		setTextSize(opt, mText, optSms2.width());
		canvas.drawText(sms, optSms2.centerX(),
				optSms2.centerY() + optSms2.height() / 2, mText);

		setTextSize(opt, mText, optCall1.width());
		canvas.drawText(opt, optCall1.centerX(),
				optCall1.centerY() + optCall1.height() / 2, mText);

		setTextSize(opt, mText, optCall2.width());
		canvas.drawText(call, optCall2.centerX(),
				optCall2.centerY() + optCall2.height() / 2, mText);

		setTextSize(schedule, mText, textShed.width());
		canvas.drawText(schedule, textShed.centerX(), textShed.centerY()
				+ textShed.height() / 2, mText);

		setTextSize(list_sms, mText, listSms.width());
		canvas.drawText(list_sms, listSms.centerX(), listSms.centerY()
				+ listSms.height() / 2, mText);

		setTextSize(list_call, mText, listCall.width());
		canvas.drawText(list_call, listCall.centerX(), listCall.centerY()
				+ listCall.height() / 2, mText);

		setTextSize(list_white, mText, textWhite.width());
		canvas.drawText(list_white, textWhite.centerX(), textWhite.centerY()
				+ textWhite.height() / 2, mText);

		setTextSize(list_black, mText, textBlack.width());
		canvas.drawText(list_black, textBlack.centerX(), textBlack.centerY()
				+ textBlack.height() / 2, mText);

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

	private void setTextSize(String text, Paint paint, int desiredWidth) {

		Rect bounds = new Rect();

		float textSize = 20;
		paint.setTextSize(textSize);

		paint.getTextBounds(text, 0, text.length(), bounds);

		while (bounds.width() > desiredWidth) {
			textSize--;
			paint.setTextSize(textSize);
			paint.getTextBounds(text, 0, text.length(), bounds);
		}

	}

}
