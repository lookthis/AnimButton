package com.apkcore.animationhelperdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apkcore.circularreveallib.AnimationHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageView;
    private LinearLayout ll_all;
    private Button bt2;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.img);
        ll_all = (LinearLayout) findViewById(R.id.ll_all);
        findViewById(R.id.bt1).setOnClickListener(this);
        bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
    }

    public  int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                if (count++ % 2 == 0) {
//                    AnimationHelper.hide(mImageView);

                    AnimationHelper.hide(ll_all, dip2px(44)/2f);
                } else {
//                    AnimationHelper.show(mImageView);
                    AnimationHelper.show(ll_all, dip2px(44)/2f);
                }
                break;
            case R.id.bt2:
                AnimationHelper.startActivity(this, MyActivity.class, bt2, R.color.colorPrimaryDark);
                break;
            default:
                break;
        }
    }
}
