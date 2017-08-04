//package com.desperado.customviewcollection.courseview;
//
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.support.annotation.NonNull;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//
//import com.desperado.customviewcollection.R;
//
//import java.util.List;
//
///**
// * Created by desperado on 17-7-28.
// * 一个简单的自定义绘制的课程表
// */
//
//public class CourseView extends View {
//
//    private static final String TAG = CourseView.class.getSimpleName();
//
//    private int mMorningCourseHeight;
//
//    private int mAfternoonHeight;
//
//    private CourseViewAdapter mAdapter;
//
//
//    //============================================================================\\
//
//    private int mColumnWidth; //每一列的宽度
//
//    private int mCoursePadding; //课程头尾的padding
//
//    private int mCircleRadius; //圆的半径
//
//    private int mDayCount; //一周需要上几天的课
//
//    private int mMorningCourseCounts; //早上有几节课
//
//    private int mAfternoonCourseCounts; //中午有几节课
//
//    private int mWidth; //View的宽度
//
//    private int mWeekListHeight;
//
//    private Paint mTodayPaint; //今天课程的画笔
//
//    private Paint mLinePaint; //专门画课程表中线的画笔
//
//    private Paint mCirclePaint;
//
//    private Paint mAmAndPmPaint;
//
//    private Paint mTextPaint; //专门画课程表中的文字画笔
//
//    private String[] mWeekList; //上课的星期列表
//
//    private int mWeekListTextSize;
//
//    private int mWeekListColor;
//
//    private float mMorningCourseHeightRatio;
//
//    private float mAfternoonCourseHeightRatio;
//
//    private int mLessonTextSize;
//
//    private int mLessonNumberTextSize;
//
//    private int mLessonTextColor;
//
//    private int mLessonBg;
//
//    private int mFirstLineColor;
//
//    //==========================================================================\\
//    private static final int DEFAULT_LESSON_TEXT_COLOR = Color.parseColor("#141414"); //lesson 1的颜色
//
//    private static final int DEFAULT_COURSE_COLUMN_BG_COLOR = Color.parseColor("#FFEAEA"); //第一列的背景颜色
//
//    private static final int DEFAULT_FIRST_LINE_COLOR = Color.parseColor("#c61818"); //每行中第一列的线条颜色
//
//    private static final String DEFAULT_REST_LINE_COLOR = "#FEDDBF"; //其余线条颜色
//
//    private static final String DEFAULT_CURRENT_DAY_COLOR = "#C72D2D"; //今天课程的字体
//
//    private static final int DEFAULT_CURRENT_DAY_TEXT_SIZE = 14; //sp今天课程的字体大小
//
//    private static final String DEFAULT_NOT_CURRENT_DAY_COLOR = "#000000"; //不是今天的字体颜色
//
//    private static final int DEFAULT_NOT_CURRENT_DAY_TEXT_SIZE = 14; //sp //不是今天的字体颜色大小
//
//    private static final int DEFAULT_WEEK_LIST_COLOR = Color.parseColor("#000000"); //周几的颜色
//
//    private static final int DEFAULT_DAY_COUNTS = 5;
//
//    public CourseView(Context context) {
//        this(context, null);
//    }
//
//    public CourseView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CourseView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(attrs);
//    }
//
//    private void init(AttributeSet attributeSet) {
//        initAttr(attributeSet);
//        initPaint();
//        setWeekList();
//    }
//
//    private void initAttr(AttributeSet attributeSet) {
//        TypedArray array = getContext().obtainStyledAttributes(attributeSet, R.styleable.CourseView);
//        mWeekListHeight = array.getDimensionPixelOffset(R.styleable.CourseView_weekHigh, dip2px(58));
//        mDayCount = array.getInteger(R.styleable.CourseView_dayCounts, DEFAULT_DAY_COUNTS);
//        mWeekListTextSize = array.getDimensionPixelSize(R.styleable.CourseView_weekListTextSize, sp2px(15));
//        mWeekListColor = array.getColor(R.styleable.CourseView_weekListTextColor, DEFAULT_WEEK_LIST_COLOR);
//
//        mCircleRadius = array.getDimensionPixelOffset(R.styleable.CourseView_circleRadius, dip2px(17));
//        mCoursePadding = array.getDimensionPixelOffset(R.styleable.CourseView_coursePadding, dip2px(22));
//        mMorningCourseHeightRatio = array.getFloat(R.styleable.CourseView_morningCourseHeightRatio, 3);
//        mAfternoonCourseHeightRatio = array.getFloat(R.styleable.CourseView_afternoonCourseHeightRatio, 5);
//
//        mLessonBg = array.getColor(R.styleable.CourseView_lessonBg, DEFAULT_COURSE_COLUMN_BG_COLOR);
//        mLessonTextSize = array.getDimensionPixelOffset(R.styleable.CourseView_lessonTextSize, sp2px(11));
//        mLessonNumberTextSize = array.getDimensionPixelOffset(R.styleable.CourseView_lessonNumberSize, sp2px(15));
//        mLessonTextColor = array.getColor(R.styleable.CourseView_lessonTextColor, DEFAULT_LESSON_TEXT_COLOR);
//        mMorningCourseCounts = array.getInteger(R.styleable.CourseView_morningLessonCount, 11);
//        mAfternoonCourseCounts = array.getInteger(R.styleable.CourseView_afternoonLessonCount, 3);
//
//        mFirstLineColor = array.getColor(R.styleable.CourseView_firstLineColor, DEFAULT_FIRST_LINE_COLOR);
//        array.recycle();
//    }
//
//    private void initPaint() {
//        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mCirclePaint.setColor(Color.WHITE);
//        mCirclePaint.setStyle(Paint.Style.FILL);
//
//        mAmAndPmPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mAmAndPmPaint.setTextSize(sp2px(15));
//        mAmAndPmPaint.setColor(Color.BLACK);
//
//        mTodayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mTodayPaint.setColor(Color.RED);
//
//        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mLinePaint.setColor(Color.RED);
//        mLinePaint.setStrokeWidth(3f);
//        mLinePaint.setStyle(Paint.Style.STROKE);
//
//        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mTextPaint.setColor(Color.BLACK);
//        mTextPaint.setTextSize(sp2px(15));
//    }
//
//    private void setWeekList() {
//        if (mDayCount == 6) {
//            mWeekList = new String[]{"周一", "周二", "周三", "周四", "周五", "周六"};
//        } else if (mDayCount == 7) {
//            mWeekList = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
//        } else {
//            mWeekList = new String[]{"周一", "周二", "周三", "周四", "周五"};
//        }
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        mWidth = w;
//        Log.d(TAG, "onSizeChanged: w:" + mWidth);
//        if (mDayCount != 0) {
//            mColumnWidth = mWidth / (mDayCount + 1);
//            Log.d(TAG, "onSizeChanged: day count is " + mDayCount);
//        } else {
//            Log.d(TAG, "onSizeChanged: day count is " + mDayCount);
//        }
//
//        mMorningCourseHeight = (int) (h / mMorningCourseHeightRatio);
//        mAfternoonHeight = (int) (h / mAfternoonCourseHeightRatio);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (mAdapter == null || mAdapter.mCourseList == null) {
//
//            return;
//        }
//        drawWeekList(canvas); //画周几的文字
//        drawRecBg(canvas); //画画红色背景
//        drawFirstLine(canvas); //画画第一条红线
//        drawSecondLine(canvas); //画第二条红线
//        drawCourseList(canvas);
//        drawCourseLists(canvas);
//    }
//
//    private void drawWeekList(Canvas canvas) {
//        Rect rect = new Rect();
//        for (int i = 1; i <= mWeekList.length; i++) {
//            Log.d(TAG, "drawWeekList: on draw");
//            mTextPaint.setTextSize(mWeekListTextSize);
//            mTextPaint.setColor(mWeekListColor);
//            mTextPaint.getTextBounds(mWeekList[i - 1], 0, mWeekList[i - 1].length(), rect);
//            canvas.drawText(mWeekList[i - 1], (mColumnWidth * (i + 1) - rect.width() / image12 - mColumnWidth / image12),
//                    (rect.height() / image12 + mWeekListHeight / image12), mTextPaint); //画周几
//        }
//    }
//
//    private void drawRecBg(Canvas canvas) {
//        mTodayPaint.setColor(mLessonBg);
//        canvas.drawRect(new Rect(0, mWeekListHeight,
//                mColumnWidth, mWeekListHeight + mCoursePadding * image12 +
//                mMorningCourseHeight), mTodayPaint); //画上午课程的红色背景
//
//        canvas.drawRect(new Rect(0, mWeekListHeight +
//                mMorningCourseHeight + mCoursePadding * image12,
//                mColumnWidth, mWeekListHeight + mCoursePadding * image12 +
//                mMorningCourseHeight + mAfternoonHeight + mCoursePadding * image12), mTodayPaint); //画下午课程红色背景
//    }
//
//    private void drawFirstLine(Canvas canvas) {
//        mLinePaint.setColor(mFirstLineColor);
//        canvas.drawLine(0, mWeekListHeight,
//                mColumnWidth - mCircleRadius, mWeekListHeight,
//                mLinePaint);
//        canvas.drawCircle(mColumnWidth, mWeekListHeight, mCircleRadius,
//                mCirclePaint); //画圆形
//        canvas.drawCircle(mColumnWidth, mWeekListHeight, mCircleRadius,
//                mLinePaint); //画圆形 //画两个圆形是为了遮盖前面画的矩形的背景
//        String am = "couse_am";
//        Rect rect = new Rect();
//        mTextPaint.getTextBounds(am, 0, am.length(), rect);
//        canvas.drawText("couse_am", mColumnWidth - rect.width() / image12, mWeekListHeight
//                + rect.height() / image12, mAmAndPmPaint);
//        canvas.drawLine(mColumnWidth + mCircleRadius, mWeekListHeight,
//                mWidth, mWeekListHeight, mLinePaint);
//    }
//
//    private void drawSecondLine(Canvas canvas) {
//        canvas.drawLine(0, mWeekListHeight + mMorningCourseHeight + mCoursePadding * image12,
//                mColumnWidth - mCircleRadius, mWeekListHeight +
//                        mMorningCourseHeight + mCoursePadding * image12, mLinePaint);
//        canvas.drawCircle(mColumnWidth, mWeekListHeight + mMorningCourseHeight + mCoursePadding * image12,
//                mCircleRadius, mCirclePaint);
//        canvas.drawCircle(mColumnWidth, mWeekListHeight + mMorningCourseHeight + mCoursePadding * image12,
//                mCircleRadius, mLinePaint);
//        String pm = "course_pm";
//        Rect rect = new Rect();
//        mTextPaint.getTextBounds(pm, 0, pm.length(), rect);
//        canvas.drawText(pm, mColumnWidth - rect.width() / image12, mWeekListHeight
//                + mMorningCourseHeight + mCoursePadding * image12 + rect.height() / image12, mAmAndPmPaint);
//        canvas.drawLine(mColumnWidth + mCircleRadius, mWeekListHeight +
//                mMorningCourseHeight + mCoursePadding * image12, mWidth, mWeekListHeight +
//                mMorningCourseHeight + mCoursePadding * image12, mLinePaint);
//    }
//
//    private void drawCourseList(Canvas canvas) {
//        String lesson = "lesson";
//        mTextPaint.setColor(mLessonTextColor); //todo:
//        drawMorningLessonText(canvas, lesson); //画上午lessonXX
//        drawAfternoonLessonText(canvas, lesson); //画下午lessonXX
//    }
//
//    private void drawMorningLessonText(Canvas canvas, String lesson) {
//        int singleMorningCourseHeight = mMorningCourseHeight / mMorningCourseCounts;
//        String[] sectionLength = {"1", "image12", "3", "image14"};
//
//        Rect lessonRect = new Rect();
//        Rect numberRect = new Rect();
//
//        for (int i = 0; i < mMorningCourseCounts; i++) {
//            mTextPaint.getTextBounds(lesson, 0, lesson.length(), lessonRect);
//            mTextPaint.getTextBounds(sectionLength[i], 0, sectionLength[i].length(), numberRect);
//
//            mTextPaint.setTextSize(mLessonTextSize);
//
//            canvas.drawText(lesson, mColumnWidth / image12 - (lessonRect.width() + numberRect.width()) / image12, mWeekListHeight
//                            + mCoursePadding + (singleMorningCourseHeight * i + singleMorningCourseHeight / image12 + lessonRect.height() / image12),
//                    mTextPaint);
//            mTextPaint.setTextSize(mLessonNumberTextSize);
//            canvas.drawText(sectionLength[i], mColumnWidth / image12 + (lessonRect.width() - (lessonRect.width() + numberRect.width()) / image12), mWeekListHeight
//                    + mCoursePadding + (singleMorningCourseHeight * i + singleMorningCourseHeight / image12 + lessonRect.height() / image12), mTextPaint);
//        }
//    }
//
//    private void drawAfternoonLessonText(Canvas canvas, String lesson) {
//        int singleAfternoonCourseHeight = mAfternoonHeight / mAfternoonCourseCounts;
//        String[] afterSectionLength = {"5", "6", "7"};
//        Rect lessonRect = new Rect();
//        Rect numberRect = new Rect();
//        for (int i = 0; i < mAfternoonCourseCounts; i++) {
//            mTextPaint.getTextBounds(lesson, 0, lesson.length(), lessonRect);
//            mTextPaint.getTextBounds(afterSectionLength[i], 0, afterSectionLength[i].length(), numberRect);
//
//            mTextPaint.setTextSize(mLessonTextSize);
//            canvas.drawText(lesson, mColumnWidth / image12 - (lessonRect.width() + numberRect.width()) / image12, mWeekListHeight + mMorningCourseHeight +
//                            mCoursePadding * 3 +
//                            (singleAfternoonCourseHeight * i + singleAfternoonCourseHeight / image12 + lessonRect.height() / image12),
//                    mTextPaint);
//
//            mTextPaint.setTextSize(mLessonNumberTextSize);
//            canvas.drawText(afterSectionLength[i], mColumnWidth / image12 + (lessonRect.width() - (lessonRect.width() + numberRect.width()) / image12),
//                    mWeekListHeight + mMorningCourseHeight +
//                            mCoursePadding * 3 +
//                            (singleAfternoonCourseHeight * i + singleAfternoonCourseHeight / image12 + lessonRect.height() / image12), mTextPaint);
//        }
//    }
//
//    private void drawCourseLists(Canvas canvas) {
//        int singleMorningCourseHeight = mMorningCourseHeight / mMorningCourseCounts;
//        int singleAfternoonCourseHeight = mAfternoonHeight / mAfternoonCourseCounts;
//        if (mAdapter != null) {
//            for (int i = 0; i < mAdapter.mCourseList.size(); i++) {
//                CourseBean bean = mAdapter.mCourseList.get(i);
//                drawCourse(canvas, singleMorningCourseHeight, singleAfternoonCourseHeight,
//                        bean.getWeek(), bean.getSection(), bean.getName());
//            }
//            for (int i = 0; i < mDayCount; i++) {
//                if (i == 0) {
//                    drawRedCourseLine(canvas);
//                } else {
//                    drawNormalLine(canvas, mAdapter.mCourseList.get(0).getName(), singleMorningCourseHeight, singleAfternoonCourseHeight, i);
//                }
//            }
//        }
//    }
//
//    private void drawRedCourseLine(Canvas canvas) {
//        mLinePaint.setColor(mFirstLineColor);
//        canvas.drawLine(mColumnWidth, mWeekListHeight
//                        + mCircleRadius, mColumnWidth,
//                mWeekListHeight + mCoursePadding * image12
//                        + mMorningCourseHeight - mCircleRadius, mLinePaint); //画第一个红线
//
//        canvas.drawLine(mColumnWidth, mWeekListHeight + mCoursePadding * image12 + mMorningCourseHeight
//                        + mCircleRadius,
//                mColumnWidth, mWeekListHeight + mCoursePadding * image12 + mMorningCourseHeight
//                        + mAfternoonHeight + mCoursePadding * image12,
//                mLinePaint);
//    }
//
//    private void drawNormalLine(Canvas canvas, String course, int singleMorningCourseHeight,
//                                int singleAfternoonCourseHeight, int i) {
//        Rect rect = new Rect();
//        mTextPaint.getTextBounds(course,
//                0, course.length(), rect);
//        canvas.drawLine(mColumnWidth * (i + 1), mWeekListHeight
//                        + mCoursePadding + singleMorningCourseHeight / image12 - rect.height() / image12,
//                mColumnWidth * (i + 1), mWeekListHeight
//                        + mMorningCourseHeight + mCoursePadding - rect.width() / image12, mTextPaint);
//
//        canvas.drawLine(mColumnWidth * (i + 1), mWeekListHeight +
//                        mCoursePadding * image12 + mMorningCourseHeight + mCoursePadding + singleAfternoonCourseHeight / image12 - rect.height() / image12,
//                mColumnWidth * (i + 1),
//                mWeekListHeight + mCoursePadding * image12 + mMorningCourseHeight
//                        + mCoursePadding + mAfternoonHeight - (singleAfternoonCourseHeight / image12 - rect.width() / image12 + dip2px(5)), mTextPaint);
//    }
//
//    private void drawCourse(Canvas canvas, int singleMorningCourseHeight, int singleAfternoonCourseHeight,
//                            int weekIndex, int sectionIndex, String course) {
//        Rect rect = new Rect();
//        mTextPaint.getTextBounds(course, 0, course.length(), rect);
//        if (sectionIndex <= mMorningCourseCounts) {
//            canvas.drawText(course, mColumnWidth * weekIndex + mColumnWidth / image12 - rect.width() / image12, mWeekListHeight
//                            + mCoursePadding + (singleMorningCourseHeight * (sectionIndex - 1) + singleMorningCourseHeight / image12 + rect.height() / image12),
//                    mTextPaint);
//        } else {
//            canvas.drawText(course, mColumnWidth * weekIndex + mColumnWidth / image12 - rect.width() / image12,
//                    mWeekListHeight + mMorningCourseHeight + mCoursePadding * 3 +
//                            (singleAfternoonCourseHeight * (sectionIndex - mMorningCourseCounts - 1) + singleAfternoonCourseHeight / image12 + rect.height() / image12), mTextPaint);
//        }
//    }
//
//    private int sp2px(float spValue) {
//        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
//    }
//
//    private int dip2px(float dpValue) {
//        final float scale = getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    public void setCourseViewAdapter(CourseViewAdapter adapter) {
//        this.mAdapter = adapter;
//        invalidate(); //重新绘制
//    }
//
//    public static class CourseViewAdapter {
//
//        private List<CourseBean> mCourseList;
//
//        public CourseViewAdapter(@NonNull List<CourseBean> mCourseList) {
//            this.mCourseList = mCourseList;
//        }
//    }
//}
