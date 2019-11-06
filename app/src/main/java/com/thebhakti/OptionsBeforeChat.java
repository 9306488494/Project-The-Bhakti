package com.thebhakti;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by Yeshveer on 10/11/2018.
 */

public class OptionsBeforeChat extends AppCompatActivity {
    private Button callBtn;
    private Button chatBtn;
    String IMEI,user,Mobile,type,Url,newIMEI,userType,name,share_times,detect_date;
    private LinearLayout shareLay;







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_before_chat);
       /* callBtn = (Button) findViewById(R.id.callBtn);*/
    /*    chatBtn = (Button) findViewById(R.id.chatBtn);*/
        /*shareLay = (LinearLayout) findViewById(R.id.shareLay);*/

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
        // Parsing Start
        RequestQueue queue = Volley.newRequestQueue(OptionsBeforeChat.this);
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
                Toast.makeText(OptionsBeforeChat.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
        // parsing stop

        // disappear share lay after complete 5 shareing
       /* {
            FirebaseFirestore db11;
            CollectionReference cr11;
            Query qry;
            db11=FirebaseFirestore.getInstance();
            cr11=db11.collection("Login_users");
            qry=cr11.whereEqualTo("user_imei",IMEI);
             qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if(task.isSuccessful())
                     {
                         if(Objects.equals(task.getResult(), ""))
                         {
                             Toast.makeText(OptionsBeforeChat.this, "App is unable to identify you, Please Register with Genuine Mobile Device, Your device don't have IMEI number", Toast.LENGTH_SHORT).show();
                         }
                         else
                         {
                             for(QueryDocumentSnapshot doc:task.getResult())
                             {
                                String shareTimes=doc.getString("share_times");
                                if(Objects.equals(shareTimes, "5"))
                                {
                                    *//*shareLay.setVisibility(View.GONE);*//*
                                    // after 5 share user went to chat
                                    Intent intent2=new Intent(OptionsBeforeChat.this,ChatYesNo.class);
                                    intent2.putExtra("user", user);
                                    intent2.putExtra("Mobile", Mobile);
                                    intent2.putExtra("IMEI", IMEI);
                                    intent2.putExtra("type", type);
                                    startActivity(intent2);
                                    finish();
                                }
                                else
                                {
                                    Intent intent2=new Intent(OptionsBeforeChat.this,SharingOutlet.class);
                                    intent2.putExtra("user", user);
                                    intent2.putExtra("Mobile", Mobile);
                                    intent2.putExtra("IMEI", IMEI);
                                    intent2.putExtra("type", type);
                                    startActivity(intent2);
                                    finish();
                                }
                             }
                         }
                     }
                     else
                     {
                         Toast.makeText(OptionsBeforeChat.this, "Unable APP to identify you, Please try again", Toast.LENGTH_SHORT).show();
                     }
                 }
             });

        }*/
// Click on call button
/*        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(OptionsBeforeChat.this,SharingOutlet.class);
                intent2.putExtra("user", user);
                intent2.putExtra("Mobile", Mobile);
                intent2.putExtra("IMEI", IMEI);
                intent2.putExtra("type", type);
                startActivity(intent2);
            }
        });*/
// Click on chat button
   /*     chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(OptionsBeforeChat.this,ChatYesNo.class);
                intent2.putExtra("user", user);
                intent2.putExtra("Mobile", Mobile);
                intent2.putExtra("IMEI", IMEI);
                intent2.putExtra("type", type);
                startActivity(intent2);
            }
        });*/



    }// onCreate closer

    private void parseData(String response) {

        try {
            // Create JSOn Object

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                newIMEI=jsonObject.getString("IMEI");
                userType=jsonObject.getString("type");
                name=jsonObject.getString("name");
                Mobile=jsonObject.getString("id");
                share_times=jsonObject.getString("share_times");
                detect_date=jsonObject.getString("detect_date");


                if(share_times.equals("5")) {

                    Intent intent2=new Intent(OptionsBeforeChat.this,ChatYesNo.class);
                    intent2.putExtra("user", name);
                    intent2.putExtra("Mobile", Mobile);
                    intent2.putExtra("IMEI", IMEI);
                    intent2.putExtra("type", userType);
                    intent2.putExtra("detect_date", detect_date);
                    startActivity(intent2);
                    finish();
                }
                else
                {
                    Intent intent2=new Intent(OptionsBeforeChat.this,SharingOutlet.class);
                    intent2.putExtra("user", name);
                    intent2.putExtra("Mobile", Mobile);
                    intent2.putExtra("IMEI", IMEI);
                    intent2.putExtra("type", userType);
                    intent2.putExtra("detect_date", detect_date);
                    startActivity(intent2);

                }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
