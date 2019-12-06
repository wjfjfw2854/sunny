package cn.emoney.appserverkeeplive2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cn.emoney.appserverkeeplive2.service.GuardService;
import cn.emoney.appserverkeeplive2.service.JobWakeUpService;
import cn.emoney.appserverkeeplive2.service.StepService;

public class MainActivity extends AppCompatActivity {

    private PowerManager.WakeLock wakeLock;
    private TextView txtOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtOpen = findViewById(R.id.txtOpen);
        txtOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAllServices();
            }
        });
    }

    private void startAllServices()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, StepService.class));
            startForegroundService(new Intent(this, GuardService.class));
            startForegroundService(new Intent(this, JobWakeUpService.class));
        }
        else {
            startService(new Intent(this, StepService.class));
            startService(new Intent(this, GuardService.class));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startService(new Intent(this, JobWakeUpService.class));
            }
        }
//        getProcessName();
        getLock(this);
    }

    private void getProcessName()
    {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for(int i = 0;i < list.size();i ++)
        {
            Log.e("wjf>>>>","正在运行的进程名称_" + i + "-------" + list.get(i).processName);
        }
    }

    synchronized private void getLock(Context context){
        if(wakeLock == null) {
            PowerManager pmg = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pmg.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, StepService.class.getName());
            wakeLock.setReferenceCounted(true);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if(hour >= 23 || hour <= 6)
            {
                wakeLock.acquire(5000);
            }
            else
            {
                wakeLock.acquire(300000);
            }
        }
    }

    synchronized private void releaseLock()
    {
        if(wakeLock != null)
        {
            if(wakeLock.isHeld())
            {
                wakeLock.release();
            }
            wakeLock = null;
        }
    }

    @Override
    protected void onDestroy() {
        releaseLock();
        super.onDestroy();
        Log.e("wjf>>>>","MainActivity中onDestroy----");
    }
}
