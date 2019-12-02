package cn.wjf.approomorm.data;

import android.text.TextUtils;

import java.io.Serializable;

import static cn.wjf.approomorm.data.GoodsParams.*;

public class Field implements Serializable, Cloneable {
    public int param = -99999;
    public String color;
    public String bgColor;
    public String format;
    public String name = "";
    public String alias;

    private Field(int param, String color, String bgColor, String format, String name) {
        this.param = param;
        this.color = color;
        this.bgColor = bgColor;
        this.format = format;
        this.name = name;
    }

    public Field alias(String alias) {
        try {
            Field field = (Field) clone();
            field.alias = alias;
            return field;
        } catch (Exception e) {

        }
        return this;
    }

    public String getFieldName() {
        if (!TextUtils.isEmpty(alias)) {
            return alias;
        }
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Field f = (Field) obj;
        return (param + name).equals(f.param + f.name);
    }

    @Override
    public int hashCode() {
        return (param + name).hashCode();
    }

    public static final Field NAME = new Field(STOCK_NAME, "", "", "", "名称");
    public static final Field CLOSE = new Field(PRE_CLO_PRC, "", "", "formatPrice", "昨收");
    public static final Field PRICE = new Field(CLO_PRC, "getColorByZD", "", "formatPrice", "最新");
    public static final Field CODE = new Field(STOCK_CODE_NAME, "", "", "", "代码");
    public static final Field ZF = new Field(CHANGE_RATIO, "getColorByZD", "getZDFBgColor", "formatZDF", "涨跌幅");
    public static final Field ZD = new Field(CHANGE_PRC, "getColorByZD", "", "formatPrice", "涨跌");
    public static final Field HS = new Field(TURNOVER_RATIO, "getYellowColor", "", "formatZDF", "换手");
    public static final Field HS5 = new Field(TURNOVER_RATIO_OF5, "getYellowColor", "", "formatZDF", "5日换");
    public static final Field ZS = new Field(VOLUME, "getYellowColor", "", "formatVolume", "总手");
    public static final Field VOL = new Field(VOLUME, "", "", "formatVolume", "成交量");
    public static final Field ZJE = new Field(AMOUNT, "getBlueColor", "", "formatAmount", "总金额");
    public static final Field ZLJM = new Field(BIG_ORDER_INFLOW_AMT, "getColorByPoM", "", "formatJL", "主力买");
    public static final Field HIGH = new Field(HIGH_PRC, "getColorByLastClose", "", "formatPrice", "最高");
    public static final Field LOW = new Field(LOW_PRC, "getColorByLastClose", "", "formatPrice", "最低");
    public static final Field LB = new Field(QUANTITY_RELATIVE_RATIO, "getYellowColor", "", "formatLB", "量比");
    public static final Field JM5 = new Field(BIG_ORDER_INFLOW_AMT5, "getColorByPoM", "", "formatAmount", "5日净买");
    public static final Field DDBL = new Field(BIG_ORDER_RATIO, "getColorByPoM", "", "formatSJL", "大单比率");
    public static final Field ZHENFU = new Field(AMPLITUDE, "getColorByPoM", "", "formatZDF", "振幅");
    public static final Field ZHANGSU = new Field(SPEED_RATIO, "getColorByPoM", "", "formatZDF", "涨速");
    public static final Field OPEN = new Field(OPEN_PRC, "getColorByLastClose", "", "formatPrice", "开盘");
    public static final Field SYLTTM = new Field(PE_RATIO, "getColorByPoM", "", "formatSYL", "市盈率(TTM)");
    public static final Field SJL = new Field(PB_RATIO, "getColorByPoM", "", "formatSJL", "市净率");
    public static final Field SYL = new Field(PE_RATIO_STATIC, "getColorByPoM", "", "formatSYL", "市盈率");
    public static final Field LTSZ = new Field(TRADABLE_MARKET_VALUE, "getBlueColor", "", "formatAmount", "流通市值");
    public static final Field LTGS = new Field(AVAILABLE_TRADABLE_SHARES, "getBlueColor", "", "formatAmount", "有效流通股");
    public static final Field ZSZ = new Field(TOTAL_MARKET_VALUE, "getYellowColor", "", "formatAmount", "总市值");
    public static final Field MGJZC = new Field(NET_ASSET_PER_SHARE, "getColorByPoM", "", "formatPrice", "每股净资产");
    public static final Field ZF5 = new Field(CHANGE_RATIO_OF5, "getColorByPoM", "", "formatZDF", "5日涨幅");
    public static final Field ZF10 = new Field(CHANGE_RATIO_OF10, "getColorByPoM", "", "formatZDF", "10日涨幅");
    public static final Field YZF = new Field(CHANGE_RATIO_MONTH, "getColorByPoM", "", "formatZDF", "月涨幅");
    public static final Field BNZF = new Field(CHANGE_RATIO_HALFYEAR, "getColorByPoM", "", "formatZDF", "半年涨幅");
    public static final Field NZF = new Field(CHANGE_RATIO_YEAR, "getColorByPoM", "", "formatZDF", "年涨幅");
    public static final Field SZJS = new Field(UP_STOCK_NUM, "getRedColor", "", "getValue", "上涨家数");
    public static final Field PPJS = new Field(PLAT_STOCK_NUM, "getNormalColor", "", "getValue", "平盘家数");
    public static final Field XDJS = new Field(DOWN_STOCK_NUM, "getGreenColor", "", "getValue", "下跌家数");
    public static final Field QXZ = new Field(FUTURES_GOODS_BALANCE, "getColorByPoM", "", "formatPrice", "期现差");
    public static final Field XIANSHOU = new Field(CUR_VOL, "", "", "formatVolume", "现手");
    public static final Field OPENINTEREST = new Field(HOLD_VOL, "", "", "formatVolume", "持仓");
    public static final Field RZC = new Field(GoodsParams.HOLD_VOL_INCREASE_DAY, "", "", "formatVolume", "日增仓");
    public static final Field HYJZ = new Field(CONTRACT_VALUE, "", "", "formatDivide10000Keep2", "合约价值<br>(万元/手)");
    public static final Field SY = new Field(STATIC_EARNINGS_PER_SHARES, "getColorByPoM", "", "formatPrice", "每股收益");
    public static final Field RZRQ = new Field(MARGIN_TRADING_FLAG, "", "", "", "融资融券");
    public static final Field CUR_OI = new Field(HOLD_VOL_INCREASE, "", "", "", "增仓");
    public static final Field CUR_JICHA = new Field(JICHA, "getColorByPoM", "", "formatPrice", "基差");
    public static final Field JICHA_PRE = new Field(PRE_JICHA, "getColorByPoM", "", "formatZDF", "差幅");
    public static final Field SETTLEMENT_PRC = new Field(PRE_SETTL_PRC, "", "", "formatPrice", "前结算");
    public static final Field SETTLEMENT = new Field(SETTL_PRC, "getColorByZD", "", "formatPrice", "结算");
    public static final Field BK_LZGG = new Field(RISE_HEAD_NAME, "", "", "", "领涨个股");
    public static final Field BK_LZGG_ZDF = new Field(RISE_HEAD_ZDF, "", "", "formatZDF", "领涨个股涨幅");
    public static final Field BK_ZFFRIST3 = new Field(BOARD_FRONT_ZF_STOCK, "", "", "", "板块涨幅前三股票");
    public static final Field ZLJM5 = new Field(BIG_ORDER_INFLOW_AMT5, "getColorByPoM", "", "formatJL", "5日资金净流");
    public static final Field ZLZC10 = new Field(BIG_ORDER_INFLOW_DAYS10, "", "", "getValue", "10日主力增仓");
    public static final Field ZLZC20 = new Field(BIG_ORDER_INFLOW_DAYS20, "", "", "getValue", "20日主力增仓");
    public static final Field ZLQM = new Field(BIG_ORDER_INFLOW_DUR_TODAY, "", "", "getValue", "连续买");
    public static final Field PJ = new Field(GOODS_DOCTOR_FLAG, "", "", "getValue", "评级");
    public static final Field TP = new Field(CLO_PRC_BF4, "", "", "getValue", "停牌标记");
    public static final Field PRE_HOLD = new Field(PRE_HOLD_AMT, "", "", "getValue", "昨持仓");
    public static final Field ZFX = new Field(TOTAL_SHARE_CAPITAL, "", "", "formatCapital", "总股本");
    public static final Field ZT = new Field(LIM_UP_PRC, "getColorByLastClose", "", "formatPrice", "涨停价");
    public static final Field DT = new Field(LIM_DOWN_PRC, "getColorByLastClose", "", "formatPrice", "跌停价");
    public static final Field AVERAGE = new Field(AVEPRICE, "getColorByLastClose", "", "formatPrice", "均价");
    public static final Field WEIBI = new Field(COMMITTEE, "getColorByPoM", "", "formatZDF", "委比");
    public static final Field TRADETIME = new Field(TRADE_DATE, "", "", "", "交易日期");
    public static final Field UPSTOCKNUM = new Field(UP_STOCK_NUM, "", "", "", "涨盘家数");
    public static final Field PLATSTOCKNUM = new Field(PLAT_STOCK_NUM, "", "", "", "平盘家数");
    public static final Field DOWNSTOCKNUM = new Field(DOWN_STOCK_NUM, "", "", "", "跌盘家数");
    public static final Field NEIPAN = new Field(IN_VOL, "getGreenColor", "", "formatVolume", "内盘");
    public static final Field WAIPAN = new Field(EX_VOL, "getRedColor", "", "formatVolume", "外盘");
    public static final Field WEICHA = new Field(COMMISSION, "getColorByPoM", "", "formatDiv100", "委差");
    public static final Field CPX_DAYS = new Field(CPX_DUR_DAYS, "getColorByPoM", "", "formatCPX", "BS天数");
    public static final Field KP = new Field(OI_TYPE, "getColorByPoM", "", "formatKP", "开平");


