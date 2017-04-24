package com.huanggang.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button btnZoomLl = (Button) findViewById(R.id.btn_zoom_ll);
        btnZoomLl.setOnClickListener(this);

        Button btnZoomRl = (Button) findViewById(R.id.btn_zoom_rl);
        btnZoomRl.setOnClickListener(this);

        Button btnDragRl = (Button) findViewById(R.id.btn_drag_rl);
        btnDragRl.setOnClickListener(this);
    }

    /**
     * 启动Activity
     */
    private <T extends Activity> void startActivity(Class<T> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zoom_ll:
                startActivity(ZoomLinearLayoutActivity.class);
                break;

            case R.id.btn_zoom_rl:
                startActivity(ZoomRelativeLayoutActivity.class);
                break;

            case R.id.btn_drag_rl:
                startActivity(ZoomLinearLayoutActivity2.class);
                break;

            default:
                break;
        }
    }// end onClick

}
