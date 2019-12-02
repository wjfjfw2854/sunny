package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFrag extends SupportFragment {
    public static final String TAG = "lifem";
    protected View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Optimize optimize = new Optimize(getClass().getSimpleName());
        onInit(savedInstanceState);
//        optimize.end();
        return root;
    }

    public abstract void onInit(Bundle savedInstanceState);

    public ViewDataBinding setContentView(int layout) {
        Log.d(TAG, "setContentView: " + this.getClass().getSimpleName());
        ViewDataBinding bind = DataBindingUtil.inflate(getLayoutInflater(), layout, null, false);
        root = bind.getRoot();
        return bind;
    }

    public void setContentViewNormal(int layout) {
        View inflate = getLayoutInflater().inflate(layout, null);
        root = inflate;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.d(TAG, "onSupportVisible: " + this.getClass().getSimpleName());
//        UBx.beginPage(getClass().getSimpleName());
    }

    @Override
    public void onSupportInvisible() {
//        UBx.endPage(getClass().getSimpleName());
        super.onSupportInvisible();
        Log.d(TAG, "onSupportInvisible: " + this.getClass().getSimpleName());
    }
}
