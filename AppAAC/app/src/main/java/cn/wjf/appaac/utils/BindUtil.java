package cn.wjf.appaac.utils;

import android.view.View;

import androidx.databinding.BindingAdapter;

import cn.wjf.appaac.vm.ShellVm;

public class BindUtil {
    @BindingAdapter({"android:clickReq"})
    public static void requestUserClick(View v, final ShellVm vm)
    {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.requestUser();
            }
        };
        v.setOnClickListener(onClickListener);
    }
}
