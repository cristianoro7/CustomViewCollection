package com.desperado.customviewcollection.imagecontainter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.desperado.customviewcollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desperado on 17-7-31.
 */

public class ImageViewContainer extends ViewGroup {

    private static final String TAG = ImageViewContainer.class.getSimpleName();

    private ChildViewManager mManager;

    private ImageViewContainerAdapter mAdapter;

    private int mHorizontalSpace = 0;

    private int mVerticalSpace = 0;

    private int mDefaultSize = dip2px(100);

    public ImageViewContainer(Context context) {
        this(context, null);
    }

    public ImageViewContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray array = getContext().obtainStyledAttributes(attributeSet, R.styleable.ImageViewContainer);
        mHorizontalSpace = array.getDimensionPixelOffset(R.styleable.ImageViewContainer_horizontalSpace, dip2px(3));
        mVerticalSpace = array.getDimensionPixelOffset(R.styleable.ImageViewContainer_verticalSpace, dip2px(3));
        array.recycle();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }


    //ViewGroup中的onMeasure需要做的两件事:测量孩子....(测量孩子的一般套路), 测量自身的大小, 通常根据孩子的大小来决定自身大小(测量自身的套路)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureSize(widthMeasureSpec);
        int height = measureSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    private int measureSize(int measureSpec) {
        int resSize;
        int parentSize = MeasureSpec.getSize(measureSpec);
        int parentMode = MeasureSpec.getMode(measureSpec);

        if (parentMode == MeasureSpec.AT_MOST) {
            resSize = mDefaultSize;
        } else {
            resSize = parentSize;
        }
        return resSize;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (mManager != null) {
            Log.d(TAG, "onLayout: performLayout");
            mManager.layout(getPaddingLeft(), getPaddingTop(), getWidth(), getHeight());
        }
    }

    public <T> void setImageViewContainerAdapter(ImageViewContainerAdapter<T> adapter) {
        this.mAdapter = adapter;
        if (mAdapter != null) {
            mManager = new ChildViewManager(mAdapter.mImageUrlList);
        }
        Log.d(TAG, "setImageViewContainerAdapter: ");
    }

    public static class ImageViewContainerAdapter<T> {

        protected List<T> mImageUrlList;

        protected Context mContext;

        private ImageLoaderEngine mImageLoaderEngine;

        public ImageViewContainerAdapter(List<T> mImageList, Context context) {
            this(mImageList, context, null);
        }

        public ImageViewContainerAdapter(List<T> mImageUrlList, Context mContext, ImageLoaderEngine mImageLoaderEngine) {
            this.mImageUrlList = mImageUrlList;
            this.mContext = mContext;
            if (mImageLoaderEngine == null) {
                this.mImageLoaderEngine = ImageLoaderEnginePicker.pickImageLoaderEngine(mContext);
            } else {
                this.mImageLoaderEngine = mImageLoaderEngine;
            }
        }

        public void setImageLoaderEngine(ImageLoaderEngine imageLoaderEngine) {
            this.mImageLoaderEngine = imageLoaderEngine;
        }

        //根据需要重写该方法
        protected ImageView createImageView() {
            ImageView imageView = new ImageView(mContext);
            MarginLayoutParams params = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        public void loadImage(Object model, ImageView imageView) {
            mImageLoaderEngine.load(model, imageView);
        }
    }

    private class ChildViewManager<T> {

        private List<T> mImageUrl;

        private List<ImageView> mList = new ArrayList<>();

        public ChildViewManager(List<T> mImageUrl) {
            if (mImageUrl != null && mImageUrl.size() != 0) {
                this.mImageUrl = mImageUrl;
                addViews();
            }
        }

        private void addViews() {
            if (mAdapter != null) {
                for (int i = 0; i < mImageUrl.size(); i++) {
                    ImageView imageView = mAdapter.createImageView();
                    mList.add(imageView);
                    addView(imageView);
                }
            }
        }

        private void layout(int left, int top, int parentWidth, int parentHeight) {
            switch (mImageUrl.size()) {
                case 1:
                    layoutOne(left, top, parentWidth, parentHeight, mImageUrl.get(0));
                    break;
                case 2:
                    layoutTwo(left, top, parentWidth, parentHeight, mImageUrl);
                    break;
                case 3:
                    layoutThree(left, top, parentWidth, parentHeight, mImageUrl);
                    break;
                case 4:
                    layoutFour(left, top, parentWidth, parentHeight, mImageUrl);
                    break;
                default:
                    break;
            }
        }

        private void layoutOne(int left, int top, int width, int height, T url) {
            ImageView imageView = mList.get(0);
            int restWidth = getRestSize(width, true);
            int resHeight = getRestSize(height, false);
            imageView.layout(left, top,
                    left + restWidth, top + resHeight);
            mAdapter.loadImage(url, imageView);
        }

        private void layoutTwo(int left, int top, int width, int height, List<T> mImageUrl) {
            int restWidth = getRestSize(width / 2, true);
            int restHeight = getRestSize(height, false);
            for (int i = 0; i < mImageUrl.size(); i++) {
                ImageView imageView = mList.get(i);
                if (imageView.getVisibility() == VISIBLE) {
                    imageView.layout(left + restWidth * i + mHorizontalSpace * i,
                            top, left + restWidth * (i + 1), top + restHeight);
                    mAdapter.loadImage(mImageUrl.get(i), imageView);
                }
            }
        }

        private void layoutThree(int left, int top, int width, int height, List<T> mImageUrl) {
            int restWidth = getRestSize(width / 2, true);
            int restHeight = getRestSize(height / 2, false);

            for (int i = 0; i < mImageUrl.size(); i++) {
                ImageView imageView = mList.get(i);
                if (imageView.getVisibility() == VISIBLE) {
                    if (i == 0) {
                        imageView.layout(left, top, left + restWidth, top + height);
                    } else {
                        imageView.layout(left + restWidth + mHorizontalSpace, top + restHeight * (i - 1) + mVerticalSpace * (i - 1),
                                left + restWidth * 2, top + restHeight * i);
                    }
                    mAdapter.loadImage(mImageUrl.get(i), imageView);
                }
            }
        }

        private void layoutFour(int left, int top, int width, int height, List<T> mImageUrl) {
            int restWidth = getRestSize(width / 2, true);
            int restHeight = getRestSize(height / 2, false);
            for (int i = 0; i < mImageUrl.size(); i++) {
                ImageView imageView = mList.get(i);
                mAdapter.loadImage(mImageUrl.get(i), imageView);
                if (imageView.getVisibility() == VISIBLE) {
                    if (i <= 1) {
                        imageView.layout(left + restWidth * i + mHorizontalSpace * i, top,
                                left + restHeight * (i + 1), top + restHeight);
                    } else {
                        imageView.layout(left + restWidth * (i - 2) + mHorizontalSpace * (i - 2), top + restHeight + mVerticalSpace,
                                left + restHeight * (i - 1), top + restHeight * 2);
                    }

                }
            }
        }

    }

    private int getRestSize(int size, boolean isWidth) {
        return isWidth ? size : size;
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int px2dp(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp / scale + 0.5f);
    }
}
