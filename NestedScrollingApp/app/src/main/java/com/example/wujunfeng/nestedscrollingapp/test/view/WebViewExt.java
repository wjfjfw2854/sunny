package com.example.wujunfeng.nestedscrollingapp.test.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Scroller;

public class WebViewExt extends WebView implements NestedScrollingChild {

    private final static int STATE_INIT = 0;
    private final static int STATE_MOVE = 1;
    private int STATE = STATE_INIT;

    private int widthMeasureSpec;
    private int heightMeasureSpec;

    private float yDownLast;
    private float xDownLast;

    private int showHeight;
    private int showAllHeight;
    private int[] consume = new int[2];
    private int[] offset = new int[2];
    private NestedScrollingChildHelper nestedScrollingChildHelper;
    private float maxYVelocity;
    private float minYVelocity;

    private Scroller scroller;
    private VelocityTracker velocityTracker;

    private int currentY;

    public WebViewExt(Context context) {
        super(context);
        init(context);
    }

    public WebViewExt(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WebViewExt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebViewExt(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public WebViewExt(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init(context);
    }

    private void init(Context context)
    {
        scroller = new Scroller(context);
//        maxYVelocity = dp2Px(context,500);
        maxYVelocity = dp2Px(context,100);
        minYVelocity = ViewConfiguration.getMinimumFlingVelocity();

        nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        nestedScrollingChildHelper.setNestedScrollingEnabled(true);

//        scrollLength = dp2Px(context,48);
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

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                yDownLast = event.getRawY();
//                xDownLast = event.getRawX();
//                STATE = STATE_INIT;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float x = event.getRawX();
//                float y = event.getRawY();
//                float midX = xDownLast - x;
//                float midY = yDownLast - y;
//                if(Math.abs(midX) < Math.abs(midY)) {
//                    STATE = STATE_MOVE;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_OUTSIDE:
//                break;
//        }
//        return STATE != STATE_INIT;
//    }

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
                float x = event.getRawX();
                int midY = (int)(yDownLast - y);
                yDownLast = y;
                    if (startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL) && dispatchNestedPreScroll(0, (int) midY, consume, offset)) {
                        int remain = Math.abs((int) midY - consume[1]);
                        if (remain != 0) {
                            currentY = remain;
                            if(getScrollY() < showAllHeight) {
                                scrollBy(0, remain);
                            }
                        }
                    } else {
                        if(midY != 0) {
                            currentY = midY;
                            if(getScrollY() < showAllHeight) {
                                scrollBy(0, midY);
                            }
                        }
                    }
                    if(Math.abs(currentY) > maxYVelocity)
                    {
                        currentY = (int)- maxYVelocity;
                    }
//                    currentY = (int) Math.min(Math.abs(midY), maxYVelocity);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                final VelocityTracker velocityTracker = this.velocityTracker;
                velocityTracker.computeCurrentVelocity(300);
                int yVelocity = (int)velocityTracker.getYVelocity();
                if(Math.abs(yVelocity) > minYVelocity)
                {
                    scroller.fling(0, currentY, 0, - yVelocity, 0, 0, 0, (int) maxYVelocity);
                }
                this.velocityTracker.clear();
                invalidate();
                STATE = STATE_INIT;
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
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(showHeight <= 0) {
            this.widthMeasureSpec = widthMeasureSpec;
            this.heightMeasureSpec = heightMeasureSpec;
            showHeight = getMeasuredHeight();//第1次atmost模式
        }

        //第2次完全展开时的高度
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        //第2次完全展开时的高度
        int hMeasureSpecX = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec,hMeasureSpecX);
        showAllHeight = getMeasuredHeight();

        //拿到第2次的高度后再置回来显示高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void scrollTo(int x, int y) {
//        int maxY = getMeasuredHeight() - showHeight;
//        if(y > maxY)
//        {
//            y = maxY;
//        }
//        if(y < 0)
//            y = 0;
        Log.e("wjf>>>>","scrollTo y = " + y);
        if(y >= showAllHeight)
        {
            return;
        }
        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset())
        {
            Log.e("wjf>>>>","computeScroll showAllHeight = " + showAllHeight + "  showHeight = " + showHeight + "  scroller.getFinalY = " + scroller.getFinalY() + "  scroller.getCurrY() = " + scroller.getCurrY());
            Log.e("wjf>>>>","computeScroll currentY = " + currentY + "  scroller.isFinished = " + scroller.isFinished());
//            currentY += 200;
            int dy = currentY + scroller.getCurrY();
            Log.e("wjf>>>>","computeScroll dy = " + dy);
            if(dy != 0) {
                scrollBy(0, dy);
                invalidate();
            }
        }
    }

    public class MyWebViewClient extends WebViewClient {
        private static final String TAG = "webm";

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            ViewGroup.LayoutParams lp = WebViewExt.this.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            WebViewExt.this.setLayoutParams(lp);
        }
    }
}
