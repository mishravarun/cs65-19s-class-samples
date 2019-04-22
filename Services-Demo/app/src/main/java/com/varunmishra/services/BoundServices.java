package com.varunmishra.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class BoundServices extends Service {
    public static boolean running;
    CustomObject c;
//    int count = 0;


    public class MyLocalBinder extends Binder {
        BoundServices getService() {
            return BoundServices.this;
        }
        public CustomObject getCount(){
            return c;
        }
    }
    private final IBinder myBinder = new MyLocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TAGG", "Bound Service onBind");

        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAGG", "Bound Service Started");




        return Service.START_STICKY;

    }

    public Notification createNotification(Service context) {

        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default";
            createChannel(context, channelId);
            notification = buildNotification(context, channelId);

        }
        return notification;
    }

    private static Notification buildNotification(Service context, String channelId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentText("Bound Service Notification");
        Notification notification=builder.build();

        return notification;

    }

    private static void createChannel(Service ctx, String channelId) {
        // Create a channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager notificationManager =
                (NotificationManager)
                        ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence channelName = "Default channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel;
            notificationChannel = new NotificationChannel(
                    channelId, channelName, importance);

            notificationManager.createNotificationChannel(
                    notificationChannel);
        }


    }

    private class DaemonThread extends Thread {

        public void run()
        {
            while(running) {
                synchronized (this) {

                    try {
                        c.setCount(c.getCount()+1);
                        Log.d("TAGG","Bound Service Count -- " + c.getCount());
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("TAGG", "Bound Service Unbind");

        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.d("TAGG", "Bound Service Created");
        c = new CustomObject(0);

        DaemonThread thread = new DaemonThread();
        thread.start();
        running = true;
//        Notification notification = createNotification(this);
//        Log.d("TAGG", "showing Notification");
//        startForeground(
//                12345, notification);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("TAGG", "Bound Service Destroyed");
        running = false;
        super.onDestroy();
    }

}
