package cn.wjf.approomorm.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import cn.wjf.approomorm.data.Goods;
import cn.wjf.approomorm.data.GoodsCache;
import cn.wjf.approomorm.nano.RankListResponse;
import cn.wjf.approomorm.nano.SortedListResponse;

public class RankListHelper {

    public ConcurrentHashMap<String,ArrayList<Goods>> getMap(RankListResponse.RankList_Response response)
    {
        ConcurrentHashMap<String,ArrayList<Goods>> map = new ConcurrentHashMap<>();
        for (RankListResponse.RankList_Response.Response data : response.rankListResponse){
            ArrayList<Goods> list = new ArrayList<>();
            if(data.templateRankResponse != null) {
                for (SortedListResponse.SortedList_Response.ValueData obj : data.templateRankResponse.valueList) {
                    Goods goods = GoodsCache.getGoods(obj.getGoodsId());
//                    if ((HongKongViewModel.strHkIndex.equals(data.getTemplateName())
//                            || LongGoodsListParams.TAG_POSITION_LIST.equals(data.getTemplateName()))
//                            && goods.exchange == Exchange.HK_STOCKS
//                            && Arrays.asList(5500001,5500020,5500021).contains(goods.getGoodsId())) {//HK不更新数据
//                        list.add(goods);
//                        continue;
//                    }

                    for (int i = 0; i < data.templateRankResponse.requestParams.fieldsId.length; i++) {
                        goods.setValue(data.templateRankResponse.requestParams.fieldsId[i], obj.fieldValue[i]);
                        goods.exchange = obj.getExchange();
                        goods.category = obj.getCategory();
                    }
                    list.add(goods);
                }
            }
            map.put(data.getTemplateName(),list);
        }
        return map;
    }
}
