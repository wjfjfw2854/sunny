package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.space;

public class SmallSpace {

    public Object obj;
    public Class type;
    public float width;
    public Object[] args;
    public int smallIndex;

    public SmallSpace(Object obj, Class type, float width)
    {
        this.obj = obj;
        this.type = type;
        this.width = width;
    }

    public SmallSpace(Object obj, Class type, float width,Object[] args)
    {
        this.obj = obj;
        this.type = type;
        this.width = width;
        this.args = args;
    }
}
