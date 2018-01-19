package com.zh.bannerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.dengzq.simplerefreshlayout.SimpleRefreshLayout;

/**
 * <p>公司名       tsingning</p>
 * <p>创建者       ZH</p>
 * <p>创建时间     2017/10/25 16:08</p>
 * <p>包名         com.tsingning.dtv.utils</p>
 * <p>描述         TODO </p>
 * <p>svn版本      $Revision: 1413 $ </p>
 * <p>更新者       $Author: zhouwb $</p>
 * <p>更新时间     $Date: 2017-10-25 16:20:41 +0800 (Wed, 25 Oct 2017) $</p>
 */
public class TouchRefreshLayout extends SimpleRefreshLayout {
    private float   startY;
    private float   startX;
    // 记录viewPager是否拖拽的标记
    private boolean mIsVpDragger;
    private int mTouchSlop = 0;

    public TouchRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsVpDragger = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsVpDragger) {
                    return false;
                }

                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpDragger = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                mIsVpDragger = false;
                break;
        }
        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev);
    }
}