    public static final Field JB = new Field(NEEQ_LEVEL, "getNormalColor", "", "getValue", "级别");
    public static final Field ZR = new Field(NEEQ_EXCHANGE, "getNormalColor", "", "getValue", "转让");
    public static final Field ZSS = new Field(MARKET_MAKER_NUM, "getNormalColor", "", "getValue", "做市商");
    public static final Field MMCJ = new Field(BID_ASK_SPREAD, "getNormalColor", "", "formatPrice", "买卖差价");
    public static final Field MSGS = new Field(HANDS_OF_SHARES, "getNormalColor", "", "getValue", "每手股数");
    public static final Field LTGB = new Field(CAPITAL, "getNormalColor", "", "formatCapital", "流通股本");
    public static final Field LX = new Field(NEEQ_TYPE, "getNormalColor", "", "getValue", "类型");
    public static final Field WEEK52HIGHPRICE = new Field(WEEK_52_HIGH_PRICE, "getColorByLastClose", "", "formatPrice", "52周最高");
    public static final Field WEEK52LOWPRICE = new Field(WEEK_52_LOW_PRICE, "getColorByLastClose", "", "formatPrice", "52周最低");
    public static final Field YJL = new Field(PREMIUM_RATIO, "getColorByPoM", "", "formatValueRatio", "溢价率");
    public static final Field JGR = new Field(DELIVERY_DATE, "getNormalColor", "", "formatDateY_M_D", "交割日");
    public static final Field JZ = new Field(NET_VALUE, "getNormalColor", "", "formatValueDivide10000", "净值");
    public static final Field MJJZ = new Field(PARENT_FUND_NET_VALUE, "getNormalColor", "", "formatValueDivide10000", "母基净值");
    public static final Field ZTYJ = new Field(OVERALL_PREMIUM_RATIO, "getColorByPoM", "", "formatValueRatio", "整体溢价");
    public static final Field CKJSZF = new Field(TARGET_STOCK_CHANGE_RATIO, "getColorByPoM", "", "formatZDF", "参考指数涨");
    public static final Field FEBL = new Field(AB_SHARE_RATIO, "getNormalColor", "", "formatAB", "份额比例");
    public static final Field XZMJXD = new Field(UNDER_PARENT_FUND_FALL, "getNormalColor", "", "formatValueRatio", "下折母基需跌");
    public static final Field SZMJXZ = new Field(UP_PARENT_FUND_RISE, "getNormalColor", "", "formatValueRatio", "上折母基需涨");
    public static final Field JJGM = new Field(FUND_SIZE, "getNormalColor", "", "formatFundSize", "基金规模");
    public static final Field JJLX = new Field(FUND_TYPE, "getNormalColor", "", "getValue", "基金类型");
    public static final Field CLR = new Field(FUND_ESTABLISH_DATE, "getNormalColor", "", "formatInfoDate", "成立日");
    public static final Field GLR = new Field(FUND_MANAGER, "getNormalColor", "", "getValue", "管理人");
    public static final Field JYYHB = new Field(FUND_NEARLY_1M_RETURN, "getColorByPoM", "", "formatZDF", "近1月");
    public static final Field JSYHB = new Field(FUND_NEARLY_3M_RETURN, "getColorByPoM", "", "formatZDF", "近3月");
    public static final Field JYNHB = new Field(FUND_NEARLY_1Y_RETURN, "getColorByPoM", "", "formatZDF", "近1年");
    public static final Field JSNHB = new Field(FUND_NEARLY_3Y_RETURN, "getColorByPoM", "", "formatZDF", "近3年");
    public static final Field ZXSSDW = new Field(MIN_CREATION_UNIT, "getNormalColor", "", "formatZXSS", "最小申赎单位");
    public static final Field FE = new Field(FUND_TOTAL_SHARES, "getNormalColor", "", "formatCapital", "份额");
    public static final Field FEZZ = new Field(FUND_TOTAL_SHARES_GROWTH, "getNormalColor", "", "formatCapital", "份额增长");
    public static final Field HMTIME = new Field(GoodsParams.HMTIME, "getNormalColor", "", "", "时间");
    public static final Field RMB_RATE = new Field(GoodsParams.BUYING_RATE, "getNormalColor", "", "formatPrice", "现汇买入价");
    public static final Field RMB_BUYING_RATE = new Field(GoodsParams.CASH_BUYING_RATE, "getNormalColor", "", "formatPrice", "现钞买入价");
    public static final Field HG_EXPIRE = new Field(GoodsParams.STATIC_OCCUPIED_DAYS, "", "", "", "回购期限");
    public static final Field HG_EXPIRE_TENTH_MILLION = new Field(GoodsParams.EARNINGS_PER100000SHARES, "getNormalColor", "", "formatPrice", "10万收益");
    public static final Field HG_EXPIRE_THOUSAND = new Field(GoodsParams.EARNINGS_PER_ONE_THOUSAND, "getNormalColor", "", "formatPrice", "1千收益");
    public static final Field HG_EXPIRE_PER_TEN_THOUSAND = new Field(GoodsParams.DAILY_EARNINGS_PER_TEN_THOUSAND, "", "", "", "每万元日收益");
    public static final Field HG_EXPIRE_DAYS = new Field(GoodsParams.REPO_DAYS, "getNormalColor", "", "formatDays", "计息天数");
    public static final Field HG_EXPIRE_DAYS_USED = new Field(GoodsParams.FUNDS_AVAILABLE_DATE, "getNormalColor", "", "formatFundsAvailableDate", "资金可用日期");

