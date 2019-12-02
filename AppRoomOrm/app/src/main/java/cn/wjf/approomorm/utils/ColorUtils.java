package cn.wjf.approomorm.utils;

import android.text.TextUtils;

import androidx.annotation.ColorInt;

import java.lang.reflect.Method;

import cn.wjf.approomorm.R;
import cn.wjf.approomorm.data.Field;
import cn.wjf.approomorm.data.Goods;
import cn.wjf.approomorm.data.GoodsParams;

import static cn.wjf.approomorm.utils.DataUtils.convertToLong;

public class ColorUtils {

    public static int ZJBY[][] = {
            {0xFFDB06E6,0xFFdbb80f,0xFF2998C2,0xFF57db6e},//white
            {0xFFDB06E6, 0xFFFFFF00, 0xFF2998C2, 0xFF00CE00}//black
    };//分时资金博弈
    public static int g_rgbInd[] = {0xFFFFFFFF, 0xFFFFFF00, 0xFFFF60FF, 0xFFFF4930, 0xFF00FFFF, 0XFF464646, 0XFFFFFFFF, 0XFF705223, 0XFF6D416E, 0xFFDB06E6, 0xFF2998C2, 0xFF00CE00};
    public static int g_rgbInd_w[] = {0xFF646464, 0xFFdbb80f, 0xFFe958e9, 0xFFfe4830, 0xFF0086c4, 0xFF464646, 0xFF646464, 0xFF705223, 0xFF6D416E, 0xFFDB06E6, 0xFF2998C2, 0xFF57db6e};


    public static int T1 = 0xDDDDDD;
    public static int T12 = 0xDDDDDD;
    public static int B2 = 0x121212;
    public static int C1 = 0xED1C24;
    public static int C2 = 0xED1C24;
    public static int C3 = 0x00ff60;
    public static int C4 = 0x00A727;
    public static int C5 = 0x4690EF;
    public static int C7 = 0xF6951D;
    public static int SP18 = 0x00ffff;
    public static int SP19 = 0xffff00;
    public static int SP33 = 0xFF0000;
    public static int SP34 = 0x00FF00;

    /**
     * 通用：根据涨跌值的正负来返回红涨绿跌
     *
     * @return
     */
    public static int getColorByZD(String zdStr) {
        if (TextUtils.isEmpty(zdStr)) {
            return T1;
        }

        double zd = DataUtils.convertToDouble(zdStr);
        return zd > 0 ? C1 : (zd == 0 ? T1 : C3);
    }

    public static int getColorByZDQuoteTitle(String zdStr) {
        if (TextUtils.isEmpty(zdStr)) {
            return T12;
        }

        double zd = DataUtils.convertToDouble(zdStr);
        return zd > 0 ? SP33 : (zd == 0 ? T12 : SP34);
    }


    public static int getSignByZD(String zdStr) {
        if (TextUtils.isEmpty(zdStr)) {
            return 0;
        }

        double zd = DataUtils.convertToDouble(zdStr);
        return zd > 0 ? R.mipmap.ic_sign_up_small : (zd == 0 ? 0 : R.mipmap.ic_sign_down_small);
    }

    public static int getColorByZD(long zd) {
        return zd > 0 ?  C1 : (zd == 0 ? T1 :  C3);
    }

    public static int getColorByZD(Goods goods, int goodsParam) {
        if (goods == null) {
            return T1;
        }
        if ("1".equals(goods.getValue(GoodsParams.CLO_PRC_BF4))) {
            return T1;
        }

        String zd = goods.getValue(GoodsParams.CHANGE_PRC);
        return getColorByZD(zd);
    }

    public static int getZDFBgColor(String zdStr) {
        //特殊处理,白版时要显示涨跌(停)深浅红绿背景,停牌背景
        //黑版时不显示任何背景
        if (TextUtils.isEmpty(zdStr)) {
            return T1;
        }

        long zd = convertToLong(zdStr);

        return zd > 0 ? C1 : (zd == 0 ? T1 : C3);
    }


    public static int getZDFBgColor(Goods goods, int goodsParam) {
        String zd = goods.getValue(GoodsParams.CHANGE_PRC);
        return getZDFBgColor(zd);

    }

