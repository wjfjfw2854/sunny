package cn.wjf.approomorm.data;

public class GoodsParams {

    public static final int STOCK_NAME = 0;// string	                股票名称
    public static final int STOCK_CODE_NAME = 1;//  int32   	        股票代码名称
    public static final int TRADE_DATE = 2;//  int32   	                交易日期：yyyymmdd
    public static final int OPEN_PRC = 3;//  int32   	                开盘价
    public static final int HIGH_PRC = 4;//  int32   	                最高价
    public static final int LOW_PRC = 5;//  int32   	                最低价
    public static final int CLO_PRC = 6;//  int32   	                成交价（最新价）
    public static final int VOLUME = 7;// int64	                        成交量=总手
    public static final int AMOUNT = 8;// int64	                        成交额=总金额
    public static final int CUR_VOL = 9;//int64                         现手(当前成交的笔数)
    public static final int IN_VOL = 10;//int64                         内盘

    public static final int BUY_ORDER_VOL_M = 12;//int64                买量（中）
    public static final int BUY_ORDER_VOL_L = 13;//int64                买量（大）
    public static final int BUY_ORDER_VOL_XL = 14;//int64               买量（特大）

    public static final int sell_order_vol_m = 16;//int64               卖量（中）
    public static final int sell_order_vol_l = 17;//int64               卖量（大）
    public static final int sell_order_vol_xl = 18;//int64              卖量（特大）
    public static final int OVERALL_PREMIUM_RATIO = 19;//int64          整体溢价
    public static final int BUY_ORDER_AMT_M = 20;//int64                买额（中）
    public static final int BUY_ORDER_AMT_L = 21;//int64                买额（大）
    public static final int BUY_ORDER_AMT_XL = 22;//int64               买额（特大）
    public static final int SELL_ORDER_AMT_S = 23;//int64               卖额（小）
    public static final int SELL_ORDER_AMT_M = 24;//int64               卖额（中）
    public static final int SELL_ORDER_AMT_L = 25;//int64               卖额（大）
    public static final int sell_order_amt_xl = 26;//int64              卖额（特大）
    public static final int PE_RATIO_STATIC = 27;//int32                静态市盈率（对应市盈（动））
    public static final int BUY_PRC1 = 28;//int32                       买一价

    public static final int CHANGE_RATIO_MONTH = 32;//int32             月涨幅
    public static final int CHANGE_RATIO_HALFYEAR = 33;//int32          半年涨幅
    public static final int CHANGE_RATIO_YEAR = 34;//int32              年涨幅
    public static final int FUTURES_GOODS_BALANCE = 35;//int32          期现差【股指期货】

    public static final int SELL_PRC1 = 38;//int32                      卖一价

    public static final int CONTRACT_VALUE = 40;//int32                 合约价值【股指期货】
    public static final int BIG_AMOUNT_3MINUTES = 41;//int64            3分钟大户（游资）资金净流（买）
    public static final int SUPER_AMOUNT_3MINUTES = 42;//int64          3分钟超户（机构）资金净流（买）

    public static final int BID_ASK_SPREAD = 49;//int64                 买卖价差
    public static final int BUY_VOL1 = 50;//int64                       买一量

    public static final int TRADABLE_MARKET_VALUE = 53;//int64          流通市值
    public static final int TOTAL_MARKET_VALUE = 54;//int64             总市值

    public static final int SELL_VOL1 = 60;//int64                      卖一量
    public static final int EARNINGS_PER100000SHARES = 61;//int64       10万收益【回购】
    public static final int VALUATIONS = 62;//int64                     估值【分级B】
    public static final int EARNINGS_PER_ONE_THOUSAND = 63;//           1千收益
    public static final int LEVER_PRICE = 64;//int64                    价格杠杆【分级B】

