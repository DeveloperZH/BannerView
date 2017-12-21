package com.zh.bannerview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
public class LoopPageAdapter extends PagerAdapter {

    private Context context;
    private List viewDataList;

    public  LoopPageAdapter(Context context, List viewDataList) {
        this.context = context;
        this.viewDataList = viewDataList;
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
        View view = LoopView(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    private View LoopView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.vp_item, null);
        ImageView iv_item = view.findViewById(R.id.iv_item);
        Glide.with(context).load(viewDataList.get(position)).into(iv_item);
        return view;
    }
}
