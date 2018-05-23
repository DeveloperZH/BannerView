package com.zh.bannerview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration

import com.dengzq.simplerefreshlayout.SimpleRefreshLayout

/**
 *
 * 公司名       tsingning
 *
 * 创建者       ZH
 *
 * 创建时间     2017/10/25 16:08
 *
 * 包名         com.tsingning.dtv.utils
 *
 * 描述         TODO
 *
 * svn版本      $Revision: 1413 $
 *
 * 更新者       $Author: zhouwb $
 *
 * 更新时间     $Date: 2017-10-25 16:20:41 +0800 (Wed, 25 Oct 2017) $
 */
class TouchRefreshLayout(context: Context, attrs: AttributeSet) : SimpleRefreshLayout(context, attrs) {
    private var startY: Float = 0.toFloat()
    private var startX: Float = 0.toFloat()
    // 记录viewPager是否拖拽的标记
    private var mIsVpDragger: Boolean = false
    private var mTouchSlop = 0

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                // 记录手指按下的位置
                startY = ev.y
                startX = ev.x
                // 初始化标记
                mIsVpDragger = false
            }
            MotionEvent.ACTION_MOVE -> {
                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsVpDragger) {
                    return false
                }

                // 获取当前手指位置
                val endY = ev.y
                val endX = ev.x
                val distanceX = Math.abs(endX - startX)
                val distanceY = Math.abs(endY - startY)
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpDragger = true
                    return false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                // 初始化标记
                mIsVpDragger = false
        }
        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev)
    }
}
