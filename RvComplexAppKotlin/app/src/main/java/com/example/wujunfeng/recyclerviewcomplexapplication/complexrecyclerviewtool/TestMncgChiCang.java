package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import com.example.wujunfeng.recyclerviewcomplexapplication.R;
import com.example.wujunfeng.recyclerviewcomplexapplication.databinding.TestmncgchicangBinding;

public class TestMncgChiCang extends BaseFrag {
    private TestmncgchicangBinding testmncgchicangBinding;
    private TestMncgChiCangViewModel testMncgChiCangViewModel;
    @Override
    public void onInit(Bundle savedInstanceState) {
        testmncgchicangBinding = (TestmncgchicangBinding)setContentView(R.layout.testmncgchicang);
        testMncgChiCangViewModel = ViewModelProviders.of(this).get(TestMncgChiCangViewModel.class);
//        testmncgchicangBinding.setVariable(BR.viewModel,testMncgChiCangViewModel);
        testmncgchicangBinding.setViewModel(testMncgChiCangViewModel);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        testMncgChiCangViewModel.initData();
    }
}
