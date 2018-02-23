package com.zh.superbanneribrary;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class CoverModeTransformer implements ViewPager.PageTransformer {

    private final String TAG = CoverModeTransformer.class.getCanonicalName();
    private ViewPager mViewPager;
    private float MIN_ALPHA = 0.5f;

    public CoverModeTransformer(ViewPager pager, float MIN_ALPHA) {
        mViewPager = pager;
        if (MIN_ALPHA < 0.5f) {
            MIN_ALPHA = 0.5f;
        }
        this.MIN_ALPHA = MIN_ALPHA;
    }

    @Override
    public void transformPage(View view, float position) {
        float mScaleMax = 1.0f;
        float mScaleMin = 0.9f;

        float paddingLeft = mViewPager.getPaddingLeft();
        float paddingRight = mViewPager.getPaddingRight();
        float width = mViewPager.getMeasuredWidth();
        float offsetPosition = paddingLeft / (width - paddingLeft - paddingRight);
        Log.i(TAG, paddingLeft + "***" + paddingRight + "*****" + width);
        float currentPos = position - offsetPosition;
        float itemWidth = view.getWidth();
        //由于左右边的缩小而减小的x的大小的一半
        float reduceX = (2.0f - mScaleMax - mScaleMin) * itemWidth / 2.0f;
        if (currentPos <= -1.0f) {
            view.setTranslationX(reduceX);
            view.setScaleX(mScaleMin);
            view.setScaleY(mScaleMin);
            view.setAlpha(MIN_ALPHA);
        } else if (currentPos <= 1.0) {
            float scale = (mScaleMax - mScaleMin) * Math.abs(1.0f - Math.abs(currentPos));
            float translationX = currentPos * -reduceX;
            if (currentPos <= -0.5) {  //两个view中间的临界，这时两个view在同一层，左侧View需要往X轴正方向移动覆盖的值()
                view.setTranslationX(translationX + Math.abs(Math.abs(currentPos) - 0.5f) / 0.5f);
            } else if (currentPos <= 0.0f) {
                view.setTranslationX(translationX);
            } else if (currentPos >= 0.5) {  //两个view中间的临界，这时两个view在同一层
                view.setTranslationX(translationX - Math.abs(Math.abs(currentPos) - 0.5f) / 0.5f);
            } else {
                view.setTranslationX(translationX);
            }
            view.setScaleX(scale + mScaleMin);
            view.setScaleY(scale + mScaleMin);
            float scaleFactor = Math.max(mScaleMin, 1 - Math.abs(position));
            view.setAlpha(MIN_ALPHA + (scaleFactor - mScaleMin) / (1 - mScaleMin) * (1 - MIN_ALPHA));
        } else {
            view.setAlpha(MIN_ALPHA);
            view.setScaleX(mScaleMin);
            view.setScaleY(mScaleMin);
            view.setTranslationX(-reduceX);
        }
    }
}

