package com.zh.superbanneribrary;

import android.content.Context;
import android.view.View;

/**
 * <p>公司名       tsingning</p>
 * <p>创建者       Z H</p>
 * <p>创建时间     2017/12/22 13:38</p>
 * <p>包名         com.zh.bannerview.view</p>
 * <p>描述         TODO </p>
 * <p>svn版本      $Revision$ </p>
 * <p>更新者       $Author$</p>
 * <p>更新时间     $Date$</p>
 */
public interface SuperHolder<T> {
    /**
     *  创建View
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);

}
