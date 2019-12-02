package cn.wjf.approomorm.vh;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import cn.wjf.approomorm.BR;

public class BaseVh extends RecyclerView.ViewHolder {
    private ViewDataBinding db;
    public BaseVh(@NonNull View itemView) {
        super(itemView);
    }

    public void setDb(ViewDataBinding db)
    {
        this.db = db;
    }

    public void setVal(/*BaseItem baseItem*/Object baseItem){
        db.setVariable(cn.wjf.approomorm.BR.item,baseItem);
        db.setVariable(cn.wjf.approomorm.BR.context,itemView.getContext());
        db.executePendingBindings();
    }
}
