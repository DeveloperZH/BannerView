package com.zh.bannerview.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    private RelativeLayout rl_parent;
    private LinearLayout ll_container;
    private LoopViewPager mLoopViewPager;
    /********************常量******************************/
    private Context context;
    private boolean isOpenSuperMode = true;  //是否开启super模式  默认不开启
    private float sideAlpha = 0.5f; //当开启super模式的时候  两边图片的透明度   默认0.5
    private IndicatorAlign mIndicatorAlign = IndicatorAlign.CENTER;  //指示器的位置  默认居中
    private List<View> indicatorViewList;

    public enum IndicatorAlign {
        LEFT,
        CENTER,//居中对齐
        RIGHT //右对齐
    }


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
//        mIndicatorAlign = typedArray.getInt(R.styleable.SuperBannerView_indicatorAlign,0);
        initView(context);
    }

    //初始化V
    private void initView(Context context) {
        this.context = context;
        indicatorViewList = new ArrayList<>();
        View view;
        if (isOpenSuperMode) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_super, this, true);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_common, this, true);
        }
        rl_parent = view.findViewById(R.id.rl_parent);
        mLoopViewPager = view.findViewById(R.id.mLoopViewPager);
        mLoopViewPager.setOffscreenPageLimit(4);
        ll_container = view.findViewById(R.id.ll_container);
        bindEvent();
    }

    private void bindEvent() {
        mLoopViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (mLoopViewPager.getCurrentItem() == ll_container.getChildAt())
            }

            @Override
            public void onPageSelected(int position) {
                //这里参数中的position 并不是真正的position
                Toast.makeText(context, "" + mLoopViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                if (indicatorViewList != null && indicatorViewList.size() >0){
                    indicatorViewList.get(mLoopViewPager.getCurrentItem()).setBackground(getResources().getDrawable(R.drawable.indicator_selected));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化指示器
    private void initIndicator(List list) {
        for (int i = 0; i < list.size(); i++) {
            View view = new View(context);
            view.setBackgroundResource(R.drawable.indicator_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
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
        }
    }


    /**************************
     * 暴露出去的方法
     ****************************************************/


    /**
     * 是否开启多屏模式   只有  openSuperMode 为true的时候有作用
     *
     * @param sideMarginpx 开启super模式的时候  中间图片到屏幕两边的距离
     * @param pageMarginPx 开启super模式的时候  中间图片到两边两张图片的距离  可为0 可为负数
     */
    public void setOpenSuperMode(int sideMarginpx, int pageMarginPx) {
        if (isOpenSuperMode) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLoopViewPager.getLayoutParams();
            lp.leftMargin = dpToPx(sideMarginpx);
            lp.rightMargin = dpToPx(sideMarginpx);
            mLoopViewPager.setLayoutParams(lp);
            mLoopViewPager.setPageMargin(dpToPx(pageMarginPx));
//            mLoopViewPager.setPageTransformer(true, new CoverModeTransformer(mLoopViewPager));
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
     * 开启轮播
     */
    public void statrLoop() {

    }

    /**
     * 暂停轮播
     */
    public void stopLoop() {

    }

    /**
     * 设置数据
     */
    public <T> void setViewData(List<T> viewDataList, SuperHolder superHolder) {
        if (null == viewDataList || viewDataList.size() == 0) {
            throw new NullPointerException("viewDataList is NULL");
        }
        mLoopViewPager.setAdapter(new LoopPageAdapter(context, viewDataList, superHolder));
        initIndicator(viewDataList);
    }


    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
