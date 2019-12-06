package cn.emoney.appserverkeeplive2;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import java.util.List;

//        1.在应用被关闭后保活（最难）
//        2.在内存占用过大，系统自动释放内存时保活（优先杀死占用较高的Service）
//        3.重启手机后自动开启Service
//        4.手机息屏后不被释放内存
//        5.手动清理内存时保活

/**
 * author  : wujunfeng
 * e-mail  : 421284553@qq.com
 * date    : 2019/11/29 10:39
 * version : 1.4.4
 */
public class KeepProcessLiveApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("wjf>>>>","KeepProcessLiveApplication中onCreate()运行次数");
        int pid = android.os.Process.myPid();
        getProcessesName(pid);
    }

    public void getProcessesName(int pid)
    {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for(int i = 0;i < list.size();i ++)
        {
            if(pid == list.get(i).pid) {
                Log.e("wjf>>>>", "正在运行的进程名称_" + i + "-------" + list.get(i).processName);
                break;
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}

