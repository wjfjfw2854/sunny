package cn.wjf.approomorm.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.wjf.approomorm.R;
import cn.wjf.approomorm.databinding.StockItemBinding;
import cn.wjf.approomorm.db.stockdb.StockList;
import cn.wjf.approomorm.vh.RvVh;

public class RvAdapter extends RecyclerView.Adapter<RvVh> {
    public List<StockList> lists = new ArrayList<>();
    @NonNull
    @Override
    public RvVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StockItemBinding db = DataBindingUtil.inflate(inflater, R.layout.stock_item,null,false);
        RvVh rvVh = new RvVh(db.getRoot());
        rvVh.setDataBinding(db);
        return rvVh;
    }

    @Override
    public void onBindViewHolder(@NonNull RvVh holder, int position) {
        if(lists.size() == 0)
        {
            return;
        }
        holder.setVal(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
