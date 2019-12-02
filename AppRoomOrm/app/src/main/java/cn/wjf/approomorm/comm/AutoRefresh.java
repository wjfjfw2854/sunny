package cn.wjf.approomorm.comm;



import android.util.Log;
import java.util.concurrent.TimeUnit;

import cn.wjf.approomorm.BuildConfig;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AutoRefresh {
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    public Disposable disposable;
    public static boolean isNetworkAvailable = true;
    public boolean isAutoRefresh;

    public void start(int intervalTime) {
        stop();

        if (isNetworkAvailable) {
            if (onResfreshListener != null) {
                try {
                    onResfreshListener.onRefresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        final int interval;
        if (intervalTime == -1) {
            interval = getInterval();
        } else if (intervalTime > 0) {
            interval = intervalTime;
        } else {
            interval = 0;
        }

        if (interval > 0) {
            disposable = Observable.interval(interval, interval, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (isAutoRefresh) {
                                if (onResfreshListener != null) {
                                    if (BuildConfig.DEBUG) {
                                        Log.d("refreshm", "call:" + onResfreshListener.toString());
                                    }
                                    try {
                                        onResfreshListener.onRefresh();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
            compositeDisposable.add(disposable);
            isAutoRefresh = true;
        } else {
            stop();
        }

    }

    public void start() {
        int t_interval = getInterval();
        start(t_interval);
    }

    public void stop() {
        compositeDisposable.clear();
        isAutoRefresh = false;
    }

    public int getInterval() {
        return 3;
    }

    private OnResfreshListener onResfreshListener;

    public void setOnResfreshListener(OnResfreshListener onResfreshListener) {
        this.onResfreshListener = onResfreshListener;
    }

    public interface OnResfreshListener {
        void onRefresh();
    }
}
