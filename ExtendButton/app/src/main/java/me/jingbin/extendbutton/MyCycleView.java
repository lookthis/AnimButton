package me.jingbin.extendbutton;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyCycleView extends View {

    //view的宽和高
    int mHeight = 0;
    int mWidth = 0;
    //圆形图片
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round);
    //圆形图片的真实半径
    int radius = 0;
    //旋转动画的矩形
    Matrix matrix = new Matrix();
    //旋转动画的角度
    int degrees = 0;
    private Paint paint;

    //-----------旋转动画-----------
    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            matrix.postRotate(degrees++, radius / 2, radius / 2);
//            matrix.postRotate(degrees++, radius / 4, radius / 4);
//            matrix.setRotate(degrees++, radius / 2, radius / 2);
//            matrix.postRotate(degrees++);
            //重绘
            invalidate();
            mHandler.postDelayed(runnable, 50);
        }
    };
    private Rect mRect;

    public MyCycleView(Context context) {
        super(context);
        initView();
    }

    public MyCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyCycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        //获取res的图片资源
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round);
        //开始旋转
        mHandler.post(runnable);

        paint = new Paint();
        mRect = new Rect();
        paint.setColor(getResources().getColor(R.color.colorAccent));
//        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);//抗锯齿
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
//        paint.setTextSize(2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量整个View的宽和高
//        mWidth = measuredWidth(widthMeasureSpec);
//        mHeight = measuredHeight(heightMeasureSpec);
//        setMeasuredDimension(mWidth, mHeight);


        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        if (modeWidth == MeasureSpec.EXACTLY) {
            mWidth = sizeWidth;
        } else {//默认宽度
            mWidth = dip2px(getContext(),150);
        }
        mHeight = mWidth;
        Log.e("------------->", "width:" + mWidth);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int measuredWidth(int widthMeasureSpec) {
        int Mode = MeasureSpec.getMode(widthMeasureSpec);
        int Size = MeasureSpec.getSize(widthMeasureSpec);
        if (Mode == MeasureSpec.EXACTLY) {
            mWidth = Size;
        } else {
            //由图片决定宽度
            int value = getPaddingLeft() + getPaddingRight() + bitmap.getWidth();
            if (Mode == MeasureSpec.AT_MOST) {
                //由图片和Padding决定宽度，但是不能超过View的宽
                mWidth = Math.min(value, Size);
            }
        }
        return mWidth;
    }

    private int measuredHeight(int heightMeasureSpec) {
        int Mode = MeasureSpec.getMode(heightMeasureSpec);
        int Size = MeasureSpec.getSize(heightMeasureSpec);
        if (Mode == MeasureSpec.EXACTLY) {
            mHeight = Size;
        } else {
            //由图片决定高度
            int value = getPaddingTop() + getPaddingBottom() + bitmap.getHeight();
            if (Mode == MeasureSpec.AT_MOST) {
                //由图片和Padding决定高度，但是不能超过View的高
                mHeight = Math.min(value, Size);
            }
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = mWidth / 2;
        int innerCircle = center; //设置内圆半径
//        int ringWidth = dip2px(getContext(), 5); //设置圆环宽度

        //绘制内圆
//        this.paint.setARGB(155, 167, 190, 206);
        paint.setColor(Color.RED);
//        this.paint.setStrokeWidth(2);
        canvas.drawCircle(center, center, innerCircle, paint);


        /**这里是圆形图片*/
//        super.onDraw(canvas);
        canvas.concat(matrix);
        //真实的半径必须是View的宽高最小值
//        radius = Math.min(mWidth, mHeight) - dip2px(getContext(),30);
        int mBitmapWidth = dip2px(getContext(),80);

        radius = Math.min(mBitmapWidth, mBitmapWidth);
        //如果图片本身宽高太大，进行相应的缩放
        bitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
        //画圆形图片
        mRect.left = (mWidth/2 - radius/2);
        mRect.top = 0 + (mWidth/2 - radius/2);
        mRect.right = 0 + (mWidth/2 +radius/2);
        mRect.bottom = 0 + (mWidth/2 + radius/2);
//        canvas.drawBitmap(createCircleImage(bitmap, radius), 0, 0, null);
        canvas.drawBitmap(createCircleImage(bitmap, radius), null, mRect, null);
        matrix.reset();

//        canvas.translate(200,200);
//        canvas.drawCircle(0,0,100,paint);

    }

    private Bitmap createCircleImage(Bitmap source, int radius) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        //产生一个同样大小的画布
        Canvas canvas = new Canvas(target);
        //首先绘制圆形
        canvas.drawCircle(radius / 2, radius / 2, radius / 2, paint);
        //使用SRC_IN模式显示后画图的交集处
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制图片，从（0，0）画
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