    public static final int CONVERSION_VALUE = 66;//int64               转股价值【可转债】
    public static final int FULL_PRICE = 67;//int64                     全价【债券】
    public static final int CPX_DUR_DAYS = 70;//  int32   	            操盘线个数（日）,正数表示买入信号持续天数（算当日），负数表示卖出信号
    public static final int CPX_DUR_WEEKS = 71;//  int32   	            操盘线个数（周）,正数表示买入信号持续天数（算当日），负数表示卖出信号
    public static final int CPX_DUR_MONTHS = 72;//  int32               操盘线个数（月）,正数表示买入信号持续天数（算当日），负数表示卖出信号
    public static final int CPX_DUR_30M = 73;//  int32   	            操盘线个数（30分钟）,正数表示买入信号持续天数（算当日），负数表示卖出信号
    public static final int CPX_DUR_60M = 74;//  int32   	            操盘线个数（60分钟）,正数表示买入信号持续天数（算当日），负数表示卖出信号
    public static final int UP_STOCK_NUM = 75;//  int32   	            涨盘家数（该值仅指数板块类股票有意义）
    public static final int DOWN_STOCK_NUM = 76;//  int32               跌盘家数（该值仅指数板块类股票有意义）
    public static final int PLAT_STOCK_NUM = 77;//  int32               平盘家数（该值仅指数板块类股票有意义）
    public static final int LEAD_UP_STOCK = 78;//  int32   	            领涨个股（该值仅指数板块类股票有意义）
    public static final int LEAD_DOWN_STOCK = 79;//  int32              领跌个股（该值仅指数板块类股票有意义）
    public static final int SETTL_PRC = 80;//  int32   	                结算价（该值仅期货类数据有效）
    public static final int HOLD_VOL = 81;//  int32   	                持仓量（该值仅期货类数据有效）
    public static final int HOLD_VOL_INCREASE = 82;//  int32   	        增仓（该值仅期货类数据有效）
    public static final int TARGET_STOCK_PRC = 83;//  int32   	        标的股现价（该值仅期货类数据有效，算基差用）
    public static final int CHANGE_PRC = 84;//  int32   	            涨跌额
    public static final int CHANGE_RATIO = 85;//  int32   	            涨跌幅
    public static final int CHANGE_RATIO_OF5 = 86;//  int32   	        5日涨跌幅
    public static final int CHANGE_RATIO_OF10 = 87;//  int32   	        10日涨跌幅
    public static final int CHANGE_RATIO_OF20 = 88;//  int32   	        20日涨跌幅
    public static final int SPEED_RATIO = 89;//  int32   	            涨速幅
    public static final int TURNOVER_RATIO = 90;//  int32   	        换手率
    public static final int TURNOVER_RATIO_OF5 = 91;//  int32           5日换手率
    public static final int PE_RATIO = 15;//  int32   	                市盈率（TTM使用该字段）
    public static final int PB_RATIO = 93;//  int32   	                市净率
    public static final int QUANTITY_RELATIVE_RATIO = 94;//  int32   	量比
    public static final int BIG_ORDER_INFLOW_AMT = 95;// int64	        资金净流=主力净买
    public static final int BIG_ORDER_INFLOW_AMT5 = 96;// int64	        5日资金净流=5日净买
    public static final int BIG_ORDER_RATIO = 97;//  int32   	        大单比率
    public static final int AMPLITUDE = 98;//  int32   	                振幅
    public static final int BIG_ORDER_INFLOW_DUR_TODAY = 99;//  int32   主力强买
    public static final int BIG_ORDER_INFLOW_DAYS5 = 100;//  int32   	5日主力增仓
    public static final int BIG_ORDER_INFLOW_DAYS10 = 101;//  int32   	10日主力增仓
    public static final int BIG_ORDER_INFLOW_DAYS20 = 102;//  int32   	20日主力增仓
    public static final int HOLD_VOL_INCREASE_DAY = 103;//  int32   	日增仓
    public static final int WEEK_52_HIGH_PRICE = 104;//  int32   	    52周最高
    public static final int WEEK_52_LOW_PRICE = 105;//  int32   	    52周最低
    public static final int PRE_CLO_PRC = 106;//  int32   	            昨收价
    public static final int CLO_PRC_BF4 = 107;//  int32   	            停牌标记 1：停牌，0：正常

    public static final int DISCOUNT_RATIO = 109;//  int32   	        折价率

