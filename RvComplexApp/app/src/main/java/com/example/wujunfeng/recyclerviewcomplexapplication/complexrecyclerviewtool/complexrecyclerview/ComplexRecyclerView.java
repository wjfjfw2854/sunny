package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.complexrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.Toast;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.ComplexRecyclerAdapter;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.baseadpater.MutiComplexRecyclerItemAdapter;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellOut;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.DataReflect;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.interfaces.ScrollListener;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.scrollhelper.ScrollHelper;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.CollectionUtils;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.ComputerUtil;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.DrawTool;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.RvItemDataType;

import java.util.ArrayList;
import java.util.List;

public class ComplexRecyclerView extends RecyclerView implements ScrollListener{

    private final int STAT_RESET = 0;
    private final int STAT_HORIZONTAL_SCROLL = 1;

    private int state;
    private int touchSlop;
    private OverScroller mScroller;
    private int scrollLength;

    private float downX;
    private float downY;

    private VelocityTracker velocityTracker;
    private float offsetX;
    private float offsetX1;
    private float offsetX2;
    private int curScrollX;
    private int curScrollX1;
    private int curScrollX2;
    private float scrollMaxX;
    private float scrollMaxX1;
    private float scrollMaxX2;

    private ScrollHelper scrollHelper = new ScrollHelper();
    private boolean overScrolled;
    private ComplexRecyclerAdapter adapter;

    private int type;
    private ComputerUtil computerUtil = new ComputerUtil();

    public ComplexRecyclerView(Context context) {
        this(context,null);
    }

