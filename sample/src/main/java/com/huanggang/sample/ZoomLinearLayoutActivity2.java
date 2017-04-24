package com.huanggang.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.huanggang.dragphotoview.DragPhotoView;
import com.huanggang.dragphotoview.zoomlayout.ZoomLinearLayout;
import com.huanggang.dragphotoview.zoomlayout.ZoomRelativeLayout;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * {@link DragPhotoView}结合{@link ZoomLinearLayout}使用示例Activity
 *
 * @author HuangGang
 */
public class ZoomLinearLayoutActivity2 extends AppCompatActivity implements View.OnClickListener, PhotoViewAttacher.OnViewTapListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_ll_2);
        init();
    }

    private void init() {
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

        ZoomLinearLayout zoomParentLayout = (ZoomLinearLayout) findViewById(R.id.zoom_parent);
        DragPhotoView dragPhotoview = (DragPhotoView) findViewById(R.id.drag_view);
        dragPhotoview.setZoomParentView(zoomParentLayout);
        dragPhotoview.setOnViewTapListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        finish();
    }
}
