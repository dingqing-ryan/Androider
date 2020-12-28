package com.ryan.core.widget.tablayout

import android.content.Context
import android.graphics.Canvas
import android.widget.HorizontalScrollView

/**
 * @Author: Ryan
 * @Date: 2020/9/9 16:49
 * @Description: 自定义TabLayout
 */
class RyanTabLayout(context: Context?) : HorizontalScrollView(context) {
    /**
     * 标题集合
     * 只支持String
     */
    private var tileList: List<String>? = null
        get() = field
        set(value) {
            field = value
        }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

    }
}