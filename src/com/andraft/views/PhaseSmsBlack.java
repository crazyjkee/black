package com.andraft.views;

import java.util.HashMap;

import com.andraft.blacklist.Checking;
import com.andraft.models.NumberModel;
import com.andraft.models.SmsModel;
import com.andraft.views.PhaseNumberBlack.blackCallback;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PhaseSmsBlack extends View {
	public static interface smsCallback {
		public void onSmsCallback(boolean back, boolean add, boolean list);
	}

	private String[] numbers,body;
	private Checking check;
	private int size, textSize = 100;

	private Paint pUnkn, pBlack, pRect, plRect, lText, pback, pbody, pnumber,
			mTextPaint, pAdd, pList, pTextAdd, pTextList;
	private Rect unkn, black, rect, lrect, backrect, bodyrect, numberrect,
			addrect, listrect;
	private int x, y;
	private boolean brect = false;

	private HashMap<String, String> hm;
	private Rect bounds;

	private smsCallback blackReadyCb = null;
	private Rect white;

	public void setSmsReadyCallback(smsCallback b) {
		this.blackReadyCb = b;

	}

	public PhaseSmsBlack(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public PhaseSmsBlack(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PhaseSmsBlack(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		Log.d("myLogs", "init");
		check = Checking.getInstance(context);
		white = new Rect();
		unkn = new Rect();
		black = new Rect();
		rect = new Rect();
		lrect = new Rect();
		backrect = new Rect();
		numberrect = new Rect();
		bodyrect = new Rect();
		addrect = new Rect();
		listrect = new Rect();
		pAdd = new Paint();
		pAdd.setColor(Color.RED);
		pList = new Paint();
		pList.setColor(Color.MAGENTA);
		pTextAdd = new Paint();
		pTextAdd.setColor(Color.WHITE);
		pTextList = new Paint();
		pTextList.setColor(Color.WHITE);
		pnumber = new Paint();
		pnumber.setColor(Color.YELLOW);
		pbody = new Paint();
		pbody.setColor(Color.RED);
		pUnkn = new Paint();
		lText = new Paint();
		plRect = new Paint();
		pUnkn.setColor(Color.LTGRAY);
		pBlack = new Paint();
		pBlack.setColor(Color.BLACK);
		pRect = new Paint();
		pRect.setColor(Color.BLUE);
		plRect.setColor(Color.BLUE);
		lText = new Paint();
		lText.setAntiAlias(true);
		lText.setTextSize(15);
		lText.setColor(Color.WHITE);
		pback = new Paint();
		pback.setAntiAlias(true);
		pback.setColor(Color.GRAY);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d("myLogs","onDraw smsBlack");
		if (!brect) {
			hm = check.sms_minus_contacts();
			size = hm.size();
			body = hm.values().toArray(new String[size]);
			numbers = hm.keySet().toArray(new String[size]);
		}

		rect.set(canvas.getWidth() / 4,
				canvas.getHeight() / 2 - canvas.getWidth() / 8,
				canvas.getWidth() - canvas.getWidth() / 4, canvas.getHeight()
						/ 2 + canvas.getWidth() / 8);
		numberrect.set(rect.left + 5, rect.top + 5, rect.bottom - rect.height()
				/ 2 - 5, rect.right - 5);
		bodyrect.set(rect.left + 5, numberrect.bottom + 5, rect.right - 5,
				rect.bottom - 5);

		lrect.set(0, 0, canvas.getWidth(), 50);
		canvas.drawRect(lrect, plRect);
		backrect.set(0, 0, 50, lrect.bottom);
		canvas.drawRect(backrect, pback);
		canvas.drawText("Label", canvas.getWidth() / 2, lrect.bottom / 2, lText);

		unkn.set(rect.left / 2, (3 * lrect.height() / 2), rect.left / 2
				+ rect.left, (3 * lrect.height() / 2) + rect.left);
		canvas.drawRect(unkn, pUnkn);

		black.set(canvas.getWidth() - rect.left / 2 - rect.left,
				(3 * lrect.height() / 2), canvas.getWidth() - rect.left / 2,
				(3 * lrect.height() / 2) + rect.left);
		canvas.drawRect(black, pBlack);
		white.set(rect.left / 2 + rect.left, rect.bottom + (lrect.height() / 2),
				canvas.getWidth() - rect.left / 2 - rect.left, rect.bottom
						+ (lrect.height() / 2) + rect.left);

		addrect.set(0, white.bottom + 10, canvas.getWidth() / 2 - 5,
				canvas.getHeight());
		canvas.drawRect(addrect, pAdd);
		textSize("Add", addrect);
		canvas.drawText(
				"Add", // Text to display
				addrect.centerX() - bounds.width() / 2,
				addrect.top
						+ (bounds.top + (addrect.height() - bounds.bottom) / 2.0f)
						+ (Math.abs(mTextPaint.ascent()) + Math.abs(mTextPaint
								.descent()) / 2), mTextPaint);
		listrect.set(canvas.getWidth() / 2 + 5, white.bottom + 10,
				canvas.getWidth(), canvas.getHeight());
		canvas.drawRect(listrect, pList);
		textSize("List", addrect);
		canvas.drawText(
				"List", // Text to display
				listrect.centerX() - bounds.width() / 2,
				listrect.top
						+ (bounds.top + (listrect.height() - bounds.bottom) / 2.0f)
						+ (Math.abs(mTextPaint.ascent()) + Math.abs(mTextPaint
								.descent()) / 2), mTextPaint);
		if (size == 0) {
			brect = false;
			canvas.drawRect(rect, pRect);
			textSize("DONE", rect);

			canvas.drawText(
					"DONE",
					rect.centerX() - bounds.width() / 2,
					rect.top
							+ (bounds.top + (rect.height() - bounds.bottom) / 2.0f)
							+ (Math.abs(mTextPaint.ascent()) + Math
									.abs(mTextPaint.descent()) / 2), mTextPaint);
			return;

		}
		if ((this.x != 0 && this.y != 0) && brect) {

			rect.set(this.x - rect.width() / 2, this.y - rect.height() / 2,
					this.x + rect.width() / 2, this.y + rect.height() / 2);
			numberrect.set(rect.left + 5, rect.top + 5, rect.right - 5,
					rect.bottom - rect.height() / 2 - 5);
			bodyrect.set(rect.left + 5, numberrect.bottom + 5, rect.right - 5,
					rect.bottom - 5);
		}
		canvas.drawRect(rect, pRect);

		canvas.drawRect(numberrect, pnumber);

		canvas.drawRect(bodyrect, pnumber);

		textSize(numbers[0], numberrect);

		canvas.drawText(
				numbers[0],
				numberrect.centerX() - bounds.width() / 2,
				numberrect.top
						+ (bounds.top + (numberrect.height() - bounds.bottom) / 2.0f)
						+ (Math.abs(mTextPaint.ascent()) + Math.abs(mTextPaint
								.descent()) / 2), mTextPaint);

		textSize(numbers[0], numberrect);

		canvas.drawText(
				body[0], // Text to display
				bodyrect.centerX() - bounds.width() / 2,
				bodyrect.top
						+ (bounds.top + (numberrect.height() - bounds.bottom) / 2.0f)
						+ (Math.abs(mTextPaint.ascent()) + Math.abs(mTextPaint
								.descent()) / 2),

				mTextPaint);
	}

	private void textSize(String s, Rect res) {
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.GREEN);

		bounds = new Rect();
		textSize = 100;
		mTextPaint.setTextSize(textSize);
		mTextPaint.getTextBounds(s, 0, s.length(), bounds);
		int text_height = bounds.height();
		int text_width = bounds.width();
		Log.d("myLogs", "text_height:" + text_height + " text_width"
				+ text_width);
		while (res.width() - Math.abs(mTextPaint.ascent())
				- Math.abs(mTextPaint.descent()) - 20 < text_width) {
			// have this the same as your text size
			mTextPaint.setTextSize(textSize);
			mTextPaint.getTextBounds(s, 0, s.length(), bounds);

			text_width = bounds.width();
			textSize--;
		}

	}

	public void onTouchUp(float x, float y) {
		Log.d("myLogs", "UP x:" + x + " ,y:" + y);
		if (this.blackReadyCb != null) {
			if (backrect.contains((int) x, (int) y)) {
				blackReadyCb.onSmsCallback(true, false, false);
			}

		}
		if (brect) {
			if (unkn.contains(this.x, this.y)) {
				//check.getDb().createSms(new SmsModel(numbers[0],body[0],2));
				Log.d("myLogs", "unkn contains");

			} else if (black.contains(this.x, this.y)) {
				//check.getDb().createSms(new SmsModel(numbers[0],body[0],1));
				Log.d("myLogs", "black contains");

			
		}}
		if (!brect) {
			if (addrect.contains(this.x, this.y))
				blackReadyCb.onSmsCallback(false, true, false);
			else if (listrect.contains(this.x, this.y))
				blackReadyCb.onSmsCallback(false, false, true);
		}
		this.x = 0;
		this.y = 0;
		this.brect = false;
		this.invalidate();

	}

	public void onMoveEvent(float x, float y) {
		// Log.d("myLogs", "MOVE x:" + x + " ,y:" + y);
		this.x = (int) x;
		this.y = (int) y;
		if (brect) {
			this.invalidate();

			Log.d("myLogs", "brect=true MOVE");
		} else
			Log.d("myLogs", "brect=false MOVE");

	}

	public void onTouchDown(float x, float y) {
		Log.d("myLogs", "DOWN x:" + x + " ,y:" + y);
		this.x = (int) x;
		this.y = (int) y;
		brect = false;
		if (rect.contains(this.x, this.y)) {
			Log.d("myLogs", "brect=true");
			brect = true;
			this.invalidate();
		}

	}

}
