package cn.wjf.approomorm.data;


public interface QuoteType {
    int TYPE_A = 1;//A股
    int TYPE_INDEX = 2;//指数
    int TYPE_BK = 3;//板块（行业、概念、地区）
    int TYPE_FJ_JJ = 4;//分级基金（分级A、分级B）
    int TYPE_ETF = 5;//ETF基金
    int TYPE_LOF_CET = 6;//LOF、封闭基金
    int TYPE_ZQ = 7;//债券（上证债券、深证债券）
    int TYPE_KZZ = 8;//可转债
    int TYPE_HG = 9;//上证回购、深证回购
    int TYPE_GLOBALINDEX = 10;//道琼斯
    int TYPE_ZGG = 11;//中概股
    int TYPE_GZQH = 12;//股指期货
    int TYPE_WH = 13;//外汇
    int TYPE_GJQH = 14;//国际期货
    int TYPE_OTHER = 15;//其它

    int TYPE_XSB = -101;
    int TYPE_DJI = -102;
    int TYPE_NASDAQ = -103;
    int TYPE_HSI = -104;
    int TYPE_HK_STOCK = -105;//港股(不含指数)
    int TYPE_SB = -106;//三板
    int TYPE_B = -107;//B股
}
