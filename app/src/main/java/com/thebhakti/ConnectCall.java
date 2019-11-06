package com.thebhakti;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.internal.telephony.ITelephony;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * Created by Yeshveer on 10/1/2018.
 */


public class ConnectCall extends AppCompatActivity {
    String IMEI, Mobile;
    Animation blink;
    private ImageView imgCall;
    private TextView callStatusMsg;
    String TodayTime;
    private LinearLayout mainLay;
    private LinearLayout mainLay2;








    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_call);
        final TextView txtStatus = (TextView) findViewById(R.id.txtStatus);
        final WebView callWebview = (WebView) findViewById(R.id.callWebview);
        final TextView connectmob = (TextView) findViewById(R.id.connectmob);
        imgCall = (ImageView) findViewById(R.id.imgCall);
        callStatusMsg = (TextView) findViewById(R.id.callStatusMsg);
        mainLay = (LinearLayout) findViewById(R.id.mainLay);
        mainLay2 = (LinearLayout) findViewById(R.id.mainLay2);


        // Receive intent
        {
            Intent intentGet = getIntent();
            IMEI = intentGet.getStringExtra("IMEI");
            Mobile = intentGet.getStringExtra("Mobile");
        }

        // set Glide Image
        Glide.with(ConnectCall.this)
                .load(R.drawable.hotline_call)
                .asGif()
                .crossFade()
                .into(imgCall);

//initilize animation
        blink= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_my);
        txtStatus.startAnimation(blink);

        // basic webview settings
        callWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        callWebview.getSettings().setJavaScriptEnabled(true);
        callWebview.getSettings().setLoadsImagesAutomatically(true);
        // External Settings
        callWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
        callWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        callWebview.getSettings().getDomStorageEnabled();

        callWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        callWebview.getSettings().setMediaPlaybackRequiresUserGesture(true);
        callWebview.getSettings().getTextZoom();
        callWebview.getSettings().getBuiltInZoomControls();
        callWebview.getSettings().getAllowUniversalAccessFromFileURLs();
        callWebview.getSettings().getAllowFileAccess();
        callWebview.getSettings().getCacheMode();
        callWebview.getSettings().getBlockNetworkLoads();
        callWebview.getSettings().getAllowContentAccess();
        callWebview.getSettings().getBlockNetworkImage();
        // webCromeClient
        callWebview.setWebChromeClient(new WebChromeClient());
        callWebview.setWebViewClient(new WebViewClient());
        LiveWebview.MyBrowser myBrowser=new LiveWebview.MyBrowser();
        callWebview.setWebViewClient(myBrowser);









        //set webclient
        callWebview.setWebViewClient(new WebViewClient() {
                                         @Override
                                         public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                             super.onPageStarted(view, url, favicon);

                                         }
                                     });



        connectmob.setText("Please wait a Movement.."+Mobile);
        // Initilize Telephony service


