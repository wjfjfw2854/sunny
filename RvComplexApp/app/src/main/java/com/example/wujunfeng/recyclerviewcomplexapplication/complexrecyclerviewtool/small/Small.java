package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;

public class Small {

    protected RectF areaSubstance;
    protected RectF areaOut;

    protected Context context;
    protected int postion;

    protected SmallSpace space;
    protected static final int DEFAULTVAL=0;
    protected static final int DOWNVAL=1;
    protected static final int UPVAL=2;
    protected int mSort = DEFAULTVAL;

    public Small(Context context,SmallSpace smallSpace)
    {
        this.context = context;
        space = smallSpace;
    }

    public void onDraw(Canvas canvas)
    {

    }

    public void setArea(RectF area)
    {
        areaOut = area;
        areaSubstance = new RectF(area);
    }

    public RectF getArea()
    {
        return areaOut;
    }

    public void bindData(Object data,int pos)
    {
        postion = pos;
    }

    public void setSortVal(){
        ++ mSort;
        if (mSort > UPVAL) {
            mSort = DEFAULTVAL;
        }
    }

    public int getSortVal() {
        return mSort;
    }
}
