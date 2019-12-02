package cn.wjf.apphan.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class BaseVm extends AndroidViewModel {
    public BaseVm(@NonNull Application application) {
        super(application);
    }
}
