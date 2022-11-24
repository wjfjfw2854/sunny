package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.datatemple;

import java.io.Serializable;

public class ComplexDataTemple implements Serializable,Cloneable {
    public int id;
    public String name;
    public String value;
    public ComplexDataTemple(int id,String name)
    {
        this.id = id;
        this.name = name;
    }
//    public static final ComplexDataTemple NAEM = new ComplexDataTemple(0,"名称");
//    public static final ComplexDataTemple ZDF = new ComplexDataTemple(1,"今日涨幅");
//    public static final ComplexDataTemple YK = new ComplexDataTemple(2,"盈亏");
//    public static final ComplexDataTemple YKL = new ComplexDataTemple(3,"盈亏率");
//    public static final ComplexDataTemple CBJ = new ComplexDataTemple(4,"成本价");
//    public static final ComplexDataTemple ZXJ = new ComplexDataTemple(5,"最新价");
//    public static final ComplexDataTemple CCGS = new ComplexDataTemple(6,"持仓股数");
//    public static final ComplexDataTemple KMGS = new ComplexDataTemple(7,"可卖股数");
    public enum DataTemple{//模拟炒股账号
        NAEM(0,"名称"),
        ZDF(1,"今日涨幅"),
        YK(2,"盈亏"),
        YKL(3,"盈亏率"),
        CBJ(4,"成本价"),
        ZXJ(5,"最新价"),
        CCGS(6,"持仓股数"),
        KMGS(7,"可卖股数"),
        KMGSZJ1(8,"可卖股数8"),
        KMGSZJ2(9,"可卖股数9");
        public int id;
        public String name;
        DataTemple(int id,String name)
        {
            this.id = id;
            this.name = name;
        }
    }
    public enum DataTempleOne{//模拟炒股账号
        NAEM(0,"标题头0"),
        ZDF(1,"标题头1"),
        YK(2,"标题头2"),
        YKL(3,"标题头3"),
        CBJ(4,"标题头4"),
        ZXJ(5,"标题头5"),
        ZXJ1(6,"标题头6"),
        ZXJ2(7,"标题头7"),
        ZXJ3(8,"标题头8");
        public int id;
        public String name;
        DataTempleOne(int id,String name)
        {
            this.id = id;
            this.name = name;
        }
    }

    public enum DataTempleTwo{//模拟炒股账号
        NAEM(0,"标题头0"),
        ZDF(1,"标题头1"),
        YK(2,"标题头2"),
        YKL(3,"标题头3"),
        CBJ(4,"标题头4"),
        ZXJ(5,"标题头5"),
        ZXJ1(6,"标题头6"),
        ZXJ2(7,"标题头7"),
        ZXJ3(8,"标题头8");
        public int id;
        public String name;
        DataTempleTwo(int id,String name)
        {
            this.id = id;
            this.name = name;
        }
    }
}
