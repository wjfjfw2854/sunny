package com.example.wujunfeng.nestedscrollingapp.nestedscrollapp.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wujunfeng.nestedscrollingapp.R;

public class NestedScrollingParent extends LinearLayout implements android.support.v4.view.NestedScrollingParent {

    private ImageView imageView;
    private TextView textView;
    private NestedScrollingChild nestedScrollingChild;
    private int imgH;
    private int tvH;
    private NestedScrollingParentHelper nestedScrollingParentHelper;

    public NestedScrollingParent(Context context) {
        this(context,null);
    }

    public NestedScrollingParent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NestedScrollingParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestedScrollingParent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context)
    {
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        imageView = (ImageView)findViewById(R.id.imgtop);
        textView = (TextView)findViewById(R.id.txttop);
        nestedScrollingChild = (NestedScrollingChild)findViewById(R.id.nestchild);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(imgH <= 0) {
                    imgH = imageView.getMeasuredHeight();
                }
            }
        });
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(tvH <= 0)
                {
                    tvH = textView.getMeasuredHeight();
                }
            }
        });
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if(target instanceof NestedScrollingChild)
        {
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        nestedScrollingParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if(showImg(dy) || hideImg(dy))
        {
            scrollBy(0,-dy);
            consumed[1] = dy;
        }
    }

    private boolean hideImg(int dy) {
        if(dy < 0)
        {
            if(getScrollY() < imgH) {
                return true;
            }
        }
        return false;
    }

    private boolean showImg(int dy) {
        if(dy > 0)
        {
            if(getScrollY() > 0 && nestedScrollingChild.getScrollY() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {
        if(y < 0)
        {
            y = 0;
        }
        if(y > imgH)
        {
            y = imgH;
        }
        super.scrollTo(x, y);
    }
}
