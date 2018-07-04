package com.zh.superbanneribrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>创建者       Z H</p>
 * <p>创建时间     2017/12/21 22:49</p>
 * <p>包名         com.zh.bannerview.view</p>
 * <p>描述         TODO </p>
 * <p>svn版本      $Revision$ </p>
 * <p>更新者       $Author$</p>
 * <p>更新时间     $Date$</p>
 */
public class SuperBannerView extends RelativeLayout {

    /********************UI******************************/
    private LinearLayout ll_container;
    private LoopViewPager mLoopViewPager;
    private RelativeLayout rl_parent;
    float startX = 0, startY = 0;
    float endY;
    float endX;
    float distanceX;
    float distanceY;
    /********************常量******************************/
    private Context context;
    private final int LOOP_TIME = 3000;  //轮播的时间
    private final int LOOP_HANDLER = 100001;
    private List viewDataList;
    private boolean isOpenSuperMode;  //是否开启super模式  默认开启
    private float sideAlpha = 0.5f; //当开启super模式的时候  两边图片的透明度   默认0.5
    private IndicatorAlign mIndicatorAlign;  //指示器的位置
    //    private int indicatorRadius;  //指示器的半径
    private int indicatorWidth, indicatorHeight; //指示器的宽高
    private int indicatorMargin;  //指示器的间隔
    private int indicatorMarginSide;  //当指示器位置为right或者left的时候 距离屏幕的边距
    private int bottomMargin;//指示器距离底部的间距
    private int circleNormalDrawable = R.drawable.indicator_normal;
    private int circleSelectDrawable = R.drawable.indicator_selected;
    private boolean showIndicator;  //是否显示指示器  默认显示
    private boolean openLoop; //是否开启轮播
    private int millisecond;  //轮播时间

    public enum IndicatorAlign {
        LEFT,//左对齐
        CENTER,//居中对齐
        RIGHT //右对齐
    }

    //轮播的Handler
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case LOOP_HANDLER:
                    int currentItem = mLoopViewPager.getCurrentItem();
                    mLoopViewPager.setCurrentItem((currentItem + 1) % viewDataList.size(), true);
                    startLoop();
                    break;
            }
            return false;
        }
    });

    public SuperBannerView(Context context) {
        this(context, null);
    }

    public SuperBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperBannerView);
        isOpenSuperMode = typedArray.getBoolean(R.styleable.SuperBannerView_openSuperMode, true);
        sideAlpha = typedArray.getFloat(R.styleable.SuperBannerView_sideAlpha, 0.5f);
        int align = typedArray.getInt(R.styleable.SuperBannerView_indicatorAlign, 0);
        if (align == 0) {  //center
            mIndicatorAlign = IndicatorAlign.CENTER;
        } else if (align == 1) {//right
            mIndicatorAlign = IndicatorAlign.RIGHT;
        } else if (align == 2) { //left
            mIndicatorAlign = IndicatorAlign.LEFT;
        }
        openLoop = typedArray.getBoolean(R.styleable.SuperBannerView_openLoop, true);
        millisecond = typedArray.getInt(R.styleable.SuperBannerView_millisecond, LOOP_TIME);
        showIndicator = typedArray.getBoolean(R.styleable.SuperBannerView_showIndicator, true);
        indicatorWidth = typedArray.getInt(R.styleable.SuperBannerView_indicatorWidth, 20);
        indicatorHeight = typedArray.getInt(R.styleable.SuperBannerView_indicatorHeight, 20);
        indicatorMargin = typedArray.getInt(R.styleable.SuperBannerView_indicatorMargin, 20);
        bottomMargin = typedArray.getInt(R.styleable.SuperBannerView_bottomMargin, 20);
        indicatorMarginSide = typedArray.getInt(R.styleable.SuperBannerView_indicatorMarginSide, 20);
        typedArray.recycle();

        initView(context);
    }

    //初始化V
    private void initView(Context context) {
        this.context = context;
        viewDataList = new ArrayList();
        View view;
        if (isOpenSuperMode) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_super, this, true);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_common, this, true);
        }
        mLoopViewPager = view.findViewById(R.id.mLoopViewPager);
        mLoopViewPager.setOffscreenPageLimit(5);
        ll_container = view.findViewById(R.id.ll_container);
        rl_parent = view.findViewById(R.id.rl_parent);

        bindEvent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void bindEvent() {
        mLoopViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < ll_container.getChildCount(); i++) {
                    if (i == mLoopViewPager.getCurrentItem()) {
                        ll_container.getChildAt(i).setBackgroundResource(circleSelectDrawable);
                    } else {
                        ll_container.getChildAt(i).setBackgroundResource(circleNormalDrawable);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                //这里参数中的position 并不是真正的position
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mLoopViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        stopLoop();

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (openLoop) {
                            startLoop();
                        }
                        break;
                }
                return false;
            }
        });

        //当为super模式的时候  使其触摸两边的view也能触发事件
        rl_parent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mLoopViewPager.dispatchTouchEvent(event);
            }
        });
    }


    //初始化指示器
    private void initIndicator(List list) {
        //设置指示器距离底部间距
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) ll_container.getLayoutParams();
        layoutParams.bottomMargin = dpToPx(bottomMargin);
        if (ll_container.getChildCount() > 0) {
            ll_container.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            TextView view = new TextView(context);
            view.setBackgroundResource(circleNormalDrawable);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(indicatorWidth), dpToPx(indicatorHeight));
            params.rightMargin = dpToPx(indicatorMargin);
            switch (mIndicatorAlign) {
                case LEFT:
                    ll_container.setGravity(Gravity.LEFT);
                    layoutParams.leftMargin = indicatorMarginSide;
                    break;
                case RIGHT:
                    ll_container.setGravity(Gravity.RIGHT);
                    layoutParams.rightMargin = indicatorMarginSide;
                    break;
                case CENTER:
                    ll_container.setGravity(Gravity.CENTER);
                    break;
            }
            ll_container.setLayoutParams(layoutParams);
            ll_container.addView(view, params);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = event.getY();
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取当前手指位置
                endY = event.getY();
                endX = event.getX();
                distanceX = Math.abs(endX - startX);
                distanceY = Math.abs(endY - startY);
