package com.huanggang.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huanggang.dragphotoview.DragPhotoView;
import com.huanggang.dragphotoview.zoomlayout.ZoomRelativeLayout;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * {@link DragPhotoView}结合{@link ZoomRelativeLayout}使用示例Activity
 *
 * @author HuangGang
 */
public class ZoomRelativeLayoutActivity extends AppCompatActivity implements PhotoViewAttacher.OnViewTapListener, View.OnClickListener {
    private FrameLayout flTitle;
    private RelativeLayout  rlImageMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_rl);
        init();
    }

    private void init() {
        flTitle = (FrameLayout) findViewById(R.id.fl_title);
        rlImageMessage = (RelativeLayout) findViewById(R.id.rl_image_message);

        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

        ZoomRelativeLayout zoomParentLayout = (ZoomRelativeLayout) findViewById(R.id.rl_zoom_parent);
        DragPhotoView dragPhotoview = (DragPhotoView) findViewById(R.id.dragphotoview);
        dragPhotoview.setZoomParentView(zoomParentLayout);
        dragPhotoview.setOnViewTapListener(this);
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        if (rlImageMessage.getVisibility() == View.VISIBLE) {
            flTitle.setVisibility(View.GONE);
            rlImageMessage.setVisibility(View.GONE);
        } else {
            flTitle.setVisibility(View.VISIBLE);
            rlImageMessage.setVisibility(View.VISIBLE);
        }
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
}
