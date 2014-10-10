package com.andraft.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.andraft.blacklist.Checking;
import com.andraft.models.NumberModel;

public class CustomNumbersList {
	private List<NumberModel> list;
	private int color, textSize = 16;
	private Paint paint;
	private HashMap<NumberModel, Rect> workMap;
	private List<Rect> rects;
	private List<NumberModel> numbers;
	private Rect workRect;
	private Checking check;
	private Rect numberrect, namerect, bounds;
	private Paint pNumber, pName, mTextPaint, pWork, scroll;
	private int start, end, y = 0, x = 0;
	private boolean drag = false;
	private Rect mainRect;

	public CustomNumbersList(Checking check, int color) {
		this.color = color;
		this.check = check;
		updateList();
		init();
	}

	private void init() {
		namerect = new Rect();
		numberrect = new Rect();

		pNumber = new Paint();
		pNumber.setColor(Color.RED);
		pName = new Paint();
		pName.setColor(Color.YELLOW);
		pWork = new Paint();
		pWork.setColor(Color.CYAN);
		scroll = new Paint();
		scroll.setColor(Color.BLUE);

	}

	public void fillWorkMap() {
		workMap = new HashMap<NumberModel, Rect>();

		int top = mainRect.top + 5;
		for (NumberModel l : list) {
			workRect = new Rect();
			// Log.d("myLogs","getName:"+l.getName()+"getNum:"+l.getNum());
			workRect.set(mainRect.left + 5, top, mainRect.right - 5, top + 40);
			workMap.put(l, workRect);
			top = workRect.bottom + 5;
			// Log.d("myLogs", "top:" + top);

		}
		top = 0;
	}

	public void onDrawMainRect(Canvas canvas, Rect mainRect) {
		Log.d("myLogs", "DrawMainRect");
		paint = new Paint();
		this.mainRect = mainRect;
		if (color == 0)
			paint.setColor(Color.DKGRAY);
		else if (color == 1)
			paint.setColor(Color.BLACK);
		else if (color == 2)
			paint.setColor(Color.MAGENTA);

		canvas.drawRect(mainRect, paint);
	}

	public void onDrawMainRect(Canvas canvas) {
		canvas.drawRect(this.mainRect, paint);
	}

	public void scrollList(Canvas canvas, int y, int x, boolean lastPoint) {

		if (lastPoint) {
			this.y = y;
			this.x = x;
		}
		if (y == 0) {
			this.y = 0;
			this.x = 0;
		}

		// Log.d("myLogs","this.y="+y+",y="+y+",this.x="+this.x+",x="+x+",lastPoint:"+lastPoint);
		if ((Math.abs(this.y - y) < Math.abs(this.x - x))) {
			Log.d("myLogs", "x-y = true");
			drag = true;
			for (Entry<NumberModel, Rect> entry : workMap.entrySet()) {
				if (entry.getValue().contains(this.x, this.y))
					entry.getValue().set(entry.getValue().left + (x - this.x),
							entry.getValue().top,
							entry.getValue().right + (x - this.x),
							entry.getValue().bottom);

			}
		} else {
			drag = false;
			Log.d("myLogs", "drag = false");

		}

		end = 0;
		start = 100;
		for (Entry<NumberModel, Rect> entry : workMap.entrySet()) {
			if (end < entry.getValue().bottom)
				end = entry.getValue().bottom;
			if (start > entry.getValue().top)
				start = entry.getValue().top;
		}

		for (Entry<NumberModel, Rect> entry : workMap.entrySet()) {
			if (!drag)
				if (canScroll()) {
					if (end >= mainRect.bottom && start <= mainRect.top)
						entry.getValue().set(entry.getValue().left,
								entry.getValue().top + (this.y - y) / 2,
								entry.getValue().right,
								entry.getValue().top + 40 + (this.y - y) / 2);
					else if (start > mainRect.top && this.y - y < 0){
						//updateYup();
						entry.getValue().set(entry.getValue().left,
								entry.getValue().top + (this.y - y) / 2,
								entry.getValue().right,
								entry.getValue().top + 40 + (this.y - y) / 2);
					}else if (end < mainRect.bottom && this.y - y > 0)
						entry.getValue().set(entry.getValue().left,
								entry.getValue().top + (this.y - y) / 2,
								entry.getValue().right,
								entry.getValue().top + 40 + (this.y - y) / 2);
					

					
				} else
					entry.getValue().set(entry.getValue().left,
							entry.getValue().top, entry.getValue().right,
							entry.getValue().top + 40);

			canvas.drawRect(entry.getValue(), pWork);

			// Log.d("myLogs","this.y="+this.y+" ,y="+y);
			// Log.d("myLogs","getName:"+entry.getKey().getName()+"getNum:"+entry.getKey().getNum());

			// Log.d("myLogs","delta:"+delta);

			// Log.d("myLogs", "rect bottom:" + entry.getValue().bottom);
			namerect.set(entry.getValue().left + 5, entry.getValue().top + 5,
					entry.getValue().right - 5, entry.getValue().bottom
							- entry.getValue().height() / 2 - 5);
			numberrect.set(entry.getValue().left + 5, namerect.bottom + 5,
					entry.getValue().right - 5, entry.getValue().bottom - 5);
			// canvas.drawRect(entry.getValue(), pName);
			canvas.drawRect(namerect, pName);
			canvas.drawRect(numberrect, pNumber);

			textSize(entry.getKey().getName(), numberrect);
			canvas.drawText(
					entry.getKey().getName(),
					namerect.centerX() - bounds.width() / 2,
					namerect.top
							+ (bounds.top + (namerect.height() - bounds.bottom) / 2.0f)
							+ (Math.abs(mTextPaint.ascent()) + Math
									.abs(mTextPaint.descent()) / 2), mTextPaint);

			textSize(entry.getKey().getNum(), numberrect);

			canvas.drawText(
					entry.getKey().getNum(), // Text to display
					numberrect.centerX() - bounds.width() / 2,
					numberrect.top
							+ (bounds.top + (numberrect.height() - bounds.bottom) / 2.0f)
							+ (Math.abs(mTextPaint.ascent()) + Math
									.abs(mTextPaint.descent()) / 2),

					mTextPaint);

			
			}
		// delta = this.y;

	}

