package cn.wjf.approomorm.vh;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.wjf.approomorm.BR;
import cn.wjf.approomorm.databinding.StockItemBinding;
import cn.wjf.approomorm.db.stockdb.StockList;


public class RvVh extends RecyclerView.ViewHolder {

    private int goodid;
    private String name;
    private String code;
    private String pinyin;
    private int exchange;
    private long category;
    private int trade_session;
    private int version;
    private int weight;
    private String format_name;

    private StockItemBinding db;
    public RvVh(@NonNull View itemView) {
        super(itemView);
    }

    public void setDataBinding(StockItemBinding db)
    {
        this.db = db;
    }

    public void setVal(StockList stockList)
    {
        db.setVariable(cn.wjf.approomorm.BR.item,stockList);
        db.executePendingBindings();
    }
}
