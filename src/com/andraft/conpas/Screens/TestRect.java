package com.andraft.conpas.Screens;

import java.util.List;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import static com.andraft.conpas.Screens.Constants.*;

public class TestRect extends RectF {
	static List<TestRect> up;
	static List<TestRect> down;
	static TestRect active = null;
	private String text[];

	TestRect() {
		super.set(new Rect(0, 0, w, 0));
	}

	private float v = 0;

	@SuppressLint("DrawAllocation")
	private void onDraw(Canvas canvas) {

		if (v > 0)
			if (up.contains(this)) {
				super.bottom = +2;
				super.top++;
			} else if (down.contains(this)) {
				super.bottom--;
				super.top = -2;
			}
		if (v < 0)
			if (up.contains(this)) {
				super.bottom = -2;
				super.top--;
			} else if (down.contains(this)) {
				super.bottom--;
				super.top = -2;
			}
		canvas.drawText("" + text[0], this.centerX(), this.centerY(), FONfill);

	}
}
