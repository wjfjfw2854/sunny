package com.example.wujunfeng.recyclerviewcomplexapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.BaseFrag;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.TestMncgChiCang;
import com.example.wujunfeng.recyclerviewcomplexapplication.vm.MainViewModel;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    private MainViewModel viewModel;
    private ViewDataBinding bind;
    private BaseFrag[] frags = new BaseFrag[]{new TestMncgChiCang()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        bind = DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        bind.setVariable(BR.viewModel,viewModel);
//        loadMultipleRootFragment(R.id.container, 0, frags);
        loadRootFragment(R.id.container,new TestMncgChiCang());
    }
}
