package com.varunmishra.broadcastreceivers;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        registerReceiver(
                receiver,
                new IntentFilter("com.varunmishra.CUSTOM_BROADCAST")
        );

        // You can also register for System Broadcasts
//        registerReceiver(
//                receiver,
//                new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED")
//        );
    }

    public void onClickShowBroadcast(View view) {
        Log.d("TAGG", "sent");
        EditText st = findViewById(R.id.txtMsg);
        Intent intent = new Intent(); //(this, MyBroadcastReceiver.class);
        intent.putExtra("msg",(CharSequence)st.getText().toString());
        intent.setAction("com.varunmishra.CUSTOM_BROADCAST");
        sendBroadcast(intent);
    }
}
