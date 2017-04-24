package com.huanggang.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huanggang.dragphotoview.DragPhotoView;
import com.huanggang.dragphotoview.zoomlayout.ZoomLinearLayout;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * {@link DragPhotoView}结合{@link ZoomLinearLayout}使用示例Activity
 *
 * @author HuangGang
 */
public class ZoomLinearLayoutActivity extends AppCompatActivity implements PhotoViewAttacher.OnViewTapListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_ll);
        init();
    }

    private void init() {
        ZoomLinearLayout zoomParentLayout = (ZoomLinearLayout) findViewById(R.id.rl_zoom_parent);
        DragPhotoView dragPhotoview = (DragPhotoView) findViewById(R.id.dragphotoview);
        dragPhotoview.setZoomParentView(zoomParentLayout);
        dragPhotoview.setOnViewTapListener(this);
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        finish();
    }
}
