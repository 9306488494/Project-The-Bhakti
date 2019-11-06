package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;



public class IncomingCalls  extends BroadcastReceiver {

    Context context;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE) || intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {


            /*String phonenumber = intent.getStringExtra(Intent.ACTION_NEW_OUTGOING_CALL);*/
            String phonenumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
       /* String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);*/

            // Send the intent with contact no on another activity
            Intent intent1 = new Intent(context, OutgoingCalls.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("OutgoingNum", phonenumber);
            context.startActivity(intent1);
        }
        else
        {
            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
        }
    }// onCreate closer

}