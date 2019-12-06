package cn.emoney.appserverkeeplive2.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import cn.emoney.appserverkeeplive2.MainActivity;
import cn.emoney.appserverkeeplive2.R;
import cn.emoney.appserverkeeplive2.bind.ProcessConnectBind;

public class StepService extends Service {

    private NotificationManager manager;

    private ProcessConnectBind processConnectBind;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("wjf>>>>","StepService_onBind()开始绑定！");
        return processConnectBind;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //链接上
            Log.e("wjf>>>>","onServiceConnected链接上了守护进程GuardService！");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("wjf>>>>","onServiceDisconnected断开了守护程GuardService！");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(StepService.this, GuardService.class));
            }
            else {
                startService(new Intent(StepService.this, GuardService.class));
            }
            bindService(new Intent(StepService.this,GuardService.class),
                    serviceConnection, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public void onCreate() {
        Log.e("wjf>>>>","StepService_onCreate()已开启！");
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(channelId, "stepServiceChannelName", NotificationManager.IMPORTANCE_HIGH);
//            manager.createNotificationChannel(notificationChannel);
//            int channel = 1;
//        PendingIntent contentIntent = PendingIntent.getService(this, 0, intent, 0);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
//            builder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setTicker("进程StepService出来了")
//                .setContentIntent(contentIntent)
//                    .setContentTitle("我是StepService，我怕谁!")
//                    .setAutoCancel(true)
//                    .setContentText("xoxox")
//                    .setWhen(System.currentTimeMillis());
//            Notification nf = builder.build();
//            Log.e("wjf>>>>", "StepService_manager.notify()通知后！channelId--" + channelId);
//            startForeground(channel, nf);
//            Log.e("wjf>>>>", "StepService_startForeground()通知后！channelId--" + channelId);
//            NotificationChannel nc = manager.getNotificationChannel(channelId);
//            if (nc.getImportance() == NotificationManager.IMPORTANCE_NONE) {
//                Intent in = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//                in.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//                in.putExtra(Settings.EXTRA_CHANNEL_ID, nc.getId());
//                startActivity(in);
//            }
//        }

        if(processConnectBind == null)
        {
            processConnectBind = new ProcessConnectBind(){
                @Override
                public void connectSuccessed(boolean successed) throws RemoteException {
                    Log.e("wjf>>>>","StepService_connectSuccessed()绑定！successed: " + successed);
                }

                @Override
                public void connectFailed(boolean failed) throws RemoteException {
                    Log.e("wjf>>>>","StepService_connectFailed()绑定！failed: " + failed);
                }
            };
        }
    }

    String channelId = "10";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("wjf>>>>","StepService_onStartCommand()已开启！");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,"stepServiceChannelName", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
            int channel = 1;
            Intent intent1 = new Intent(this, MainActivity.class);
//            PendingIntent contentIntent = PendingIntent.getService(this, 0, intent1, 0);
            PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("进程StepService出来了")
                    .setContentIntent(contentIntent)
                    .setContentTitle("我是StepService，我怕谁!")
                    .setAutoCancel(true)
                    .setContentText("xoxox")
                    .setWhen( System.currentTimeMillis());
            Notification nf = builder.build();
//            Log.e("wjf>>>>","StepService_manager.notify()通知后！startId--" + startId);
            startForeground(channel,nf);
//            Log.e("wjf>>>>","StepService_startForeground()通知后！startId--" + startId);
            NotificationChannel nc = manager.getNotificationChannel(channelId);
            if(nc.getImportance() == NotificationManager.IMPORTANCE_NONE)
            {
                Intent in = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                in.putExtra(Settings.EXTRA_APP_PACKAGE,getPackageName());
                in.putExtra(Settings.EXTRA_CHANNEL_ID,nc.getId());
                startActivity(in);
            }
        }
        bindService(new Intent(this,GuardService.class),serviceConnection,BIND_IMPORTANT);
        return START_STICKY;
    }
}
