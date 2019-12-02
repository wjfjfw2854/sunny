package cn.wjf.appaac.vm;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import cn.wjf.appaac.basic.BasicVm;
import cn.wjf.appaac.business.UserBusiness;
import cn.wjf.appaac.datas.User;
import cn.wjf.appaac.livedata.UserLiveData;

public class UserVm extends BasicVm implements UserBusiness.UserListener {

    private UserBusiness userBusiness = UserBusiness.get();
    private UserLiveData userLiveData;

    public UserVm(@NonNull Application application) {
        super(application);
    }

    public void observe(LifecycleOwner owner, Observer<User> observer)
    {
        User user = userBusiness.getUser();
        userLiveData = new UserLiveData();
        userLiveData.observe(owner,observer);
        userLiveData.postValue(user);
        userBusiness.addListener(this);
    }

    public void requestUser()
    {
        userBusiness.requestUser();
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

    @Override
    protected void onCleared() {
        super.onCleared();
        userBusiness.removeListener(this);
    }
}
