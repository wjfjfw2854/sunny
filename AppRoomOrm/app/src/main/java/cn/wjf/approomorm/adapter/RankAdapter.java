package cn.wjf.approomorm.adapter;


import cn.wjf.approomorm.R;
import cn.wjf.approomorm.data.Goods;
import cn.wjf.approomorm.pojo.IndexZpsItemData;

public class RankAdapter extends BaseRvAdapter{

    @Override
    public int getItemViewType(int position) {
        if(datas.size() > position)
        {
            Object object = datas.get(position);
            if(object instanceof Goods)
            {
                return R.layout.rank_item;//layoutId.get(R.layout.rank_item);
            }
            else if(object instanceof IndexZpsItemData)
            {
                return R.layout.rank_item_top;//layoutId.get(R.layout.rank_item_top);
            }
        }
        return 0;
    }
}
