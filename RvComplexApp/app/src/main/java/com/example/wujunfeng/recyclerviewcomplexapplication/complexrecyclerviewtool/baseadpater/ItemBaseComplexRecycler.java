package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.baseadpater;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ItemBaseComplexRecycler extends RecyclerView.ViewHolder {
    public LayoutInflater inflater;
    protected Object[] args;

    public ItemBaseComplexRecycler(View itemView, LayoutInflater inflater, Object[] args) {
        super(itemView);
        this.inflater = inflater;
        this.args = args;
        init();
    }

    private void init() {
    }

    public abstract void initView();

    public abstract void bindData(Object data, int pos);

    public void onRecycle() {

    }

    public View findViewById(int id) {
        return itemView.findViewById(id);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public void setBackgroundColor(int color) {
        itemView.setBackgroundColor(color);
    }

    public Resources getResources() {
        return itemView.getResources();
    }

}