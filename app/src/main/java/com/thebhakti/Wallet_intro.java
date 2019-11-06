package com.thebhakti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Yeshveer on 9/28/2018.
 */

public class Wallet_intro extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_intro);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1=new Intent(Wallet_intro.this,MyWallet2.class);
                startActivity(intent1);
                finish();
            }
        },3500);
    }
}
