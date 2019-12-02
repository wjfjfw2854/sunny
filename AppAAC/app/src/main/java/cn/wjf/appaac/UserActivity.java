package cn.wjf.appaac;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cn.wjf.appaac.basic.BasicActivity;
import cn.wjf.appaac.databinding.ActivityUserBinding;
import cn.wjf.appaac.datas.User;
import cn.wjf.appaac.vm.UserVm;

public class UserActivity extends BasicActivity implements Observer<User> {

    private ActivityUserBinding db;
    private UserVm vm;

    private TextView txtName;
    private TextView txtAge;
    private Button butRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this,R.layout.activity_user);
        vm = ViewModelProviders.of(this).get(UserVm.class);
        vm.observe(this,this);
        init();
    }

    private void init()
    {
        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);
        butRefresh = findViewById(R.id.butRefresh);
        butRefresh.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                vm.requestUser();
            }
        });
    }

    @Override
    public void onChanged(User user) {
        txtName.setText("昵称：" + user.name);
        txtAge.setText("年龄：" + user.age);
    }
}
