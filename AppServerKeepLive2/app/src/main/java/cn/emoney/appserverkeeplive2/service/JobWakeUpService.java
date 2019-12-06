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
import android.os.AsyncTask;
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

    private int jobWakeUpId = 0x00200;
//    private final long INTERVAL = 15 * 60 * 1000L;//7.0以后JobService最小间格不得小于15分钟
    String channelId = "80";
    private NotificationManager manager;
    private int jobx = -1;

    @Override
    public void onCreate() {
        Log.e("wjf>>>>","JobWakeUpService中onCreate()创建！");
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("wjf>>>>","JobWakeUpService中onStartCommand()创建！");
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
            startForeground(channel,nf);
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
        Log.e("wjf>>>>","JobWakeUpService中onStartJob()创建！");
        new AsyncTaskMonitor().execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startMain(intent1);
        return false;
    }

    private void scheduleJob(JobInfo jobInfo)
    {
        JobScheduler jobScheduler = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            jobScheduler = (JobScheduler)getSystemService(JobScheduler.class);
        }
        else
        {
            jobScheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
        }
        jobScheduler.cancel(jobWakeUpId);
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
        Log.e("wjf>>>>","JobWakeUpService退出onDestroy()准备再次自启动");
        Intent intent1 = new Intent();
        intent1.setClass(this,JobWakeUpService.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent1);
        Log.e("wjf>>>>","JobWakeUpService退出onDestroy()自启动完成");
    }

    private class AsyncTaskMonitor extends AsyncTask<JobParameters,Void,String>{
        private JobParameters jobParameters;
        @Override
        protected String doInBackground(JobParameters... jobParameters) {//进度process
            this.jobParameters = jobParameters[0];
            boolean msgServiceAlive = serviceAlive(StepService.class.getName());
            boolean msgGuardServiceAlive = serviceAlive(GuardService.class.getName());
            Log.e("wjf>>>>","JobWakeUpService中onStartJob()的子线程AsyncTaskMonitor--msgServiceAlive---" + msgServiceAlive + "::msgGuardServiceAlive---" + msgGuardServiceAlive);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!msgServiceAlive) {
                    startForegroundService(new Intent(JobWakeUpService.this, StepService.class));
                }
                if (!msgGuardServiceAlive)
                {
                    startForegroundService(new Intent(JobWakeUpService.this, GuardService.class));
                }
            }
            else {
                if (!msgServiceAlive) {
                    startService(new Intent(JobWakeUpService.this, StepService.class));
                }
                if (!msgGuardServiceAlive) {
                    startService(new Intent(JobWakeUpService.this, GuardService.class));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {//结果result
            super.onPostExecute(s);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                jobFinished(jobParameters,true);//调用后才重复执行
            }
            else
            {
                jobFinished(jobParameters,false);//调用后才重复执行
            }
        }
    }
}