    public static final int COMMITTEE = 116;//  int32                   委比
    public static final int TRADE_NUM = 117;//  int32                   笔数
    public static final int AVEPRICE = 118;//  int32                    均价
    public static final int PRE_SETTL_PRC = 119;//  int32   	        昨结算价（该值仅期货类数据有效）

    public static final int TARGET_STOCK_ID = 121;//  int32   	        相关股票ID
    public static final int EXERCISE_PRICE = 122;//  int32   	        行权价（股票期权有效）
    public static final int CALL_PUT_FLAG = 123;//  int32   	        认沽认购标记（股票期权有效）
    public static final int PREMIUM_RATIO = 124;//  int32   	        溢价率
    public static final int DATA_TIMESTAMP = 125;//  int32   	        数据时间戳(YYYYMMDDhhmmss)
    public static final int FUND_SIZE = 126;//  uint64   	            基金规模
    public static final int FUND_TYPE = 127;//  string   	            基金类型
    public static final int FUND_ESTABLISH_DATE = 128;//  string   	    基金成立日
    public static final int FUND_MANAGER = 129;//  string   	        基金管理人
    public static final int FUND_NEARLY_1M_RETURN = 130;//  uint32   	基金近一个月回报
    public static final int FUND_NEARLY_3M_RETURN = 131;//  uint32   	基金近三个月回报
    public static final int AVAILABLE_TRADABLE_SHARES = 132;//  uint64  有效流通股
    public static final int AMPLITUDE_OF_FLUCTATION = 133;//  int32   	波幅（国际汇率）
    public static final int BUYING_RATE = 134;//  uint32   	            现汇买入价
    public static final int CASH_BUYING_RATE = 135;//  uint32   	    现钞买入价
    public static final int SELLING_RATE = 136;//  uint32   	        现汇卖出价
    public static final int CASH_SELLING_RATE = 137;//  uint32   	    现钞卖出价
    public static final int FUND_NEARLY_1Y_RETURN = 138;//  uint32   	基金近一年回报
    public static final int FUND_NEARLY_3Y_RETURN = 139;//  uint32   	基金近三年回报
    public static final int FUND_TOTAL_SHARES = 140;//  uint64   	    基金份额
    public static final int FUND_TOTAL_SHARES_GROWTH = 141;//  uint64   基金份额增长
    public static final int NET_ASSET_PER_SHARE = 142;//  uint64        每股净资产
    public static final int FUND_TOTAL_SHARES_GROWTH_DATETIME = 143;//uint64    基金份额增长年月日(时间戳精确到毫秒)
    public static final int AMOUNT_AHT = 160;//盘后交易额
    public static final int VOLUME_AHT = 161;//盘后交易量
    public static final int TRADE_AHT = 162;//盘后交易笔数

