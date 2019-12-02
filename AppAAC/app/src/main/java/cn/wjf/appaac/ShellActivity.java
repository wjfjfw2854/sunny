package cn.wjf.appaac;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import cn.wjf.appaac.basic.BasicActivity;
import cn.wjf.appaac.databinding.ActivityShellBinding;
import cn.wjf.appaac.vm.ShellVm;

public class ShellActivity extends BasicActivity {

    private ActivityShellBinding db;
    private ShellVm vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this,R.layout.activity_shell);
        vm = ViewModelProviders.of(this).get(ShellVm.class);
        vm.observe(this);
        db.setVm(vm);
        db.setUser(vm.user);
    }
}
