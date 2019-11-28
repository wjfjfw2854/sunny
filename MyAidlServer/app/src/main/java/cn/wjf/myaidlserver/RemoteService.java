package cn.wjf.myaidlserver;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class RemoteService extends Service {
    private MyBinder myBinder;

    @Override
    public void onCreate() {
        Log.e("wjf>>>>","进程RemoteService中的onCreate()启动成功！");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("wjf>>>>","进程RemoteService中的onStart()启动成功！");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("wjf>>>>","进程RemoteService中的onStartCommand()启动成功！");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        Log.e("wjf>>>>","进程RemoteService中的startForegroundService()启动成功！");
        return super.startForegroundService(service);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        getApplication().getContentResolver();
        if(myBinder == null)
        {
            myBinder = new MyBinder();
        }
        return myBinder;
    }
}
