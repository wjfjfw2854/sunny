package cn.emoney.appserverkeeplive2.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("wjf>>>>","BootCompleteReceiver的用处待定！");
    }
}