    public static final Field FOUND_FJB_RECK = new Field(GoodsParams.VALUATIONS, "", "", "", "估值");
    public static final Field FOUND_CONTRAST_INDEX = new Field(GoodsParams.TARGET_STOCK_NAME, "", "", "getValue", "参考指数");
    public static final Field FOUND_PRICE_LEVER = new Field(GoodsParams.LEVER_PRICE, "", "", "formatDivide10000Keep3", "价格杠杆");
    public static final Field FOUND_MOTHER_NAME = new Field(GoodsParams.PARENT_FUND_NAME, "", "", "getValue", "母基名称");

    public static final Field HOLD_COAST = new Field(GoodsParams.HOLD_COAST, "getHoldColor", "", "formatPrice", "持有成本");
    public static final Field HOLD_TODAY_PROFIT = new Field(GoodsParams.HOLD_TODAY_PROFIT, "getSignColor", "", "formatPrice", "今日盈亏");
    public static final Field HOLD_NUMBER = new Field(GoodsParams.HOLD_NUMBER, "getYellowColor", "", "getValue", "持有股数");
    public static final Field HOLD_SZ = new Field(GoodsParams.HOLD_SZ, "getYellowColor", "", "formatPrice", "市值");
    public static final Field HOLD_ZYK = new Field(GoodsParams.HOLD_ZYK, "getSignColor", "", "formatPrice", "总盈亏额");
    public static final Field HOLD_ZYKB = new Field(GoodsParams.HOLD_ZYKB, "getSignColor", "", "formatZDF", "总盈亏比");
    public static final Field YSZZL = new Field(GoodsParams.MAIN_REVENUE_GROWTH_RATE, "", "", "formatZDF", "营业收入增长率");
    public static final Field VOLUME_PANHOU = new Field(GoodsParams.VOLUME_AHT, "", "", "formatVolume", "盘后量");
    public static final Field AMOUNT_PANHOU = new Field(GoodsParams.AMOUNT_AHT, "", "", "formatAmount", "盘后额");
    public static final Field BISHU_PANHOU = new Field(GoodsParams.TRADE_AHT, "", "", "getValue", "盘后交易笔数");
    public static final Field YS_PROFIT_ZZL = new Field(GoodsParams.MAIN_PROFIT_GROWTH_RATE, "", "", "formatZDF", "营业利率增长率");
    public static final Field STATIC_INDUSTRY = new Field(GoodsParams.INDUSTRY_BELONG,"","","","所属行业");

