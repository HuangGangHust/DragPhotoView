package com.huanggang.dragphotoview.zoomlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.huanggang.dragphotoview.IDragView;
import com.huanggang.dragphotoview.SingleDragDetector;

/**
 * 跟随单指拖动手势变化的RelativeLayout
 *
 * @author HuanggGang
 */
public class ZoomRelativeLayout extends RelativeLayout implements IDragView {
	private SingleDragDetector mDetector;

	public ZoomRelativeLayout(Context context) {
		this(context, null);
	}

	public ZoomRelativeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ZoomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		if (null == mDetector || null == mDetector.getZoomLayout()) {
			mDetector = new SingleDragDetector(this);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		preOnDraw(canvas);
		super.onDraw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		afterSizeChanged(w, h);
	}

	@Override
	protected void onAttachedToWindow() {
		init();
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		mDetector.cleanup();
		super.onDetachedFromWindow();
	}

	@Override
	public boolean dispatchEvent(MotionEvent event) {
		return mDetector.dispatchEvent(event);
	}

	@Override
	public void preOnDraw(Canvas canvas) {
		mDetector.preOnDraw(canvas);
	}

	@Override
	public void afterSizeChanged(int w, int h) {
		mDetector.afterSizeChanged(w, h);
	}

	@Override
	public void dragFinishJudge(MotionEvent event) {
		mDetector.dragFinishJudge(event);
	}

}
