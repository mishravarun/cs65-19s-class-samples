package com.varunmishra.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class TestServiceForeground  extends Service {
    private DaemonThread thread;
    public static boolean running;
    private int count = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAGG", "Foreground Service Started");

        thread = new DaemonThread();
        thread.start();
        running = true;


        return START_STICKY;

    }


    private class DaemonThread extends Thread {

        public void run()
        {
            while(running) {
                synchronized (this) {

                    try {
                        count++;
                        Log.d("TAGG","Foreground Service Count -- " + count);
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onCreate() {
        Log.d("TAGG", "Foreground Service Created");
        setForeground();

        super.onCreate();
    }

    private void setForeground() {
        final String PRIMARY_NOTIF_CHANNEL = "default";
        final int PRIMARY_FOREGROUND_NOTIF_SERVICE_ID = 1001;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel chan1 = new NotificationChannel(
                    PRIMARY_NOTIF_CHANNEL,
                    "default",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(chan1);
            Notification notification = new NotificationCompat.Builder(this, PRIMARY_NOTIF_CHANNEL)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentTitle("Test Service")
                    .setContentText("Running Foreground Service")
                    .build();

            startForeground(PRIMARY_FOREGROUND_NOTIF_SERVICE_ID, notification);
        }
    }

    @Override
    public void onDestroy() {
        Log.d("TAGG", "Foreground Service Destroyed");
        running = false;
        super.onDestroy();
    }

}
