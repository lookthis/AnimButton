package me.jingbin.extendbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import me.jingbin.extendbutton.view.AnimationHelper;

/**
 * @author jingbin
 */
public class ButtonActivity extends AppCompatActivity {

    private ImageView imageView2;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private boolean isStart = false;
    private boolean isFirst = true;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        imageView2 = findViewById(R.id.image2);
        imageView = findViewById(R.id.image);
        linearLayout = findViewById(R.id.linearLayout);
        relativeLayout = findViewById(R.id.relativeLayout);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isStart) {
                    isStart = true;
                    if (isFirst) {
                        play();
                    } else {
                        AnimationHelper.hide(linearLayout, dip2px(50) / 2f, new AnimationHelper.AnimatorEndListener() {
                            @Override
                            public void onEnd() {
                                play();
                            }
                        });
                    }
                }
            }
        });
    }

    private void play() {
        float startF = 1.0f;
        float endF = 5 / 4f;

        float startRF = 0.0f;
        float endRF = 45.0f;
        if (!isFirst) {
            startF = 5 / 4f;
            endF = 1.0f;

            startRF = 45.0f;
            endRF = 0.0f;
        }
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView2, "scaleX", startF, endF);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageView2, "scaleY", startF, endF);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(imageView, "rotation", startRF, endRF);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator1).with(objectAnimator3).with(objectAnimator2);
        animatorSet.setDuration(300);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isFirst) {
//                    AnimationHelper.show(linearLayout,dip2px(50) / 2f, new AnimationHelper.AnimatorEndListener() {
                    AnimationHelper.show(linearLayout, relativeLayout, dip2px(50) / 2f, 300, new AnimationHelper.AnimatorEndListener() {
                        @Override
                        public void onEnd() {
                            isFirst = false;
                            isStart = false;
                        }
                    });
                } else {
                    isFirst = true;
                    isStart = false;
                }
            }
        });
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ButtonActivity.class));
    }
}