    //低频字段
    public static final int HANDS_OF_SHARES = 1001;//int64                  每股手数(港股)
    public static final int MARKET_MAKER_NUM = 1002;//int64                 做市商数(新三板)
    public static final int DELIVERY_DATE = 1003;//uint32                   交割日(股指期货)
    public static final int AB_SHARE_RATIO = 1004;//uint32                  A:B份额比(分级基金)
    public static final int PARENT_FUND_ID = 1005;//uint32                  母基金id(分级基金)
    public static final int UNDER_PARENT_FUND_FALL = 1006;//int64           下折母基金需跌（分级基金）
    public static final int UP_PARENT_FUND_RISE = 1007;//int64              上折母基金需涨（分级基金）
    public static final int PARENT_FUND_NET_VALUE = 1008;//int64            母基净值（分级基金）
    public static final int AB_FUND_ID = 1009;//  int32   	                分级A/B股票ID（分级基金）
    public static final int MIN_CREATION_UNIT = 1010;// int64	            最小申赎单位（ETF基金）
    public static final int INTEREST_PAYMENT = 1011;//int32                 距付息(天)【债券】
    public static final int REMAINING_YEARS = 1012;//int32                  剩余年限【债券】【可转债】
    public static final int COUPON = 1013;//int32                           票息【债券】
    public static final int DURATION = 1014;//int64                         久期【债券】
    public static final int BOND_SCALE = 1015;//int64                       规模【债券】
    public static final int GUARANTEE = 1016;//string                       担保【债券】
    public static final int BOND_CREDIT = 1017;//string                     债券信用【债券】
    public static final int MAIN_CREDIT = 1018;//string                     主体信用【债券】
    public static final int DUE_TIME = 1019;//int32                         到期时间【债券、可转债】
    public static final int RESALE_PRICE = 1020;//int32                     回售触发价【可转债】
    public static final int STRONG_PRICE = 1021;//int32                     强赎触发价【可转债】
    public static final int CONVERTIBLE_BONDS_RATIO = 1022;//int32          转债占比【可转债】
    public static final int NET_ASSETS = 1023;//int64                       净资产【可转债】
    public static final int CONVERSION_PRICE = 1024;//int64                 转股价【可转债】
    public static final int FUNDS_AVAILABLE_DATE = 1025;//uint32            资金可用日期【回购】
    public static final int REPO_DAYS = 1026;//  int32   	                占款天数、计息天数（回购）
    public static final int TOTAL_SHARE_CAPITAL = 1027;//int64              总股本
    public static final int CAPITAL = 1028;// int64	                        流通股本
    public static final int LIM_UP_PRC = 1029;//  int32   	                涨停价
    public static final int LIM_DOWN_PRC = 1030;//  int32   	            跌停价
    public static final int AVG_VOL_OF5 = 1031;// int64	                    5日均量（算量比）
    public static final int STATIC_EARNINGS_PER_SHARES = 1032;//int64       每股收益（EPS）
    public static final int NET_VALUE = 1033;//int64                        净值【分级】
    public static final int REPORT_DATA = 1034;//                           报告日期YYYYMMDD
    public static final int PRE_TAX_EARNINGS_RATE = 1035; //                税前收益率【债券】[原68]
    public static final int AFTER_TAX_EARNINGS_RATE = 1036; //              税后收益率【债券】[原69]
    public static final int NEEQ_INFO = 1037; //                            新三板信息（创新层、优先股等）
    public static final int STOCK_HOLD_RATE = 1038; //                      股票持仓比【分级A、分级B】
    public static final int REPORT_SOURCE = 1039; //                        报告来源【港股 1-4 标识某一季度的业报】
    public static final int INSTITUTIONAL_POSITION_RATIO = 1040;//          机构持仓占比
    //    public static final int NET_ASSET_PER_SHARE = 1041;//                   每股净资产
//    public static final int AVAILABLE_TRADABLE_SHARES = 1042;//             有效流通股本
    public static final int INSTITUTIONAL_POSITION_TOTAL = 1043;//          机构持股占总股本比
    public static final int AMOUNT_SUPER4 = 1044;//                         前4交易日机构（超户）资金净买（流）【算5日用】
    public static final int AMOUNT_BIG4 = 1045;//                           前4交易日游资（大户）资金净买（流）【算5日用】
    public static final int AMOUNT4 = 1046;//                               前4交易日成交额的累计【算5日净买占比用】
    public static final int STATIC_OCCUPIED_DAYS = 1047;//                  回购期限、品种天数（回购）

    public static final int STATIC_TARGET_STOCK_CODE = 1048;//股指期货对应标地指数，AH股，基金标地，分级AB，转债正股
    public static final int PRE_CLOSE = 1049;//昨收价
    public static final int PRE_SETTLEMENT = 1050;//昨结算
    public static final int PRE_OPEN_INTEREST = 1051;//昨持仓
    public static final int ED = 1052;//每股公积金
    public static final int ROE = 1053;//净资产收益率
    public static final int OPERATING_PROFIT_RATIO = 1054;//营业利润率
    public static final int NET_MARGIN = 1055;//净利润率
    public static final int ND = 1056;//每股未分配利润
    public static final int LISTING_DATE = 1057;//上市日期
    public static final int SHAREHOLDERS = 1058;//股东数
    public static final int STATIC_INDUSTRY = 1059;//所属行业
    public static final int MAIN_REVENUE_GROWTH_RATE = 1060;//营业收入增长率
    public static final int MAIN_PROFIT_GROWTH_RATE = 1061;//营业利率增长率
    public static final int NET_PROFIT_GROWTH_RATE = 1062;//净利润增长率
    public static final int GROSS_PROFIT_MARGIN = 1063;//销售毛利率
    public static final int CASH_FLOW_PER_SHARE = 1064;//每股经营现金流
    public static final int STOCK_FLAG = 1065;//个股标记

