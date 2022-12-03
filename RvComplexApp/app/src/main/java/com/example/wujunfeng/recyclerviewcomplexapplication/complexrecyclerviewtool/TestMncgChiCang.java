package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import com.example.wujunfeng.recyclerviewcomplexapplication.R;
import com.example.wujunfeng.recyclerviewcomplexapplication.databinding.TestmncgchicangBinding;
import com.example.wujunfeng.recyclerviewcomplexapplication.util.Care4OldersShared;

public class TestMncgChiCang extends BaseFrag {
    private TestmncgchicangBinding testmncgchicangBinding;
    private TestMncgChiCangViewModel testMncgChiCangViewModel;
    @Override
    public void onInit(Bundle savedInstanceState) {
        testmncgchicangBinding = (TestmncgchicangBinding)setContentView(R.layout.testmncgchicang);
        testMncgChiCangViewModel = ViewModelProviders.of(this).get(TestMncgChiCangViewModel.class);
//        testmncgchicangBinding.setVariable(BR.viewModel,testMncgChiCangViewModel);
        testmncgchicangBinding.setViewModel(testMncgChiCangViewModel);
        testmncgchicangBinding.txtAddTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testMncgChiCangViewModel.refreshData();
            }
        });

        testmncgchicangBinding.txtCare4oldersHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Care4OldersShared.isOpenCare4Olders = !Care4OldersShared.isOpenCare4Olders;
                testMncgChiCangViewModel.labelCare4Olders = Care4OldersShared.isOpenCare4Olders?"老年版列表item高度100dp，点击！！！":"正常版列表item高度32dp，点击！！！";
                testmncgchicangBinding.txtCare4oldersHeight.setText(testMncgChiCangViewModel.labelCare4Olders);
                testMncgChiCangViewModel.rvAdapterRefresh();
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //可见后可加一些ui展示
    }
}
