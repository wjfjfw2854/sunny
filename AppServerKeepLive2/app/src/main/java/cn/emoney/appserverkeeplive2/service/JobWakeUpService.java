package cn.emoney.appserverkeeplive2.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.List;

import cn.emoney.appserverkeeplive2.MainActivity;
import cn.emoney.appserverkeeplive2.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {

    private int jobWakeUpId = 0;
//    private final long INTERVAL = 15 * 60 * 1000L;//7.0以后JobService最小间格不得小于15分钟
    String channelId = "80";
    private NotificationManager manager;
    private int jobx = -1;

    @Override
    public void onCreate() {
        Log.e("wjf>>>>","应用退出后JobWakeUpService中onCreate()创建！");
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("wjf>>>>","应用退出后JobWakeUpService中onStartCommand()创建！");
        Intent intent1 = new Intent(this, MainActivity.class);
        startMain(intent1);
        return START_STICKY;
    }

    private void startMain(Intent intent) {
        scheduleJob(getJobInfo());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,"GuardServiceChannelName", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
            int channel = 2;
//            PendingIntent contentIntent = PendingIntent.getService(this, 0, intent, 0);
            PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("进程JobWakeUpService出来了")
                    .setContentIntent(contentIntent)
                    .setContentTitle("我是JobWakeUpService，无时雷？!" + (++jobx) + "次！")
                    .setAutoCancel(true)
                    .setContentText("wilderNess!")
                    .setWhen( System.currentTimeMillis());
            Notification nf = builder.build();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = Integer.valueOf(nf.getChannelId());
            }
//            Log.e("wjf>>>>","JobWakeUpService_manager.notify()通知后！startId--" + startId);
            startForeground(channel,nf);
//            Log.e("wjf>>>>","JobWakeUpService_startForeground()通知后！startId--" + startId);
            NotificationChannel nc = manager.getNotificationChannel(channelId);
            if(nc.getImportance() == NotificationManager.IMPORTANCE_NONE)
            {
                Intent in = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                in.putExtra(Settings.EXTRA_APP_PACKAGE,getPackageName());
                in.putExtra(Settings.EXTRA_CHANNEL_ID,nc.getId());
                startActivity(in);
            }
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("wjf>>>>","应用退出后JobWakeUpService中onStartJob()创建！");
        boolean msgServiceAlive = serviceAlive(StepService.class.getName());
        boolean msgGuardServiceAlive = serviceAlive(GuardService.class.getName());
        Log.e("wjf>>>>","应用退出后JobWakeUpService中onStartJob()创建！msgServiceAlive---" + msgServiceAlive + "::msgGuardServiceAlive---" + msgGuardServiceAlive);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!msgServiceAlive) {
                startForegroundService(new Intent(this, StepService.class));
            }
            if (!msgGuardServiceAlive)
            {
                startForegroundService(new Intent(this, GuardService.class));
            }
        }
        else {
            if (!msgServiceAlive) {
                startService(new Intent(this, StepService.class));
            }
            if (!msgGuardServiceAlive) {
                startService(new Intent(this, GuardService.class));
            }
        }
//        jobFinished(params,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startMain(intent1);
        return true;
    }

    private void scheduleJob(JobInfo jobInfo)
    {
        JobScheduler jobScheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }

    private JobInfo getJobInfo()
    {
        JobInfo.Builder builder = new JobInfo.Builder(jobWakeUpId,new ComponentName(this,JobWakeUpService.class));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);//最小延迟
            builder.setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);//最长延迟
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
            builder.setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS,JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        }
        else
        {
            builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
        }
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(true);
        builder.setRequiresDeviceIdle(false);
        return builder.build();
    }

    private boolean serviceAlive(String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
