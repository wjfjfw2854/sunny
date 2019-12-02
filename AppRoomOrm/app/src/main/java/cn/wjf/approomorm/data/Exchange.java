package cn.wjf.approomorm.data;


public class Exchange {
    //环球指数,国际商品期货,美股中国概念股返回的是小分类如：6703，6704，比较第一位即可
    public final static int SH = 0;//沪市
    public final static int SZ = 1;//深市
    public final static int BK = 2;//板块
    public final static int GZ_GZ_QH = 4;//股指国债期货
    public final static int HK_STOCKS = 5;//港股
    public final static int GLOBAL_INDEX = 6;//环球指数
    public final static int GN_SPQH = 7;//国内商品期货
    public final static int GJ_SPQH = 8;//国际商品期货
    public final static int USA_CN_STOCKS = 9;//美股中国概念股
    public final static int GJ_HL = 11;//国际汇率
    public final static int RMB_PJ= 12;//人民币牌价
}
