package com.huanggang.dragphotoview;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * 单指拖动代理者
 *
 * @author HuanggGang
 */
public class SingleDragDetector implements IDragView {
    private static final String TAG = SingleDragDetector.class.getSimpleName();
    private final static int MIN_TRANSLATE_Y = 180;
    private final static int MAX_TRANSLATE_Y = 500;
    private final static float mMinScale = 0.5f;
    private boolean isSingleDraging = false;// 是否正在单指拖动。
    private float downX, downY, moveX, moveY, mScale = 1;
    private int mWidth, mHeight, mAlpha = 255;
    private Paint mPaint;
    // Gesture Detector.
    private GestureDetector gestureDetector;
    /**
     * 跟随拖动缩放的布局。
     * the zoom layout with dragging.
     */
    private WeakReference<ViewGroup> zoomLayout;

    public SingleDragDetector(ViewGroup zoomLayout) {
        super();
        init(zoomLayout);
    }

    private void init(ViewGroup zoomLayout) {
        this.zoomLayout = new WeakReference<>(zoomLayout);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        if (null == gestureDetector && null != zoomLayout) {
            gestureDetector = new GestureDetector(zoomLayout.getContext(), new SingleDragListener());
        }
    }

    @Override
    public boolean dispatchEvent(MotionEvent event) {
        dragFinishJudge(event);
        Log.d("hgSingleDragDetector", "dispatchEvent：" + gestureDetector.onTouchEvent(event));
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void preOnDraw(Canvas canvas) {
        mPaint.setAlpha(mAlpha);
        canvas.drawRect(0, 0, 2560, 2560, mPaint);
        canvas.translate(moveX, moveY);
        canvas.scale(mScale, mScale, mWidth / 2, mHeight / 2);
    }

    @Override
    public void afterSizeChanged(int w, int h) {
        mWidth = w;
        mHeight = h;
    }

    @Override
    public void dragFinishJudge(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            isSingleDraging = false;
            if (event.getY() - downY > MIN_TRANSLATE_Y && Math.abs((event.getY() - downY) / (event.getX() - downX)) > 1) {
                mAlpha = 0;
                getZoomLayout().invalidate();
                dragFinish();
            } else {
                finishAnim();
            }
        }
    }

    /**
     * 拖动结束处理
     */
    private void dragFinish() {
        ((Activity) getZoomLayout().getContext()).finish();
    }

    /**
     * 拖动动画
     *
     * @param moveEvent 触摸事件
     */
    private void dragAnim(MotionEvent moveEvent) {
        moveX = moveEvent.getX() - downX;
        moveY = moveEvent.getY() - downY;
        // 保证上划到到顶还可以继续滑动
        if (moveY < 0) {
            moveY = 0;
        }

        float percent = moveY / MAX_TRANSLATE_Y;
        if (percent > 1) {
            percent = 1;
        }

        if (mScale >= mMinScale && mScale <= 1f) {
            mScale = 1 - percent;
            mAlpha = (int) (255 * (1 - percent));
        }

        if (mScale < mMinScale) {
            mScale = mMinScale;
        }

        if (mScale > 1f) {
            mScale = 1;
        }

        getZoomLayout().invalidate();
    }

    /**
     * 结束动画
     */
    private void finishAnim() {
        getScaleAnimation().start();
        getTranslateXAnimation().start();
        getTranslateYAnimation().start();
        getAlphaAnimation().start();
    }

    private ValueAnimator getScaleAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(mScale, 1);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mScale = (float) valueAnimator.getAnimatedValue();
                ViewGroup zoomLayout = getZoomLayout();
                if (zoomLayout != null) {
                    zoomLayout.invalidate();
                }
            }
        });
        return animator;
    }

    private ValueAnimator getTranslateXAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(moveX, 0);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                moveX = (float) valueAnimator.getAnimatedValue();
            }
        });
        return animator;
    }

    private ValueAnimator getTranslateYAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(moveY, 0);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                moveY = (float) valueAnimator.getAnimatedValue();
            }
        });

        return animator;
    }

    private ValueAnimator getAlphaAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(mAlpha, 255);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAlpha = (int) valueAnimator.getAnimatedValue();
            }
        });
        return animator;
    }

    /**
     * Clean-up the resources attached to this object. This needs to be called when the zoomLayout is
     * no longer used. A good example is from {@link android.view.View#onDetachedFromWindow()} or
     * from {@link android.app.Activity#onDestroy()}. This is automatically called if you are using
     * {@link DragPhotoView}.
     */
    public void cleanup() {
        if (null == zoomLayout) {
            return; // cleanup already done
        }

        final ViewGroup view = zoomLayout.get();

        if (null != view) {
            // Remove the zoomLayout's reference to this
            view.setOnTouchListener(null);
        }

        if (null != gestureDetector) {
            gestureDetector = null;
        }

        // Finally, clear zoomLayout
        zoomLayout = null;
    }

    public ViewGroup getZoomLayout() {
        ViewGroup view = null;

        if (null != zoomLayout) {
            view = zoomLayout.get();
        }

        // If we don't have an zoomLayout, call cleanup()
        if (null == view) {
            cleanup();
            Log.i(TAG, "zoomLayout no longer exists. You should not use this any more.");
        }

        return view;
    }

    /**
     * 单指拖动监听
     */
    private class SingleDragListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            if (e.getPointerCount() == 1) {
                downX = e.getX();
                downY = e.getY();
            }
            Log.d("hgSingleDragListener", "onDown：" + e + "，downX = " + downX + "，downY = " + downY);
            return super.onDown(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (isSingleDraging) {// 正在单指拖动
                Log.d("hgSingleDragListener", "onScroll正在单指拖动，e1 = " + e1 + "，e2 = " + e2
                        + "，distanceX = " + distanceX + "，distanceY = " + distanceY);
                if (e1.getPointerCount() == 1 && e2.getPointerCount() == 1
                        && e2.getY() - e1.getY() != 0) {
                    dragAnim(e2);
                }
                return true;
            }

            if (e1.getPointerCount() == 1 && e2.getPointerCount() == 1
                    && Math.abs(distanceY / distanceX) > 0.6 && e2.getY() - e1.getY() != 0) {// 单指下滑
                isSingleDraging = true;
                dragAnim(e2);
                Log.d("hgSingleDragListener", "onScroll单指下滑，e1 = " + e1 + "，e2 = " + e2
                        + "，distanceX = " + distanceX + "，distanceY = " + distanceY);
                return true;
            }
            Log.d("hgSingleDragListener", "onScroll未进行单指拖动和单指下滑，e1 = " + e1 + "，e2 = " + e2
                    + "，distanceX = " + distanceX + "，distanceY = " + distanceY);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isSingleDraging = false;
            Log.d("hgSingleDragListener", "onDoubleTap：" + e);
            return super.onDoubleTap(e);
        }
    }// end SingleDragListener
}
