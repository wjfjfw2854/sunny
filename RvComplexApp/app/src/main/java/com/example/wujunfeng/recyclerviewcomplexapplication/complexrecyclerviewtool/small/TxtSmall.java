package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.small;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.ComplexDataTemple;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple.DataReflect;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space.SmallSpace;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.DrawTool;

import java.util.HashMap;

public class TxtSmall extends Small{
    private Paint paint;
    private Object data;
    private RectF area;

    public TxtSmall(Context context, SmallSpace smallSpace) {
        super(context, smallSpace);
        init();
    }

    private void init()
    {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(DrawTool.dp2Px(context,16));
        paint.setColor(0xFF252525);
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

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(data != null&&space != null) {
//            if(data instanceof MncgUtils.SecuShareItem) {
//                stockName.setText(data.stockName);
//                stockCode.setText(data.stockCode);
//                zdf.setText(data.zdf);
//                zdf.setTextColor(data.zdfColor);
//                yk.setText(data.yk);
//                yk.setTextColor(data.ykColor);
//                ykl.setText(data.ykl);
//                ykl.setBackgroundResource(data.yklBgRes);
//                costPrice.setText(data.costPrice);
//                newPrice.setText(data.newPrice);
//                secuShareNum.setText(data.secuShareNum);
//                availableNum.setText(data.availableNum);
//            }
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
                hashMap = (HashMap)((DataReflect) data).hashMapItem;
                if(hashMap.size() == 0)
                {
                    hashMap = (HashMap)((DataReflect) data).hashMapItemOne;
                    if(hashMap.size() == 0)
                    {
                        hashMap = (HashMap)((DataReflect) data).hashMapItemTwo;
                    }
                }
            }
            if(hashMap != null) {
                String val = "";
                if(dataTemple != null && hashMap.containsKey(dataTemple.id)) {
                    val = hashMap.get(dataTemple.id);
                }
                else if(dataTempleOne != null && hashMap.containsKey(dataTempleOne.id)) {
                    val = hashMap.get(dataTempleOne.id);
                }
                else if(dataTempleTwo != null && hashMap.containsKey(dataTempleTwo.id)) {
                    val = hashMap.get(dataTempleTwo.id);
                }
                if(!TextUtils.isEmpty(val)) {
                    int gap = 0;
                    RectF rectF = new RectF(area.left,area.top,area.right,area.bottom);
                    int colorContent = 0xff252525;//默认白版字体颜色
                    int gravity = DrawTool.CENTER;
                    Object[] args = space.args;
                    if (args != null) {
                        if (args.length > 0) {
                            Object obj = args[0];
                            if (obj instanceof Integer) {
                                gravity = ((Integer) obj).intValue();
                            }
                        }
                        if (args.length > 4) {
                            Object obj = args[4];
                            if (obj != null && obj instanceof int[][]) {
                                int[][] bgs = (int[][]) obj;
                                int len = bgs.length;
                                float valF = .1f;
                                try {
                                    valF = Float.parseFloat(val);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (len > 1) {
                                    int[] bg = bgs[0];
                                    if (bg != null && bg.length > 2) {
                                        if (valF > 0) {
                                            paint.setColor(bg[0]);
                                        } else if (valF < 0) {
                                            paint.setColor(bg[1]);
                                        } else {
                                            paint.setColor(bg[2]);
                                        }
                                    }
                                    int[] gapOut = bgs[1];
                                    if (gapOut != null && gapOut.length > 0) {
                                        gap = gapOut[0];
                                    }
                                }
                                if (len > 2) {
                                    int[] colorFont = bgs[2];
                                    colorContent = colorFont[0];//白版颜色 colorFont[1]-黑版字体颜色
                                }
                                rectF = new RectF(area.left+gap,area.top+gap,area.right-gap,area.bottom-gap);
                                DrawTool.drawBg(canvas,paint,rectF);
                            }
                        }
                    }
                    paint.setColor(colorContent);
                    DrawTool.drawRectText(canvas, val, paint, rectF, gravity, true);
                }
            }
        }
    }
}
