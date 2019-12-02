package cn.wjf.approomorm.buinesses;

import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableInt;

import com.google.protobuf.nano.InvalidProtocolBufferNanoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import cn.wjf.approomorm.data.Category;
import cn.wjf.approomorm.data.Exchange;
import cn.wjf.approomorm.db.stockdb.StockList;
import cn.wjf.approomorm.db.stockdb.StockListDataBase;
import cn.wjf.approomorm.nano.BaseResponse;
import cn.wjf.approomorm.nano.CodeListUpdateRequest;
import cn.wjf.approomorm.nano.CodeListUpdateResponse;
import cn.wjf.approomorm.network.BusinessPackage;
import cn.wjf.approomorm.network.NetworkHelper;
import cn.wjf.approomorm.network.RequestRet;
import cn.wjf.approomorm.protocol.XProtocol;
import cn.wjf.approomorm.utils.DataUtils;
import cn.wjf.approomorm.utils.GoodsTableUtil;
import cn.wjf.approomorm.utils.PinyinUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class StockDbUpdate {

    private int dbVersion;

    public void requestGoodsTable(final Observer observer) {
        Log.e("wjf>>>>","码表发包dbVersion：" + dbVersion);
        if(StockListDataBase.instance != null && StockListDataBase.instance.getOpenHelper() != null) {
            dbVersion = StockListDataBase.instance.getOpenHelper().getWritableDatabase().getVersion();
        }
        Log.e("wjf>>>>","得到股票数据库初始dbVersion：" + dbVersion);
        if (dbVersion == 0) return;
        BusinessPackage businessPackage = new BusinessPackage();
        final CodeListUpdateRequest.CodeListUpdate_Request request = new CodeListUpdateRequest.CodeListUpdate_Request();
        request.setLastVersion(dbVersion);//假设dbVersion=1
        request.setSizeLimit(99999);
        businessPackage.setProtocolId(XProtocol.GoodsTable);
        businessPackage.setMsgData(request);
        businessPackage.setMsgType(BusinessPackage.MSG_TYPE_PB3);
        NetworkHelper.requestBusiness(businessPackage, System.currentTimeMillis() + "")
                .observeOn(Schedulers.computation())
                .map(new Function<BusinessPackage, Object>() {
                    @Override
                    public Object apply(BusinessPackage businessPackage1) throws Exception {
                        byte[] bsBaseResponse = businessPackage1.getMsgData();
                        RequestRet reqRet = new RequestRet();
                        try {
                            BaseResponse.Base_Response baseRes = BaseResponse.Base_Response.parseFrom(bsBaseResponse);
                            int retCode = baseRes.result.getCode();

                            if (retCode == 0) {
                                byte[] bsDetail = baseRes.detail.getValue();
                                CodeListUpdateResponse.CodeListUpdate_Response res = CodeListUpdateResponse.CodeListUpdate_Response.parseFrom(bsDetail);

                                int version = res.getFinalVersion();
                                int len = res.updateList.length;
                                if (len > 0) {
                                    updateDB(res.updateList, version,observer);
                                }
                                reqRet.retCode = 0;
                            } else {
                                reqRet.retCode = baseRes.result.getCode();
                                reqRet.retMsg = baseRes.result.getMsg();
                            }
                        } catch (InvalidProtocolBufferNanoException e) {
                            reqRet.retCode = -1;
                            reqRet.retMsg = "--------码表pb包解析失败--------";
                        }
                        return reqRet;
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void updateDB(CodeListUpdateResponse.CodeListUpdate_Response.CodeList[] aryGoods, int version,Observer observer) {
        if(aryGoods == null || aryGoods.length == 0)
        {
            Log.e("wjf>>>>","收到码表数据为空了！------检查------");
            return;
        }
        Log.e("wjf>>>>","码表大小aryGoods.size：" + aryGoods.length);
        Vector<StockList> deletes = new Vector<>();
        Vector<StockList> adds = new Vector<>();
        for (CodeListUpdateResponse.CodeListUpdate_Response.CodeList goods : aryGoods) {
            if (goods.getFlag() == -1) {
                //删除
                StockList sqlGoods = new StockList();
                sqlGoods.goodid = goods.getStockCode();
                deletes.add(sqlGoods);
            } else if (goods.getFlag() == 1) {
                StockList sqlGoods = new StockList();
                String[] pinyins = PinyinUtil.GetAllMultiFirstPinyin(goods.getStockName());
                StringBuilder buf = new StringBuilder();
                int len = pinyins.length;
                for (int j = 0; j < len; j++) {
                    if (j == (len - 1)) {
                        buf.append(pinyins[j]);
                    } else {
                        buf.append(pinyins[j]).append(",");
                    }
                }
                sqlGoods.name = goods.getStockName();
                sqlGoods.format_name = GoodsTableUtil.getFromatGoodsName(goods.getStockName());
                sqlGoods.goodid = goods.getStockCode();
                sqlGoods.code = goods.getCodeName();
                sqlGoods.exchange = goods.getExchange();
                sqlGoods.category = goods.getCategory();
                sqlGoods.version = goods.getVersion();
                String searchStr = buf.toString();
                boolean isSHOption = (goods.getExchange() == Exchange.SH
                        && DataUtils.isCategory(sqlGoods.category, Category.SH_OPTIONS));
                if (!isSHOption) {
                    if (!TextUtils.isEmpty(sqlGoods.code)) {
                        searchStr = searchStr + "," + sqlGoods.code.toLowerCase();
                    }
                }
                sqlGoods.pinyin = searchStr;
                sqlGoods.trade_session = goods.getTradeSession();
                sqlGoods.weight = GoodsTableUtil.getSearchWeigth(goods.getExchange(), goods.getCategory());
                adds.add(sqlGoods);
            }
        }
        dbVersion = version;
        Log.e("wjf>>>>","码表adds.size：" + adds.size() + "----deletes.size: " + deletes.size());
        optStockListDb(adds,deletes,observer);
    }

    private void optStockListDb(final Vector<StockList> adds, final Vector<StockList> del,Observer observer)
    {
        ObservableOnSubscribe observableOnSubscribe = new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                List<StockList> lists = new ArrayList<>();
                if(StockListDataBase.instance != null)
                {
                    StockListDataBase.instance.stockListDao().delete(del);
                    StockListDataBase.instance.stockListDao().insert(adds);
                    lists.addAll(StockListDataBase.instance.stockListDao().getAll());
                }
                e.onNext(lists);
            }
        };
        Observable.create(observableOnSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