    public static String getARGBHexStringColorByZD(String zdStr) {
        int iColor = getColorByZD(zdStr);
        String hexStr = "#" + Integer.toHexString(iColor);
        return hexStr;
    }

    public static String getRGBHexStringColorByZD(String zdStr) {
        int iColor = getColorByZD(zdStr) & 0xffffff;

        String hexStr = "#" + Integer.toHexString(iColor);
        return hexStr;
    }

    /**
     * 通用：根据value值的正负来返回红涨绿跌
     */
    public static int getColorByPoM(String value) {
        if (TextUtils.isEmpty(value)) {
            return T1;
        }

        return getColorByPoM(convertToLong(value));
    }

    public static int getColorByPoM(Goods goods, int goodsParam) {

        return getColorByPoM(goods, goodsParam, false);
    }

    public static int getColorByPoM(Goods goods, int goodsParam, boolean ignoreSuspension) {
        if (goods == null) {
            return T1;
        }

        if (!ignoreSuspension && "1".equals(goods.getValue(GoodsParams.CLO_PRC_BF4))) {
            return T1;
        }

        String value = goods.getValue(goodsParam);
        return getColorByPoM(value);
    }

    public static int getColorByPoM(int value) {
        return value > 0 ? C1 : (value == 0 ? T1 : C3);
    }

    public static int getColorByPoM(float value) {
        return value > 0 ? C1 : (value == 0 ? T1 : C3);
    }

    public static int getColorByPoM(long value) {
        return value > 0 ? C1 : (value == 0 ? T1 : C3);
    }

    /**
     * 通用：返回正常黑色
     */
    public static int getNormalColor() {
        return T1;
    }

    public static int getC7(Goods goods, int goodsParam) {
        return  C7;
    }

    public static int getNormalColor(Goods goods, int goodsParam) {
        return getNormalColor();
    }

    /**
     * 通用：返回红色
     */
    public static int getRedColor() {
        return C1;
    }

    public static int getRedColor(Goods goods, int goodsParam) {
        return getRedColor();
    }

    /**
     * 通用：返回绿色
     */
    public static int getGreenColor() {
        return C3;
    }

    /**
     * 通用：返回黄色
     */
    public static int getYellowColor() {
        return C4;
    }

    /**
     * 通用：返回蓝色
     */
    public static int getBlueColor() {
        return C5;
    }

    public static int getGreenColor(Goods goods, int goodsParam) {
        return getGreenColor();
    }

    public static int getYellowColor(Goods goods, int goodsParam) {
        return  SP19;
    }

    public static int getBlueColor(Goods goods, int goodsParam) {
        return  SP18;
    }

    /**
     * 最高 高低 开盘 等
     *
     * @return
     */
    public static int getColorByLastClose(Goods goods, int goodsParam) {
        if (goods == null) {
            return T1;
        }

        if ("1".equals(goods.getValue(GoodsParams.CLO_PRC_BF4))) {
            return T1;
        }

        return getColorByLastClose(goods.getValue(goodsParam), goods.getValue(GoodsParams.PRE_CLO_PRC));
    }

    public static int getColorByLastClose(String value, String closePrc) {
        if (TextUtils.isEmpty(value)) {
            return T1;
        }

        double t = DataUtils.convertToDouble(value);
        double tLastColse = DataUtils.convertToDouble(closePrc);
        return getColorByLastClose((int) t, (int) tLastColse);
    }

    public static int getColorByLastClose(int value, int closePrc) {
        int temp = value - closePrc;
        return temp > 0 ? C1 : (temp == 0 ? T1 : C3);
    }

    /**
     * 仅适用于沪深、港股、板块等分组列表数据的颜色处理
     *
     * @return
     */
    public static int getColorForMarketGroupList(int dataType, Goods goods) {
        if (goods == null) {
            return T1;
        }

        switch (dataType) {
            case GoodsParams.CHANGE_PRC:
                //涨幅榜,跌幅榜
                return getColorByPoM(goods, GoodsParams.CHANGE_PRC);
            case GoodsParams.SPEED_RATIO:
                //5分钟快速涨幅:
                return getColorByPoM(goods, GoodsParams.SPEED_RATIO);
            case GoodsParams.QUANTITY_RELATIVE_RATIO:
                //量比榜
                return T1;
            case GoodsParams.BIG_ORDER_INFLOW_AMT:
                //资金净流入，资金净流出
                return getColorByPoM(goods, GoodsParams.BIG_ORDER_INFLOW_AMT);
            case GoodsParams.AMOUNT:
            case GoodsParams.TURNOVER_RATIO:
                //换手率榜
                //成交额榜
                return T1;
            default:
                return T1;
        }
    }

