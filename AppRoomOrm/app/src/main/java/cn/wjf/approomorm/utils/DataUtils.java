package cn.wjf.approomorm.utils;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.wjf.approomorm.data.Category;
import cn.wjf.approomorm.data.Exchange;
import cn.wjf.approomorm.data.Field;
import cn.wjf.approomorm.data.Goods;
import cn.wjf.approomorm.data.GoodsParams;
import cn.wjf.approomorm.data.QuoteType;


/**
 * 数据处理工具类
 * 1.价格类型（开高低收，结算价等）：
 * 单位精确到厘后面1位，获取出来的原始数据除以10000即转换成单位元
 * 如：收盘价25300 => 即收盘价2.53元
 * <p>
 * 2.成交量类型（成交量，持仓量等）
 * 单位精确到股，各品种股手转换倍率如下
 * 股票：1手=100股
 * 债券：1手=10股
 * 期货：1手=1股（合约）
 * <p>
 * 3.成交额类型（成交额，资金净流等）
 * 单位精确到厘
 * 如：成交额3523425000 => 即成交额为3523425元
 * <p>
 * 4.百分比类型（涨跌幅，换手率等）
 * 百分比类数据一律按照原始小数进行换算
 * 单位精确到万分之一
 * 如：涨跌幅253 => 即涨跌幅2.53%
 * <p>
 * <p>
 * 5.以下是放大了10000倍的字段,四位小数
 * 剩余年限
 * 票息
 * 合约价值
 * 回售触发价
 * 强赎触发价
 * 转债占比
 * 下折母基金需跌
 * 上折母基金需涨
 * 久期
 * 规模
 * 母基净值
 * 净资产
 * 分级B估值
 * 分级B净值
 * 杠杆价格
 * 转股价
 * 转股价值
 * 全价
 * <p>
 * 6.放大一百倍，加%
 * 分级B溢价率（100）
 * 股指期货升水比例（100）
 * 可转债溢价率（100）
 * 可转债的市净率
 * 分级A折价率（100）
 * ETF溢价率（100）
 * LOF、CEF折价率（100）
 * 税前收益
 * 税后收益
 *
 */
public class DataUtils {

    public final static int PRICE_RISE = 1;
    public final static int PRICE_FALL = -1;
    public final static int PRICE_EQUAL = 0;

    private final static String ZD_ZDF_SP = "  ";
    public static final String PLACE_HOLDER = "--";
    private static final String SUSPENSION = "停牌";
    private static final String LOSS = "亏损";
    private static final String ZERO_STR = "0";
    public static final DecimalFormat mDecimalFormat = new DecimalFormat("0");
    public static final DecimalFormat mDecimalFormat1 = new DecimalFormat("0.0");
    public static final DecimalFormat mDecimalFormat2 = new DecimalFormat("0.00");
    public static final DecimalFormat mDecimalFormat3 = new DecimalFormat("0.000");
    public static final DecimalFormat mDecimalFormat4 = new DecimalFormat("0.0000");
    public static final DecimalFormat mAmountFormat = new DecimalFormat("0");
    public static final DecimalFormat mZDFFormat = new DecimalFormat("0.00");
    public static final DecimalFormat mHSLFormat = new DecimalFormat("0.00");
    public static final DecimalFormat mFloat2Percent = new DecimalFormat("0.00");
    public static final DecimalFormat mFloat2JL = new DecimalFormat("0.00");

    public static final DecimalFormat mDecimalFormat1_max = new DecimalFormat("0.#");
    public static final DecimalFormat mDecimalFormat2_max = new DecimalFormat("0.##");
    public static final DecimalFormat mDecimalFormat3_max = new DecimalFormat("0.###");
    public static final DecimalFormat mDecimalFormat4_max = new DecimalFormat("0.####");


    static {
        mDecimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        mDecimalFormat1.setRoundingMode(RoundingMode.HALF_UP);
        mDecimalFormat2.setRoundingMode(RoundingMode.HALF_UP);
        mDecimalFormat3.setRoundingMode(RoundingMode.HALF_UP);
        mDecimalFormat4.setRoundingMode(RoundingMode.HALF_UP);

        mDecimalFormat1_max.setRoundingMode(RoundingMode.HALF_UP);
        mDecimalFormat2_max.setRoundingMode(RoundingMode.HALF_UP);
        mDecimalFormat3_max.setRoundingMode(RoundingMode.HALF_UP);
        mDecimalFormat4_max.setRoundingMode(RoundingMode.HALF_UP);
    }


    // 分母限制大于0
    public static float calZDFPercent(float offset, float orgiPrice) {
        if (orgiPrice <= 0) {
            return 0.0f;
        }
        BigDecimal b_offset = new BigDecimal(offset);
        BigDecimal b_orgiPrice = new BigDecimal(orgiPrice);
        BigDecimal percent = b_offset.divide(b_orgiPrice, 4, BigDecimal.ROUND_HALF_UP);
        return percent.floatValue();
    }


    /**
     * 格式化价格 进行单位换算（在原值基础上除以10000）
     *
     * @param goods
     * @param goodsParam
     * @return
     */
    public static String formatPrice(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        return formatPrice(goods.getValue(goodsParam), goods.getExchange(), goods.getCategory());
    }

    public static String formatPrice(String value, int exchange, long category) {
        if (TextUtils.isEmpty(value)) {
            return PLACE_HOLDER;
        }
        return formatPrice(convertToDouble(value) / 10000f, exchange, category);
    }

    public static String formatPrice(long value, int exchange, long category) {
        return formatPrice(value / 10000f, exchange, category);
    }

    public static String formatPrice(double price, int exchange, long category) {
        //1位小数：股指期货
        //3位小数：所有基金、深证债券、股票期权、沪市B股、深市-股转系统-两网及退市（三板）其中的B股退市、港股主板、上证回购
        //4位小数：国际汇率
        //2位小数：其它
        if (isGZQH(exchange, category)) {
            return mDecimalFormat1.format(price);
        } else if (DataUtils.isGuoZaiQH(exchange, category) || isJJ(exchange, category) || isSZZQ(exchange, category) || isGPQQ(exchange,
                category) || isSH_B(exchange, category) || isSZ_TS_B(exchange, category) ||
                isHK_STOCK(exchange, category) || isSHHG(exchange, category)) {
            return mDecimalFormat3.format(price);
        } else if (isGJHL(exchange) || isRMB_PJ(exchange)) {
            return mDecimalFormat4.format(price);
        } else {
            return mDecimalFormat2.format(price);
        }
    }

    /**
     * 格式化涨跌和涨跌幅
     *
     * @param goods
     * @return
     */
    public static String formatZDAndZDF(Goods goods) {
        if (goods == null) {
            return PLACE_HOLDER + ZD_ZDF_SP + PLACE_HOLDER;
        }
        if (goods.isSuspension()) {
            //停牌
            return formatZDF(goods, GoodsParams.CHANGE_RATIO);
        } else {
            //正常
            return formatPrice(goods, GoodsParams.CHANGE_PRC) + ZD_ZDF_SP + formatZDF(goods,
                    GoodsParams.CHANGE_RATIO);
        }
    }

