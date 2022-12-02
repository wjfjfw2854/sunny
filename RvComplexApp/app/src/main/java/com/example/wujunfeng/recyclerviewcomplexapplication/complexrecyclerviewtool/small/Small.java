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
    /*
    * 仅点击列表(不包含头点击)
    * */
    public void click(int positionRow,int indexColumn) {}
}
