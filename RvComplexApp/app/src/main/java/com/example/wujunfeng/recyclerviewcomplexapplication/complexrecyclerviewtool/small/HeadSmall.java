package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;

import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.ComplexDataTemple;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.DataReflect;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.interfaces.HeadClickLis;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.DrawTool;

import java.util.HashMap;

public class HeadSmall extends Small{

    private Paint paint;
    private RectF area;
    private Object data;
    protected static final int DEFAULTVAL=0;
    protected static final int DOWNVAL=1;
    protected static final int UPVAL=2;
    protected int mSort = DEFAULTVAL;

    public HeadSmall(Context context, SmallSpace smallSpace) {
        super(context, smallSpace);
        init();
    }

    private void init()
    {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DrawTool.dp2Px(context,13));
        paint.setColor(0xFF4444FF);
    }

    @Override
    public void setArea(RectF area) {
        super.setArea(area);
        this.area = new RectF(areaOut.left,areaOut.top,areaOut.right,areaOut.bottom);
    }

    @Override
    public void bindData(Object data, int pos) {
        super.bindData(data, pos);
        this.data = data;
    }

    public void resetSortVal() {
        mSort = DEFAULTVAL;
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

    public void headClick(int positionRow,int indexColumn) {
        if (space != null) {
            Object[] args = space.args;
            if (args != null && args.length > 3) {
                Object obj = args[3];
                if (obj != null && obj instanceof HeadClickLis) {
                    String val = "headtitlename";
                    if(data != null && space != null) {
                        val = getTitleName();
                    }
                    HeadClickLis headClickLis = (HeadClickLis) obj;
                    headClickLis.clickHead(val,positionRow,indexColumn);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        String str = (String)space.obj;
//        cn.emoney.hvscroll.DrawTool.drawRectText(canvas, str, paint, area, cn.emoney.hvscroll.DrawTool.CENTER, false);
        if(data != null&&space != null)
        {
            String val = getTitleName();
            if(!TextUtils.isEmpty(val)) {
                    int gravity = DrawTool.CENTER;
                    Object[] args = space.args;
                    float rightSortImgWidth = 0;
                    float rightSortImgHeight = 0;
                    if (args != null) {
                        if (args.length > 0) {
                            Object obj = args[0];
                            if (obj != null && obj instanceof Integer) {
                                gravity = ((Integer) obj).intValue();
                            }
                        }
                        if (args.length > 2) {
                            Object obj = args[2];
                            if (obj != null && obj instanceof int[]) {
                                int[] wh = (int[])obj;
                                rightSortImgWidth = wh[0];
                                rightSortImgHeight = wh[1];
                            }
                        }
                        if (args.length > 1) {
                            Object arg1 = args[1];
                            if (arg1 != null && arg1 instanceof int[]) {
                                int[] drawableId = (int[]) arg1;
                                if (drawableId.length > 0) {
                                    RectF rectF = new RectF(area.right - rightSortImgWidth, area.top, area.right, area.bottom);
                                    int sortVal = getSortVal();
                                    int drawableIdFinal = drawableId[sortVal >= drawableId.length?0:sortVal];
                                    DrawTool.drawRectImage(context, canvas, drawableIdFinal, rectF, rightSortImgWidth, rightSortImgHeight);
                                }
                            }
                        }
                    }
                    RectF rectF = new RectF(area.left,area.top,area.right-rightSortImgWidth,area.bottom);
                    DrawTool.drawRectText(canvas, val, paint, rectF, gravity, false);
                }
        }
    }

    private String getTitleName() {
        ComplexDataTemple.DataTemple dataTemple = null;
        ComplexDataTemple.DataTempleOne dataTempleOne = null;
        ComplexDataTemple.DataTempleTwo dataTempleTwo = null;
        if(space.obj instanceof ComplexDataTemple.DataTemple)
        {
            dataTemple = (ComplexDataTemple.DataTemple)space.obj;
        }
        else if(space.obj instanceof ComplexDataTemple.DataTempleOne)
        {
            dataTempleOne = (ComplexDataTemple.DataTempleOne)space.obj;
        }
        else if(space.obj instanceof ComplexDataTemple.DataTempleTwo)
        {
            dataTempleTwo = (ComplexDataTemple.DataTempleTwo)space.obj;
        }
        HashMap<Integer,String> hashMap = null;
        if(data instanceof DataReflect) {
            hashMap = (HashMap)((DataReflect) data).hashMapHead;
            if(hashMap.size() == 0)
            {
                hashMap = (HashMap)((DataReflect) data).hashMapHeadOne;
                if(hashMap.size() == 0)
                {
                    hashMap = (HashMap)((DataReflect) data).hashMapHeadTwo;
                }
            }
        }
        String val = "";
        if(hashMap != null) {
            if (dataTemple != null && hashMap.containsKey(dataTemple.id)) {
                val = hashMap.get(dataTemple.id);
            } else if (dataTempleOne != null && hashMap.containsKey(dataTempleOne.id)) {
                val = hashMap.get(dataTempleOne.id);
            } else if (dataTempleTwo != null && hashMap.containsKey(dataTempleTwo.id)) {
                val = hashMap.get(dataTempleTwo.id);
            }
        }
        return val;
    }
}
