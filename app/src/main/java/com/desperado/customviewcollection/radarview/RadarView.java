package com.desperado.customviewcollection.radarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desperado on 17-8-3.
 * 未完成
 */

public class RadarView extends View {

    private List<RadarBean> mList = new ArrayList<>();

    private int mWidth;

    private int mHeight;

    private int mRadius;

    private int mCenterX;

    private int mCenterY;

    private Path mPath;

    private Paint mLinePaint;

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RadarView(Context context) {
        super(context);
    }

    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(4);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight = h;
        mWidth = w;

        mRadius = (int) ((Math.min(mHeight, mWidth) / 2) * 0.7);

        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawNet(canvas);
    }

    private void drawNet(Canvas canvas) {
        int singeRadius = mRadius / 5;
        int angle = 360 / mList.size();
        int h = (int) (Math.tan(angle / 2) * singeRadius); //确定第一个点的高度
        mPath.moveTo(mCenterX + singeRadius, mCenterY - h); //将Path移动到第一个点,做好开始画的准备
        for (int i = 0; i < mList.size(); i++) {

        }
    }


}
