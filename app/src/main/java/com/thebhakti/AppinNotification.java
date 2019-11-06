package com.thebhakti;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

/**
 * Created by Yeshveer on 8/27/2018.
 */

public class AppinNotification extends AppCompatActivity {

    private EditText txtMsg;
    private EditText txtSub;
    private Button sendBtn;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_lay);

        txtMsg = (EditText) findViewById(R.id.txtMsg);
        txtSub = (EditText) findViewById(R.id.txtSub);
        sendBtn = (Button) findViewById(R.id.sendBtn);



        // CLick Listner on the button for notification sending
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=txtMsg.getText().toString();
                String content=txtSub.getText().toString();
                Intent intent = new Intent(AppinNotification.this, DisplayAct.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(AppinNotification.this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(AppinNotification.this)
                        .setSmallIcon(R.drawable.app_logo)
                        .setContentTitle("This is the title")
                        .setContentText("Hello new update")
                        .setContentIntent(pendingIntent);
                notificationbuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(AppinNotification.this);
                notificationManagerCompat.notify(1,notificationbuilder.build());
                startActivity(intent);


            }
        });




    }
}
