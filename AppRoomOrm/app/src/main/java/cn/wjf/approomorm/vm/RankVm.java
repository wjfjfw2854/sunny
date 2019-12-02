package cn.wjf.approomorm.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import cn.wjf.approomorm.R;
import cn.wjf.approomorm.adapter.RankAdapter;
import cn.wjf.approomorm.base.BaseVm;
import cn.wjf.approomorm.data.Category;
import cn.wjf.approomorm.data.Exchange;
import cn.wjf.approomorm.data.Field;
import cn.wjf.approomorm.data.Goods;
import cn.wjf.approomorm.data.GoodsParams;
import cn.wjf.approomorm.data.PMClassTypes;
import cn.wjf.approomorm.datawrap.LongGoodsListParams;
import cn.wjf.approomorm.nano.RankListRequest;
import cn.wjf.approomorm.nano.RankListResponse;
import cn.wjf.approomorm.nano.SortedListRequest;
import cn.wjf.approomorm.network.BusinessPackage;
import cn.wjf.approomorm.network.NetworkHelper;
import cn.wjf.approomorm.parse.PBParser;
import cn.wjf.approomorm.pojo.IndexZpsItemData;
import cn.wjf.approomorm.protocol.XProtocol;
import cn.wjf.approomorm.utils.CollectionUtils;
import cn.wjf.approomorm.utils.RankListHelper;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RankVm extends BaseVm {

    public final static Field[] idsZF = { Field.PRICE, Field.ZF, Field.ZD,Field.HIGH,Field.LOW,Field.SYLTTM,Field.SJL,Field.LTSZ,Field.OPEN,Field.CLOSE, Field.HS, Field.ZS, Field.ZJE,Field.ZLJM, Field.LB, Field.ZHENFU, Field.ZHANGSU,Field.ZF5, Field.HS5};
    public Field[] ids = idsZF;
    public LongGoodsListParams longGoodsListHelper;

    private final String strZps = "涨盘家数";
    public int m_nOffset;
    public RankAdapter adapter;

//    public ObservableField<IndexZpsItemData> indexZps1 = new ObservableField<>();
//    public ObservableField<IndexZpsItemData> indexZps2 = new ObservableField<>();
//    public ObservableField<IndexZpsItemData> indexZps3 = new ObservableField<>();
//    public ObservableField<IndexZpsItemData> indexZps4 = new ObservableField<>();

    public List<IndexZpsItemData> list = new ArrayList<>();


    public void init()
    {
        adapter = new RankAdapter();
        adapter.layoutId.put(R.layout.rank_item_top,R.layout.rank_item_top);
        adapter.layoutId.put(R.layout.rank_item,R.layout.rank_item);
        longGoodsListHelper = new LongGoodsListParams();
        longGoodsListHelper.reqLongArray = true;
    }

    public void refreshGrid(int m_nOffset) {
        longGoodsListHelper.sort = -1;//CellHeader.SORT_DSC//sort;
        longGoodsListHelper.sortID = Field.ZF;//sortId;
        longGoodsListHelper.position = m_nOffset;

        ArrayList<PMClassTypes.Params> paramsAry = getPMClassTypes(getZfbClassType());
        if(!CollectionUtils.isEmpty(paramsAry)){
            SortedListRequest.SortedList_Request.ClassTypeList.ClassType[] classTypes = new SortedListRequest.SortedList_Request.ClassTypeList.ClassType[paramsAry.size()];
            for (int i = 0; i < paramsAry.size(); i++) {
                PMClassTypes.Params params = paramsAry.get(i);
                SortedListRequest.SortedList_Request.ClassTypeList.ClassType type = new SortedListRequest.SortedList_Request.ClassTypeList.ClassType();
                type.setExchange(params.exchange);
                type.setCategory(params.category);
                classTypes[i] = type;
            }
            longGoodsListHelper.classTypes = classTypes;
        }
        int[] fields = getFieldsId(ids.length + 4);
        longGoodsListHelper.positionFields = fields;

        RankListRequest.RankList_Request request = new RankListRequest.RankList_Request();
        request.rankListRequest = new RankListRequest.RankList_Request.Request[longGoodsListHelper.reqLongArray ? 3 : 2];
        if (longGoodsListHelper.reqLongArray) {
            request.rankListRequest[0] = getLongGoodList(longGoodsListHelper);
            request.rankListRequest[1] = getPositionGoodList(longGoodsListHelper);
        } else {
            request.rankListRequest[0] = getPositionGoodList(longGoodsListHelper);
        }

        RankListRequest.RankList_Request.Request requestZps = new RankListRequest.RankList_Request.Request();
        requestZps.templateRankRequest = getDaPanUp();
        requestZps.setTemplateName(strZps);
        if(longGoodsListHelper.reqLongArray) {
            request.rankListRequest[2] = requestZps;
        }
        else
        {
            request.rankListRequest[1] = requestZps;
        }

        composeBusinesspkg(longGoodsListHelper, request);
    }

    private ArrayList<PMClassTypes.Params> getPMClassTypes(SortedListRequest.SortedList_Request.ClassTypeList.ClassType[] classTypes) {
        ArrayList<PMClassTypes.Params> params = new ArrayList<>();
        for (SortedListRequest.SortedList_Request.ClassTypeList.ClassType type : classTypes) {
            PMClassTypes.Params param = new PMClassTypes.Params();
            param.exchange = type.getExchange();
            param.category = type.getCategory();
            params.add(param);
        }
        return params;
    }

    private SortedListRequest.SortedList_Request.ClassTypeList.ClassType[] getZfbClassType()
    {
        SortedListRequest.SortedList_Request.ClassTypeList.ClassType[] shszClassTypes = new SortedListRequest.SortedList_Request.ClassTypeList.ClassType[2];
        shszClassTypes[0] = new SortedListRequest.SortedList_Request.ClassTypeList.ClassType();
        shszClassTypes[0].setExchange(Exchange.SH);
        shszClassTypes[0].setCategory(Category.SH_A);
        shszClassTypes[1] = new SortedListRequest.SortedList_Request.ClassTypeList.ClassType();
        shszClassTypes[1].setExchange(Exchange.SZ);
        shszClassTypes[1].setCategory(Category.SZ_A | Category.SZ_ZXB | Category.SZ_CYB);
        return shszClassTypes;
    }

    private int[] getFieldsId(int fieldSize) {
        int[] fields = new int[fieldSize];
        for (int i = 0; i < ids.length; i++) {
            fields[i] = ids[i].param;
        }
        fields[ids.length] = Field.NAME.param;
        fields[ids.length + 1] = Field.CODE.param;
        fields[ids.length + 2] = Field.CLOSE.param;
        fields[ids.length + 3] = GoodsParams.MARGIN_TRADING_FLAG;
        return fields;
    }

    private RankListRequest.RankList_Request.Request getLongGoodList(LongGoodsListParams longGoodsListHelper) {
        RankListRequest.RankList_Request.Request request = new RankListRequest.RankList_Request.Request();
        SortedListRequest.SortedList_Request req = new SortedListRequest.SortedList_Request();
        req.fieldsId = new int[]{Field.CODE.param};
        if (longGoodsListHelper.sort != 0) {
            SortedListRequest.SortedList_Request.SortOptions sortOptions = new SortedListRequest.SortedList_Request.SortOptions();
            sortOptions.setSortAsce(longGoodsListHelper.sort == 1);
            sortOptions.setSortField(longGoodsListHelper.sortID.param);
            req.sortOption = sortOptions;
        }
        req.setBeginPosition(0);
        SortedListRequest.SortedList_Request.ClassTypeList.ClassType[] classTypes = longGoodsListHelper.classTypes;
        req.setLimitSize(3_000);
        if (longGoodsListHelper.groupInfo != null) {
            req.setGroup(longGoodsListHelper.groupInfo);
        }
        if (longGoodsListHelper.classTypes != null) {
            SortedListRequest.SortedList_Request.ClassTypeList classTypeList = new SortedListRequest.SortedList_Request.ClassTypeList();
            classTypeList.exchangeCategory = longGoodsListHelper.classTypes;
            req.setSystem(classTypeList);
        }
        if (longGoodsListHelper.bkGoodsID != 0) {
            req.setGoods(longGoodsListHelper.bkGoodsID);
        }
        request.templateRankRequest = req;
        request.setTemplateName(LongGoodsListParams.TAG_LONG_LIST);
        return request;
    }

    private RankListRequest.RankList_Request.Request getPositionGoodList(LongGoodsListParams longGoodsListHelper) {
        RankListRequest.RankList_Request.Request request = new RankListRequest.RankList_Request.Request();
        SortedListRequest.SortedList_Request req = new SortedListRequest.SortedList_Request();
        req.fieldsId = longGoodsListHelper.positionFields;
        if (longGoodsListHelper.sort != 0) {
            SortedListRequest.SortedList_Request.SortOptions sortOptions = new SortedListRequest.SortedList_Request.SortOptions();
            sortOptions.setSortAsce(longGoodsListHelper.sort == 1);
            sortOptions.setSortField(longGoodsListHelper.sortID.param);
            req.sortOption = sortOptions;
        }
        req.setBeginPosition(longGoodsListHelper.position);
        req.setLimitSize(longGoodsListHelper.pageLength);
        if (longGoodsListHelper.groupInfo != null) {
            req.setGroup(longGoodsListHelper.groupInfo);
        }
        if (longGoodsListHelper.classTypes != null) {
            SortedListRequest.SortedList_Request.ClassTypeList classTypeList = new SortedListRequest.SortedList_Request.ClassTypeList();
            classTypeList.exchangeCategory = longGoodsListHelper.classTypes;
            req.setSystem(classTypeList);
        }
        if (longGoodsListHelper.bkGoodsID != 0) {
            req.setGoods(longGoodsListHelper.bkGoodsID);
        }
        request.templateRankRequest = req;
        request.setTemplateName(LongGoodsListParams.TAG_POSITION_LIST);
        return request;
    }

    private SortedListRequest.SortedList_Request getDaPanUp() {
        SortedListRequest.SortedList_Request req = new SortedListRequest.SortedList_Request();
        SortedListRequest.SortedList_Request.GoodsList list = new SortedListRequest.SortedList_Request.GoodsList();
        list.goodsId = new int[]{1, 1399001, 1399005, 1399006};
        req.setCustom(list);
        req.fieldsId = new int[]{Field.NAME.param, Field.CODE.param, Field.SZJS.param,Field.XDJS.param,Field.PPJS.param,Field.PRICE.param, Field.ZF.param, Field.ZD.param,Field.CLOSE.param};
        req.setLimitSize(list.goodsId.length);
        return req;
    }

    private void composeBusinesspkg(final LongGoodsListParams longGoodsListHelper, RankListRequest.RankList_Request request) {
        BusinessPackage businessPackage = new BusinessPackage();
        businessPackage.setProtocolId(XProtocol.XPRO_RANK_LIST_ID);
        businessPackage.setMsgData(request);
        businessPackage.setMsgType(BusinessPackage.MSG_TYPE_PB3);
        NetworkHelper.requestBusiness(businessPackage,System.currentTimeMillis() + "")
                .observeOn(Schedulers.computation())
                .flatMap(new PBParser<>(RankListResponse.RankList_Response.class))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessPackage<RankListResponse.RankList_Response>>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(BusinessPackage<RankListResponse.RankList_Response> resp) {
                                   RankListResponse.RankList_Response v = resp.getResEntity();
                                   RankListHelper rankListHelper = new RankListHelper();
                                   ConcurrentHashMap<String, ArrayList<Goods>> map = rankListHelper.getMap(v);
                                   int begin = v.rankListResponse[map.size() > 2 ? 1 : 0].templateRankResponse.requestParams.getBeginPosition();
                                   if (begin == longGoodsListHelper.position) {
                                       boolean beInit = false;
                                       if(adapter == null)
                                       {
                                           return;
                                       }

                                       if (map.containsKey(LongGoodsListParams.TAG_LONG_LIST)) {
                                           longGoodsListHelper.reqLongArray = false;
                                           adapter.datas.clear();
                                           ArrayList<Goods> reciveLongList = map.get(LongGoodsListParams.TAG_LONG_LIST);
                                           for (Goods good : reciveLongList) {
                                               adapter.datas.add(good);
                                           }
                                           beInit = true;
//                                           initOnItemClick();
                                       }
                                       if (map.containsKey(LongGoodsListParams.TAG_POSITION_LIST)) {
                                           int total = adapter.datas.size();
                                           ArrayList<Goods> reciveList = map.get(LongGoodsListParams.TAG_POSITION_LIST);
                                           int length = reciveList.size();
                                           for (int i = begin; i < begin + length; i++) {
                                               if (i < total && i - begin >= 0 && i - begin < length) {
                                                   adapter.datas.set(i, reciveList.get(i - begin));
                                               }
                                           }
                                       }
                                       if(map.containsKey(strZps)) {
                                           List<Goods> goods = map.get(strZps);
                                           if(!CollectionUtils.isEmpty(goods))
                                           {
                                               if(adapter.datas.containsAll(list))
                                               {
                                                   adapter.datas.removeAll(list);
                                               }
                                               list.clear();
                                           }
                                           for (int k = 0; k < goods.size(); k++) {
                                               IndexZpsItemData indexZpsItemData = new IndexZpsItemData(k,goods);
                                               list.add(indexZpsItemData);
//                                               if (k == 0) {
//                                                   indexZps1.set(indexZpsItemData);
//                                               } else if (k == 1) {
//                                                   indexZps2.set(indexZpsItemData);
//                                               } else if (k == 2) {
//                                                   indexZps3.set(indexZpsItemData);
//                                               } else if (k == 3) {
//                                                   indexZps4.set(indexZpsItemData);
//                                               }
                                           }
                                       }
                                       adapter.datas.addAll(0,list);
                                       adapter.notifyDataSetChanged();
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }

}
