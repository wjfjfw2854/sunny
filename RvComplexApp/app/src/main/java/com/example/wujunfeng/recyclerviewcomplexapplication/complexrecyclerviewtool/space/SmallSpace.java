package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space;

public class SmallSpace {

    public Object obj;
    public Class type;
    public float width;
    public Object[] args;
    public int smallIndex;

    private boolean needDealWidthSpecial;//宽度需要特殊处理

    public SmallSpace(Object obj, Class type, float width)
    {
        this.obj = obj;
        this.type = type;
        this.width = width;
    }

    public SmallSpace(Object obj, Class type,boolean needDealWidthSpecial, float width,Object[] args)
    {
        this.obj = obj;
        this.type = type;
        this.needDealWidthSpecial = needDealWidthSpecial;
        this.width = width;
        this.args = args;
    }

    public boolean isNeedDealWidthSpecial() {
        return needDealWidthSpecial;
    }
}
