package com.thebhakti;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Yeshveer on 9/3/2018.
 */

public class ChatUserIdentifier extends AppCompatActivity {
    String user_Name,user_Imei,user_Type,user_Mobile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Receive user intent for chat
        {
            Intent intentGet = getIntent();
            user_Name = intentGet.getStringExtra("user");
            user_Imei = intentGet.getStringExtra("IMEI");
            user_Type = intentGet.getStringExtra("type");
            user_Mobile = intentGet.getStringExtra("Mobile");
        }

        if (user_Type.equals("chatter"))
        {
            Intent intent1=new Intent(ChatUserIdentifier.this,ChatPanelUser.class);
            intent1.putExtra("user",user_Name);
            intent1.putExtra("Mobile", user_Mobile);
            intent1.putExtra("IMEI", user_Imei);
            intent1.putExtra("type", user_Type);
            startActivity(intent1);

        }
        else
        {
            Intent intent1=new Intent(ChatUserIdentifier.this,UserList.class);

            startActivity(intent1);
            finish();
        }
    }
}
