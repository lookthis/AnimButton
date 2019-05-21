package me.jingbin.extendbutton.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.View;


/**
 * Created by Apkcore on 2017/5/4.
 */

public class AnimationHelper {
    public static final int MINI_RADIUS = 0;
    public static final int DEFAULT_DURIATION = 300;

    /**
     * @param view
     * @param startRadius
     * @param durationMills
     */
    public static void show(final View view, float startRadius, long durationMills, final AnimatorEndListener listener) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            view.setVisibility(View.VISIBLE);
//            return;
//        }
//        int xCenter = (view.getLeft() + view.getRight()) / 2;
//        int yCenter = (view.getTop() + view.getBottom()) / 2;


        int[] location = new int[2];
        view.getLocationInWindow(location);
//        final int xCenter = location[0] + view.getWidth() / 2;
//        final int yCenter = location[1] + view.getHeight() / 2;

        int mSceenWidth = view.getResources().getDisplayMetrics().widthPixels;
//        int xCenter = mSceenWidth / 2;

        int w = view.getWidth();
        int h = view.getHeight();

//        int endRadius = (int) (Math.sqrt(w * w + h * h) + 1);

        /**如果第一次view是隐藏的，则获取到的宽度为0*/
        final int xCenter = view.getWidth() / 2;
        final int yCenter = view.getHeight() / 2;

        Log.e("xCenter", xCenter + "");
        Log.e("yCenter", xCenter + "");

        // 获取扩散的半径
        float endRadius = (float) Math.hypot((double) xCenter, (double) yCenter);

        Animator animator = ViewAnimationCompatUtils.createCircularReveal(view, xCenter, yCenter, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animator.setDuration(durationMills);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onEnd();
                }
            }
        });
        animator.start();
    }


    /**
     * @param view         要操作的view
     * @param haveShowView 已经显示的view，且和展开的view同宽同高
     * @param startRadius
     */
    public static void show(final View view, View haveShowView, float startRadius, long durationMills, final AnimatorEndListener listener) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            view.setVisibility(View.VISIBLE);
//            return;
//        }
//        int xCenter = (view.getLeft() + view.getRight()) / 2;
//        int yCenter = (view.getTop() + view.getBottom()) / 2;


        int[] location = new int[2];
        view.getLocationInWindow(location);
//        final int xCenter = location[0] + view.getWidth() / 2;
//        final int yCenter = location[1] + view.getHeight() / 2;

        int mSceenWidth = view.getResources().getDisplayMetrics().widthPixels;
//        int xCenter = mSceenWidth / 2;

        int w = view.getWidth();
        int h = view.getHeight();

//        int endRadius = (int) (Math.sqrt(w * w + h * h) + 1);

        final int xCenter = haveShowView.getWidth() / 2;
        final int yCenter = haveShowView.getHeight() / 2;

        Log.e("xCenter", xCenter + "");
        Log.e("yCenter", xCenter + "");

        // 获取扩散的半径
        float endRadius = (float) Math.hypot((double) xCenter, (double) yCenter);

        Animator animator = ViewAnimationCompatUtils.createCircularReveal(view, xCenter, yCenter, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animator.setDuration(durationMills);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onEnd();
                }
            }
        });
        animator.start();
    }

    public static void hide(final View view, float endRadius, long durationMills, final int visible, final AnimatorEndListener listener) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            view.setVisibility(visible);
//            return;
//        }
//        int xCenter = (view.getLeft() + view.getRight()) / 2;
//        int yCenter = (view.getTop() + view.getBottom()) / 2;
        int w = view.getWidth();
        int h = view.getHeight();

//        int startRadius = (int) (Math.sqrt(w * w + h * h) + 1);
        final int xCenter = view.getWidth() / 2;
        final int yCenter = view.getHeight() / 2;

        // 获取扩散的半径
        float startRadius = (float) Math.hypot((double) xCenter, (double) yCenter);

        Animator animator = ViewAnimationCompatUtils.createCircularReveal(view, xCenter, yCenter, startRadius, endRadius);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(visible);
                if (listener != null) {
                    listener.onEnd();
                }
            }
        });
        animator.setDuration(durationMills);
        animator.start();
    }

    public interface AnimatorEndListener {
        void onEnd();
    }

    public static void show(View myView, float startRadius, AnimatorEndListener listener) {
        show(myView, startRadius, DEFAULT_DURIATION, listener);
    }

    public static void show(View myView, float startRadius) {
        show(myView, startRadius, DEFAULT_DURIATION, null);
    }

    public static void hide(View myView, float startRadius, AnimatorEndListener listenerAdapter) {
        hide(myView, startRadius, DEFAULT_DURIATION, View.GONE, listenerAdapter);
    }

    public static void hide(View myView, float startRadius) {
        hide(myView, startRadius, DEFAULT_DURIATION, View.GONE, null);
    }

    public static void show(View myView) {
        show(myView, MINI_RADIUS, DEFAULT_DURIATION, null);
    }

    /**
     * 默认View隐藏状态为 GONE
     *
     * @param myView
     */
    public static void hide(View myView) {
        hide(myView, MINI_RADIUS, DEFAULT_DURIATION, View.GONE, null);
    }

    /**
     * @param myView     要隐藏的view
     * @param endVisible 动画执行结束是view的状态, 是View.INVISIBLE 还是GONE
     */
    public static void hide(View myView, int endVisible) {
        hide(myView, MINI_RADIUS, DEFAULT_DURIATION, endVisible, null);
    }

}
