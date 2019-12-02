package cn.wjf.approomorm.data;



public class Category {

    //沪市
    public final static long SH_INDEX = 0x00000001;//沪市-指数
    public final static long SH_A = 0x00000002;//沪市-A股
    public final static long SH_B = 0x00000004;//沪市-B股
    public final static long SH_JJ = 0x00000008;//沪市-基金
    public final static long SH_ZQ = 0x00000010;//沪市-债券
    public final static long SH_ZZ = 0x00000020;//沪市-转债
    public final static long SH_ST = 0x00000040;//沪市-ST股
    public final static long SH_HG = 0x00800000L;//沪市-回购
    public final static long SH_WIT = 0x00000100;//沪市-国债预发行
    public final static long SH_N = 0x00000200;//沪市-新股
    public final static long SH_OTHER = 0x00000400;//沪市-其他
    public final static long SH_Z = 0x00000800;//沪市-沪退市整理
    public final static long SH_OPTIONS = 0x00001000;//沪市-期权
    public final static long SH_CEF = 0x00002000;//沪市-封闭式基金
    public final static long SH_OEF = 0x00004000;//沪市-开放式基金
    public final static long SH_ETF = 0x00008000;//沪市-ETF式基金
    public final static long SH_LOF = 0x00010000;//沪市-LOF式基金
    public final static long SH_XGZ = 0x00020000;//沪市-沪小公债
    public final static long SH_HGT = 0x00040000;//沪市-沪港通中的沪股通
    public final static long SH_AH = 0x00080000;//沪市-沪市AH股
    public final static long SH_GPQQBD = 0x00100000;//沪市-沪市股票期权标的股
    public final static long SH_JJ_A = 0x00200000;//沪市-沪市分级A基金
    public final static long SH_JJ_B = 0x00400000;//沪市-沪市分级B基金
    public final static long SH_YXZZ = 0x01000000;//沪市-沪市有效转债
    public final static long SH_FXJS = 0x02000000;//沪市-沪市风险警示
    public final static long SH_CCB = 0x04000000;//科创板
    public final static long SH_HLT = 0x10000000;//沪伦通

    //深市
    public final static long SZ_INDEX = 0x00000001;//深市-指数
    public final static long SZ_A = 0x00000002;//深市-A股
    public final static long SZ_B = 0x00000004;//深市-B股
    public final static long SZ_JJ = 0x00000008;//深市-基金
    public final static long SZ_ZQ = 0x00000010;//深市-债券
    public final static long SZ_ZZ = 0x00000020;//深市-转债
    public final static long SZ_ST = 0x00000040;//深市-ST股
    public final static long SZ_HG = 0x80000000L;//深市-回购
    public final static long SZ_N = 0x00000100;//深市-新股
    public final static long SZ_OTHER = 0x00000200;//深市-其他
    public final static long SZ_ZXB = 0x00000400;//深市-深中小板888888
    public final static long SZ_CYB = 0x00000800;//深市-深创业板
    public final static long SZ_SXB = 0x00001000;//深市-深三板
    public final static long SZ_Z = 0x00002000;//深市-退市整理
    public final static long SZ_CEF = 0x00004000;//深市-封闭式基金
    public final static long SZ_OEF = 0x00008000;//深市-开放式基金
    public final static long SZ_ETF = 0x00010000;//深市-ETF式基金
    public final static long SZ_LOF = 0x00020000;//深市-LOF式基金
    public final static long SZ_XSBXYZL = 0x00040000;//深市-新三板协议转让
    public final static long SZ_XSBZSZL = 0x00080000;//深市-新三板做市转让
    public final static long SZ_XSBCXC = 0x00100000;//深市-新三板创新层
    public final static long SZ_XSBJCC = 0x00200000;//深市-新三板基础层
    public final static long SZ_XSBJJJL = 0x400000000L;//深市-新三板竞价转让
    public final static long SZ_A_TS = 0x00400000;//深市-两网及A股退市
    public final static long SZ_B_TS = 0x00800000;//深市-B股退市
    public final static long SZ_NZL = 0x01000000;//深市-新增及首日转让
    public final static long SZ_TS = 0x02000000;//深市-股转系统-两网及退市（三板）
    public final static long SZ_GPGS = 0x04000000;//深市-股转系统-挂牌公司
    public final static long SZ_SGT = 0x08000000;//深市-深港通中的深股通
    public final static long SZ_AH = 0x10000000;//深市-AH
    public final static long SZ_JJ_A = 0x20000000;//深市-分级A基金
    public final static long SZ_JJ_B = 0x40000000;//深市-分级B基金
    public final static long SZ_ZQZYSHG = 0x80000000;//深市-深市债券质押式回购
    public final static long SZ_XSB_INDEX = 0x100000000L;//深市新三板指数
    public final static long SZ_YXZZ = 0x200000000L;//深市-深市有效转债

