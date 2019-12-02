package cn.wjf.appaac.vm;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import cn.wjf.appaac.basic.BasicVm;
import cn.wjf.appaac.business.UserBusiness;
import cn.wjf.appaac.datas.User;
import cn.wjf.appaac.livedata.UserLiveData;

public class ShellVm extends BasicVm implements Observer<User>, UserBusiness.UserListener {
    private UserBusiness userBusiness = UserBusiness.get();
    private UserLiveData userLiveData;
    public User user;
    public ObservableField<User> userObser = new ObservableField<>();
    public ShellVm(@NonNull Application application) {
        super(application);
    }

    public void observe(LifecycleOwner owner)
    {
        user = userBusiness.getUser();
        userObser.set(user);
        userLiveData = new UserLiveData();
        userLiveData.observe(owner,this);
        userLiveData.postValue(user);
        userBusiness.addListener(this);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userBusiness.removeListener(this);
    }

    public void requestUser()
    {
        userBusiness.requestUser();
    }

    @Override
    public void onChanged(User user) {
//        this.user.name = user.name;
//        this.user.age = user.age;
        userObser.set(user);
        userObser.notifyChange();
    }

    @Override
    public void onRequestUserResult(int code, User user) {
        if(code == 0)
        {
            userLiveData.postValue(user);
        }
        else
        {
            Toast.makeText(getApplication(),"返回结果出错！！！",Toast.LENGTH_SHORT).show();
        }
    }
}
