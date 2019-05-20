package com.example.tongzhang.nbbutton;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

import com.example.tongzhang.nbbutton.ui.AnimatorButton;
import com.example.tongzhang.nbbutton.ui.NbButton;
import com.example.tongzhang.nbbutton.ui.OneNbButton;

public class MainActivity extends AppCompatActivity {
    private NbButton button;
    private OneNbButton buttonOne;
    private AnimatorButton buttonTwo;
    private RelativeLayout rlContent;
    private Handler handler;
    private Handler handlerOne;
    private Handler handlerTwo;
    private Animator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button_test);
        buttonOne = findViewById(R.id.button_test_one);
        buttonTwo = findViewById(R.id.button_test_two);
        rlContent = findViewById(R.id.rl_content);

        rlContent.getBackground().setAlpha(0);
        handlerOne = new Handler();
        handlerTwo = new Handler();
        handler = new Handler();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.startAnim();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //跳转
//                            gotoNew();
                    }
                }, 3000);

            }
        });

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOne.startAnim();

                handlerOne.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //跳转
//                            gotoNew();
                    }
                }, 3000);

            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonTwo.startAnim();

                handlerTwo.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //跳转
//                            gotoNew();
                    }
                }, 3000);

            }
        });
//                ViewAnimationUtils.createCircularReveal()
//        Animator mAnimator = ViewAnimationUtils.createCircularReveal( nsv, 0, nsv.getHeight(),ViewAnimationCompatUtils.RECT_TOP);
    }

    private void gotoNew() {
        button.gotoNew();

        final Intent intent = new Intent(this, Main2Activity.class);

        int xc = (button.getLeft() + button.getRight()) / 2;
        int yc = (button.getTop() + button.getBottom()) / 2;
        animator = ViewAnimationUtils.createCircularReveal(rlContent, xc, yc, 0, 1111);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);

                    }
                }, 200);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        rlContent.getBackground().setAlpha(255);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (animator != null) {
            animator.cancel();
        }
        rlContent.getBackground().setAlpha(0);
        button.regainBackground();
    }
}
