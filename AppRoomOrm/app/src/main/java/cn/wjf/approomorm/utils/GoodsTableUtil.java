package cn.wjf.approomorm.utils;

import cn.wjf.approomorm.data.Category;
import cn.wjf.approomorm.data.Exchange;

public class GoodsTableUtil {
    /*
    -A股:        1000+
        -沪市:        1000
        -深市:        1300+
            -普通:        1300
            -中小:        1310
            -创业:        1320
        -版块:        1500
        -指数:        1600+
            -上证指数:      1600
            -深证指数:      1610
            -港股指数:      1620
            -全球指数:      1630
    -B股:        2000+
        -沪市B股       2000
        -深市B股       2100
        -其它B股       2200
    -港股:        3000
    -三板:        4000
    -基金:        5000
    -债券:        6000
    -美股:        7000
    -其它:        8000+
        -其它     8000
     */

    public static int getSearchWeigth(int exchange, long category) {
        if (DataUtils.isSHAllA(exchange, category)) {//沪市A股
            return 1000;
        } else if (DataUtils.isSZAllA(exchange, category)) {//深市A股
            if (DataUtils.isCategory(category, Category.SZ_ZXB)) {//中小板
                return 1310;
            } else if (DataUtils.isCategory(category, Category.SZ_CYB)) {//创业板
                return 1320;
            } else {//普通深市A股
                return 1300;
            }
        } else if (exchange == Exchange.BK) {//版块
            return 1500;
        }
        //指数
        else if (exchange == Exchange.SH && DataUtils.isCategory(category, Category.SH_INDEX)) {//沪市指数
            return 1600;
        } else if (exchange == Exchange.SZ && DataUtils.isCategory(category, Category.SZ_INDEX)) {//深指数
            return 1610;
        } else if (DataUtils.isHK_INDEX(exchange, category)) {//港股指数
            return 1620;
        } else if (DataUtils.isGlobalIndex(exchange)) {//全球指数
            return 1630;
        } else if (DataUtils.isB(exchange, category)) { //B股
            if (DataUtils.isSH_B(exchange, category)) {//沪市B股
                return 2000;
            } else if (DataUtils.isSZ_B(exchange, category)) {//深市B股
                return 2100;
            } else {//其它B股
                return 2200;
            }
        } else if (DataUtils.isHK_STOCK(exchange, category)) {//港股,不包含指数
            return 3000;
        } else if (DataUtils.isXSB(exchange, category)) {//三板
            return 4000;
        } else if (DataUtils.isJJ(exchange, category)) {//基金
            return 5000;
        } else if (DataUtils.isZQ(exchange, category)) {//债券
            return 6000;
        } else if (DataUtils.isZGG(exchange)) {//美股中概股
            return 7000;
        } else {
            return 8000;
        }

    }

    /**
     * 去除空格,全角转半角,字母小写
     *
     * @param orgName
     * @return
     */
    public static String getFromatGoodsName(String orgName) {

        if (orgName == null || orgName.length() == 0)
            return "";
        orgName = orgName.toLowerCase();
        orgName = orgName.replaceAll(" ", "").replaceAll("　", "").replaceAll("-", "").replaceAll("－", "");
        StringBuilder myStr = new StringBuilder();
        int da = 'ａ' - 'a';
        int d0 = '０' - '0';
        for (int i = 0; i < orgName.length(); i++) {
            char vChar = orgName.charAt(i);
            // 若是字母则直接输出
            if (vChar >= 'ａ' && vChar <= 'ｚ') {
                vChar = (char) ((int) vChar - da);
                myStr.append(vChar);
            } else if (vChar >= '０' && vChar <= '９') {
                vChar = (char) ((int) vChar - d0);
                myStr.append(vChar);
            } else {
                myStr.append(vChar);
            }
        }
        return myStr.toString();
    }

}
