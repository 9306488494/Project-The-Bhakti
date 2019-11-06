package com.thebhakti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by Yeshveer on 8/11/2018.
 */

public class NetworkNot extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_network);
        if (AppStatus.getInstance(this).isOnline()) {

            Intent intent1=new Intent(NetworkNot.this,DisplayAct.class);
            startActivity(intent1);
            finish();


        } else {

            Toast.makeText(NetworkNot.this, "Please !! Make your network ON", Toast.LENGTH_SHORT).show();

        }
    }// on Create Closer

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
          Intent intent1=new Intent(NetworkNot.this,DisplayAct.class);
          startActivity(intent1);
          finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
