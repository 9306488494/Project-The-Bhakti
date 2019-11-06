package com.thebhakti;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Yeshveer on 10/1/2018.
 */

public class OutgoingCalls extends AppCompatActivity {
    private TextView txtOutgoing;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outgoing_calls);
        txtOutgoing = (TextView) findViewById(R.id.txtOutgoing);


// Receive Intent details for outgoing mobile no
   /*     {
            Intent intent1;
            intent1 = getIntent();
            String Mobile=intent1.getStringExtra("OutgoingNum");
            Toast.makeText(this, Mobile, Toast.LENGTH_SHORT).show();

        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] per = {android.Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.SEND_SMS};

        }


       /* Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor c = getContentResolver().query(allCalls, null, null, null, null);

        assert c != null;
        String num= c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));// for  number
        String name= c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
        String duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));// for duration
        *//*int type = Integer.parseInt(c.getString(c.getColumnIndex(CallLog.Calls.TYPE)));// for call type, Incoming or out going.*//*
        Toast.makeText(this, "Details are: "+num+","+name+","+duration, Toast.LENGTH_SHORT).show();
        c.close();*/


      /*  private String getCallDetails() {*/

        StringBuffer sb = new StringBuffer();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            sb.append("Call Details :");
            while (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                        + dir + " \nCall Date:--- " + callDayTime
                        + " \nCall duration in sec :--- " + callDuration);
                sb.append("\n----------------------------------");
            }
            managedCursor.close();
        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        txtOutgoing.setText(sb.toString());
        /*    return sb.toString();

        }
        */


    }// onCreate Closer

/*    @Override
    protected void onStop() {
        unregisterReceiver(IncomingCalls);
        super.onStop();
    }*/
}
