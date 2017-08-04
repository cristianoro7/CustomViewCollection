package com.desperado.customviewcollection.circlesnake;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.desperado.customviewcollection.R;

/**
 * Created by desperado on 17-8-3.
 */

public class CircleSnakeBar extends View {

    private static final String TAG = CircleSnakeBar.class.getSimpleName();

    private int mHeight;

    private int mWidth;

    private int mCenterX;

    private int mCenterY;

    private int mRadius;

    private float mRadiusRatio;

    private float mSweepAngle = 135;

    private float mStartAngele = -90;

    private int mProgress = 0;

    private int mUnreachedColor;

    private int mUnreachedBarWidth;

    private int mReachedColor;

    private int mReachedBarWidth;

    private int mTextSize;

    private int mAlphaSnakeTailColor = Color.parseColor("#80c77eb5");

    private Paint mUnreachedPaint;

    private Paint mReachPaint;

    private Paint mSnakeTailPaint;

    private Paint mTextPaint;

    private int mSnakeTailRadius;

    private int mAlphaSnakeTailRadius;

    private PointF mSnakeTailR = new PointF();

    private OnCircleSnakeBarSnakeListener mListener;


    public CircleSnakeBar(Context context) {
        this(context, null);
    }

    public CircleSnakeBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleSnakeBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray array = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircleSnakeBar);
        mRadiusRatio = array.getFloat(R.styleable.CircleSnakeBar_radiusRatio, 0.7f);
        mUnreachedColor = array.getColor(R.styleable.CircleSnakeBar_unreachedColor, Color.parseColor("#fffffb"));
        mReachedColor = array.getColor(R.styleable.CircleSnakeBar_reachedColor, Color.parseColor("#c77eb5"));
        mUnreachedBarWidth = array.getDimensionPixelOffset(R.styleable.CircleSnakeBar_unreachedBarWidth, dip2px(15));
        mReachedBarWidth = array.getDimensionPixelOffset(R.styleable.CircleSnakeBar_reachedBarWidth, dip2px(20));
        mTextSize = array.getDimensionPixelOffset(R.styleable.CircleSnakeBar_textSize, sp2px(30));
        mProgress = array.getInt(R.styleable.CircleSnakeBar_progress, 0);
        mSnakeTailRadius = array.getDimensionPixelOffset(R.styleable.CircleSnakeBar_snakeTailRadius, dip2px(14));
        mAlphaSnakeTailRadius = array.getDimensionPixelOffset(R.styleable.CircleSnakeBar_AlphaSnakeTailRadius, dip2px(5));
        array.recycle();

        mUnreachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedPaint.setStrokeWidth(mUnreachedBarWidth);
        mUnreachedPaint.setColor(mUnreachedColor);
        mUnreachedPaint.setStyle(Paint.Style.STROKE);

        mReachPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachPaint.setStrokeWidth(mReachedBarWidth);
        mReachPaint.setColor(mReachedColor);
        mReachPaint.setStyle(Paint.Style.STROKE);

        mSnakeTailPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSnakeTailPaint.setStyle(Paint.Style.FILL);
        mSnakeTailPaint.setColor(mAlphaSnakeTailColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mUnreachedColor);
        mTextPaint.setTextSize(mTextSize);

        mSweepAngle = progressToAngle(mProgress);
    }

    public void setOnCircleBarSnakeListener(OnCircleSnakeBarSnakeListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;

        mRadius = (int) (Math.min(mHeight, mWidth) / 2 * mRadiusRatio);
        angleToPosition(mSnakeTailR, mStartAngele, mSweepAngle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = Math.max(getSuggestedMinimumWidth(), dip2px(100));
        int maxHeight = Math.max(getSuggestedMinimumHeight(), dip2px(100));

        int width = resolveSize(maxWidth, widthMeasureSpec);
        int height = resolveSize(maxHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawUnreachedCircle(canvas); //画一个圆
        drawReachCircle(canvas);
        drawSnakeTail(canvas);
        drawOffset(canvas);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), mTextSize);
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), mTextSize);
    }

    private void drawUnreachedCircle(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mUnreachedPaint);
    }

    private void drawReachCircle(Canvas canvas) {
        canvas.drawArc(new RectF(mCenterX - mRadius, mHeight / 2 - mRadius, mWidth / 2 + mRadius,
                mHeight / 2 + mRadius), mStartAngele, mSweepAngle, false, mReachPaint);
    }

    private void drawSnakeTail(Canvas canvas) {
        //绘制snake头部的外圆
        mSnakeTailPaint.setColor(mAlphaSnakeTailColor);
        canvas.drawCircle(mSnakeTailR.x, mSnakeTailR.y, mSnakeTailRadius + mAlphaSnakeTailRadius, mSnakeTailPaint);
        //绘制snake头部的内圆
        mSnakeTailPaint.setColor(mReachedColor);
        canvas.drawCircle(mSnakeTailR.x, mSnakeTailR.y, mSnakeTailRadius, mSnakeTailPaint);
    }

    private void drawOffset(Canvas canvas) {
        String text = mProgress + "";
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, mCenterX - rect.width() / 2, mCenterY + rect.height() / 2, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isInSnakeTail(x, y) && mListener != null) {
                    mListener.onStartSnake(mProgress);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: " + isInSnakeTail(x, y));
                if (isInSnakeTail(x, y)) {
                    mSweepAngle = (float) positionToAngle(x, y);
                    mProgress = angleToProgress(mSweepAngle);
                    angleToPosition(mSnakeTailR, mStartAngele, mSweepAngle);
                    invalidate();
                    if (mListener != null) {
                        mListener.onSnaking(mProgress);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isInSnakeTail(x, y) && mListener != null) {
                    mListener.onEndSnake(mProgress);
                }
        }
        return true;
    }

    private boolean isInSnakeTail(float touchX, float touchY) {
        return (touchX >= mSnakeTailR.x - mSnakeTailRadius && touchX <= mSnakeTailR.x + mSnakeTailRadius)
                && (touchY >= mSnakeTailR.y - mSnakeTailRadius && touchY <= mSnakeTailR.y + mSnakeTailRadius);
    }

    public void setProgress(int progress) {
        if (progress != mProgress && progress >=0 && progress <= 100) {
            this.mProgress = progress;
            mSweepAngle = progressToAngle(mProgress); //为了实时更新
            angleToPosition(mSnakeTailR, mStartAngele, mSweepAngle);
            invalidate();
        }
    }

    public interface OnCircleSnakeBarSnakeListener {

        void onStartSnake(int progress);

        void onSnaking(int progress);

        void onEndSnake(int progress);
    }

    private double positionToAngle(float x, float y) {
        float fx = x - mWidth / 2;
        float fy = y - mHeight / 2;
        double angle = Math.toDegrees(Math.atan2(fy, fx) + (Math.PI / 2));
        angle = (angle < 0) ? (angle + 360) : angle;
        return angle;
    }

    private void angleToPosition(PointF pointF, float startAngele, double offset) {
        pointF.x = (float) Math.cos(Math.abs(startAngele + offset) * Math.PI / 180) * mRadius + mCenterX;
        pointF.y = (float) Math.sin((startAngele + offset) * Math.PI / 180) * mRadius + mCenterY;
    }

    private float progressToAngle(int progress) {
        return (float) (progress * 360) / 100;
    }

    private int angleToProgress(float sweepAngle) {
        return (int) (mSweepAngle * 100 / 360);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int px2dp(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp / scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
