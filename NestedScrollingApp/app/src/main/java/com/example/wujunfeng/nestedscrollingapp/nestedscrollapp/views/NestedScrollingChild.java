package com.example.wujunfeng.nestedscrollingapp.nestedscrollapp.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class NestedScrollingChild extends LinearLayout implements android.support.v4.view.NestedScrollingChild {

    private VelocityTracker mVelocityTracker;
    private int showHeight;
    private int[] consume = new int[2];
    private int[] offset = new int[2];
    private int lastY;
    private NestedScrollingChildHelper nestedScrollingChildHelper;
    private float maxVelocity;
    private float minVelocity;
    private Scroller scroller;
    private int yVelocity;

    public NestedScrollingChild(Context context) {
        this(context,null);
    }

    public NestedScrollingChild(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NestedScrollingChild(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestedScrollingChild(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context)
    {
        nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        nestedScrollingChildHelper.setNestedScrollingEnabled(true);

        scroller = new Scroller(context,null,true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        maxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        minVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return nestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        nestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return nestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public void stopNestedScroll() {
        nestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return nestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public void scrollTo(int x, int y) {
        int maxY = getMeasuredHeight() - showHeight;
        if(y > maxY)
            y = maxY;
        if(y < 0)
            y = 0;
        super.scrollTo(x, y);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        showHeight = getMeasuredHeight();//第1次atmost测量模式

        //第2次测量完全展示内容所需高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocity(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastY = (int)event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int)event.getRawY();
                int marginY = y - lastY;
                lastY = y;
                if(startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL)
                && dispatchNestedPreScroll(0,marginY,consume,offset)//父嵌套滚动控件
                )
                {
                    int remain = marginY - consume[1];
                    if(remain != 0)
                    {
                        scrollBy(0,-remain);
                    }
                }
                else
                {
                    scrollBy(0,-marginY);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                if(getScrollY() > consume[1]) {
                    mVelocityTracker.computeCurrentVelocity(1000, maxVelocity);
                    yVelocity = (int) mVelocityTracker.getYVelocity();
                    if (Math.abs(yVelocity) > minVelocity) {
                        scroller.fling(0, getScrollY(), 0, -yVelocity, 0, 0, -50000, 5000);
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    private void initVelocity(MotionEvent event)
    {
        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset())
        {
            invalidate();
            int dy = scroller.getFinalY() - scroller.getCurrY();
            scrollBy(0,dy);
        }
    }
}
