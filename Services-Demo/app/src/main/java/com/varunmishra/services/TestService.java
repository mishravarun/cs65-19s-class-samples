package com.varunmishra.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service {
    public static Thread t;
    private DaemonThread thread;
    public static boolean running;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAGG", "Service Started");

        thread = new DaemonThread();
        thread.start();
        running = true;
        return START_STICKY;

    }

    private class DaemonThread extends Thread {
        int count = 0;

        public void run()
        {
            while(running) {
                synchronized (this) {

                    try {
                        count++;
                        Log.d("TAGG","Service Count -- " + count);
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
        Log.d("TAGG", "Service Created");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("TAGG", "Service Destroyed");
        running = false;
        super.onDestroy();
    }

}
