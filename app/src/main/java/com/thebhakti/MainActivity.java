package com.thebhakti;

import android.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.scwang.wave.MultiWaveHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
     ImageView imageView;
     MultiWaveHeader waveHeader;
    private String Check_ver,ver;
    FirebaseFirestore db;
    CollectionReference cr;
    int reqcode=1;
    private String IMEI,value,Url;


 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        waveHeader = (MultiWaveHeader) findViewById(R.id.waveHeader);


        // Run time Permission for Read Phone State and SEND SMS
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         String[] per = {android.Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.SEND_SMS};
         requestPermissions(per, reqcode);
     }
     // Permissions Tag closed



    }// onCreate Method closer

    private void parseData(String response) {
        try {
        // Create JSOn Object

        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
        ver=jsonObject.getString("current_ver");

            if (AppStatus.getInstance(this).isOnline()) {

                // Check Versions of the app
                PackageManager manager = this.getPackageManager();
                PackageInfo info = null;
                try {
                    info = manager.getPackageInfo(this.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                assert info != null;
                Check_ver = info.versionName;

                if (Check_ver.equals(ver)) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent1 = new Intent(MainActivity.this, DisplayAct.class);
                            startActivity(intent1);
// Create directory
                            final String Paths = Environment.getExternalStorageDirectory() + File.separator + "THE_Bhakti" + File.separator + "Data2" + File.separator;
                            File dir1;
                            dir1 = new File(Paths);
                            if (!dir1.isDirectory()) {
                                if (!dir1.exists()) {
                                    dir1.mkdirs();
                                    Toast.makeText(MainActivity.this, "Directory Created", Toast.LENGTH_SHORT).show();
                                }
                            }
                            finish();


                        }
                    }, 1000);
                } else {
                    Intent intent1 = new Intent(MainActivity.this, CheckUpdate.class);
                    startActivity(intent1);
                    finish();

                }
            }
            else
            {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1=new Intent(MainActivity.this,NetworkNot.class);
                        startActivity(intent1);
                        finish();


                    }
                },1000);
            }

        }

        }
        catch (JSONException e) {
        e.printStackTrace();
        }

    }

    // create instance for close the app
    @SuppressLint("StaticFieldLeak")
    public static MainActivity fs;

    @Override
    protected void onStart() {
        fs=this;
        super.onStart();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == reqcode && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            currentVersion();

        }
        else
        {
            Toast.makeText(this, "Application need permission for better results", Toast.LENGTH_SHORT).show();
        }
    }

    private void currentVersion() {
        if (AppStatus.getInstance(this).isOnline()) {

            // Check Versions of the app
            PackageManager manager = this.getPackageManager();
            PackageInfo info = null;
            try {
                info = manager.getPackageInfo(this.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            assert info != null;
            Check_ver=info.versionName ;
            final PackageInfo finalInfo = info;

            // fetch from api
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            Url = "https://prabhubhakti.com/API2/cv.php?id=00021";

            StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //weatherData.setText("Response is :- ");
                    parseData(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

                }
            });
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            queue.add(request);
            //close parse



        } else {

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1=new Intent(MainActivity.this,NetworkNot.class);
                    startActivity(intent1);
                    finish();


                }
            },1000);

        }
    }
}