	private boolean canScroll() {
		if (end <= mainRect.bottom && start >= mainRect.top)
			return false;
		else
			return true;
	}

	private void textSize(String s, Rect res) {
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.GREEN);

		bounds = new Rect();
		textSize = 18;
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
		// Log.d("myLogs", "TextSize:" + textSize);

	}

	public void updateList() {
		list = this.check.getDb().getWhereNumber(color);
	}

	public Rect getMainRect() {
		return mainRect;

	}

	public CustomNumbersList() {
		super();
	}

	public HashMap<NumberModel, Rect> getWorkMap() {
		return workMap;
	}

	public int getColor() {
		return color;
	}

	public void updateX() {
		for (Entry<NumberModel, Rect> entry : workMap.entrySet()) {

			entry.getValue().set(mainRect.left + 5, entry.getValue().top,
					mainRect.right - 5, entry.getValue().bottom);
		}
	}

	public void updateY(Rect rect) {
		rects = new ArrayList<Rect>();
		numbers = new ArrayList<NumberModel>();

		for (Entry<NumberModel, Rect> entry : workMap.entrySet()) {
			if (entry.getValue().top > rect.top) {
				entry.getValue().set(mainRect.left + 5,
						entry.getValue().top - rect.height() - 5,
						mainRect.right - 5,
						entry.getValue().top - rect.height() + 35);
			}
			if (!entry.getValue().contains(rect)) {
				rects.add(entry.getValue());
				numbers.add(entry.getKey());
			}
		}

		workMap = new HashMap<NumberModel, Rect>();
		for (int i = 0; i < list.size(); i++) {
			workMap.put(numbers.get(i), rects.get(i));
		}

	}

	public void updateYup() {
		rects = new ArrayList<Rect>();
		numbers = new ArrayList<NumberModel>();
		int bottom = start -5;

		for (Entry<NumberModel, Rect> entry : workMap.entrySet()) {
			if (entry.getValue().contains(mainRect.centerX(), end-10)) {
				rects.add(entry.getValue());
				numbers.add(entry.getKey());
			}
		}

		//workMap = new HashMap<NumberModel, Rect>();
		for (int i = rects.size()-1; i >=0; i--) {
			workMap.get(numbers.get(i)).set(mainRect.left+5,bottom-40,mainRect.right,bottom-5);
			break;
		}

	}
}
