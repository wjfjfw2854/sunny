package com.example.wujunfeng.nestedscrollingapp.test.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.Scroller;

public class TextViewExtTest extends AppCompatTextView implements NestedScrollingChild {

    private float yDownLast;

    private int showHeight;
    private int[] consume = new int[2];
    private int[] offset = new int[2];
    private NestedScrollingChildHelper nestedScrollingChildHelper;
    private float maxYVelocity;
    private float minYVelocity;

    private Scroller scroller;
    private VelocityTracker velocityTracker;

    private int scrollLength;
    private int currentY;

    public TextViewExtTest(Context context) {
        this(context,null);
    }

    public TextViewExtTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextViewExtTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        scroller = new Scroller(context);
        maxYVelocity = dp2Px(context,500);
        minYVelocity = ViewConfiguration.getMinimumFlingVelocity();

        nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        nestedScrollingChildHelper.setNestedScrollingEnabled(true);

        scrollLength = dp2Px(context,48);
    }

    public static int dp2Px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
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
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public void stopNestedScroll() {
        nestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        loadVelocity(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                yDownLast = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getRawY();
                float midY = yDownLast - y;
                yDownLast = y;
                if(startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL) && dispatchNestedPreScroll(0,(int)midY,consume,offset))
                {
                    int remain = Math.abs((int)midY - consume[1]);
                    if(remain != 0)
                    {
                        scrollBy(0,remain);
                    }
                }
                else
                {
                    scrollBy(0,(int)midY);
                }
                currentY = (int)Math.min(Math.abs(midY),maxYVelocity + scrollLength);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                final VelocityTracker velocityTracker = this.velocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int yVelocity = (int)velocityTracker.getYVelocity();
                if(Math.abs(yVelocity) > minYVelocity)
                {
                    scroller.fling(0, currentY, 0, - yVelocity, 0, 0, 0, (int) maxYVelocity);
                }
                this.velocityTracker.clear();
                invalidate();
                break;
        }
        return true;
    }

    private void loadVelocity(MotionEvent event) {
        if(velocityTracker == null)
        {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        showHeight = getMeasuredHeight();//第1次atmost模式

        //第2次完全展开时的高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    public void scrollTo(int x, int y) {
        int maxY = getMeasuredHeight() - showHeight;
        if(y > maxY)
        {
            y = maxY;
        }
        if(y < 0)
            y = 0;
        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset())
        {
            currentY = scroller.getCurrY();
            int dy = scroller.getFinalY() - currentY;
            scrollBy(0,dy);
            invalidate();
        }
    }
}
