package com.example.kenjihosaka.sampleandroid.app.common.ui.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class InterceptTouchEventFrameLayout extends FrameLayout {

    public InterceptTouchEventFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
