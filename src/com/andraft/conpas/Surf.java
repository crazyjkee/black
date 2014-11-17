package com.andraft.conpas;

import com.andraft.blacklist.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public final class Surf extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener {
	private Context context;

	public Surf(Context context) {
		super(context);
		init(context);
	}

	public Surf(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Surf(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);

	}

	private void init(Context context) {
		getHolder().addCallback(this);
		this.context = context;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		DrawThread.Init(holder, context);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.setOnTouchListener(this);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		DrawThread.Stop();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		return MainActivity.getActiveScreen().touch(event);
	}
}