//                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (distanceX > distanceY) {
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoop();
        removeAllViews();
    }

    /**************************
     * 暴露出去的方法
     ****************************************************/


    public void setOpenSuperMode(boolean openSuperMode) {
        this.isOpenSuperMode = openSuperMode;
    }

    /**
     * 是否开启多屏模式   只有  openSuperMode 为true的时候有作用
     *
     * @param sideMargin 开启super模式的时候  中间图片到屏幕两边的距离
     * @param pageMargin 开启super模式的时候  中间图片到两边两张图片的距离  可为负数
     */
    public void setSuperModeMargin(int sideMargin, int pageMargin, boolean scaleY) {
        if (isOpenSuperMode) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLoopViewPager.getLayoutParams();
            lp.leftMargin = dpToPx(sideMargin);
            lp.rightMargin = dpToPx(sideMargin);
            lp.bottomMargin = 0;
            lp.topMargin = 0;
            mLoopViewPager.setLayoutParams(lp);
            mLoopViewPager.setPageMargin(dpToPx(pageMargin));
            mLoopViewPager.setPageTransformer(true, new CoverModeTransformer(mLoopViewPager, sideAlpha, scaleY));
        }
    }

    /**
     * 设置两边图片的透明度
     *
     * @param sideAlpha 取值范围 0~1
     */
    public void setSideAlpha(float sideAlpha) {
        this.sideAlpha = sideAlpha;
        mLoopViewPager.setPageTransformer(true, new CoverModeTransformer(mLoopViewPager, sideAlpha));
    }


    /**
     * 设置是否显示指示器
     */
    public void showIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
    }


    /**
     * 设置指示器的位置
     */
    public void setIndicatorAlign(IndicatorAlign mIndicatorAlign) {
        this.mIndicatorAlign = mIndicatorAlign;
    }


    /**
     * 当指示器位置为right或者left的时候 距离屏幕的边距
     * 当IndicatorAlign为center时  该方法无效
     */
    public void setIndicatorMarginSide(int indicatorMarginSide) {
        this.indicatorMarginSide = indicatorMarginSide;
    }

    /**
     * 设置圆形指示器的大小  间距  到底部距离
     */
    public void setIndicatorInfo(int indicatorWidth, int indicatorHeight, int margin, int bottomMargin) {
        this.indicatorWidth = indicatorWidth;
        this.indicatorHeight = indicatorHeight;
        this.indicatorMargin = margin;
        this.bottomMargin = bottomMargin;
    }

    /**
     * 设置指示器的Drawable
     *
     * @param circleNormalDrawable 未选中
     * @param circleSelectDrawable 选中
     */
    public void setCircleIndicatorDrawable(@DrawableRes int circleNormalDrawable, @DrawableRes int circleSelectDrawable) {
        this.circleNormalDrawable = circleNormalDrawable;
        this.circleSelectDrawable = circleSelectDrawable;
    }


    /**
     * 是否开启轮播  默认开启
     *
     * @param openLoop true  开启  默认开启
     */
    public void openLoop(boolean openLoop) {
        this.openLoop = openLoop;
    }

    /**
     * 开始轮播
     */
    public void startLoop() {
        stopLoop();
        mHandler.sendEmptyMessageDelayed(LOOP_HANDLER, millisecond);
    }

    /**
     * 暂停轮播
     */
    public void stopLoop() {
        mHandler.removeMessages(LOOP_HANDLER);
    }


    /**
     * 设置轮播时间
     *
     * @param millisecond 单位 毫秒
     */
    public void setLoopTime(int millisecond) {
        this.millisecond = millisecond;
    }

    /**
     * 设置数据
     */
    public <T> void setViewData(List<T> viewDataList, SuperHolder<T> superHolder) {
        if (null == viewDataList || viewDataList.size() == 0) {
            throw new NullPointerException("viewDataList is NULL");
        }
        this.viewDataList = viewDataList;
        mLoopViewPager.setAdapter(new LoopPageAdapter(viewDataList, superHolder));
        if (showIndicator) initIndicator(viewDataList);
        if (openLoop) startLoop();
    }


    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float dpToSp(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, Resources.getSystem().getDisplayMetrics());
    }

}