    public static int getColorByIndexValue(int colorValue) {
        if (colorValue == -1) {
            return C3;
        } else if (colorValue == 1) {
            return C1;
        } else {
            return T1;
        }
    }


    //反射获取颜色
    public static int getTextColor(Goods goods, Field field, int defaultColor) {
        String colorMethod = field.color;
        if (!TextUtils.isEmpty(colorMethod)) {
            try {
                Class clazz = Class.forName("cn.emoney.level2.util.ColorUtils");
                Method colorFunc = clazz.getDeclaredMethod(colorMethod, Goods.class, int.class);
                return (int) colorFunc.invoke(null, goods, field.param);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return defaultColor;
    }

    public static int getBgColor(Goods goods, Field field, int defaultColor) {
        String bgColorMethod = field.bgColor;
        if (!TextUtils.isEmpty(bgColorMethod)) {
            try {
                Class clazz = Class.forName("cn.emoney.level2.util.ColorUtils");
                Method colorFunc = clazz.getDeclaredMethod(bgColorMethod, Goods.class, int.class);
                return (int) colorFunc.invoke(null, goods, field.param);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return defaultColor;
    }


    /**
     * 根据提供的alpha和color值获得带透明度的color值
     * ex: formatColor(90, 0xaaffff) = 0xe6aaffff, formatColor(90, 0x80aaffff) = 0xe6aaffff
     *
     * @param alpha 0 - 100 (0代表完全透明， 100代表完全不透明)
     * @param color 颜色值（如原来的颜色值带有透明度，则该透明度无效, 将被新的透明度取代）
     * @return argb颜色值
     */
    @ColorInt
    public static int formatColor(int alpha, @ColorInt int color) {
        if (alpha < 0) {
            alpha = 0;
        }
        if (alpha > 100) {
            alpha = 100;
        }

        return (Math.round(0xff * alpha / 100f) << 24) + (color & 0x00ffffff);
    }


    public static String getRGBHexStringColor(int color) {
        int iColor = color & 0xffffff;
        String hexStr = "#" + Integer.toHexString(iColor);
        return hexStr;
    }

    /**
     * 获取分时明细买卖方向
     */
    public static int getTradeDirectionColor(int flag) {
        //买是1, 卖是-1,平是0
        if (flag == 1) {
            return C1;
        } else if (flag == -1) {
            return C3;
        } else {
            return T1;
        }
    }

    public static int getSignColor(Goods goods, int goodsParam) {
        if (goods == null) {
            return T1;
        }
        String value = goods.getValue(goodsParam);
        return getSignColor(value);
    }

    public static int getSignColor(String value) {
        try {
            Double aDouble = Double.valueOf(value);
            if (aDouble > 0) {
                return C1;
            } else if (aDouble < 0) {
                return C3;
            } else {
                return T1;
            }
        } catch (Exception e) {

        }
        return T1;
    }

    public static int getHoldColor(Goods goods, int goodsParam) {
        try {
            double value = Double.valueOf(goods.getValue(goodsParam));
            double price = Double.valueOf(goods.getValue(GoodsParams.CLO_PRC));
            if (value > price) return C3;
            if (value < price) return C1;
        } catch (Exception e) {

        }
        return T1;
    }

    public static int getZDFBgColor_normal(Goods goods, int goodsParam) {
        //特殊处理,白版时要显示涨跌(停)深浅红绿背景,停牌背景
        if (goods == null) {
            return  B2;
        }
        String sZd = goods.getValue(GoodsParams.CHANGE_PRC);

        long zd = convertToLong(sZd);


        return zd == 0 ?  B2 : (zd > 0 ?  C2 :  C4);
    }

    public static int getColorByType(int type) {
        switch (type) {
            case 1:
                return  C1;
            case 2:
                return  C3;
            default:
                return T1;

        }
    }

    public static int[] getIndRgb() {
//        return style == BLACK ? g_rgbInd : g_rgbInd_w;
        return g_rgbInd;
    }
}
