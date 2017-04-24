package com.huanggang.dragphotoview;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * 控件拖动接口
 *
 * @author HuanggGang
 */
public interface IDragView {
    /**
     * 拖动事件拦截
     */
    boolean dispatchEvent(MotionEvent event);

    /**
     * 预绘制。在View的onDraw(canvas)之前调用。
     */
    void preOnDraw(Canvas canvas);

    /**
     * 在View的onSizeChanged(int w, int h, int oldw, int oldh)之后调用
     */
    void afterSizeChanged(int w, int h);

    /**
     * 拖动结束判断
     */
    void dragFinishJudge(MotionEvent event);
}