    public ComplexRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ComplexRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(layoutManager);
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new OverScroller(getContext(),new DecelerateInterpolator(0.5f));
        scrollLength = DrawTool.dp2Px(getContext(),40);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        canScrollMaxX(getMeasuredWidth());

    }

    public void canScrollMaxX(int width)
    {
//        int leftWidth = 0;
//        int rightScrollWidth = 0;
//        if(type == RvItemDataType.TYPE_TOP0) {
//            for (SmallSpace smallSpace : scrollHelper.spaceLeft) {
//                leftWidth += smallSpace.width;
//            }
//            for (SmallSpace smallSpace : scrollHelper.spaceRight) {
//                rightScrollWidth += smallSpace.width;
//            }
//            int canScrollZone = width - leftWidth;
//            scrollMaxX = rightScrollWidth - canScrollZone;
//            scrollHelper.scrollMaxX = scrollMaxX;
//        }
//        else if(type == RvItemDataType.TYPE_TOP1) {
//            for (SmallSpace smallSpace : scrollHelper.spaceLeftOne) {
//                leftWidth += smallSpace.width;
//            }
//            for (SmallSpace smallSpace : scrollHelper.spaceRightOne) {
//                rightScrollWidth += smallSpace.width;
//            }
//            int canScrollZone = width - leftWidth;
//            scrollMaxX1 = rightScrollWidth - canScrollZone;
//            scrollHelper.scrollMaxX1 = scrollMaxX1;
//        }
        float[] scrollMaxs = computerUtil.canScrollMaxX(type,width,scrollHelper);
        scrollMaxX = scrollMaxs[0];
        scrollMaxX1 = scrollMaxs[1];
        scrollMaxX2 = scrollMaxs[2];
    }

    private int matchTypeX(final int pos)
    {
        int matchType = -1;
        if(adapter != null)
        {
            if(!CollectionUtils.isEmpty(adapter.datas) && adapter.datas.size() > pos)
            {
                Object obj = adapter.datas.get(pos);
                if(obj != null && obj instanceof DataReflect)
                {
                    DataReflect dataReflect = (DataReflect)obj;
                    matchType = dataReflect.type;
                }
            }
        }
        return matchType;
    }

    private List<DataReflect> findMatchTypeDatas(final int type)
    {
        List<DataReflect> list0 = null;
        if(adapter != null && !CollectionUtils.isEmpty(adapter.datas))
        {
            list0 = new ArrayList<>();
            for(int i = 0;i < adapter.datas.size();i ++)
            {
                Object obj = adapter.datas.get(i);
                if(obj != null && obj instanceof DataReflect)
                {
                    DataReflect dataReflect = (DataReflect)obj;
                    if(dataReflect.type == type)
                    {
                        dataReflect.canScroll = true;
                        list0.add(dataReflect);
                    }
                }
            }
        }
        return list0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                state = STAT_RESET;
                if(!mScroller.isFinished())
                {
                    mScroller.abortAnimation();
                }
                float x = e.getX();
                float y = e.getY();
                downX = x;
                downY = y;
                View childView = findChildViewUnder(x, y);
                if(childView == null)
                    return false;
                int position = getChildLayoutPosition(childView);
                type = matchTypeX(position);
                if(type <= 0)
                {
                    return false;
                }
//                if(type == RvItemDataType.TYPE_TOP0) {
//                    offsetX = downX + curScrollX;
//                }
//                else if(type == RvItemDataType.TYPE_TOP1)
//                {
//                    offsetX1 = downX + curScrollX1;
//                }
                float[] offsetXs = computerUtil.getOffsetX(type,downX,curScrollX,curScrollX1,curScrollX2);
                offsetX = offsetXs[0];
                offsetX1 = offsetXs[1];
                offsetX2 = offsetXs[2];
                super.onInterceptTouchEvent(e);
                break;
            case MotionEvent.ACTION_MOVE:
                if(state == STAT_RESET)
                {
                    y = e.getY();
                    float moveX = Math.abs(downX - e.getX());
                    float moveY = Math.abs(downY - y);
                    if(moveX > moveY && moveX > touchSlop)
                    {
                        state = STAT_HORIZONTAL_SCROLL;
                        canScrollMaxX(getMeasuredWidth());
                    }
                    else
                    {
                        return super.onInterceptTouchEvent(e);
                    }
                }
                break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_OUTSIDE:
                    break;
        }
        return state != STAT_RESET;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(velocityTracker == null)
        {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(e);
        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if(state == STAT_HORIZONTAL_SCROLL)
                {
//                    int scrollX = 0;
//                    if(type == RvItemDataType.TYPE_TOP0) {
//                        scrollX = (int) (offsetX - e.getX());
//                        scrollX = Math.max(scrollX, -scrollLength);
//                        scrollX = (int) Math.min(scrollX, scrollMaxX + scrollLength);
//                    }
//                    else if(type == RvItemDataType.TYPE_TOP1)
//                    {
//                        scrollX = (int) (offsetX1 - e.getX());
//                        scrollX = Math.max(scrollX, -scrollLength);
//                        scrollX = (int) Math.min(scrollX, scrollMaxX1 + scrollLength);
//                    }
                    int scrollX = computerUtil.getMove(type,offsetX,offsetX1,offsetX2,e,scrollLength,scrollMaxX,scrollMaxX1,scrollMaxX2);
                    dragScroll(scrollX);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
                if(state == STAT_HORIZONTAL_SCROLL)
                {
                    final VelocityTracker velocityTracker = this.velocityTracker;
                    velocityTracker.computeCurrentVelocity(1000);
                    int velocityX = (int)velocityTracker.getXVelocity();
//                    int curScrollXMid = curScrollX;
//                    int scrollMaxMid = (int)scrollMaxX;
                    int[] intMids = computerUtil.getStartScroll(type,curScrollX,curScrollX1,curScrollX2,(int)scrollMaxX,(int)scrollMaxX1,(int)scrollMaxX2);
                    int curScrollXMid = intMids[0];
                    int scrollMaxMid = intMids[1];
//                    if(type == RvItemDataType.TYPE_TOP0)
//                    {
//                        curScrollXMid = curScrollX;
//                        scrollMaxMid = (int)scrollMaxX;
//                    }
//                    else if(type == RvItemDataType.TYPE_TOP1)
//                    {
//                        curScrollXMid = curScrollX1;
//                        scrollMaxMid = (int)scrollMaxX1;
//                    }
                    if(curScrollXMid < 0 || curScrollXMid > scrollMaxMid)
                    {
                        overScrolled = true;
                    }
                    if(!overScrolled && mScroller != null)
                    {
                        mScroller.fling(curScrollXMid,0,-velocityX,0,0,Math.max(0,scrollMaxMid),0,0,scrollLength,scrollLength);
                        invalidate();
                    }
                    else
                    {
                        mScroller.springBack(curScrollXMid,0,0,(int)scrollMaxMid,0,0);
                        invalidate();
                    }
                    overScrolled = false;
                    downX = 0;
                    downY = 0;
                    velocityTracker.clear();
                }
                break;
        }
        return state == STAT_HORIZONTAL_SCROLL ? true : super.onTouchEvent(e);
    }

    private void dragScroll(int scrollX) {
        int[] curScrollx = computerUtil.getCruScrollX(type,scrollX);
        curScrollX = curScrollx[0];
        curScrollX1 = curScrollx[1];
        curScrollX2 = curScrollx[2];
//        if(type == RvItemDataType.TYPE_TOP0) {
//            curScrollX = scrollX;
//        }
//        else if(type == RvItemDataType.TYPE_TOP1)
//        {
//            curScrollX1 = scrollX;
//        }
        scroll(scrollX,0);
        invalidate();
    }

    @Override
    public void scroll(float x, float y) {
        computerUtil.scroll(type,x,y,scrollHelper);
//        if(type == RvItemDataType.TYPE_TOP0) {
//            for (CellOut cellOuts : scrollHelper.cellOuts0) {
//                cellOuts.scrollTo((int) x, (int) y);
//            }
//            scrollHelper.curScrollX = (int)x;
//        }
//        if(type == RvItemDataType.TYPE_TOP1) {
//            for (CellOut cellOuts : scrollHelper.cellOuts1) {
//                cellOuts.scrollTo((int) x, (int) y);
//            }
//            scrollHelper.curScrollX1 = (int)x;
//        }
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset())
        {
            int mScrollX = mScroller.getCurrX();
            dragScroll(mScrollX);
        }
        if(mScroller.isFinished() && downX == 0)
        {
            justScroll(mScroller.getCurrX());
        }
    }

    private void justScroll(int currX) {
        int left = 0;
        int right = 0;
        List<SmallSpace> spaceRight = computerUtil.getListSSpaces(type,scrollHelper);
//        if(type == RvItemDataType.TYPE_TOP0)
//        {
//            spaceRight = scrollHelper.spaceRight;
//        }
//        else if(type == RvItemDataType.TYPE_TOP1)
//        {
//            spaceRight = scrollHelper.spaceRightOne;
//        }
        if(spaceRight == null)
            return;
        for(int i = 0;i < spaceRight.size();i ++)
        {
            right += spaceRight.get(i).width;
            if(currX > left && currX < right)
            {
                if(Math.abs(currX - left) > Math.abs(currX - right))
                {
                    mScroller.startScroll(currX,0,right - currX,0);
                }
                else
                {
                    mScroller.startScroll(currX,0,left - currX,0);
                }
            }
            left = right;
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.adapter = (ComplexRecyclerAdapter)adapter;
        this.adapter.setScrollHelper(scrollHelper);
        super.setAdapter(adapter);
        this.adapter.setOnItemClickListner(new MutiComplexRecyclerItemAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(getContext(),"你点击败的是第" + pos + "列",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSmallSpace(List<SmallSpace> leftHeadSpace,
                              List<SmallSpace> rightHeadSpace,
                              List<SmallSpace> leftSpace,
                              List<SmallSpace> rightSpace,
                              List<SmallSpace> leftHeadSpaceOne,
                              List<SmallSpace> rightHeadSpaceOne,
                              List<SmallSpace> leftSpaceOne,
                              List<SmallSpace> rightSpaceOne,
                              List<SmallSpace> leftHeadSpaceTwo,
                              List<SmallSpace> rightHeadSpaceTwo,
                              List<SmallSpace> leftSpaceTwo,
                              List<SmallSpace> rightSpaceTwo
                              )
    {
        scrollHelper.spaceHeadLeft = leftHeadSpace;
        scrollHelper.spaceHeadRight = rightHeadSpace;

        scrollHelper.spaceLeft = leftSpace;
        scrollHelper.spaceRight = rightSpace;


        scrollHelper.spaceHeadLeftOne = leftHeadSpaceOne;
        scrollHelper.spaceHeadRightOne = rightHeadSpaceOne;

        scrollHelper.spaceLeftOne = leftSpaceOne;
        scrollHelper.spaceRightOne = rightSpaceOne;

        scrollHelper.spaceHeadLeftTwo = leftHeadSpaceTwo;
        scrollHelper.spaceHeadRightTwo = rightHeadSpaceTwo;

        scrollHelper.spaceLeftTwo = leftSpaceTwo;
        scrollHelper.spaceRightTwo = rightSpaceTwo;
    }
}
