package cn.wjf.appaac;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import cn.wjf.appaac.basic.BasicActivity;
import cn.wjf.appaac.business.UserBusiness;
import cn.wjf.appaac.contoller.UserContoller;
import cn.wjf.appaac.datas.User;
import cn.wjf.appaac.livedata.UserLiveData;

public class MainActivity extends BasicActivity implements Observer<User>, UserBusiness.UserListener {

    private TextView txtName;
    private TextView txtAge;
    private Button butRefresh;

    private UserBusiness userBusiness = UserBusiness.get();
    private UserLiveData userLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getLifecycle().addObserver(new UserContoller(getLifecycle()));
        log("onCreate");
        init();
    }

    private void init()
    {
        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);
        butRefresh = findViewById(R.id.butRefresh);
        butRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBusiness.requestUser();
            }
        });
        User user = userBusiness.getUser();
        userLiveData = new UserLiveData();
        userLiveData.observe(this,this);
        userLiveData.postValue(user);
        userBusiness.addListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
        userBusiness.removeListener(this);
    }

    private void log(String msg)
    {
        Log.e("wjf>>>>","MainActivity + " + msg);
    }

    @Override
    public void onRequestUserResult(int code, User user) {
        if(code == 0)
        {
            userLiveData.postValue(user);
        }
        else
        {
            Toast.makeText(this,"返回结果出错！！！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChanged(User user) {
        txtName.setText("昵称：" + user.name);
        txtAge.setText("年龄：" + user.age);
    }
}
