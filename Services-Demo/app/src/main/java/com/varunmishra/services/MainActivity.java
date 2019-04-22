package com.varunmishra.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    BoundServices mService;
    boolean isBound = false;
    private CustomObject mActivityCount;
    @Override
    protected void onDestroy() {
        Log.d("TAGG", "Activity onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("TAGG", "Activity onCreate");

    }


    public void onStopService1(View view) {
        Log.d("TAGG", "Activity Stopping Service 1");
        stopService(new Intent(this, TestService.class));
    }

    public void onStartService1(View view) {
        Log.d("TAGG", "Activity Starting Service 1");
        startService(new Intent(this, TestService.class));
    }

    public void onStartForegroundService(View view) {
        Log.d("TAGG", "Activity Starting Foreground Service");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, TestServiceForeground.class));
        }
    }

    public void onStopForegroundService(View view) {
        Log.d("TAGG", "Activity Stopping Foreground Service");
        stopService(new Intent(this, TestServiceForeground.class));
    }


    private ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.d("TAGG", "Activity Service Connected");
            BoundServices.MyLocalBinder binder = (BoundServices.MyLocalBinder) service;
            mService = binder.getService();
            mActivityCount = binder.getCount();
            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("TAGG", "Activity Service Disconnected");

            isBound = false;
        }
    };

    public void onStopBoundService(View view) {
        if (isBound){
            unbindService(mServiceConnection);
        }
    }

    public void onStartBoundService(View view) {
        Intent intent = new Intent(this, BoundServices.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

    }

    public void onBtnRefresh(View view) {
        if (isBound) {
            TextView tvCount = findViewById(R.id.tvCount);
            tvCount.setText("Count -- " + mActivityCount.getCount());
        }
    }
}
