package cn.wjf.apphan;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import cn.wjf.apphan.base.BaseActivity;
import cn.wjf.apphan.databinding.ActivityMainBinding;
import cn.wjf.apphan.frags.HandFrag;
import cn.wjf.apphan.vm.MainVm;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding db;
    private MainVm vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this,R.layout.activity_main);
        vm = ViewModelProviders.of(this).get(MainVm.class);
        db.setVm(vm);
        HandFrag frag = new HandFrag();
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameRoot,frag).commit();
    }
}
