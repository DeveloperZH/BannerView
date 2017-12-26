package com.zh.bannerview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zh.bannerview.LoopPageAdapter;
import com.zh.bannerview.R;

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
    /********************常量******************************/
    private Context context;
    private final int LOOP_TIME = 3000;  //轮播的时间
    private final int LOOP_HANDLER = 100001;
    private List viewDataList;
    private boolean isOpenSuperMode = true;  //是否开启super模式  默认不开启
    private float sideAlpha = 0.5f; //当开启super模式的时候  两边图片的透明度   默认0.5
    private IndicatorAlign mIndicatorAlign;  //指示器的位置
    private IndicatorType mIndicatorType;  //指示器类型
    private boolean openLoop; //是否开启轮播
    private int circleOrTextSize;  //圆圈大小
    private int normalColor; //未选择颜色
    private int selectColor;  //选中颜色

    private List<TextView> indicatorViewList;

    public enum IndicatorAlign {
        LEFT,//左对齐
        CENTER,//居中对齐
        RIGHT //右对齐
    }

    public enum IndicatorType {
        NUMBER,  //数字
        CIRCLE  //圆圈
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
        isOpenSuperMode = typedArray.getBoolean(R.styleable.SuperBannerView_openSuperMode, false);
        sideAlpha = typedArray.getFloat(R.styleable.SuperBannerView_sideAlpha, 0.5f);
        int align = typedArray.getInt(R.styleable.SuperBannerView_indicatorAlign, 0);
        if (align == 0) {  //center
            mIndicatorAlign = IndicatorAlign.CENTER;
        } else if (align == 1) {//right
            mIndicatorAlign = IndicatorAlign.RIGHT;
        } else if (align == 2) { //left
            mIndicatorAlign = IndicatorAlign.LEFT;
        }
        int indicatorType = typedArray.getInt(R.styleable.SuperBannerView_indicatorType, 0);
        if (indicatorType == 0) {
            mIndicatorType = IndicatorType.CIRCLE;
        } else if (indicatorType == 1) {
            mIndicatorType = IndicatorType.NUMBER;
        }
        openLoop = typedArray.getBoolean(R.styleable.SuperBannerView_openLoop, true);
        circleOrTextSize = typedArray.getInt(R.styleable.SuperBannerView_circleOrTextSize, 20);
        normalColor = typedArray.getInt(R.styleable.SuperBannerView_normalColor, R.color.colorPrimary);
        selectColor = typedArray.getInt(R.styleable.SuperBannerView_selectColor, R.color.colorAccent);
        initView(context);
    }

    //初始化V
    private void initView(Context context) {
        this.context = context;
        indicatorViewList = new ArrayList<>();
        viewDataList = new ArrayList();
        View view;
        if (isOpenSuperMode) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_super, this, true);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_common, this, true);
        }
        mLoopViewPager = view.findViewById(R.id.mLoopViewPager);
        mLoopViewPager.setOffscreenPageLimit(4);
        ll_container = view.findViewById(R.id.ll_container);
        bindEvent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void bindEvent() {
        mLoopViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (indicatorViewList != null && indicatorViewList.size() > 0) {
                    for (int i = 0; i < indicatorViewList.size(); i++) {
                        switch (mIndicatorType) {
                            case CIRCLE:
                                if (i == mLoopViewPager.getCurrentItem()) {
                                    indicatorViewList.get(i).setBackgroundColor(getResources().getColor(selectColor));
                                } else {
                                    indicatorViewList.get(i).setBackgroundColor(getResources().getColor(normalColor));
                                }
                                break;
                            case NUMBER:
                                if (i == mLoopViewPager.getCurrentItem()) {
                                    indicatorViewList.get(i).setTextColor(getResources().getColor(selectColor));
                                } else {
                                    indicatorViewList.get(i).setTextColor(getResources().getColor(normalColor));
                                }
                                break;
                        }
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
    }

    //初始化指示器
    private void initIndicator(List list) {
        for (int i = 0; i < list.size(); i++) {
            switch (mIndicatorType) {
                case NUMBER:
                    TextView number_view = new TextView(context);
                    number_view.setTextSize(circleOrTextSize);
                    number_view.setGravity(Gravity.CENTER);
                    number_view.setText(String.valueOf(i + 1));
                    LinearLayout.LayoutParams numberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    numberParams.rightMargin = 20;
                    switch (mIndicatorAlign) {
                        case LEFT:
                            ll_container.setGravity(Gravity.LEFT);
                            break;
                        case RIGHT:
                            ll_container.setGravity(Gravity.RIGHT);
                            break;
                        case CENTER:
                            ll_container.setGravity(Gravity.CENTER);
                            break;
                    }
                    ll_container.addView(number_view, numberParams);
                    indicatorViewList.add(number_view);
                    break;
                case CIRCLE:
//                    indicatorViewList.clear();
                    TextView view = new TextView(context);
                    view.setBackgroundColor(getResources().getColor(normalColor));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(circleOrTextSize), dpToPx(circleOrTextSize));
                    params.rightMargin = 20;
                    switch (mIndicatorAlign) {
                        case LEFT:
                            ll_container.setGravity(Gravity.LEFT);
                            break;
                        case RIGHT:
                            ll_container.setGravity(Gravity.RIGHT);
                            break;
                        case CENTER:
                            ll_container.setGravity(Gravity.CENTER);
                            break;
                    }
                    ll_container.addView(view, params);
                    indicatorViewList.add(view);
                    break;
            }
        }
    }


    /**************************
     * 暴露出去的方法
     ****************************************************/


    /**
     * 是否开启多屏模式   只有  openSuperMode 为true的时候有作用
     *
     * @param sideMargin 开启super模式的时候  中间图片到屏幕两边的距离
     * @param pageMargin 开启super模式的时候  中间图片到两边两张图片的距离  可为0 可为负数
     */
    public void setOpenSuperMode(int sideMargin, int pageMargin) {
        if (isOpenSuperMode) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLoopViewPager.getLayoutParams();
            lp.leftMargin = dpToPx(sideMargin);
            lp.rightMargin = dpToPx(sideMargin);
            mLoopViewPager.setLayoutParams(lp);
            mLoopViewPager.setPageMargin(dpToPx(pageMargin));
            mLoopViewPager.setPageTransformer(true, new CoverModeTransformer(mLoopViewPager));
        }
    }

    /**
     * 设置两边图片的透明度
     *
     * @param sideAlpha 取值范围 0~1
     */
    // TODO: 2017/12/22   这里有问题 暂时不可用
    public void setSideAlpha(float sideAlpha) {
        this.sideAlpha = sideAlpha;
//        mLoopViewPager.setPageTransformer(true, new CoverModeTransformer(mLoopViewPager));
    }

    /**
     * 设置指示器的位置
     */
    public void setIndicatorAlign(IndicatorAlign mIndicatorAlign) {
        this.mIndicatorAlign = mIndicatorAlign;
    }

    /**
     * 设置指示器的类型
     */
    public void setIndicatorType(IndicatorType mIndicatorType) {
        this.mIndicatorType = mIndicatorType;
    }

    /**
     * 当指示器为圆圈的时候 设置圆圈的大小  未选中颜色  选择颜色
     */
    public void setCircleIndicator(int circleOrTextSize, int normalColor, int selectColor) {
        this.circleOrTextSize = circleOrTextSize;
        this.normalColor = normalColor;
        this.selectColor = selectColor;
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
        mHandler.removeMessages(LOOP_HANDLER);
        mHandler.sendEmptyMessageDelayed(LOOP_HANDLER, LOOP_TIME);
    }

    /**
     * 暂停轮播
     */
    public void stopLoop() {
        mHandler.removeMessages(LOOP_HANDLER);
    }

    /**
     * 设置数据
     */
    public <T> void setViewData(List<T> viewDataList, SuperHolder superHolder) {
        if (null == viewDataList || viewDataList.size() == 0) {
            throw new NullPointerException("viewDataList is NULL");
        }
        this.viewDataList = viewDataList;
        mLoopViewPager.setAdapter(new LoopPageAdapter(context, viewDataList, superHolder));
        initIndicator(viewDataList);
        if (openLoop) {
            startLoop();
        }
    }


    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float dpToSp(int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,dp,Resources.getSystem().getDisplayMetrics());
    }

}
