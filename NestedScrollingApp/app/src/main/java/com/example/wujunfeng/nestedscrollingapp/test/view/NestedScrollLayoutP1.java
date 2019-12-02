package com.example.wujunfeng.nestedscrollingapp.test.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import com.example.wujunfeng.nestedscrollingapp.R;
import com.example.wujunfeng.nestedscrollingapp.nestedscrollapp.views.NestedScrollingChild;


public class NestedScrollLayoutP1 extends LinearLayout implements NestedScrollingParent
{
    private static final String TAG = "NestedScrollLayoutP1";

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes)
    {
        Log.e(TAG, "onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes)
    {
        Log.e(TAG, "onNestedScrollAccepted");
    }

    @Override
    public void onStopNestedScroll(View target)
    {
        Log.e(TAG, "onStopNestedScroll");
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        Log.e(TAG, "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed)
    {
        Log.e(TAG, "onNestedPreScroll");
        if(target instanceof RecyclerView && "4000".equals(target.getTag()))
        {//----头部RecyclerView滚到底部前---不消费consumed[1]
            final RecyclerView recyclerView = (RecyclerView) target;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            int counts = adapter.getItemCount();
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (dy < 0) {
                if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    return;
                }
            }
            else if(dy >= 0)
            {
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() < counts - 1)
                {
                    return;
                }
            }
        }
        if(showTop(dy,target) || hidTop(dy))
        {
            scrollBy(0,dy);
            consumed[1] = dy;
        }
    }

    private boolean hidTop(int dy) {
        if(dy > 0)
        {
            if(getScrollY() < mTopViewHeight)
            {
                return true;
            }
        }
        return false;
    }

    private boolean showTop(int dy,View target) {
        if(dy < 0)
        {
            if(getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1))
            {
                return true;
            }
        }
        return false;
    }

    private int TOP_CHILD_FLING_THRESHOLD = 3;
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed)
    {
        //如果是recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        //这里判断如果第一个元素的位置是大于TOP_CHILD_FLING_THRESHOLD的
        //认为已经被消耗，在animateScroll里不会对velocityY<0时做处理
        if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
        }
        int size = getChildCount();
        for(int i = 0;i < size;i ++)
        {
            View view = getChildAt(i);
            if(view instanceof RecyclerView)
            {
                RecyclerView recyclerView = (RecyclerView)view;
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int first = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                int last = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                int sizeCount = linearLayoutManager.getItemCount() - 1;
                if("4000".equals(recyclerView.getTag()))
                {
                    if(first > 0 && last < sizeCount) {
                        return true;
                    }
                }
            }
        }
        if (!consumed) {
            animateScroll(velocityY, computeDuration(0),consumed);
        } else {
            animateScroll(velocityY, computeDuration(velocityY),consumed);
        }
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY)
    {
       //不做拦截 可以传递给子View
//        if(target instanceof RecyclerView ) {
//            final RecyclerView recyclerView = (RecyclerView) target;
//            RecyclerView.Adapter adapter = recyclerView.getAdapter();
//            int counts = adapter.getItemCount();
//            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//            final View firstChild = recyclerView.getChildAt(0);
//            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
//            if (velocityY < 0) {
//                if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() <= 0) {
//                    return true;
//                }
//            }
//            else if(velocityY >= 0)
//            {
//                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() >= counts - 1)
//                {
//                    return true;
//                }
//            }
//        }
       return false;
    }

    @Override
    public int getNestedScrollAxes()
    {
        Log.e(TAG, "getNestedScrollAxes");
        return 0;
    }

    /**
     * 根据速度计算滚动动画持续时间
     * @param velocityY
     * @return
     */
    private int computeDuration(float velocityY) {
        final int distance;
        if (velocityY > 0) {
            distance = Math.abs(mTop.getHeight() + recyclerVTop.getHeight() - getScrollY());
        } else {
//            distance = Math.abs(mTop.getHeight() - (mTop.getHeight() - getScrollY()));//这个是原始的但逻辑应该不对
            distance = Math.abs(getScrollY());
        }


        final int duration;
        velocityY = Math.abs(velocityY);
        if (velocityY > 0) {
            duration = 3 * Math.round(1000 * (distance / velocityY));
        } else {
            final float distanceRatio = (float) distance / getHeight();
            duration = (int) ((distanceRatio + 1) * 150);
        }

        return duration;

    }

    private void animateScroll(float velocityY, final int duration,boolean consumed) {
        final int currentOffset = getScrollY();
        final int topHeight = mTop.getHeight() + recyclerVTop.getHeight();
        if (mOffsetAnimator == null) {
            mOffsetAnimator = new ValueAnimator();
            mOffsetAnimator.setInterpolator(mInterpolator);
            mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Integer) {
                        scrollTo(0, (Integer) animation.getAnimatedValue());
                    }
                }
            });
        } else {
            mOffsetAnimator.cancel();
        }
        mOffsetAnimator.setDuration(Math.min(duration, 600));

        if (velocityY >= 0) {
            mOffsetAnimator.setIntValues(currentOffset, topHeight);
            mOffsetAnimator.start();
        }else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if(!consumed){
                mOffsetAnimator.setIntValues(currentOffset, 0);
                mOffsetAnimator.start();
            }

        }
    }

    private View mTop;
    private RecyclerView recyclerVTop;
    private View mNav;
    private ViewPager mViewPager;

    private int mTopViewHeight;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private ValueAnimator mOffsetAnimator;
    private Interpolator mInterpolator;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;

    public NestedScrollLayoutP1(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();

    }

    private void initVelocityTrackerIfNotExists()
    {
        if (mVelocityTracker == null)
        {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker()
    {
        if (mVelocityTracker != null)
        {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        initVelocityTrackerIfNotExists();
//        mVelocityTracker.addMovement(event);
//        int action = event.getAction();
//        float y = event.getY();
//
//        switch (action)
//        {
//            case MotionEvent.ACTION_DOWN:
//                if (!mScroller.isFinished())
//                    mScroller.abortAnimation();
//                mLastY = y;
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                float dy = y - mLastY;
//
//                if (!mDragging && Math.abs(dy) > mTouchSlop)
//                {
//                    mDragging = true;
//                }
//                if (mDragging)
//                {
//                    scrollBy(0, (int) -dy);
//                }
//
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                mDragging = false;
//                recycleVelocityTracker();
//                if (!mScroller.isFinished())
//                {
//                    mScroller.abortAnimation();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                mDragging = false;
//                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
//                int velocityY = (int) mVelocityTracker.getYVelocity();
//                if (Math.abs(velocityY) > mMinimumVelocity)
//                {
//                    fling(-velocityY);
//                }
//                recycleVelocityTracker();
//                break;
//        }
//
//        return super.onTouchEvent(event);
//    }


    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        mTop = findViewById(R.id.id_stickynavlayout_topview);
        mNav = findViewById(R.id.id_stickynavlayout_indicator);
        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (!(view instanceof ViewPager))
        {
            throw new RuntimeException(
                    "id_stickynavlayout_viewpager show used by ViewPager !");
        }
        recyclerVTop = findViewById(R.id.recyclerVTop);
        mViewPager = (ViewPager) view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + mNav.getMeasuredHeight() + mViewPager.getMeasuredHeight());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight() + recyclerVTop.getMeasuredHeight();
    }


    public void fling(int velocityY)
    {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > mTopViewHeight)
        {
            y = mTopViewHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }


}
