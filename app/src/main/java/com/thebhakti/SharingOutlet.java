package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yeshveer on 10/29/2018.
 */

public class SharingOutlet extends AppCompatActivity {
    private ImageView prgressImg;
    private TextView walletCash;
    private CardView shareBtn;
    int reqcode=1;

    private Button whatsappShare;
    String IMEI,user,Mobile,type,Url,amount,share_times,detect_date,reg_date,stop;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_outlet);

        prgressImg = (ImageView) findViewById(R.id.prgressImg);
        walletCash = (TextView) findViewById(R.id.walletCash);
  /*      shareBtn = (CardView) findViewById(R.id.shareBtn);*/
    /*    whatsappShare = (Button) findViewById(R.id.whatsappShare);*/

        // getIntents
        {
            Intent intent1;
            intent1=getIntent();
            IMEI=intent1.getStringExtra("IMEI");
            user=intent1.getStringExtra("user");
            Mobile=intent1.getStringExtra("Mobile");
            type=intent1.getStringExtra("type");
            detect_date=intent1.getStringExtra("detect_date");

        }
            JSONParser();
            // Reload Data
        //Add 26 rs on initial stage







/*
        *//*First we will show the data and image of progress*//*
        FirebaseFirestore db1;
        CollectionReference cr1;
        Query qry;
        db1=FirebaseFirestore.getInstance();
        cr1=db1.collection("Login_users");
        qry=cr1.whereEqualTo("user_imei",IMEI);
        qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                   if(Objects.equals(task.getResult(), ""))
                   {
                       Toast.makeText(SharingOutlet.this, "No sharing Record Found", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       for(QueryDocumentSnapshot doc:task.getResult())
                       {
                           *//*Display amount on wallet on sharing partition*//*
                           String AMT=doc.getString("amount");
                           walletCash.setText("Wallet Balance : "+AMT);

                           *//*Change the Progress Image on sharing counts*//*
                           String ST=doc.getString("share_times");
                            if(Objects.equals(ST, "0"))
                            {
                                prgressImg.setImageResource(R.drawable.bar_0);
                            }
                            else if(Objects.equals(ST, "1"))
                            {
                                prgressImg.setImageResource(R.drawable.bar_1);
                            }
                            else if(Objects.equals(ST, "2"))
                            {
                                prgressImg.setImageResource(R.drawable.bar_2);
                            }
                            else if(Objects.equals(ST, "3"))
                            {
                                prgressImg.setImageResource(R.drawable.bar_3);
                            }
                            else if(Objects.equals(ST, "4"))
                            {
                                prgressImg.setImageResource(R.drawable.bar_4);
                            }
                            else if(Objects.equals(ST, "5"))
                            {
                                prgressImg.setImageResource(R.drawable.bar_5);
                            }


                       }
                   }
                }
                else
                {
                    Toast.makeText(SharingOutlet.this, "Unable to find your record", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

            /* now click events on whatsapp sharing */
  /*      shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareintent=new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/url");
                String data="अपने बारे में जानने के लिए या अपनी किसी भी समस्या के समाधान के लिए फ्री में अपनी कुंडली दिखाए और इसके साथ साथ आनन्द ले प्रसिद्ध मंदिरो के लाइव दर्शन, धार्मिक कथा, आरती और भजन वीडियो। Download Prabhu Bhakti App now! : \n  https://play.google.com/store/apps/details?id=com.thebhakti";
                shareintent.putExtra(Intent.EXTRA_TEXT,data);
                //intent=MainActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                startActivity(Intent.createChooser(shareintent,"Share Via"));
                startActivityForResult(shareintent,reqcode);
            }
        });*/
/* also apply sharing on Share button*/
 /*       whatsappShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareintent=new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/url");
                String data="अपने बारे में जानने के लिए या अपनी किसी भी समस्या के समाधान के लिए फ्री में अपनी कुंडली दिखाए और इसके साथ साथ आनन्द ले प्रसिद्ध मंदिरो के लाइव दर्शन, धार्मिक कथा, आरती और भजन वीडियो। Download Prabhu Bhakti App now! \n \n  https://play.google.com/store/apps/details?id=com.thebhakti";
                shareintent.putExtra(Intent.EXTRA_TEXT,data);
            *//*    shareintent=SharingOutlet.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp");*//*
                shareintent.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(shareintent,"Share Via"));
                startActivityForResult(shareintent,reqcode);
            }
        });*/



    }// onCreate Closer




    private void JSONParser() {
        // Parsing Start
        RequestQueue queue = Volley.newRequestQueue(SharingOutlet.this);
        Url = "https://thebhakti.com/PbChat/API/what_is_IMEI.php?IMEI="+IMEI;

        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SharingOutlet.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);

        // parsing stop

    }

    @SuppressLint("SetTextI18n")
    private void parseData(String response) {

        try {
            // Create JSOn Object

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                amount=jsonObject.getString("amount");
                share_times=jsonObject.getString("share_times");
                reg_date=jsonObject.getString("rdate");

                walletCash.setText("Wallet Balance : "+amount);
                if(share_times.equals("0"))
                {
                    prgressImg.setImageResource(R.drawable.bar_0);
                    // send user to chat on zero sharing without share anymore
                    Intent intent2=new Intent(SharingOutlet.this,PhpRegistration.class);
                    intent2.putExtra("user", user);
                    intent2.putExtra("Mobile", Mobile);
                    intent2.putExtra("IMEI", IMEI);
                    intent2.putExtra("type", type);
                    intent2.putExtra("detect_date", detect_date);
                    intent2.putExtra("reg_date", reg_date);
                    intent2.putExtra("amount", amount);
                    startActivity(intent2);
                    finish();

                }
              /*  else if(share_times.equals("1"))
                {
                    prgressImg.setImageResource(R.drawable.bar_1);

                }
                else if(share_times.equals("2"))
                {
                    prgressImg.setImageResource(R.drawable.bar_2);

                }
                else if(share_times.equals("3"))
                {
                    prgressImg.setImageResource(R.drawable.bar_3);

                }
                else if(share_times.equals("4"))
                {
                    prgressImg.setImageResource(R.drawable.bar_4);

                }*/
                else if(share_times.equals("1"))
                {
                    prgressImg.setImageResource(R.drawable.bar_4);
                    Intent intent2=new Intent(SharingOutlet.this,PhpRegistration.class);
                    intent2.putExtra("user", user);
                    intent2.putExtra("Mobile", Mobile);
                    intent2.putExtra("IMEI", IMEI);
                    intent2.putExtra("type", type);
                    intent2.putExtra("detect_date", detect_date);
                    intent2.putExtra("reg_date", reg_date);
                    intent2.putExtra("amount", amount);
                    startActivity(intent2);
                    finish();
                }


            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
if(requestCode==reqcode && resultCode==RESULT_OK)
{
//   Increment share points and add cash on wallet
/* incrementCash();*/


}

        super.onActivityResult(requestCode, resultCode, data);
    }

 /*   private void incrementCash() {
        // Parsing Start
        RequestQueue queue = Volley.newRequestQueue(SharingOutlet.this);
        Url = "https://thebhakti.com/PbChat/API/incrementCash.php?cash=26&IMEI="+IMEI;

        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData1(response);
                JSONParser();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SharingOutlet.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
        // parsing stop


    }*/
/*
    private void parseData1(String response) {

        try {
            // Create JSOn Object

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(SharingOutlet.this, DisplayAct.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }




    }

