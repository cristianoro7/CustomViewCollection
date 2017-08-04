package com.desperado.customviewcollection.chatview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desperado on 17-8-2.
 * 未完成
 */

public class ChatView extends View {

    private int mFontPadding = 10;

    private int mFontSize;

    private int mLineColor;

    private int mVirtualLineColor;

    private int mHeight;

    private int mWidth;

    private int mSingleDayWidth;

    private int mDayHeight;

    private int mSingleDataHeight;

    private String mCurrentDay;

    private List<ChatBean> mChatBeanList;

    private Paint mTextPaint;

    private Paint mVirtualPaint;

    private String[] mDataList = new String[]{"0", "4万", "8万", "12万", "16万"};

    public ChatView(Context context) {
        this(context, null);
    }

    public ChatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initData();
    }

    private void initPaint() {
        mVirtualPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVirtualPaint.setStrokeWidth(4f);
        mVirtualPaint.setPathEffect(new DashPathEffect(new float[]{20, 20}, 0)); //把画的线条设置为虚线
        mVirtualPaint.setTextSize(40);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(55);
        mTextPaint.setColor(Color.parseColor("#76624c"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;

        mSingleDataHeight = mHeight / mDataList.length;
        mSingleDayWidth = mWidth / mChatBeanList.size();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDayText(canvas);
        drawDataText(canvas);
//        drawVirtualLine(canvas);
    }

    private void drawDayText(Canvas canvas) {
        Rect rect = new Rect();
        for (int i = 0; i < mChatBeanList.size(); i++) {
            mTextPaint.getTextBounds(mChatBeanList.get(i).getDate(), 0, mChatBeanList.get(i).getDate().length(), rect);
            mSingleDayWidth = Math.max(mSingleDayWidth, rect.width());
            canvas.drawText(mChatBeanList.get(i).getDate(), mSingleDayWidth * i + mSingleDayWidth / 2 - rect.width() / 2,
                    mHeight - rect.height() / 2, mTextPaint);
        }
        mDayHeight = rect.height();
    }

    private void drawDataText(Canvas canvas) {
        Rect rect = new Rect();
        for (int i = 0; i < mDataList.length; i++) {
            mTextPaint.getTextBounds(mDataList[i], 0, mDataList[i].length(), rect);
            mSingleDataHeight = Math.max(mSingleDataHeight, rect.height());
            canvas.drawText(mDataList[i], 0, (mHeight - mDayHeight - mSingleDataHeight * i - mFontPadding
            )- rect.height() / 2, mTextPaint);
        }
    }

    private void drawVirtualLine(Canvas canvas) {
        for (int i = 0; i < mChatBeanList.size(); i++) {
            canvas.drawLine(0, mSingleDataHeight * (i + 1), mWidth, mSingleDataHeight * (i + 1), mVirtualPaint);
        }
    }

    private void initData() {
        mChatBeanList = new ArrayList<>();
        mChatBeanList.add(new ChatBean("02-02", "4"));
        mChatBeanList.add(new ChatBean("02-03", "12"));
        mChatBeanList.add(new ChatBean("02-04", "4"));
        mChatBeanList.add(new ChatBean("02-05", "6"));
        mChatBeanList.add(new ChatBean("02-06", "8"));
        mChatBeanList.add(new ChatBean("02-07", "12"));
        mChatBeanList.add(new ChatBean("02-08", "16"));
    }
}
