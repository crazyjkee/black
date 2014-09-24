package com.andraft.views;

import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.andraft.blacklist.Checking;
import com.andraft.models.NumberModel;
import com.andraft.utils.CustomNumbersList;

enum Checker {
	leftRect, rightRect, def
}

public class PhaseNumberBlack extends View {
	public static interface blackCallback {
		public void onblackCallback(boolean back, boolean add, boolean list);
	}

	private Checking check;
	private int textSize = 50;
	private Checker check_rect = Checker.def;
	private Paint pUnkn, pBlack, pWhite, pRect, plRect, lText, pback,
			mTextPaint, pAdd;
	private Rect left, main, right, lrect, backrect, addrect;
	blackCallback blackReadyCb = null;
	private int x, y;
	private boolean brect = false, last = false, swap = false, drag = false;
	private CustomNumbersList[] cust = new CustomNumbersList[3];// 0 - white, 1
																// - black, 2 -
																// unknown

	private Rect bounds;

	public PhaseNumberBlack(Context context) {
		super(context);
		init(context);
	}

	public PhaseNumberBlack(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public PhaseNumberBlack(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		Log.d("myLogs", "init");
		check = Checking.getInstance(context);
		cust[2] = new CustomNumbersList(check, 2);
		cust[0] = new CustomNumbersList(check, 0);
		cust[1] = new CustomNumbersList(check, 1);
		main = new Rect();
		left = new Rect();
		right = new Rect();
		;
		lrect = new Rect();
		backrect = new Rect();

		addrect = new Rect();
		pAdd = new Paint();
		pAdd.setColor(Color.RED);

		pUnkn = new Paint();
		lText = new Paint();
		plRect = new Paint();
		pUnkn.setColor(Color.MAGENTA);
		pBlack = new Paint();
		pBlack.setColor(Color.BLACK);
		pWhite = new Paint();
		pWhite.setColor(Color.LTGRAY);
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

		lrect.set(0, 0, canvas.getWidth(), 50);

		backrect.set(0, 0, 50, lrect.bottom);

		addrect.set(0, canvas.getHeight() - lrect.height() * 2,
				canvas.getWidth(), canvas.getHeight());

		main.set(canvas.getWidth() / 4, lrect.bottom, canvas.getWidth()
				- canvas.getWidth() / 4, addrect.top);
		left.set(0, lrect.bottom, canvas.getWidth() / 4, addrect.top);
		right.set(canvas.getWidth() - canvas.getWidth() / 4, lrect.bottom,
				canvas.getWidth(), addrect.top);
		textSize("Add", addrect);

		if (!brect && !drag) {

			switch (check_rect) {

			case leftRect:
				Log.d("myLogs", "leftRect");
				for (int i = 0; i < cust.length; i++) {
					if (cust[i].getMainRect().contains(left)) {
						for (int j = 0; j < cust.length; j++) {
							if (cust[j].getMainRect().contains(main)) {
								cust[i].onDrawMainRect(canvas, main);
								cust[i].fillWorkMap();
								cust[j].onDrawMainRect(canvas, left);
								swap = true;
								break;
							}
						}
						if (swap)
							break;
					}
				}
				for (int i = 0; i < cust.length; i++) {
					cust[i].onDrawMainRect(canvas);
				}
				break;
			case rightRect:
				Log.d("myLogs", "rightRect");
				for (int i = 0; i < cust.length; i++) {
					if (cust[i].getMainRect().contains(right)) {
						for (int j = 0; j < cust.length; j++) {
							if (cust[j].getMainRect().contains(main)) {
								cust[i].onDrawMainRect(canvas, main);
								cust[i].fillWorkMap();
								cust[j].onDrawMainRect(canvas, right);
								swap = true;
								break;
							}
						}
						if (swap)
							break;
					}
				}
				for (int i = 0; i < cust.length; i++) {
					cust[i].onDrawMainRect(canvas);
				}
				break;
			case def:
				cust[0].onDrawMainRect(canvas, left);
				cust[0].fillWorkMap();
				cust[1].onDrawMainRect(canvas, right);
				cust[1].fillWorkMap();
				cust[2].onDrawMainRect(canvas, main);
				cust[2].fillWorkMap();
				break;

			}
		}
		for (int i = 0; i < cust.length; i++) {
			cust[i].onDrawMainRect(canvas);
		}
		swap = false;

		for (int i = 0; i < cust.length; i++)
			if (cust[i].getMainRect().contains(main))
				cust[i].scrollList(canvas, y, x, last);

		canvas.drawRect(lrect, plRect);
		canvas.drawRect(backrect, pback);
		canvas.drawText("Label", canvas.getWidth() / 2, lrect.bottom / 2, lText);
		canvas.drawRect(addrect, pAdd);
		canvas.drawText(
				"Add", // Text to display
				addrect.centerX() - bounds.width() / 2,
				addrect.top
						+ (bounds.top + (addrect.height() - bounds.bottom) / 2.0f)
						+ (Math.abs(mTextPaint.ascent()) + Math.abs(mTextPaint
								.descent()) / 2), mTextPaint);

		super.onDraw(canvas);
	}

	public void setBlackReadyCallback(blackCallback b) {
		this.blackReadyCb = b;

	}

	public void onTouchUp(float x, float y) {
		Log.d("myLogs", "UP x:" + x + " ,y:" + y);
		if (this.blackReadyCb != null) {
			if (backrect.contains((int) x, (int) y)) {
				blackReadyCb.onblackCallback(true, false, false);
			}

		}
		if (main.contains((int) x, (int) y)) {
			brect = true;
			last = false;
			drag = false;
			this.y = 0;
			this.x = 0;
			for (int j = 0; j < cust.length; j++)
				if (cust[j].getMainRect().contains(main))
					cust[j].updateX();
			this.invalidate();
			return;
		} else if (right.contains((int) x, (int) y)) {
			drag = false;
			for (int i = 0; i < cust.length; i++) {
				if (cust[i].getMainRect().contains(main)) {
					Log.d("myLogs", "right rect1");
					for (Entry<NumberModel, Rect> e : cust[i].getWorkMap()
							.entrySet()) {

						if (e.getValue().right > main.right) {
							drag = true;
							for (int j = 0; j < cust.length; j++)
								if (cust[j].getMainRect().contains(right)) {
									check.getDb().updateNumber(e.getKey(),
											cust[j].getColor());
									cust[i].updateList();
									cust[i].updateY(e.getValue());
								}
							Log.d("myLogs", "right rect2");
						}
					}
					break;

				}

			}
			for (int i = 0; i < cust.length; i++) {
				cust[i].updateList();
			}
			this.y = 0;
			this.x = 0;
			last = false;
			brect = false;
			this.invalidate();

			return;
		} else if (left.contains((int) x, (int) y)) {
			drag = false;
			for (int i = 0; i < cust.length; i++) {
				if (cust[i].getMainRect().contains(main)) {
					Log.d("myLogs", "left rect1");
					for (Entry<NumberModel, Rect> e : cust[i].getWorkMap()
							.entrySet()) {
						Log.d("myLogs", "e.left:" + e.getValue().left
								+ " ,main.left:" + main.left);
						if (e.getValue().left < main.left) {
							drag = true;
							for (int j = 0; j < cust.length; j++)
								if (cust[j].getMainRect().contains(left)) {
									check.getDb().updateNumber(e.getKey(),
											cust[j].getColor());
									cust[i].updateList();
									cust[i].updateY(e.getValue());
								}
							Log.d("myLogs", "left rect2");
						}
					}
					break;

				}

			}
			for (int i = 0; i < cust.length; i++) {
				cust[i].updateList();
				// cust[i].fillWorkMap();
			}
			this.y = 0;
			this.x = 0;
			last = false;
			brect = false;
			this.invalidate();

			return;
		}

		this.x = 0;
		this.y = 0;
		this.brect = false;
		this.last = false;
		// this.invalidate();

	}

	public void onMoveEvent(float x, float y) {
		// Log.d("myLogs", "MOVE x:" + x + " ,y:" + y);
		this.x = (int) x;
		this.y = (int) y;
		last = false;
		if (brect) {
			this.invalidate();

			// Log.d("myLogs", "brect=true MOVE");
		}

	}

	public void onTouchDown(float x, float y) {
		// Log.d("myLogs", "DOWN x:" + x + " ,y:" + y);
		this.x = (int) x;
		this.y = (int) y;
		brect = false;
		if (main.contains(this.x, this.y)) {
			// Log.d("myLogs", "brect=true");
			brect = true;
			last = true;
			this.invalidate();

		} else if (left.contains((int) x, (int) y)) {
			check_rect = Checker.leftRect;
			// Log.d("myLogs", "left_contains");
		} else if (right.contains((int) x, (int) y)) {
			// Log.d("myLogs", "right_contains");
			check_rect = Checker.rightRect;
		}

	}

	private void textSize(String s, Rect res) {
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.GREEN);

		bounds = new Rect();
		textSize = 50;
		mTextPaint.setTextSize(textSize);
		mTextPaint.getTextBounds(s, 0, s.length(), bounds);
		int text_height = bounds.height();
		int text_width = bounds.width();
		// Log.d("myLogs", "text_height:" + text_height + " text_width"
		// + text_width);
		while (res.width() - Math.abs(mTextPaint.ascent())
				- Math.abs(mTextPaint.descent()) - 20 < text_width) {
			// have this the same as your text size
			mTextPaint.setTextSize(textSize);
			mTextPaint.getTextBounds(s, 0, s.length(), bounds);

			text_width = bounds.width();
			textSize--;
		}

	}

}
