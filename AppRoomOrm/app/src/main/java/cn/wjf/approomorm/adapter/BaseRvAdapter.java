package cn.wjf.approomorm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.wjf.approomorm.utils.DpTool;
import cn.wjf.approomorm.vh.BaseVh;

public class BaseRvAdapter extends RecyclerView.Adapter<BaseVh> {
    public Map<Integer,Integer> layoutId = new HashMap<>();//界面
//    public List<BaseItem> datas = new ArrayList<>();//数据
    public List<Object> datas = new ArrayList<>();//数据
    @NonNull
    @Override
    public BaseVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewDataBinding db = DataBindingUtil.inflate(layoutInflater,layoutId.get(viewType),null,false);
        View v = db.getRoot();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpTool.dp2px(context,50));
        v.setLayoutParams(lp);
        BaseVh baseVh = new BaseVh(v);
        baseVh.setDb(db);
        return baseVh;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVh holder, int position) {
        if(datas.size() > position) {
            holder.setVal(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        Set<Integer> set = layoutId.keySet();
        for(Integer integer : set)
        {
            return layoutId.get(integer);
        }
        return 0;
    }
}
