package cn.wjf.approomorm.data;



import androidx.collection.LruCache;

import java.util.ArrayList;
import java.util.List;

public class GoodsCache {
    public static LruCache<Integer, Goods> cache1st = new LruCache<>(50);
    public static LruCache<Integer, Goods> cache = new LruCache<>(100);
//    public static LruCache<Integer, List<DataMinute>> minutes = new LruCache<>(5);

    public static Goods getGoods(int goodId) {
        Goods good = GoodsCache.cache.get(goodId);
        if (good == null) {
            good = new Goods(goodId);
            cache.put(goodId, good);
        }
        return good;
    }

    /**
     * persistent goods,don't alloc frequency.
     *
     * @param goodId
     * @return
     */
    public static Goods getGoods1st(int goodId) {
        Goods goods = cache1st.get(goodId);
        if (goods == null) {
            goods = getGoods(goodId);
            cache1st.put(goodId, goods);
        }
        return goods;
    }

//    public static List<DataMinute> getMinutes(int goodId) {
//        List<DataMinute> minutes = GoodsCache.minutes.get(goodId);
//        if (minutes == null) {
//            minutes = new ArrayList<>();
//            GoodsCache.minutes.put(goodId, minutes);
//        }
//        return minutes;
//    }

    public static void clear(List<Integer>goods) {
        for (Integer good : goods) {
            cache.remove(good);
        }
    }
}
