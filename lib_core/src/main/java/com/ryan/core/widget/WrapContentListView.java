package com.ryan.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @Author: Ryan
 * @Date: 2020/6/16 13:43
 * @Description: java类作用描述
 */
public class WrapContentListView extends ListView {

    public WrapContentListView(Context context) {
        super(context);
    }

    public WrapContentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE / 4, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}