    //板块
    public final static long BK_GN = 0x00000001;//概念板块
    public final static long BK_HY = 0x00000002;//行业板块
    public final static long BK_DQ = 0x00000004;//地区板块
    public final static long BK_ZGN = 0x00000008;//子概念板块
    public final static long BK_ZHY = 0x00000010;//子行业板块

    //股指国债期货
    public final static long GZGZQH_INDEX_QH = 0x00000001;//股指期货
    public final static long GZGZQH_ND_QH = 0x00000002;//国债期货
    public final static long GZGZQH_HS300 = 0x00000008;    //以沪深300为标的的期货，即IF
    public final static long GZGZQH_SZ50 = 0x00000010;    //以上证50为标的的期货，即IH
    public final static long GZGZQH_ZZ500 = 0x00000020;    //以中证500为标的的期货，即ICz

    //港股
    public final static long HK_ZB = 0x00000001;//港股-香港主板
    public final static long HK_SH = 0x00000002;//港股-沪市H股
    public final static long HK_AH = 0x00000004;//港股-AH股对应的H股
    public final static long HK_HGT = 0x00000008;//港股-沪港通中的港股通
    public final static long HK_SGT = 0x00000010;//港股-深港通中的港股通
    public final static long HK_INDEX = 0x00000020;//港股-指数

    //环球指数
    public final static long GLOBAL_MZ_INDEX = 0x00000001;//环球指数-美洲市场指数
    public final static long GLOBAL_OZ_INDEX = 0x00000002;//环球指数-欧洲市场指数
    public final static long GLOBAL_YT_INDEX = 0x00000004;//环球指数-亚太市场指数

    //国际商品期货
    public final static long GJSPQH_NY = 0x00000001;//国际商品期货-能源
    public final static long GJSPQH_GJS = 0x00000002;//国际商品期货-贵金属
    public final static long GJSPQH_JBJS = 0x00000004;//国际商品期货-基本金属
    public final static long GJSPQH_NCP = 0x00000008;//国际商品期货-农产品

    //美股中国概念股
    public final static long USA_CN_NYSE = 0x00000001;//美股中国概念股-纽交所上市
    public final static long USA_CN_NASDAQ = 0x00000002;//美股中国概念股-纳斯达克上市
    public final static long USA_CN_MJS = 0x00000004;//美股中国概念股-美交所上市
    public final static long USA_CN_PINK = 0x00000008;//美股中国概念股-PINK市场
    public final static long USA_CN_OTC = 0x00000010;//美股中国概念股-OTC市场
    public final static long USA_CN_DJIA = 0x00000020;//美股中国概念股-道琼斯指数成分股
    public final static long USA_CN_ZGG = 0x00000040;//美股中国概念股-中概股（中概股二级列表使用该Category）

    //国际汇率
    public final static long GJHL = 0x00000001;//国际汇率

    //人民币牌价
    public final static long RMB_PJ = 0x00000001;//人民币牌价

    public Category() {
    }
}
