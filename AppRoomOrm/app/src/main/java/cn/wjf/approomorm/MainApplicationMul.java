package cn.wjf.approomorm;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import cn.wjf.approomorm.db.stockdb.StockListSqliteHelper;
import cn.wjf.approomorm.network.HttpCodeProcessInterface;
import cn.wjf.approomorm.network.NetworkHelper;
import cn.wjf.approomorm.network.OkHttpUtil;
import cn.wjf.approomorm.network.RxException;
import cn.wjf.approomorm.system.YMProduct;

public class MainApplicationMul extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        StockListSqliteHelper.init(this);
        initNet();
    }

    private void initNet() {
        NetworkHelper.init(YMProduct.appName, YMProduct.version);
        OkHttpUtil.doInit(this, "", false, 1);
        NetworkHelper.setHttpCodeProcessPlug(new HttpCodeProcessInterface() {

            @Override
            public void processOK(int i, String s) {

            }

            @Override
            public RxException processErr(int i, String s) {
                Log.e("wjf>>>>", "network_error" + String.format("error code:%d,protocalId:%s", i, s));
                return new RxException(i, s);
            }
        });
//        ReqWrap.init(YMProduct.MOBILE_OS, YMProduct.PRODUCT_ID, YMProduct.version, YMProduct.channel);
    }

}
