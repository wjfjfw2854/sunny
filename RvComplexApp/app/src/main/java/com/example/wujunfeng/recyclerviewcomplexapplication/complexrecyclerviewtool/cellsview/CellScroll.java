package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.HeadSmall;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.Small;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;

import java.util.ArrayList;
import java.util.List;

public class CellScroll extends View {

    List<Small> smalls = new ArrayList<>();
    private int position;
    private float x,y;
    private boolean isLeft;

    public CellScroll(Context context) {
        this(context,null);
    }

    public CellScroll(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CellScroll(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initSmalls(List<SmallSpace> spaces)
    {
        smalls.clear();
        setSmalls(spaces);
    }

    public void setSmalls(List<SmallSpace> spaces) {
        int right = 0;
        for (Small small:smalls) {
            right += small.getArea().width();
        }
        int index = smalls.size();
        for(int i = index; i < spaces.size(); i ++)
        {
            SmallSpace smallSpace = spaces.get(i);
            smallSpace.smallIndex = i;
            Small small = birthSmall(smallSpace);
            int left = right;
            right += smallSpace.width;
            small.setArea(new RectF(left,getTop(),right,getBottom()));
            smalls.add(small);
        }
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = right;
        setLayoutParams(lp);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int column = -1;
                int size = smalls.size();
                boolean isHead = false;
                for (int i = 0;i < size;i ++) {
                    Small small = smalls.get(i);
                    if (small != null) {
                        RectF rectF = small.getArea();
                        if (rectF != null && rectF.contains(x,y)) {
                            column = i;
                            if (small instanceof HeadSmall) {
                                isHead = true;
                                for (int k = 0;k < size;k ++) {
                                    if (k == i) continue;
                                    HeadSmall hs = (HeadSmall) smalls.get(k);
                                    hs.resetSortVal();
                                }
                                HeadSmall headSmall = (HeadSmall)small;
                                //有效的头部点击
                                headSmall.setSortVal();
                                headSmall.headClick(position,column);
                                //点击标题排序后要刷新
                                invalidate();
                            }
                            break;
                        }
                    }
                }
                if (!isHead) {
                    if (smalls.size() > column) {
                        Small small = smalls.get(column);
                        small.click(position,column);
                    }
//                    Toast.makeText(getContext(), "点击的是-" + (isLeft ? "左边" : "右边") + "-第position=" + position + "行,第column=" + column + "列", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            x = event.getX();
            y = event.getY();
        }
        return super.onTouchEvent(event);
    }

    private Small birthSmall(SmallSpace smallSpace) {
        Class cls;
        try{
            cls = Class.forName(smallSpace.type.getName());
            return (Small)cls.getConstructor(Context.class,SmallSpace.class).newInstance(getContext(),smallSpace);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Small small : smalls)
        {
            canvas.save();
            canvas.clipRect(small.getArea());
            small.onDraw(canvas);
            canvas.restore();
        }
    }

    public void bindData(Object data,int pos,boolean isLeft)
    {
        this.isLeft = isLeft;
        position = pos;
        for(Small small : smalls)
        {
            small.bindData(data,pos);
        }
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(changed)
        {
            for(Small small : smalls)
            {
                RectF rectF = small.getArea();
                rectF.bottom = bottom;
                rectF.top = top;
                small.setArea(rectF);
            }
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    public int getPosition() {
        return position;
    }

    public List<Small> getSmalls() {
        return smalls;
    }
}
