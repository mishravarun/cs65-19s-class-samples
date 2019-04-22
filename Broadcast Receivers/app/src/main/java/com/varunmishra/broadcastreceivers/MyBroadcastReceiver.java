package com.varunmishra.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("TAGG", intent.getAction());
        CharSequence data = intent.getCharSequenceExtra("msg");
        Toast.makeText(context,"Received Message: "+data,Toast.LENGTH_LONG).show();
    }
}
