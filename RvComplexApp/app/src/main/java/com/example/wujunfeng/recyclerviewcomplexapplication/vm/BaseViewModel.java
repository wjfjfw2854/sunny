package com.example.wujunfeng.recyclerviewcomplexapplication.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
//import cn.emoney.level2.net.NetworkManager;
//import cn.emoney.sky.libs.network.BusinessPackage;
//import cn.emoney.sky.libs.network.OkHttpUtil;
//import rx.Observable;
//import rx.Subscription;
//import rx.schedulers.Schedulers;

public class BaseViewModel extends AndroidViewModel {
//    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected String vmTag = this.getClass().getName();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        cancelAllRequestAndSubscription();
    }

//    public void cancelAllRequestAndSubscription() {
//        OkHttpUtil.cancelRequest(vmTag);
//        compositeDisposable.clear();
//    }

//    public Observable<BusinessPackage> requestBusiness(final BusinessPackage reqData) {
//        return NetworkManager.requestBusiness(reqData, vmTag)
//                .observeOn(Schedulers.immediate());
//    }

//    public void compose(Subscription subscription) {
//        compositeDisposable.add(subscription);
//    }
}
