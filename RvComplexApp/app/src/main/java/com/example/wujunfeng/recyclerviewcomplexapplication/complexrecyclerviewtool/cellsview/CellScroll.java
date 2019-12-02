package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small.Small;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;

import java.util.ArrayList;
import java.util.List;

public class CellScroll extends View {

    List<Small> smalls = new ArrayList<>();
    private int position;

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
        int right = 0;
        for(int i = 0;i < spaces.size();i ++)
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

    public void bindData(Object data,int pos)
    {
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
}
