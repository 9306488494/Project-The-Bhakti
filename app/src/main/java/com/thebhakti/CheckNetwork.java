package com.thebhakti;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Yeshveer on 9/3/2018.
 */

public class CheckNetwork extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppStatus.getInstance(this).isOnline())

        {

            // Check Versions of the app
            PackageManager manager = this.getPackageManager();
            PackageInfo info = null;
            try {
                info = manager.getPackageInfo(this.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            Intent intent1=new Intent(CheckNetwork.this,NetworkNot.class);
            startActivity(intent1);
            finish();

        }
    }
}