// Get balance from firebase for how many time a user can connect to call
        FirebaseFirestore db1;
        CollectionReference cr1;
        Query qry;
        db1=FirebaseFirestore.getInstance();
        cr1=db1.collection("Login_users");
        qry=cr1.whereEqualTo("user_imei",IMEI);
        qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {

                    if(Objects.equals(task.getResult(), ""))
                    {
                        Toast.makeText(ConnectCall.this, "No Record Found in database", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        for(QueryDocumentSnapshot doc:task.getResult()) {
                            // check now balance for call if balamnce is below to 30 Rs then dont connect call
                                if(Integer.parseInt(String.valueOf(doc.getString("amount")))<=25)
                                {
                                    Toast.makeText(ConnectCall.this, "Your Balance is low for call! Need Balance more than Rs.25 in Wallet ! Please Recharge", Toast.LENGTH_LONG).show();
                                    Intent intent1=new Intent(ConnectCall.this,Wallet_intro.class);
                                    startActivity(intent1);
                                    finish();
                                }
                                else {
                                    // check 10am to 7 pm calling time
                                    @SuppressLint("SimpleDateFormat") DateFormat time1 = new SimpleDateFormat("HH.mm");
                                    Date time = new Date();
                                    TodayTime = time1.format(time);
                                    String mrng="10.00";
                                    String seven="19.00";
                                    if(Float.valueOf(TodayTime)>Float.valueOf(mrng) && Float.valueOf(TodayTime)<Float.valueOf(seven))
                                    {
                                        // connect Call
                                        /*Intent intent1 = new Intent(Intent.ACTION_VIEW,Uri.parse("http://cloudagent.in/calite/c2capi.html?number="+Mobile+"&username=99.squareknot&apiKey=af47f0992f34682c9841fbbf392e709a"));*/
                                        String Amount=doc.getString("amount");
                                        //1min=60sec*1500;
                                        Integer timer=(Integer.parseInt(String.valueOf(Amount))/10)*90000;


                                        // Load Data on webview
                                        callWebview.loadUrl("http://cloudagent.in/calite/c2capi.html?number=" + Mobile + "&username=99.squareknot&apiKey=8e232e30ecf9345003c97d1a49c7b3f7");


                                        // wait for call disconnect

                                        // Disconnect Call
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                                                    Class<?> clazz;
                                                    assert tm != null;
                                                    clazz = Class.forName(tm.getClass().getName());
                                                    assert clazz != null;
                                                    Method method;
                                                    method = clazz.getDeclaredMethod("getITelephony");
                                                    method.setAccessible(true);
                                                    ITelephony telephonyService = (ITelephony) method.invoke(tm);
                                                    telephonyService.endCall();
                                                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                                                    e.printStackTrace();
                                                }

                                                ITelephony ts = new ITelephony() {
                                                    @Override
                                                    public boolean endCall() {
                                                        Toast.makeText(ConnectCall.this, "Your Wallet amount is finished, Please Recharge Wallet", Toast.LENGTH_SHORT).show();
                                                        return false;
                                                    }

                                                    @Override
                                                    public void answerRingingCall() {

                                                    }

                                                    @Override
                                                    public void silenceRinger() {

                                                    }
                                                };



                                            }
                                        },timer);


                                    }
                                    else
                                    {
                                        Toast.makeText(ConnectCall.this, "Talk time is between 10.00 AM to 07.00 PM   \n आचार्य से बात करने का समय सुबह 10  बजे से शाम 7  बजे तक है | \n (Monday-Saturday)", Toast.LENGTH_LONG).show();
                                        mainLay.setVisibility(View.GONE);
                                        imgCall.setVisibility(View.GONE);
                                        connectmob.setVisibility(View.GONE);
                                        mainLay2.setVisibility(View.VISIBLE);
                                        txtStatus.setVisibility(View.GONE);
                                        finish();

                                    }




                                        }// closer of balance below Rs. 30
                        }// closer of Main for loop
                    }
                }
            }
        });

/*

         ITelephony ts = new ITelephony() {
                                                @Override
                                                public boolean endCall() {
                                                    Toast.makeText(ConnectCall.this, "Disconnecting Calls", Toast.LENGTH_SHORT).show();
                                                    return false;
                                                }

                                                @Override
                                                public void answerRingingCall() {

                                                }

                                                @Override
                                                public void silenceRinger() {

                                                }
                                            };
                                        }
        Intent intent1 = new Intent(Intent.ACTION_CALL);
        intent1.setData(Uri.parse("tel:" + "9306488494"));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent1);

        // Disconnect Call
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                Class<?> clazz;
                assert tm != null;
                clazz = Class.forName(tm.getClass().getName());
                assert clazz != null;
                Method method;
                method = clazz.getDeclaredMethod("getITelephony");
                method.setAccessible(true);
                ITelephony telephonyService = (ITelephony) method.invoke(tm);
                telephonyService.endCall();
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }




            }
        },timer);*/
/*
// JSON parsing Status
        RequestQueue queue6= Volley.newRequestQueue(ConnectCall.this);
        String Url6="http://cloudagent.in/calite/c2capi.html?number="+Mobile+"&username=99.squareknot&apiKey=8e232e30ecf9345003c97d1a49c7b3f7";
        StringRequest request6=new StringRequest(Request.Method.GET, Url6, new Response.Listener<String>() {
            @Override
            public void onResponse(String response6) {
                //weatherData.setText("Response is :- ");
                parseStatus(response6);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConnectCall.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });

        queue6.add(request6);
        super.onStart();*/


    }// closer of onCreate Method

  /*  private void parseStatus(String response6) {
        try {
            // Create JSOn Object
            JSONArray jsonArray = new JSONArray(response6);


            for(int i=0;i<jsonArray.length();i++)
            {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String Msg=jsonObject.getString("0");
                callStatusMsg.setText(Msg);

                String Status=jsonObject.getString("status");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/


    private class MyBrowser extends WebViewClient {
        }

        // page on Load Events
    @Override
    protected void onStart() {
        mainLay2.setVisibility(View.GONE);
        mainLay.setVisibility(View.VISIBLE);
        super.onStart();
    }

    // Evenets on back key Pressed

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            Intent intent1=new Intent(ConnectCall.this,OptionsBeforeChat.class);
            startActivity(intent1);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
