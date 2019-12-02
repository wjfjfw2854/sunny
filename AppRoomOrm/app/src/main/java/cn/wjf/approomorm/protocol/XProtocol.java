package cn.wjf.approomorm.protocol;


public class XProtocol {

    public static final String XPRO_CONTENT_TYPE_JSON = "application/json";
    public static final String XPRO_CONTENT_TYPE_TEXT = "text/plain";
    public static final String XPRO_CONTENT_TYPE_MSGPACK = "application/x-msgpack";
    public static final String XPRO_CONTENT_TYPE_PROTOBUF = "application/x-protobuf-v3";

    /**
     * 登录协议（JSON）
     */
    public static final String XPRO_LOGIN_ID = "1100";

    /**
     * 个人设定查询（JSON）
     */
    public static final String XPRO_SEARCH_USER_ID = "1200";

    /**
     * 码表更新
     */
    public static final String XPRO_BASE_CODE_UPDATE_ID = "2000";

    /**
     * 当日资金余额（沪股通、深股通、港股通等）
     */
    public static final String XPRO_CONNECT_AMOUNT_ID = "2800";

    /**
     * 个股交易状态和所属交易所的行情时间
     */
    public static final String XPRO_TIME_STATUS_ID = "3300";

    /**
     * 行情（板块）排序列表
     */
    public static final String XPRO_BK_SORTED_LIST_ID = "2100";

    /**
     * 排序列表(批量)
     */
    public static final String XPRO_RANK_LIST_ID = "2600";

    /**
     * 排序列表(变更数据定制)
     */
    public static final String XPRO_TEMPLATE_LIST_ID = "2700";

    /**
     * 个股详情（不含买卖盘等）
     */
    public static final String XPRO_STOCK_VALUE_DATA_ID = "2200";

    /**
     * 买卖矩阵
     */
    public static final String XPRO_STOCK_MATRIX_DATA_ID = "2500";

    /**
     * 买卖盘（盘口数据，lv1五档，lv2十档）
     */
    public static final String XPRO_STOCK_BUYING_SELLING_DATA_ID = "3000";

    /**
     * 分笔成交
     */
    public static final String XPRO_STOCK_BARGAIN_DATA_ID = "3100";

    /**
     * K线（含历史）
     */
    public static final String XPRO_STOCK_BASE_KLINE_DATA_ID = "2400";

    /**
     * K线（含指标）
     */
    public static final String XPRO_STOCK_KLINE_DATA_IND01_ID = "2401";

    /**
     * K线（含指标，优化指标）
     */
    public static final String XPRO_STOCK_KLINE_DATA_IND02_ID = "2402";

    /**
     * 指标计算（系统指标，用户自定义参数）
     */
    public static final String XPRO_INDEX_CALC00_ID = "2900";

    /**
     * 指标计算（系统指标，用户自定义参数）
     */
    public static final String XPRO_INDEX_CALC01_ID = "2901";

    /**
     * 资金净流（方便日后鉴权）
     */
    public static final String XPRO_STOCK_ZJJL_ID = "3200";
    /**
     * 资金净流（龙虎修改）
     */
    public static final String XPRO_STOCK_ZJJL01_ID = "3201";

    /**
     * 价格除权（批量处理股票价格除复权）
     */
    public static final String XPRO_STOCK_PROCE_DO_EX_RIGHTS_ID = "3500";

    /**
     * 分时走势（当日、5日)
     */
    public static final String XPRO_STOCK_TREND_LINE00_ID = "2300";

    /**
     * 分时走势（当日、5日)
     */
    public static final String XPRO_STOCK_TREND_LINE01_ID = "2301";


    /**
     * 分时科创板
     */
    public static final String XPRO_STOCK_TREND_LINE03_0ID = "2303";


    /**
     * 分时指标（仅限当日)
     */
    public static final String XPRO_STOCK_TREND_INDEX_ID = "3400";

    /**
     * 获取自选股（暂时支持一个分组）
     */
    public static final String XPRO_ZXG_ID = "4000";

    /**
     * 添加自选股（暂时支持一个分组，允许批量往前或往后添加）
     */
    public static final String XPRO_ZXG_INSERT_ID = "4100";

    /**
     * 删除自选股（暂时支持一个分组，允许批量删除，但不支持清空所有）
     */
    public static final String XPRO_ZXG_DELETE_ID = "4200";

    /**
     * 覆盖自选股（暂时支持一个分组，供交换自选股顺序使用）
     */
    public static final String XPRO_ZXG_COVER_ID = "4300";

    /**
     * 获取好股模型详情页行情信息
     */
    public static final String XPRO_GET_HAOGU_DETAIL_ID = "5000";

    /**
     * 获取收费模型详情页行情信息
     */
    public static final String XPRO_GET_ZIXUN_DETAIL_ID = "5100";

    /**
     * 获取收费模型详情页行情信息
     */
    public static final String XPRO_GET_CELUE_DETAIL_ID = "5200";

    /**
     * 获取策略模型详情页行情信息
     */
    public static final String XPRO_GET_ZDLH_DETAIL_ID = "5300";

    /**
     * 获取策略模型详情页行情信息
     */
    public static final String XPRO_GET_YJJX_DETAIL_ID = "5400";
    public static final String XPRO_GET_SIMILAR_DETAIL_ID = "5700";
    /**
     * 码表更新
     */
    public static String GoodsTable = "2000";
    /**
     * 时间校准
     */
    public static final String TIME_STATUS = "3300";
    //登录
    public static final String XPRO_LOGIN = "1000";
    //刷新Token
    public static final String XPRO_RENEWAL_TOKEN = "1100";
    public static final String BIND_PUSH = "4500";
    public static final String UN_BIND_PUSH = "4600";
    //逐笔
    public static final String SPEC_DEAL = "3700";
    //首页推荐
    public static final String HOT_RECOMMEND = "8300";

    //特色选股
    public static final String XPRO_TSXG = "8900";

    public static final String XPRO_TIMES = "8600";
    public static final String XPRO_TIMES_HISTORY = "8601";//排名中的实时大盘

    //北上南下列表
    public static final String XPRO_NORTHLY_SOUTHLY_LIST = "5600";
    //北上南下走势
    public static final String XPRO_NORTHLY_SOUTHLY_TREND = "2801";
    //北上南下历史资金余额
    public static final String XPRO_NORTHLY_SOUTHLY_HISTROY = "2802";


    public static final String XPRO_BOARD_ATTACK="8001";

    public static final String XPRO_STRAGEGY_POOL_SINGLE = "9700";

}
