package com.huanggang.dragphotoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 单指拖动PhotoView
 *
 * @author HuanggGang
 */
public class DragPhotoView extends PhotoView {
    private IDragView zoomParentView;

    public DragPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    public DragPhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public DragPhotoView(Context context) {
        this(context, null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (null != zoomParentView && getScale() == 1) {
            boolean result = zoomParentView.dispatchEvent(event);
            if (result) {
                return result;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 设置跟随拖动进行缩放的布局
     *
     * @param zoomLayout 跟随拖动对应变化的布局
     */
    public void setZoomParentView(IDragView zoomLayout) {
        if (null == zoomParentView && null != zoomLayout) {
            zoomParentView = zoomLayout;
        }
    }

    /**
     * 兼容使用PhotoView原生的设置单击监听
     */
    @Override
    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener) {
        super.setOnViewTapListener(listener);
    }
}