    //拓展字段
    public static final int RISE_HEAD_NAME = -1;// string	                板块（指数）领涨个股名称
    public static final int FALL_HEAD_NAME = -2;// string                   板块（指数）领跌个股名称
    public static final int RISE_HEAD_ZDF = -3;// string                    板块（指数）领涨个股涨跌幅
    public static final int FALL_HEAD_ZDF = -4;// string                    板块（指数）领跌个股涨跌幅
    public static final int RISH_HEAD_PRICE = -26;// string领涨个股最新价                    板块（指数）领跌个股涨跌幅
    public static final int TARGET_STOCK_CODE = -5;// string                相关股票代码
    public static final int TARGET_STOCK_CLO_PRC = -6;// string             相关股票最新价
    public static final int TARGET_STOCK_CHANGE_PRC = -7;// string          相关股票涨跌额
    public static final int TARGET_STOCK_CHANGE_RATIO = -8;// string        相关股票涨跌幅
    public static final int MARGIN_TRADING_FLAG = -9;// int32               融资融券标志
    public static final int PARENT_FUND_NAME = -10;// string                母基金名称
    public static final int TARGET_STOCK_NAME = -11;// string               相关股票名称
    public static final int AB_FUND_CODE = -12;// string                    关联基金（AB）代码
    public static final int AB_FUND_NAME = -13;// string                    关联基金（AB）名称
    public static final int AB_FUND_CHANGE_PRC = -14;// string              关联基金（AB）涨跌额
    public static final int AB_FUND_CHANGE_RATIO = -15;// string            关联基金（AB）涨跌幅
    public static final int SUSPENDED_REASON = -16;// string                停牌理由
    public static final int SUSPENDED_TIME = -17;// uint32                  停牌开始时间
    public static final int RESUME_TIME = -18;// uint32                     停牌结束时间（预计）
    public static final int EX_VOL = -19;// int64                           外盘
    public static final int AVAILABLE_MARKET_VALUE = -20;// uint64          有效流通市值
    public static final int NEEQ_TYPE = -21;// string                       类型（新三版）
    public static final int NEEQ_LEVEL = -22;// string                      级别（新三板）
    public static final int NEEQ_EXCHANGE = -23;// string                   转让（新三板）
    public static final int REPORT_QUARTER = -24;// uint32                  报告季度（1-4表示1-4季度）
    public static final int DAILY_EARNINGS_PER_TEN_THOUSAND = -25;//int32   每万元日收益
    public static final int GOODS_DOCTOR_FLAG = -28;//string                股票医生评级
    public static final int BOARD_FRONT_ZF_STOCK = -29;//Json Array         板块涨幅前三股票
    public static final int JICHA = -30;//Json Array         基差
    public static final int PRE_JICHA = -31;//Json Array         基差
    public static final int BOARD_INFO = -32;//Json                         股票所属行业板块
    public static final int DIFF_AMT_SUPER_DAY = -33;//int64                超户（机构）净买（净流）
    public static final int DIFF_AMT_BIG_DAY = -34;//int64                  大户（游资）净买（净流）
    public static final int DIFF_AMT_SUPER_DAY5 = -35;//int64               超户（机构）5日净买（净流）
    public static final int DIFF_AMT_BIG_DAY5 = -36;//int64                 大户（游资）5日净买（净流）
    public static final int BOARD_TOP_SUPER_AMOUNT_STOCK = -37;//Json       板块机构（超户）资金流入第一股
    public static final int BOARD_TOP_BIG_AMOUNT_STOCK = -38;//Json         板块游资（大户）资金流入第一股
    public static final int BOARD_TOP_SUPER_AMOUNT5_STOCK = -39;//Json      板块机构（超户）5日资金流入第一股
    public static final int BOARD_TOP_BIG_AMOUNT5_STOCK = -40;//Json        板块游资（大户）5日资金流入第一股
    public static final int NEEQ_EXCHANGE_TYPE = -41;//String                  新三板转让类型
    public static final int PRE_HOLD_AMT = -200;//昨持仓
    public static final int COMMISSION = 120; //委差
    public static final int OI_TYPE = -46; //开平（期货）
    public static final int INDUSTRY_BELONG = -47;//所属行业

