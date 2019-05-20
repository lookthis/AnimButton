package com.example.tongzhang.nbbutton.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.example.tongzhang.nbbutton.R;

/**
 * Created by tong.zhang on 2017/12/1.
 */

public class OneNbButton extends android.support.v7.widget.AppCompatButton {

    private int width;
    private int heigh;

    private GradientDrawable backDrawable;

    private boolean isMorphing;
    private int startAngle;

    private Paint paint;

    private ValueAnimator arcValueAnimator;

    public OneNbButton(Context context) {
        super(context);
        init(context);
    }

    public OneNbButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OneNbButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        isMorphing = false;

        backDrawable = new GradientDrawable();
        int colorDrawable = context.getColor(R.color.cutePink);
        backDrawable.setColor(colorDrawable);
        backDrawable.setCornerRadius(120);
        setBackground(backDrawable);

        setText("登陆");

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        if (heighMode == MeasureSpec.EXACTLY) {
            heigh = heighSize;
        }
        // 770 175
        log("width     " + width);
        log("heigh     " + heigh);
    }

    public void startAnim() {
        isMorphing = true;

        setText("");
        // 770 175
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(width, heigh);
        final int over = 770;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(width, over);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 从  770  -- 175

                // 从  175  -- 770
                int value = (int) animation.getAnimatedValue();


                int lOffset = (over - value) / 2;
                int rOffset = over - lOffset;
//                int rOffset = (over - value) / 2;
//                int lOffset = over - rOffset;

                // 就左右在变 左在增加  右在减小   最后：297 0 473 175
                backDrawable.setBounds(lOffset, 0, rOffset, heigh);

                log("value     " + value); // 770 -- 175
                log("rightOffset     " + rOffset); // 0 -- 297
                log("leftOffset     " + lOffset); // 770 -- 473
            }
        });
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(backDrawable, "cornerRadius", heigh / 2, 120);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(backDrawable, "cornerRadius", 120, heigh / 2);
        // 87 取高度的 1/2   一个圆的半径，则 如果是一个圆，那它的高度为heigh
        log("heigh / 2     " + heigh / 2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        // 圆角长度和
        animatorSet.playTogether(valueAnimator, objectAnimator);
        animatorSet.start();

        //画中间的白色圆圈

        showArc();
    }

    public void gotoNew() {
        isMorphing = false;

        arcValueAnimator.cancel();
        setVisibility(GONE);

    }

    public void regainBackground() {
        setVisibility(VISIBLE);
        backDrawable.setBounds(0, 0, width, heigh);
        backDrawable.setCornerRadius(24);
        setBackground(backDrawable);
        setText("登陆");
        isMorphing = false;
    }

    private void showArc() {
        arcValueAnimator = ValueAnimator.ofInt(0, 1080);
        arcValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        arcValueAnimator.setInterpolator(new LinearInterpolator());
        arcValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        arcValueAnimator.setDuration(3000);
        arcValueAnimator.start();


    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (isMorphing == true) {
            final RectF rectF = new RectF(getWidth() * 5 / 12, getHeight() / 7, getWidth() * 7 / 12, getHeight() - getHeight() / 7);
            canvas.drawArc(rectF, startAngle, 270, false, paint);
        }
    }

    public void log(Object o) {
        Log.e("jing", "" + o);
    }
}
