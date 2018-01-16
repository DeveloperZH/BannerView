package com.zh.superbanneribrary;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;



import java.util.List;

/**
 * <p>创建者       Z H</p>
 * <p>创建时间     2017/12/21 23:12</p>
 * <p>包名         com.zh.bannerview</p>
 * <p>描述         TODO </p>
 * <p>svn版本      $Revision$ </p>
 * <p>更新者       $Author$</p>
 * <p>更新时间     $Date$</p>
 */
public class LoopPageAdapter<T> extends PagerAdapter {

    private Context context;
    private List<T> viewDataList;
    private SuperHolder<T> mSuperHolder;

    public LoopPageAdapter(Context context, List<T> viewDataList, SuperHolder<T> mSuperHolder) {
        this.context = context;
        this.viewDataList = viewDataList;
        this.mSuperHolder = mSuperHolder;
    }

    @Override
    public int getCount() {
        return viewDataList == null ? 0 : viewDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mSuperHolder.createView(context);
        mSuperHolder.onBind(context,position, viewDataList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