    //客户端自定字段
    public static final int CHANGE_RATIO_MAX = -9001;//  int32   	        最大涨跌幅
    public static final int LZGG_ZDF = -100001;// string                    板块（指数）领涨个股名称+涨跌幅
    public static final int HAOGU_LM = -100002;// string                    好股 栏目
    //    public static final int HAOGU_ZXSJ = -100003;// string            好股 终选时间
    public static final int HAOGU_RXR = -100004;// string                   好股 入选日
    public static final int HAOGU_SIMPLE_TYPE = -100005;// string           好股 SIMPLE类,类型field
    public static final int YYB_CHANGE_RATIO = -200001; //int               收益率
    public static final int YYB_SUCCESS_RATIO = -200002;    //int           成功率
    public static final int YYB_MAX_BUY_COUNT = -200003;    //int           主买次数
    public static final int YYB_FOLLOW_BUY_COUNT = -200004; //int           跟买次数
    public static final int YYB_ACTIVENESS = -200005;   //int               资金类型（活跃性）
    public static final int YYB_STAR = -200006; //int                       星级
    public static final int CHARTS_TYPE = -200007;
    public static final int HMTIME = -200008;
    public static final int HOLD_COAST = -200009;
    public static final int HOLD_TODAY_PROFIT = -200010;
    public static final int HOLD_NUMBER = -200011;
    public static final int HOLD_SZ = -200012;
    public static final int HOLD_ZYK = -200013;
    public static final int HOLD_ZYKB = -200014;
    public static final int HEARD_DRAGON_CODE = -200015;//涨停龙头代码
    public static final int HEARD_DRAGON_ID = -200016;//涨停龙头ID
    public static final int TAGS_STYLE = -200017;//小标签
    public static final int TAGS_COLOR_FLAG = -200018;//小标签颜色
    public static final int GOODS_DESCRIPTION = -200019;//描述
    public static final int LINKBOARD3TAG = -200020;//3日标签
    public static final int LONGHUJME = -200021;//龙虎榜中的“净买额”
    public static final int LONGHUTWOBOARD = -200022;//龙虎榜中的标签2连板
    public static final int LONGHUYYB = -200023;//龙虎榜中的所属板块
    public static final int LONGHUISALL = -200024;//龙虎榜中是否个股全部标记
    public static final int SIMILARKSHRINKFLAG = -200025;//相似K线点击收缩标记
    public static final int SIMILARKSCORE = -200026;//相似度
    public static final int SIMILARKLASTDAY = -200027;//相似k线中的个股列表中个股的k线最后日期（往前查k线历史）
    public static final int GPCBOTTOMFLOAG = -200028;//股票池当日入选股票标志

    //打板利器
    public static final int PATTERNERMAKE_ZT_TIME = -300001;//涨停时间
    public static final int PATTERNERMAKE_FDJE = -52;//封单金额
    public static final int PATTERNERMAKE_FB_TIME = -300001;//封板时间
    public static final int PATTERNERMAKE_KBZF = -300002;//开板涨幅
    public static final int PATTERNERMAKE_JDZF = -300003;//阶段涨幅
    public static final int PATTERNERMAKE_JJZF = -48;//竞价涨幅
    public static final int PATTERNERMAKE_FDB = -49;//封单比
    public static final int PATTERNERMAKE_ZTWME = -50;//涨停委买额
    public static final int PATTERNERMAKE_DTWME = -51;//跌停委卖额
    public static final int PATTERNERMAKE_HOTPOINT = -300005;//热点
    public static final int PATTERNERMAKE_HEADER_DRAGON = -300004;//涨停龙头

}