    public static final Field PATTERNERMAKE_ZT_TIME = new Field(GoodsParams.PATTERNERMAKE_ZT_TIME,"","","formatMinuteTime","涨停时间");
    public static final Field PATTERNERMAKE_FDJE = new Field(GoodsParams.PATTERNERMAKE_FDJE,"","","formatAmount","封单金额");
    public static final Field PATTERNERMAKE_FB_TIME = new Field(GoodsParams.PATTERNERMAKE_FB_TIME,"","","formatMinuteTime","封板时间");
    public static final Field PATTERNERMAKE_KBZF = new Field(GoodsParams.PATTERNERMAKE_KBZF,"getColorByPoM","","formatYybRation","开板涨幅");
    public static final Field PATTERNERMAKE_JDZF = new Field(GoodsParams.PATTERNERMAKE_JDZF,"getColorByPoM","","formatYybRation","阶段涨幅");
    public static final Field PATTERNERMAKE_JJZF = new Field(GoodsParams.PATTERNERMAKE_JJZF,"getColorByPoM","","formatZDF","竞价涨幅");
    public static final Field PATTERNERMAKE_ZTWME = new Field(GoodsParams.PATTERNERMAKE_ZTWME,"","","formatAmount","涨停委买额");
    public static final Field PATTERNERMAKE_DTWME = new Field(GoodsParams.PATTERNERMAKE_DTWME,"","","formatAmount","跌停委卖额");
    public static final Field PATTERNERMAKE_FDB = new Field(GoodsParams.PATTERNERMAKE_FDB,"","","","封单比");
    public static final Field PATTERNERMAKE_HOTPOINT = new Field(GoodsParams.PATTERNERMAKE_HOTPOINT,"","","getValue","热点名称");
    public static final Field PATTERNERMAKE_HEADER_DRAGON = new Field(GoodsParams.PATTERNERMAKE_HEADER_DRAGON,"","","","涨停龙头");
    public static final Field HEARD_DRAGON_CODE = new Field(GoodsParams.HEARD_DRAGON_CODE,"","","","涨停龙头代码");
    public static final Field HEARD_DRAGON_ID = new Field(GoodsParams.HEARD_DRAGON_ID,"","","","涨停龙头ID");
    public static final Field TAGS_STYLE = new Field(GoodsParams.TAGS_STYLE,"","","","标签");
    public static final Field TAGS_COLOR_FLAG = new Field(GoodsParams.TAGS_COLOR_FLAG,"","","","标签颜色标记");
    public static final Field GOODS_DESCRIPTION = new Field(GoodsParams.GOODS_DESCRIPTION,"","","","描述");
    public static final Field LINKBOARD3TAG = new Field(GoodsParams.LINKBOARD3TAG,"","","","3日");
    public static final Field LONGHUJME = new Field(GoodsParams.LONGHUJME,"getColorByPoM","","formatAmount","净买额");
    public static final Field LONGHUTWOBOARD = new Field(GoodsParams.LONGHUTWOBOARD,"","","","2连板");
    public static final Field LONGHUYYB = new Field(GoodsParams.LONGHUYYB,"","","","国家队");
    public static final Field LONGHUISALL = new Field(GoodsParams.LONGHUISALL,"","","","是否全部");//龙虎榜中是否个股全部标记
    public static final Field SIMILARKSHRINKFLAG = new Field(GoodsParams.SIMILARKSHRINKFLAG,"","","","相似K线点击收缩标记");//相似K线点击收缩标记
    public static final Field SIMILARKSCORE = new Field(GoodsParams.SIMILARKSCORE,"getC7","","formatZDF","相似度");
    public static final Field SIMILARKLASTDAY = new Field(GoodsParams.SIMILARKLASTDAY,"","","","k线最后日期");

    //-------------以下是北上南下资金特殊用到，其它地方禁止用-------------------start------------------------------
    public static final Field NORTHLY_SOUTHLY_BUY = new Field(NorthSouthCapitalParams.BUY,"","","formatAmount","买入额");
    public static final Field NORTHLY_SOUTHLY_SALE = new Field(NorthSouthCapitalParams.SALE,"","","formatAmount","卖出额");
    public static final Field NORTHLY_SOUTHLY_NET = new Field(NorthSouthCapitalParams.NET,"getColorByPoM","","formatAmount","净流入");
    //-------------以下是北上南下资金特殊用到，其它地方禁止用-------------------end--------------------------------

    //-------------股票池用到字段------------------------------------------------start-----------------------------
    public static final Field GPC_TIME = new Field(GoodsParams.HAOGU_RXR,"getNormalColor","","formatGPCTime","最近入选");
    public static final Field GPCBOTTOMFLOAG = new Field(GoodsParams.GPCBOTTOMFLOAG,"","","","当日入选股票");
    //-------------股票池用到字段------------------------------------------------end-------------------------------
}
