package com.example.wujunfeng.recyclerviewcomplexapplication.util;

import android.view.MotionEvent;

import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellOut;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.scrollhelper.ScrollHelper;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;

import java.util.List;

public class ComputerUtil {
    public float[] getOffsetX(int type,float downX,int curScrollX,float curScrollX1,float curScrollX2)
    {
        float[] offsetXs = new float[3];
        if(type == RvItemDataType.TYPE_TOP0) {
            offsetXs[0] = downX + curScrollX;
        }
        else if(type == RvItemDataType.TYPE_TOP1)
        {
            offsetXs[1] = downX + curScrollX1;
        }
        else if(type == RvItemDataType.TYPE_TOP2)
        {
            offsetXs[2] = downX + curScrollX2;
        }
        return offsetXs;
    }

    public float[] canScrollMaxX(int type,int width, ScrollHelper scrollHelper)
    {
        float[] scrollMaxs = new float[3];
        int leftWidth = 0;
        int rightScrollWidth = 0;
        if(type == RvItemDataType.TYPE_TOP0) {
            for (SmallSpace smallSpace : scrollHelper.spaceLeft) {
                leftWidth += smallSpace.width;
            }
            for (SmallSpace smallSpace : scrollHelper.spaceRight) {
                rightScrollWidth += smallSpace.width;
            }
            int canScrollZone = width - leftWidth;
            scrollMaxs[0] = rightScrollWidth - canScrollZone;
            scrollHelper.scrollMaxX = scrollMaxs[0];
        }
        else if(type == RvItemDataType.TYPE_TOP1) {
            for (SmallSpace smallSpace : scrollHelper.spaceLeftOne) {
                leftWidth += smallSpace.width;
            }
            for (SmallSpace smallSpace : scrollHelper.spaceRightOne) {
                rightScrollWidth += smallSpace.width;
            }
            int canScrollZone = width - leftWidth;
            scrollMaxs[1] = rightScrollWidth - canScrollZone;
            scrollHelper.scrollMaxX1 = scrollMaxs[1];
        }
        else if(type == RvItemDataType.TYPE_TOP2) {
            for (SmallSpace smallSpace : scrollHelper.spaceLeftTwo) {
                leftWidth += smallSpace.width;
            }
            for (SmallSpace smallSpace : scrollHelper.spaceRightTwo) {
                rightScrollWidth += smallSpace.width;
            }
            int canScrollZone = width - leftWidth;
            scrollMaxs[2] = rightScrollWidth - canScrollZone;
            scrollHelper.scrollMaxX2 = scrollMaxs[2];
        }
        return scrollMaxs;
    }

    public int getMove(int type, float offsetX, float offsetX1,float offsetX2, MotionEvent e, int scrollLength, float scrollMaxX, float scrollMaxX1,float scrollMaxX2)
    {
        int scrollX = 0;
        if(type == RvItemDataType.TYPE_TOP0) {
            scrollX = (int) (offsetX - e.getX());
            scrollX = Math.max(scrollX, -scrollLength);
            scrollX = (int) Math.min(scrollX, scrollMaxX + scrollLength);
        }
        else if(type == RvItemDataType.TYPE_TOP1)
        {
            scrollX = (int) (offsetX1 - e.getX());
            scrollX = Math.max(scrollX, -scrollLength);
            scrollX = (int) Math.min(scrollX, scrollMaxX1 + scrollLength);
        }
        else if(type == RvItemDataType.TYPE_TOP2)
        {
            scrollX = (int) (offsetX2 - e.getX());
            scrollX = Math.max(scrollX, -scrollLength);
            scrollX = (int) Math.min(scrollX, scrollMaxX2 + scrollLength);
        }
        return scrollX;
    }

    public int[] getStartScroll(int type,int curScrollX,int curScrollX1,int curScrollX2,int scrollMaxX,int scrollMaxX1,int scrollMaxX2)
    {
        int[] intMids = new int[3];
        if(type == RvItemDataType.TYPE_TOP0)
        {
            intMids[0] = curScrollX;
            intMids[1] = scrollMaxX;
        }
        else if(type == RvItemDataType.TYPE_TOP1)
        {
            intMids[0] = curScrollX1;
            intMids[1] = scrollMaxX1;
        }
        else if(type == RvItemDataType.TYPE_TOP2)
        {
            intMids[0] = curScrollX2;
            intMids[1] = scrollMaxX2;
        }
        return intMids;
    }

    public int[] getCruScrollX(int type,int scrollX)
    {
        int[] curScrollXs = new int[3];
        if(type == RvItemDataType.TYPE_TOP0) {
            curScrollXs[0] = scrollX;
        }
        else if(type == RvItemDataType.TYPE_TOP1)
        {
            curScrollXs[1] = scrollX;
        }
        else if(type == RvItemDataType.TYPE_TOP2)
        {
            curScrollXs[2] = scrollX;
        }
        return curScrollXs;
    }

    public void scroll(int type,float x,float y,ScrollHelper scrollHelper)
    {
        if(type == RvItemDataType.TYPE_TOP0) {
            for (CellOut cellOuts : scrollHelper.cellOuts0) {
                cellOuts.scrollTo((int) x, (int) y);
            }
            scrollHelper.curScrollX = (int)x;
        }
        else if(type == RvItemDataType.TYPE_TOP1) {
            for (CellOut cellOuts : scrollHelper.cellOuts1) {
                cellOuts.scrollTo((int) x, (int) y);
            }
            scrollHelper.curScrollX1 = (int)x;
        }
        else if(type == RvItemDataType.TYPE_TOP2) {
            for (CellOut cellOuts : scrollHelper.cellOuts2) {
                cellOuts.scrollTo((int) x, (int) y);
            }
            scrollHelper.curScrollX2 = (int)x;
        }
    }

    public List<SmallSpace> getListSSpaces(int type, ScrollHelper scrollHelper)
    {
        List<SmallSpace> spaceRight = null;
        if(type == RvItemDataType.TYPE_TOP0)
        {
            spaceRight = scrollHelper.spaceRight;
        }
        else if(type == RvItemDataType.TYPE_TOP1)
        {
            spaceRight = scrollHelper.spaceRightOne;
        }
        else if(type == RvItemDataType.TYPE_TOP2)
        {
            spaceRight = scrollHelper.spaceRightTwo;
        }
        return spaceRight;
    }
}
