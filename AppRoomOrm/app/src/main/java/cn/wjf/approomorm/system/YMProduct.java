package cn.wjf.approomorm.system;

import cn.wjf.approomorm.BuildConfig;

public class YMProduct {
    public static String channel = "8888";
    public static final String MOBILE_OS = "10";//平台
    public static final String version = BuildConfig.VERSION_NAME;
    public static final String MODEL = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;//"Android"; //机型
    public static String appName = "EStockL2";
    public static final String PRODUCT_ID =  "2";
    public static final String APP_NAME="Orm数据库";//BuildConfig.appName;

    
    public static boolean isPlus() {
        return BuildConfig.APPLICATION_ID.equals("cn.emoney.pf");
    }
}
