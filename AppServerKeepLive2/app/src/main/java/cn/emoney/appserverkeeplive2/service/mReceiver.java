package cn.emoney.appserverkeeplive2.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * author  : wujunfeng
 * e-mail  : 421284553@qq.com
 * date    : 2019/11/29 10:42
 * version : 1.4.4
 */
public class mReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("wjf>>>>","mReceiver ：onReceive收到开机广播");
        Intent intent1 = new Intent(context,StepService.class);
        Intent intent2 = new Intent(context, GuardService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent1);
            context.startForegroundService(intent2);
        }
        else {
            context.startService(intent1);
            context.startService(intent2);
        }
    }
}