    /**
     * 格式化涨跌幅、换手率（包含停牌逻辑）
     */
    public static String formatZDF(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }
        if (goods.isSuspension()) {
            if (goodsParam == GoodsParams.CHANGE_RATIO) {
                return SUSPENSION;
            }

            if (isValueEqualsZero(goods, goodsParam)) {
                return PLACE_HOLDER;
            }
        }

        return formatZDF(goods.getValue(goodsParam));
    }


    /**
     * 格式化涨跌幅、换手率（包含停牌逻辑,包含+号）
     */
    public static String formatZDFWithSign(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }
        if (goods.isSuspension()) {
            if (goodsParam == GoodsParams.CHANGE_RATIO) {
                return SUSPENSION;
            }

            if (isValueEqualsZero(goods, goodsParam)) {
                return PLACE_HOLDER;
            }
        }

        String value = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(value)) {
            return PLACE_HOLDER;
        }

        float valueF = convertToFloat(value);
        String s = formatZDF((int) valueF);
        if (valueF > 0) {
            s = "+" + s;
        }
        return s;
    }


    /**
     * 格式化涨跌幅
     */
    public static String formatZDF(String zdf) {
        if (TextUtils.isEmpty(zdf)) {
            return PLACE_HOLDER;
        }

        float value = convertToFloat(zdf);
        return formatZDF((int) value);
    }

    /**
     * 格式化涨跌幅
     */
    public static String formatZDF(int zdf) {
        return formatZDF(zdf, mZDFFormat);
    }

    /**
     * 格式化涨跌幅
     */
    public static String formatZDF(int zdf, DecimalFormat customFormat) {
        double value = 1.0d * zdf / 100;
        return customFormat.format(value) + "%";
    }


    /**
     * 格式化市盈率（百分比数据，但不加百分号），数值小于等于0时显示“亏损”
     */
    public static String formatSYL(String zdf) {
        if (TextUtils.isEmpty(zdf)) {
            return PLACE_HOLDER;
        }

        double value = convertToDouble(zdf);
        value = value / 100;
        return mZDFFormat.format(value);
    }

    /**
     * 格式化市盈率（百分比数据，但不加百分号），数值小于等于0时显示“亏损”
     */
    public static String formatSYL(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatSYL(tStr);
    }

    /**
     * 格式化市净率、大单比率（百分比数据，但不加百分号）
     */
    public static String formatSJL(String zdf) {
        if (TextUtils.isEmpty(zdf)) {
            return PLACE_HOLDER;
        }

        double value = convertToDouble(zdf);
        value = value / 100;
        return mZDFFormat.format(value);
    }

    /**
     * 格式化市盈率、市净率（百分比数据，但不加百分号）
     */
    public static String formatSJL(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatSJL(tStr);
    }

    /**
     * 格式化溢价率折价率等
     * 以下字段使用：
     * 分级B溢价率
     * 股指期货升水比例
     * 可转债溢价率
     * 可转债的市净率
     * 分级A折价率
     * ETF溢价率
     * LOF、CEF折价率
     * 税前收益
     * 税后收益
     *
     * @param goods
     * @param goodsParam
     * @return
     */
    public static String formatValueRatio(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatValueRatio(tStr);
    }

    /**
     * 格式化溢价率折价率等
     * 以下字段使用：
     * 分级B溢价率
     * 股指期货升水比例
     * 可转债溢价率
     * 可转债的市净率
     * 分级A折价率
     * ETF溢价率
     * LOF、CEF折价率
     * 税前收益
     * 税后收益
     *
     * @param value
     * @return
     */
    public static String formatValueRatio(String value) {
        if (TextUtils.isEmpty(value)) {
            return PLACE_HOLDER;
        }

        double mValue = convertToDouble(value);
        mValue = mValue / 100;
        return mZDFFormat.format(mValue) + "%";
    }

    /**
     * 年利率
     *
     * @param goods
     * @param goodsParam
     * @return
     */
    public static String formatYearRatio(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatYearRatio(tStr);
    }

    /**
     * 年利率
     *
     * @param value
     * @return
     */
    public static String formatYearRatio(String value) {
        if (TextUtils.isEmpty(value)) {
            return PLACE_HOLDER;
        }

        double mValue = convertToDouble(value);
        mValue = mValue / 10000;
        return mZDFFormat.format(mValue) + "%";
    }

    /**
     * 直接获取值
     */
    public static String getValue(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        } else {
            return tStr;
        }
    }

    public static String formatCouponRate(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        } else {
            double value = convertToDouble(tStr);
            value = value / 10000;
            return mDecimalFormat2.format(value) + "%";
        }
    }

    public static String formatYybRation(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }
        String value = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(value)) {
            return PLACE_HOLDER;
        }

        double mValue = convertToDouble(value);
        mValue = mValue / 10000;
        return mZDFFormat.format(mValue) + "%";
    }

    public static String formatRatioDivide10000Keep2(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        }

        return formatDivide10000Keep2(tStr) + "%";
    }

    public static String formatDivide10000Keep2(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatDivide10000Keep2(tStr);
    }

    private static String formatDivide10000Keep2(String tStr) {
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        } else {
            double value = convertToDouble(tStr);
            value = value / 10000;
            return mDecimalFormat2.format(value);
        }
    }

    public static String formatDivide10000Keep3(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatDivide10000Keep3(tStr);
    }

    private static String formatDivide10000Keep3(String tStr) {
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        } else {
            double value = convertToDouble(tStr);
            value = value / 10000;
            return mDecimalFormat3.format(value);
        }
    }

    public static String formatDivide10000Keep1(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatDivide10000Keep1(tStr);
    }

    private static String formatDivide10000Keep1(String tStr) {
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        } else {
            double value = convertToDouble(tStr);
            value = value / 10000;
            return mDecimalFormat1.format(value);
        }
    }

    /**
     * 直接获取值除以一万
     */
    public static String formatValueDivide10000(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatValueDivide10000(tStr);
    }

    public static String formatValueDivide10000(String tStr) {
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        } else {
            double value = convertToDouble(tStr);
            value = value / 10000;
            return mDecimalFormat4_max.format(value);
        }
    }

    /**
     * A:B份额比
     */
    public static String formatAB(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        }

        if (tStr.length() == 2) {
            StringBuilder sb = new StringBuilder(tStr);
            sb.insert(1, ":");
            return sb.toString();
        } else {
            return PLACE_HOLDER;
        }
    }

    /**
     * 格式化估值
     */
    public static String formatGZ(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String gz = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(gz)) {
            return PLACE_HOLDER;
        }

        double value = convertToDouble(gz);
        value = value / 1000;
        return mDecimalFormat3.format(value) + "%";
    }

    /**
     * 格式化年收益
     */
    public static String formatYearForward(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String gz = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(gz)) {
            return PLACE_HOLDER;
        }

        double value = convertToDouble(gz);
        value = value / 10000;
        return mDecimalFormat3.format(value) + "%";
    }

    //金额
    public static String formatAmount(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatAmount(tStr);
    }


    /**
     * 判断数值是否等于0
     *
     * @param goods
     * @param goodsParam
     * @return
     */
    private static boolean isValueEqualsZero(Goods goods, int goodsParam) {
        if (goods == null) {
            return false;
        }

        return ZERO_STR.equals(goods.getValue(goodsParam));
    }

    public static boolean isValueEmpty(Goods goods, int goodsParam) {
        if (goods == null) {
            return true;
        }

        return TextUtils.isEmpty(goods.getValue(goodsParam));
    }

    public static boolean isValueEmptyOrZero(Goods goods, int goodsParam) {
        return isValueEmpty(goods, goodsParam) || isValueEqualsZero(goods, goodsParam);
    }

    // 金额
    public static String formatAmount(String val) {
        if (TextUtils.isEmpty(val)) {
            return PLACE_HOLDER;
        }

        return formatAmount(convertToLong(val));
    }

    public static String formatAmount(long val) {
        val = val / 1000;

        String flag = "";
        if (val < 0) {
            flag = "-";
            val = -val;
        }

        if (val < 10000) {
            return flag + String.valueOf(val);
        } else {
            return formatAmount10000(flag, val);
        }
    }

    //格式金额10000以上的情况
    public static String formatAmount10000(String flag, long val) {
        //最多四位数字，如：13.14万，131.4万，1314万
        if (val < 100000) {
            //1万<=val<10万
            double newVal = val;
            newVal = newVal / 10000;
            return flag + mDecimalFormat3_max.format(newVal) + "万";
        } else if (val < 1000000) {
            //10万<=val<100万
            double newVal = val;
            newVal = newVal / 10000;
            return flag + mDecimalFormat2_max.format(newVal) + "万";
        } else if (val < 10000000) {
            //100万<=val<100万
            double newVal = val;
            newVal = newVal / 10000;
            return flag + mDecimalFormat1_max.format(newVal) + "万";
        } else if (val < 100000000) {
            //1000万<=val<1亿
            double newVal = val;
            newVal = newVal / 10000;
            return flag + mDecimalFormat.format(newVal) + "万";
        } else if (val < 1000000000) {
            //1亿<=val<10亿
            double newVal = val;
            newVal = newVal / 100000000;
            return flag + mDecimalFormat3_max.format(newVal) + "亿";
        } else if (val < 10000000000L) {
            //10亿<=val<100亿
            double newVal = val;
            newVal = newVal / 100000000;
            return flag + mDecimalFormat2_max.format(newVal) + "亿";
        } else if (val < 100000000000L) {
            //100亿<=val<1000亿
            double newVal = val;
            newVal = newVal / 100000000;
            return flag + mDecimalFormat1_max.format(newVal) + "亿";
        } else {
            //1000亿<=val
            double newVal = val;
            newVal = newVal / 100000000;
            return flag + mDecimalFormat.format(newVal) + "亿";
        }
    }

    /**
     * 主力净买
     * 净流
     *
     * @return
     */

    public static String formatJL(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        return formatJL(convertToLong(goods.getValue(goodsParam)), goods.getExchange(), goods
                .getCategory());
    }

    /**
     * 主力净买
     * 净流
     *
     * @param val
     * @return
     */
    public static String formatJL(long val, int exchange, long category) {
        val = val / 1000;

        String flag = "";
        if (val < 0) {
            flag = "-";
            val = -val;
        }

        if (val < 10000) {
            if (isJJ(exchange, category)) {
                return flag + mDecimalFormat1.format(val);
            } else {
                return flag + mDecimalFormat.format(val);
            }
        } else {
            return formatAmount10000(flag, val);
        }
    }

    /**
     * 格式化量比
     */
    public static String formatLB(String lb) {
        if (TextUtils.isEmpty(lb)) {
            return PLACE_HOLDER;
        }

        // 1. 转换为数值型
        double value = convertToDouble(lb);
        // 2. 除以100
        value = value / 100;
        // 3. 保留两位小数
        return mZDFFormat.format(value);
    }

    /**
     * 最小申赎单位
     *
     * @param goods
     * @return
     */
    public static String formateValueOfUnit(Goods goods, int goodsParam, String unitStr) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        return formateValueOfUnit(goods.getValue(goodsParam), unitStr);
    }

    /**
     * 最小申赎单位
     *
     * @param goods
     * @return
     */
    public static String formatZXSS(Goods goods, int goodsParam) {
        return formateValueOfUnit(goods, goodsParam, "万份");
    }

    // 最小申赎单位
    public static String formateValueOfUnit(String val, String unitStr) {
        if (TextUtils.isEmpty(val)) {
            return PLACE_HOLDER;
        }

        return val + unitStr;
    }

    public static String formatLB(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        return formatLB(goods.getValue(goodsParam));
    }

    public static String formatFloat2Percent(float val) {
        val = val * 100;
        return mZDFFormat.format(val) + "%";
    }

    /**
     * 格式化除数
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param formator 格式器
     * @return
     */
    public static String formatDivider(double dividend, double divisor, DecimalFormat formator) {
        if (divisor == 0) {
            return "0";
        }
        return formator.format(1.0f * dividend / divisor);
    }

    public static String formatGoodsName(String goodsName) {
        if (TextUtils.isEmpty(goodsName)) {
            return PLACE_HOLDER;
        }

        return goodsName;
    }

    /**
     * 仅适用于沪深、港股、板块等分组列表数据的处理
     *
     * @return
     */
    public static String getStrForMarketGroupList(int dataType, Goods goods) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        switch (dataType) {
            case GoodsParams.CHANGE_PRC:
                //涨幅榜,跌幅榜
                return formatZDF(goods, GoodsParams.CHANGE_RATIO);
            case GoodsParams.SPEED_RATIO:
                //5分钟快速涨幅
                return formatZDF(goods, GoodsParams.SPEED_RATIO);
            case GoodsParams.QUANTITY_RELATIVE_RATIO:
                //量比榜
                return formatLB(goods, GoodsParams.QUANTITY_RELATIVE_RATIO);
            case GoodsParams.AMOUNT:
                //成交额榜
                return formatAmount(goods, GoodsParams.AMOUNT);
            case GoodsParams.BIG_ORDER_INFLOW_AMT:
                //资金净流入，资金净流出
                return formatJL(goods, GoodsParams.BIG_ORDER_INFLOW_AMT);
            case GoodsParams.TURNOVER_RATIO:
                //换手率榜
                return formatZDF(goods, GoodsParams.TURNOVER_RATIO);
            default:
                return PLACE_HOLDER;
        }
    }

    /**
     * string转int
     *
     * @param str
     * @return
     */
    public static int convertToInt(String str) {
        int iRet = 0;
        if (TextUtils.isEmpty(str)) {
            return iRet;
        }

        try {
            iRet = Integer.parseInt(str);
        } catch (Exception e) {
        }
        return iRet;
    }

    /**
     * string转int
     *
     * @return
     */
    public static String convertToUTF8String(byte[] bs) {
        String sRet = "";
        if (bs == null) {
            return "";
        }

        try {
            sRet = new String(bs, "UTF-8");
        } catch (Exception e) {
        }
        return sRet;
    }

    /**
     * string转long
     *
     * @param str
     * @return
     */
    public static long convertToLong(String str) {
        long iRet = 0;
        try {
            iRet = Long.parseLong(str);
        } catch (Exception e) {
        }
        return iRet;
    }

    /**
     * string转float
     *
     * @param str
     * @return
     */
    public static float convertToFloat(String str) {
        float fRet = 0;
        try {
            fRet = Float.parseFloat(str);
        } catch (Exception e) {
        }
        return fRet;
    }

    /**
     * string转double
     *
     * @param value
     * @return
     */
    public static double convertToDouble(String value) {
        double result = 0d;

        try {
            result = Double.parseDouble(value);
        } catch (Exception e) {
        }

        return result;
    }


    // return
    // -1:ver1 < ver2; 1:ver1 > ver2; 0:ver1 == ver2
    public static int compareVersion(String ver1, String ver2) {
        if (ver1 == null || ver2 == null) return 0;
        String[] thisParts = ver1.split("\\.");
        String[] thatParts = ver2.split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? convertToInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? convertToInt(thatParts[i]) : 0;

            if (thisPart < thatPart) return -1;
            if (thisPart > thatPart) return 1;
        }
        return 0;
    }


    /**
     * 总股本、流通股本
     */
    public static String formatCapital(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        return formatCapital(convertToLong(goods.getValue(goodsParam)));
    }

    /**
     * 总股本、流通股本、份额、份额增长
     */
    public static String formatCapital(long val) {
        String flag = "";
        if (val < 0) {
            flag = "-";
            val = -val;
        }

        if (val < 10000) {
            return mDecimalFormat.format(val);
        } else {
            return formatAmount10000(flag, val);
        }
    }

    /**
     * 总手
     */
    public static String formatVolume(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        return formatVolume(convertToLong(tStr), goods.getExchange(), goods.getCategory());
    }

    public static String formatVolume(String val, int exchange, long category) {
        return formatVolume(convertToLong(val), exchange, category);
    }

    public static String formatVolume(long tempVal, int exchange, long category) {
        double val = getVolumeValue(tempVal, exchange, category);
        if (val < 100) {
            return mDecimalFormat2_max.format(val);
        } else if (val < 10000) {
            return mDecimalFormat.format(val);
        } else {
            return formatAmount10000("", (long) val);
        }
    }

    public static String formatVolumeShou(long tempVal, int exchange, long category)
    {
        double val = getVolumeValue(tempVal, exchange, category);
        if (val < 100) {
            return mDecimalFormat.format(val);
        } else if (val < 10000) {
            return mDecimalFormat.format(val);
        } else {
            return formatAmount10000("", (long) val);
        }
    }

    /**
     * 格式化成交明细的成交量
     */
    public static String formatVolumeForDetail(long tempVal, int exchange, long category) {
        double val = getVolumeValue(tempVal, exchange, category);
        //格式化规则：1.31万，13.1万，131万，1314万
        if (val < 10000) {
            return mDecimalFormat.format(val);
        } else if (val < 100000) {
            //1万<=val<10万
            double newVal = val;
            newVal = newVal / 10000;
            return mDecimalFormat2_max.format(newVal) + "万";
        } else if (val < 1000000) {
            //10万<=val<100万
            double newVal = val;
            newVal = newVal / 10000;
            return mDecimalFormat1_max.format(newVal) + "万";
        } else if (val < 100000000) {
            //100万<=val<1亿
            double newVal = val;
            newVal = newVal / 10000;
            return mDecimalFormat.format(newVal) + "万";
        } else if (val < 1000000000) {
            //1亿<=val<10亿
            double newVal = val;
            newVal = newVal / 100000000;
            return mDecimalFormat2_max.format(newVal) + "亿";
        } else if (val < 10000000000L) {
            //10亿<=val<100亿
            double newVal = val;
            newVal = newVal / 100000000;
            return mDecimalFormat1_max.format(newVal) + "亿";
        } else {
            //100亿<=val
            double newVal = val;
            newVal = newVal / 100000000;
            return mDecimalFormat.format(newVal) + "亿";
        }
    }

    public static double getVolumeValue(long tempVal, int exchange, long category) {
        //* 成交量类型（成交量，持仓量等）
        //* 单位精确到股，各品种股手转换倍率如下
        //* 股票：1手=100股
        //* 债券：1手=10股
        //* 期货：1手=1股（合约）
        double val = tempVal;
        if (isZQ(exchange, category)) {
            val = val / 10;
        } else if (isQH(exchange)) {
            val = val;
        } else {
            val = val / 100;
        }

        return val;
    }

    /**
     * 报告季度
     *
     * @param goods
     * @return
     */
    public static String formatReportQuarter(Goods goods) {
        //（1-3表示1-3季度，0表示4季度）
        if (goods == null) {
            return null;
        }

        String tStr = goods.getValue(GoodsParams.REPORT_QUARTER);
        if ("0".equals(tStr)) {
            return "④";
        } else if ("1".equals(tStr)) {
            return "①";
        } else if ("2".equals(tStr)) {
            return "②";
        } else if ("3".equals(tStr)) {
            return "③";
        } else {
            return null;
        }
    }

    public static String formatDateY_M_D(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        String orgDate = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(orgDate)) {
            return PLACE_HOLDER;
        }

        String fixDate = PLACE_HOLDER;

        int nlen = orgDate.length();
        if (nlen >= 8) {
            String tYear = orgDate.substring(0, 4);
            String tMonth = orgDate.substring(4, 6);
            String tDay = orgDate.substring(6, 8);
            fixDate = String.format("%s-%s-%s", tYear,tMonth,tDay);
        }

        return fixDate;
    }


    /**
     * 商品类型
     *
     * @param exchange
     * @param category i.	指数（含沪指、深指、全球指数等）标签为“指数”，板块标签为“板块”。
     *                 ii.	沪市A股标签为“沪A”，沪市B股标签为“沪B”。
     *                 iii.	深市A股标签为“深A”，深市B股标签为“深B”。
     *                 iv.	港股标签为“港股”，美股标签为“美股”。
     *                 v.	基金（含股票期权、分级A等）标签为“基金”，债券（含上证回购、可转债等）标签为“债券”。
     *                 vi.	股指期货、国际商品标签为“期货”。
     *                 vii.	国际汇率、人民币牌价标签为“外汇”。
     * @return
     */
    public static String getGoodsType(int exchange, long category) {
        if(isSH_HLT(exchange,category)){
            return "GDR";
        }
        else if(isSH_KCB(exchange,category)){
            return "科创";
        } else if (exchange == Exchange.SH && isCategory(category, Category.SH_INDEX) || exchange == Exchange.SZ
                && isCategory(category, Category.SZ_INDEX) || isGlobalIndex(exchange)) {
            return "指数";
        } else if (exchange == Exchange.BK) {
            return "板块";
        } else if (isSHAllA(exchange, category)) {
            return "沪A";
        } else if (isSZAllA(exchange, category)) {
            return "深A";
        } else if (isSH_B(exchange, category)) {
            return "沪B";
        } else if (isSZ_B(exchange, category)) {
            return "深B";
        } else if (exchange == Exchange.HK_STOCKS) {
            return "港股";
        } else if (isZGG(exchange)) {
            return "美股";
        } else if (isJJ(exchange, category) || isGPQQ(exchange, category)) {
            return "基金";
        } else if (isZQ(exchange, category)) {
            return "债券";
        } else if (isQH(exchange)) {
            return "期货";
        } else if (exchange == Exchange.GJ_HL) {
            return "外汇";
        }

        return "其它";
    }

    public static String getGoodsTypeUnion(int exchange, long category) {
        if (isA(exchange, category)) {
            return "沪深Ａ股";
        } else if (isSHAllA(exchange, category)) {
            return "上证Ａ股";
        } else if (exchange == Exchange.SH
                && isCategory(category, Category.SH_B)) {
            return "上证Ｂ股";
        } else if (isSZAllA(exchange, category)) {
            return "深证Ａ股";
        } else if (exchange == Exchange.SZ && isCategory(category, Category.SZ_B)) {
            return "深证Ｂ股";
        } else if (isSHJJ(exchange, category)) {
            return "上证基金";
        } else if (isSZJJ(exchange, category)) {
            return "深证基金";
        } else if (isSHZQ(exchange, category)) {
            return "上证债券";
        } else if (isSZZQ(exchange, category)) {
            return "深证债券";
        } else if (exchange == Exchange.SZ && isCategory(category, Category.SZ_ZXB)) {
            return "中小板";
        } else if (exchange == Exchange.SH && isCategory(category, Category.SH_INDEX) || exchange == Exchange.SZ && isCategory(category, Category.SZ_INDEX)) {
            return "指数";
        } else if (exchange == Exchange.BK && isCategory(category, Category.BK_GN)) {
            return "概念板块";
        } else if (exchange == Exchange.BK && isCategory(category, Category.BK_HY)) {
            return "行业板块";
        } else if (exchange == Exchange.BK && isCategory(category, Category.BK_DQ)) {
            return "地区板块";
        } else if (exchange == Exchange.SZ && isCategory(category, Category.SZ_OEF) || exchange == Exchange.SH && isCategory(category, Category.SH_OEF)) {
            return "开放基金";
        } else if (isGZQH(exchange, category)) {
            return "股指期货";
        } else if (isGuoZaiQH(exchange, category)) {
            return "国债期货";
        } else if (exchange == Exchange.HK_STOCKS && isCategory(category, Category.HK_SH)) {
            return "香港H股";
        } else if (isHKZB(exchange, category)) {
            return "香港主板";
        }
        return "其它";
    }

    public static boolean isQH(int exchange) {
        return exchange == Exchange.GZ_GZ_QH || exchange == Exchange.GN_SPQH || isGJSPQH(exchange);
    }

    /**
     * 是否外盘
     *
     * @param exchange
     * @param category
     * @return
     */
    public static boolean isForeignQuote(int exchange, long category) {
        int stockType = getQuoteType(exchange, category);
        return stockType == QuoteType.TYPE_GLOBALINDEX || stockType == QuoteType.TYPE_ZGG ||
                stockType == QuoteType.TYPE_WH || stockType == QuoteType.TYPE_GJQH;
    }


    public static int getQuoteTypeForNews(Goods goods){
        if (goods.getGoodsId() == Goods.GOODS_DJI.getGoodsId()) {
            return QuoteType.TYPE_DJI;
        }
        if (goods.getGoodsId() == Goods.GOODS_HSI.getGoodsId()) {
            return QuoteType.TYPE_HSI;
        }
        if (goods.getGoodsId() == Goods.GOODS_NASDAQ.getGoodsId()) {
            return QuoteType.TYPE_NASDAQ;
        }

        if (DataUtils.isXSB(goods.exchange, goods.category)) {
            return QuoteType.TYPE_XSB;
        }

        if (DataUtils.isHK_STOCK(goods.exchange, goods.category)) {
            return QuoteType.TYPE_HK_STOCK;
        }

        if (DataUtils.isSB(goods.exchange, goods.category)) {
            return QuoteType.TYPE_SB;
        }

        if (DataUtils.isB(goods.exchange, goods.category)) {
            return QuoteType.TYPE_B;
        }
        return getQuoteType(goods.exchange,goods.category);
    }

    /**
     * 个股类型(个股页面绘制使用)
     * 备注：有些类型有包含关系，判断顺序如下：
     * A.回购->可转债->债券
     * B.分级基金->其它基金
     *
     * @param exchange
     * @param category
     * @return
     */
    public static int getQuoteType(int exchange, long category) {
        int stockType = QuoteType.TYPE_A;
        if (isA(exchange, category)
                || isHK_STOCK(exchange, category)
                || isXSB(exchange, category)
                || isB(exchange, category)
                || isSB(exchange, category)
                || isTSZL(exchange, category)) {
            //A股
            stockType = QuoteType.TYPE_A;
        } else if (isCNIndex(exchange, category)) {
            //指数
            stockType = QuoteType.TYPE_INDEX;
        } else if (exchange == Exchange.BK) {
            //板块（行业、概念、地区）
            stockType = QuoteType.TYPE_BK;
        } else if (isFJJJAB(exchange, category)) {
            //分级基金（分级A、分级B）
            stockType = QuoteType.TYPE_FJ_JJ;
        } else if (isETFAll(exchange, category) || isOEFAndJJALL(exchange, category)) {
            //ETF基金
            stockType = QuoteType.TYPE_ETF;
        } else if (isLOFAll(exchange, category) || isCEFAll(exchange, category)) {
            //LOF、封闭基金
            stockType = QuoteType.TYPE_LOF_CET;
        } else if (isHGAll(exchange, category)) {
            //上证回购、深证回购
            stockType = QuoteType.TYPE_HG;
        } else if (isZZAll(exchange, category)) {
            //可转债
            stockType = QuoteType.TYPE_KZZ;
        } else if (isZQ(exchange, category)) {
            //债券
            stockType = QuoteType.TYPE_ZQ;
        } else if (isZGG(exchange)) {
            //中概股
            stockType = QuoteType.TYPE_ZGG;
        } else if (isGZQH(exchange, category)) {
            //股指期货
            stockType = QuoteType.TYPE_GZQH;
        } else if (exchange == Exchange.GJ_HL) {
            //外汇
            stockType = QuoteType.TYPE_WH;
        } else if (isGlobalIndex(exchange) || isHK_INDEX(exchange, category)) {
            //环球指数
            stockType = QuoteType.TYPE_GLOBALINDEX;
        } else if (isGJSPQH(exchange)) {
            //国际期货
            stockType = QuoteType.TYPE_GJQH;
        } else {
            stockType = QuoteType.TYPE_OTHER;
        }
        return stockType;
    }

    //OEF，JJ(沪市、深市)
    public static boolean isOEFAndJJALL(int exchange, long category) {
        return exchange == Exchange.SH && (isCategory(category, Category.SH_OEF) ||
                isCategory(category, Category.SH_JJ))
                || exchange == Exchange.SZ && (isCategory(category, Category.SZ_OEF) ||
                isCategory(category, Category.SZ_JJ));
    }

    /**
     * 是否国内指数
     */
    public static boolean isCNIndex(int exchange, long category) {
        return exchange == Exchange.SH
                && isCategory(category, Category.SH_INDEX)
                || exchange == Exchange.SZ
                && (isCategory(category, Category.SZ_INDEX)
                || isCategory(category, Category.SZ_XSB_INDEX));
    }

    /**
     * 是否环球指数
     *
     * @param exchange
     * @return
     */
    public static boolean isGlobalIndex(int exchange) {
        //道琼斯,环球指数
        return exchange == Exchange.GLOBAL_INDEX || String.valueOf(exchange).startsWith(String
                .valueOf(Exchange.GLOBAL_INDEX));
    }

    /**
     * 国际商品期货
     *
     * @param exchange
     * @return
     */
    public static boolean isGJSPQH(int exchange) {
        return exchange == Exchange.GJ_SPQH || String.valueOf(exchange).startsWith(String.valueOf
                (Exchange.GJ_SPQH));
    }

    /**
     * 中概股
     *
     * @param exchange
     * @return
     */
    public static boolean isZGG(int exchange) {
        return exchange == Exchange.USA_CN_STOCKS || String.valueOf(exchange).startsWith(String
                .valueOf(Exchange.USA_CN_STOCKS));
    }

    /***
     * 沪港通
     *
     * @param exchange
     * @param category
     * @return
     */
    public static boolean isHGT(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_HGT) ||
                exchange == Exchange.HK_STOCKS && isCategory(category, Category.HK_HGT);
    }

    /***
     * 深港通
     *
     * @param exchange
     * @param category
     * @return
     */
    public static boolean isSGT(int exchange, long category) {
        return exchange == Exchange.SZ && isCategory(category, Category.SZ_SGT) ||
                exchange == Exchange.HK_STOCKS && isCategory(category, Category.HK_SGT);
    }

    //沪深B股集合
    public static boolean isB(int exchange, long category) {
        return isSH_B(exchange, category) || isSZ_B(exchange, category);
    }

    //是否沪市B股
    public static boolean isSH_B(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_B);
    }

    //是否深市B股
    public static boolean isSZ_B(int exchange, long category) {
        return exchange == Exchange.SZ && isCategory(category, Category.SZ_B);
    }

    //是否深市-股转系统-两网及退市（三板）其中的B股退市
    public static boolean isSZ_TS_B(int exchange, long category) {
        return exchange == Exchange.SZ && isCategory(category, Category.SZ_TS) &&
                isCategory(category, Category.SZ_B_TS);
    }

    //基金
    public static boolean isJJ(int exchange, long category) {
        return isSHJJ(exchange, category) || isSZJJ(exchange, category);
    }

    //沪市的基金
    public static boolean isSHJJ(int exchange, long category) {
        return exchange == Exchange.SH && (isCategory(category, Category.SH_CEF) ||
                isCategory(category, Category.SH_OEF) || isCategory(category, Category.SH_ETF)
                || isCategory(category, Category.SH_LOF) ||
                isCategory(category, Category.SH_JJ));
    }


    //深市的基金
    public static boolean isSZJJ(int exchange, long category) {
        return exchange == Exchange.SZ && (isCategory(category, Category.SZ_CEF) ||
                isCategory(category, Category.SZ_OEF) || isCategory(category, Category.SZ_ETF)
                || isCategory(category, Category.SZ_LOF) ||
                isCategory(category, Category.SZ_JJ));
    }

    //港股-主板
    public static boolean isHKZB(int exchange, long category) {
        return exchange == Exchange.HK_STOCKS && isCategory(category, Category.HK_ZB);
    }
    //港股通（港沪通）
    public static boolean isHKSH(int exchange, long category) {
        return exchange == Exchange.HK_STOCKS && isCategory(category, Category.HK_HGT);
    }

    //港股-AH
    public static boolean isAH(int exchange, long category) {
        return exchange == Exchange.HK_STOCKS && isCategory(category, Category.HK_AH);
    }

    //沪深A股集合
    public static boolean isA(int exchange, long category) {
        return isSHAllA(exchange, category) || isSZAllA(exchange, category);
    }

    //沪市的所有A股
    public static boolean isSHAllA(int exchange, long category) {
        return exchange == Exchange.SH
                && (isCategory(category, Category.SH_A) || (isCategory(category, Category.SH_ST)
                && !isCategory(category, Category.SH_B)));
    }

    //深市的所有A股
    public static boolean isSZAllA(int exchange, long category) {
        return exchange == Exchange.SZ && (isCategory(category, Category.SZ_A) ||
                isCategory(category, Category.SZ_ZXB) || isCategory(category, Category.SZ_CYB)
                || (isCategory(category, Category.SZ_ST) && !isCategory(category, Category.SZ_B)));
    }

    //港股-指数
    public static boolean isHK_INDEX(int exchange, long category) {
        return exchange == Exchange.HK_STOCKS && isCategory(category, Category.HK_INDEX);
    }

    //港股(不含指数)
    public static boolean isHK_STOCK(int exchange, long category) {
        return exchange == Exchange.HK_STOCKS && !isCategory(category, Category.HK_INDEX);
    }

    //债券
    public static boolean isZQ(int exchange, long category) {
        return isSHZQ(exchange, category) || isSZZQ(exchange, category);
    }

    //沪市的债券
    public static boolean isSHZQ(int exchange, long category) {
        return exchange == Exchange.SH && (isCategory(category, Category.SH_ZQ) ||
                isCategory(category, Category.SH_ZZ)
                || isCategory(category, Category.SH_HG));
    }

    //深市的债券
    public static boolean isSZZQ(int exchange, long category) {
        return exchange == Exchange.SZ && (isCategory(category, Category.SZ_ZQ) ||
                isCategory(category, Category.SZ_ZZ) || isCategory(category, Category.SZ_HG));
    }

    //转债(沪、深)
    public static boolean isZZAll(int exchange, long category) {
        return exchange == Exchange.SH && (isCategory(category, Category.SH_ZZ)) ||
                exchange == Exchange.SZ && (isCategory(category, Category.SZ_ZZ));
    }

    //回购(沪、深)
    public static boolean isHGAll(int exchange, long category) {
        return isSHHG(exchange, category) || isSZHG(exchange, category);
    }

    //深证回购
    public static boolean isSZHG(int exchange, long category) {
        return exchange == Exchange.SZ && isCategory(category, Category.SZ_HG);
    }

    //上证回购
    public static boolean isSHHG(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_HG);
    }

    //股票期权
    public static boolean isGPQQ(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_OPTIONS);
    }

    //股指期货
    public static boolean isGZQH(int exchange, long category) {
        return exchange == Exchange.GZ_GZ_QH && isCategory(category, Category.GZGZQH_INDEX_QH);
    }

    //国债期货
    public static boolean isGuoZaiQH(int exchange, long category) {
        return exchange == Exchange.GZ_GZ_QH && isCategory(category, Category.GZGZQH_ND_QH);
    }


    //国际汇率
    public static boolean isGJHL(int exchange) {
        return exchange == Exchange.GJ_HL;
    }

    //分级基金(沪市、深市 分级A，分级B)
    public static boolean isFJJJAB(int exchange, long category) {
        return isFJA(exchange, category) || isFJB(exchange, category);
    }

    public static boolean isFJA(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_JJ_A) || exchange == Exchange
                .SZ && isCategory(category, Category.SZ_JJ_A);
    }

    public static boolean isFJB(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_JJ_B) || exchange == Exchange
                .SZ && isCategory(category, Category.SZ_JJ_B);
    }

    /**
     * LOF+ETF(沪市、深市)
     */
    public static boolean isLOFAndETFAll(int exchange, long category) {
        return isLOFAll(exchange, category)
                || isETFAll(exchange, category);
    }

    //ETF(沪市、深市)
    public static boolean isETFAll(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_ETF) ||
                exchange == Exchange.SZ && isCategory(category, Category.SZ_ETF);
    }

    //LOF(沪市、深市)
    public static boolean isLOFAll(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_LOF) ||
                exchange == Exchange.SZ && isCategory(category, Category.SZ_LOF);
    }

    //CEF(沪市、深市)
    public static boolean isCEFAll(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_CEF) ||
                exchange == Exchange.SZ && isCategory(category, Category.SZ_CEF);
    }

    //新三板
    public static boolean isXSB(int exchange, long category) {
        return exchange == Exchange.SZ && (isCategory(category, Category.SZ_XSBXYZL)
                || isCategory(category, Category.SZ_XSBZSZL) ||
                isCategory(category, Category.SZ_XSBCXC) || isCategory(category, Category.SZ_XSBJCC)
                || isCategory(category, Category.SZ_XSBJJJL));
    }

    //新三板+指数
    public static boolean isXSBAll(int exchange, long category) {
        return exchange == Exchange.SZ && isCategory(category, Category.SZ_XSB_INDEX)
                || isXSB(exchange, category);
    }

    //深市-股转系统-两网及退市（三板）
    public static boolean isSB(int exchange, long category) {
        return exchange == Exchange.SZ && isCategory(category, Category.SZ_TS);
    }

    //退市整理-沪深
    public static boolean isTSZL(int exchange, long category) {
        return isSH_TSZL(exchange, category) || isSZ_TSZL(exchange, category);
    }

    //沪市退市整理
    public static boolean isSH_TSZL(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_Z);
    }

    //深市退市整理
    public static boolean isSZ_TSZL(int exchange, long category) {
        return exchange == Exchange.SZ && isCategory(category, Category.SZ_Z);
    }

    //科创板
    public static boolean isSH_KCB(int exchange, long category) {
        return exchange == Exchange.SH && isCategory(category, Category.SH_CCB);
    }

    public static boolean isSH_HLT(int exchange,long category){
        return exchange == Exchange.SH && isCategory(category, Category.SH_HLT);
    }

    /**
     * 人民币牌价
     *
     * @param exchange
     * @return
     */
    public static boolean isRMB_PJ(int exchange) {
        return exchange == Exchange.RMB_PJ;
    }

    public static boolean isBk(int exchange,long category)
    {
        return (exchange == Exchange.BK && (isCategory(category, Category.BK_GN) || isCategory(category, Category.BK_HY) || isCategory(category, Category.BK_DQ)));
    }

    /**
     * 是否显示交易时间
     * 除A股、港股、指数、板块外均不显示交易时间
     *
     * @return
     */
    public static boolean isShowMarketTime(int exchange, long category) {
        return isSHAllA(exchange, category) || isSZAllA(exchange, category) || exchange == Exchange
                .HK_STOCKS || isCNIndex(exchange, category) || exchange == Exchange.BK ||
                isXSBAll(exchange, category);
    }

    /**
     * 将百分比数字转化为小数
     */
    public static String formatPercent2Float(String percent) {
        double value = convertToDouble(percent);
        value = value / 100;
        return mDecimalFormat4.format(value);
    }

    /**
     * 是否显示盘口
     * 显示：AB股,港股,新三板,板块,指数,中概股,股指期货,基金（分级基金、ETF、LOF、其他）
     * 不显示：债券（回购、可转债、其他）,环球指数,外汇,人民币牌价,国际商品期货,三板（两网及退市）,港股指数
     *
     * @param exchange
     * @param category
     * @return
     */
    public static boolean isShowPanKou(int exchange, long category) {
//        if (isZQ(exchange, category) || isGlobalIndex(exchange) || isHK_INDEX(exchange, category)
//                || exchange == Exchange.GJ_HL || isRMB_PJ(exchange) || isGJSPQH(exchange) || isSB
//                (exchange, category)) {
//            return false;
//        }
        return isA(exchange, category)
                || isB(exchange, category)
                || isHK_STOCK(exchange, category)
                || isXSB(exchange, category)
                || exchange == Exchange.BK
                || isCNIndex(exchange, category)
                || isZGG(exchange)
                || isGZQH(exchange, category)
                || isJJ(exchange, category);
    }

    public static String formatValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return PLACE_HOLDER;
        } else {
            return value;
        }
    }

    public static String formatSumRate(long sumRate) {
        return mDecimalFormat2.format(sumRate / 100.0) + "%";
    }

    public static String formatSumAndRate(long sum, long sumRate) {
        if (sum == 0 || sumRate == 0) {
            return PLACE_HOLDER;
        }
        return Math.round(sum / 100000000.0) + "(" + formatSumRate(sumRate) + ")";
    }

    /**
     * 是否显示买卖档和交易明细
     *
     * @return
     */
    public static boolean isShowTradDetail(int exchange, long category) {
        //A股、基金、债券、三板、新三板、股票期权、B股
        return isA(exchange, category)
                || isJJ(exchange, category)
                || isZQ(exchange, category)
                || isSB(exchange, category)
                || isXSB(exchange, category)
                || isGPQQ(exchange, category)
                || isB(exchange, category);
    }

    //反射格式化字符串
    public static String getFormatData(Goods goods, Field field) {
        String formatMethod = field.format;
        if (!TextUtils.isEmpty(formatMethod)) {
            try {
//                Class clazz = Class.forName("utils.DataUtils");
                Class clazz = Class.forName("cn.wjf.approomorm.utils.DataUtils");
                Method formatFunc = clazz.getDeclaredMethod(formatMethod, Goods.class, int.class);
                return (String) formatFunc.invoke(null, goods, field.param);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "--";
    }

    public static String formatJiePanItemTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return "";
        }
        title = "【" + title + "】";
        return title;
    }

    public static int calculateChangeRatio(long price, long close) {
        if (close == 0) {
            return 0;
        }

        if (price > close) {
            return (int) ((100000 * (price - close) / close + 5) / 10);
        } else if (price < close) {
            return (int) ((100000 * (price - close) / close - 5) / 10);
        } else {
            return 0;
        }
    }

    /**
     * 判断catogry统一使用此方式
     */
    public static boolean isCategory(long category, long targetCategory) {
        return (category & targetCategory) > 0;
    }


    /**
     * 获取当前使用的资金字段ID
     *
     * @param isJG 机构还是游资
     * @param is5  当日还是五日
     * @return FieldsId
     */
    public static int getZJFieldsId(boolean isJG, boolean is5) {
        //当日机构
        if (isJG && !is5) {
            return GoodsParams.DIFF_AMT_SUPER_DAY;
        }
        //当日游资
        if (!isJG && !is5) {
            return GoodsParams.DIFF_AMT_BIG_DAY;
        }
        //五日机构
        if (isJG) {
            return GoodsParams.DIFF_AMT_SUPER_DAY5;
        }
        //五日游资
        return GoodsParams.DIFF_AMT_BIG_DAY5;
    }

    public static float formatZJPercent(Goods goods, boolean isJG, boolean is5) {
        if (goods == null) {
            return 0;
        }
        long numerator = 0;
        long denominator = 0;
        if (!is5) {
            if (!TextUtils.isEmpty(goods.getValue(GoodsParams.AMOUNT))) {
                denominator = Long.parseLong(goods.getValue(GoodsParams.AMOUNT));
            }

            if (isJG) {
                if (!TextUtils.isEmpty(goods.getValue(GoodsParams.DIFF_AMT_SUPER_DAY))) {
                    numerator = Long.parseLong(goods.getValue(GoodsParams.DIFF_AMT_SUPER_DAY));
                }
            } else {
                if (!TextUtils.isEmpty(goods.getValue(GoodsParams.DIFF_AMT_SUPER_DAY))) {
                    numerator = Long.parseLong(goods.getValue(GoodsParams.DIFF_AMT_BIG_DAY));
                }
            }
        } else {
            long amount1 = 0;
            long amount4 = 0;
            if (!TextUtils.isEmpty(goods.getValue(GoodsParams.AMOUNT))) {
                amount1 = Long.parseLong(goods.getValue(GoodsParams.AMOUNT));
            }
            if (!TextUtils.isEmpty(goods.getValue(GoodsParams.AMOUNT4))) {
                amount4 = Long.parseLong(goods.getValue(GoodsParams.AMOUNT4));
            }
            denominator = amount1 + amount4;

            if (isJG) {
                if (!TextUtils.isEmpty(goods.getValue(GoodsParams.DIFF_AMT_SUPER_DAY5))) {
                    numerator = Long.parseLong(goods.getValue(GoodsParams.DIFF_AMT_SUPER_DAY5));
                }
            } else {
                if (!TextUtils.isEmpty(goods.getValue(GoodsParams.DIFF_AMT_BIG_DAY5))) {
                    numerator = Long.parseLong(goods.getValue(GoodsParams.DIFF_AMT_BIG_DAY5));
                }
            }
        }

        if (denominator == 0 || numerator == 0) {
            return 0;
        } else {
            double result = (double) numerator / (double) denominator;
            return ((float) result) * 100;
        }
    }

    /**
     * 根据涨跌返回涨、跌、平标志
     */
    public static int getPriceRiseFall(Goods goods) {
        if (goods == null || goods.getValue(GoodsParams.CHANGE_PRC) == null) {
            return PRICE_EQUAL;
        }
        double price = convertToDouble(goods.getValue(GoodsParams.CHANGE_PRC));
        if (price > 0) {
            return PRICE_RISE;
        } else if (price == 0) {
            return PRICE_EQUAL;
        } else {
            return PRICE_FALL;
        }
    }

    public static String formatFocusVolume(float mValue, int exchange, long category) {
        String value = "";
        Float fValue = mValue;
        if (!fValue.isNaN()) {
            value = DataUtils.formatVolume((long) mValue, exchange, category);
        }
        return value;
    }

    public static String formatFocusPrice(float mValue, int exchange, long category) {
        String value = "";
        Float fValue = mValue;
        if (!fValue.isNaN()) {
            value = DataUtils.formatPrice(String.valueOf(mValue * 10000), exchange, category);
        }
        return value;
    }

    public static String formatDiv100(Goods goods, int param) {
        String value = goods.getValue(param);
        long l = convertToLong(value);
        return l / 100 + "";
    }

    public static String formatCPX(Goods goods, int param) {
        String value = goods.getValue(param);
        long l = convertToLong(value);
        return Math.abs(l) + "";
    }

    public static String formatKP(Goods goods, int param) {
        try {
            int value = Math.abs(Integer.valueOf(goods.getValue(param)));
            switch (value) {
                case 1:
                    return "双平";
                case 2:
                    return "双开";
                case 3:
                    return "多换";
                case 4:
                    return "空换";
                case 5:
                    return "多开";
                case 6:
                    return "空开";
                case 7:
                    return "空平";
                case 8:
                    return "多平";
            }
        } catch (Exception e) {

        }
        return "--";
    }

    public static SimpleDateFormat FORMAT_yyMMdd = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatInfoDate(Goods goods, int param) {
        try {
            long timeStamp = Long.valueOf(goods.getValue(param));
            if (timeStamp == 0) {
                return "";
            }
            Date date = new Date(timeStamp);
            return FORMAT_yyMMdd.format(date);
        } catch (Exception e) {
            return "--";
        }
    }

    public static String formatHHmmTime(Goods goods, int param) {
        try {
            String value = goods.getValue(param);
            Integer time = Integer.valueOf(value);
            return String.format("%02d:%02d", time / 100, time % 100);
        } catch (Exception e) {
            return "--";
        }
    }

    public static String formatFundSize(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String amount = formatAmount(goods.getValue(goodsParam));
//        String date = formatInfoDate(goods, GoodsParams.FUND_TOTAL_SHARES_GROWTH_DATETIME);
        return amount;
    }

    /**
     * 资金可用
     *
     * @param goods
     * @param goodsParam
     * @return
     */
    public static String formatFundsAvailableDate(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        String ymd = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(ymd)) {
            return PLACE_HOLDER;
        }

        return DateUtils.formatDateM_D(ymd, "-");
    }


    public static String formatDays(Goods goods,int goodsParam){
        return formateValueOfUnit(goods,goodsParam,"天");
    }


    public static boolean isCnGoods(int exchange) {
        return exchange == Exchange.SH || exchange == Exchange.SZ || exchange == Exchange.BK || exchange == Exchange.GZ_GZ_QH;
    }

    public static String formatValueNonnegative(int value) {
        if (value < 0) {
            return PLACE_HOLDER;
        } else {
            return String.valueOf(value);
        }
    }

    public static String formatMinuteTime(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }
        String sTimeStamp = goods.getValue(goodsParam);
        long lTimeStamp = DataUtils.convertToLong(sTimeStamp);
        if (lTimeStamp == 0) {
            return PLACE_HOLDER;
        }
        return DateUtils.formatInfoDate(lTimeStamp, DateUtils.mFormatHM);
    }

    /**
     * 直接获取值(绝对值)
     */
    public static String getValueABS(Goods goods, int goodsParam) {
        if (goods == null) {
            return PLACE_HOLDER;
        }

        if (goods.isSuspension() && isValueEqualsZero(goods, goodsParam)) {
            return PLACE_HOLDER;
        }

        String tStr = goods.getValue(goodsParam);
        if (TextUtils.isEmpty(tStr)) {
            return PLACE_HOLDER;
        } else {
            return String.valueOf(Math.abs(convertToLong(tStr)));
        }
    }

    public static String formatDecimal(Object value, String decimal) {
        if (value == null) {
            Log.e("wjf>>>>","value为空，无法格式化");
            return "";
        }

        DecimalFormat decimalFormat = new DecimalFormat(decimal);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(value);
    }


    public static String formatGPCTime(Goods goods,int goodParam) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat sdfHHmm = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdfMMdd = new SimpleDateFormat("MM-dd");
        if (goods == null) {
            return PLACE_HOLDER;
        }
        try {
            Date date = sdf.parse(goods.getValue(goodParam));
            if (DateUtils.isToday(date)) {
                return sdfHHmm.format(date);
            } else {
                return sdfMMdd.format(date);
            }
        } catch (Exception e) {

        }
        return PLACE_HOLDER;
    }


}
