package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.baseadpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class MutiComplexRecyclerItemAdapter extends RecyclerView.Adapter<ItemBaseComplexRecycler> {
    private final LayoutInflater inflater;
    public List<Object> datas = new ArrayList<Object>();
    protected Context context;
    protected SparseArray<Class> templates = new SparseArray<Class>();
    protected SparseArray<Object[]> argsMap = new SparseArray<Object[]>();

    public MutiComplexRecyclerItemAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemBaseComplexRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        return createItemView(viewType, parent);
    }

    @Override
    public void onBindViewHolder(ItemBaseComplexRecycler holder, final int position) {
        holder.bindData(datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        initTemplates(templates, argsMap);
        return datas.size();
    }

    protected abstract void initTemplates(SparseArray<Class> templates, SparseArray<Object[]> argsMap);

    private ItemBaseComplexRecycler createItemView(int viewType, ViewGroup parent) {
        Class cls = null;
        try {
            cls = Class.forName(templates.get(viewType).getName());
            Object[] args = argsMap.get(viewType);
            View itemView = inflater.inflate(viewType, parent, false);
            ItemBaseComplexRecycler itemBase = (ItemBaseComplexRecycler) cls.getConstructor(View.class, LayoutInflater.class, Object[].class).newInstance(itemView, inflater, args);
            itemBase.initView();
            return itemBase;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private OnItemClickListner onItemClickListner;

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClick(int pos);
    }
}
