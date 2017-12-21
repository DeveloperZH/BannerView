package com.zh.bannerview.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zh.bannerview.LoopPageAdapter;
import com.zh.bannerview.R;

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
    private LoopViewPager mLoopViewPager;
    /********************常量******************************/
    private Context context;
    private boolean isOpenSuperMode = false;  //是否开启super模式  默认不开启


    public SuperBannerView(Context context) {
        this(context, null);
        initView(context);
    }

    public SuperBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public SuperBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = null;
        if (isOpenSuperMode) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_super, this, true);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_common, this, true);
        }
        rl_parent = view.findViewById(R.id.rl_parent);
        mLoopViewPager = view.findViewById(R.id.mLoopViewPager);
    }


    /**************************
     * 暴露出去的方法
     ****************************************************/


    /**
     * 是否开启多屏模式
     *
     * @param openSuperMode 是否开启super模式  如果为false
     */
    public void setOpenSuperMode(boolean openSuperMode) {
        isOpenSuperMode = openSuperMode;
    }


    /**
     * 设置数据
     */
    public <T> void setViewData(List<T> viewDataList) {
        if (null == viewDataList || viewDataList.size() == 0) {
            throw new NullPointerException("viewDataList is NULL");
        }
        mLoopViewPager.setAdapter(new LoopPageAdapter(context, viewDataList));
    }

}
