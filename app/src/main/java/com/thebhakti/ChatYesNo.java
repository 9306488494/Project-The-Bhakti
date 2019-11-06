package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.ads.AdRequest;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Yeshveer on 10/11/2018.
 */

public class ChatYesNo extends AppCompatActivity{
    private TextView balanceTxt;
    private Button rechargeBtn;
    private Button continueBtn;
    String IMEI,user,Mobile,type,Url,newIMEI,userType,name,amount,reg_date,detect_date;

FirebaseFirestore db1;
CollectionReference cr1;
Task<QuerySnapshot> qry1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yes_no);
        balanceTxt = (TextView) findViewById(R.id.balanceTxt);
        rechargeBtn = (Button) findViewById(R.id.rechargeBtn);
        continueBtn = (Button) findViewById(R.id.continueBtn);

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
        RequestQueue queue = Volley.newRequestQueue(ChatYesNo.this);
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
                Toast.makeText(ChatYesNo.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
        // parsing stop






       /* // Fill the amount and check condition of low balance
        db1=FirebaseFirestore.getInstance();
        cr1=db1.collection("Login_users");
        qry1=cr1.whereEqualTo("user_imei",IMEI).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(Objects.equals(task.getResult(), ""))
                    {
                        Toast.makeText(ChatYesNo.this, "Your Record Not Found", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        for(QueryDocumentSnapshot doc:task.getResult())
                        {
                            String AMT=doc.getString("amount");
                            String reg_date=doc.getString("user_rdate");

                            // Set Balance on Text View
                            balanceTxt.setText("Rs "+AMT);
                                    *//* Time and Date *//*
                                      *//* Time and Date *//*
                            @SuppressLint("SimpleDateFormat") DateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");
                            Date date = new Date();
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.DATE, -2);

                            Date todate1 = cal.getTime();
                            Calendar cal2 = Calendar.getInstance();
                            cal2.add(Calendar.DATE, -1);
                            Date todate2 = cal2.getTime();

                            String old_date = date1.format(todate1);
                            String old_date2 = date1.format(todate2);
                            String current_date = date1.format(date);


                            // check the date of current equal to the req_date
                            if (old_date2.equals(reg_date))
                            {

                               continueBtn.setVisibility(View.VISIBLE);

                            }
                            else if(old_date.equals(reg_date))
                            {
                                continueBtn.setVisibility(View.VISIBLE);
                            }
                            else if(current_date.equals(reg_date))
                            {
                                continueBtn.setVisibility(View.VISIBLE);
                            }
                            else {

                                // if yes then check the balance if balance is below to 25 /- then call the user for recharge wallet
                                if (Integer.parseInt(AMT) <= 25)
                                {
                                    Toast.makeText(ChatYesNo.this, "Your Balance is Low Need More than Rs 25, Please Recharge", Toast.LENGTH_LONG).show();
                                    rechargeBtn.setVisibility(View.VISIBLE);

                                }
                                else
                                {
                                    continueBtn.setVisibility(View.VISIBLE);
                                }
                            }



                        }
                    }
                }
            }
        });*/

// click on continue button for chat
continueBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Extra Linker connects here

        // Parsing Start
        RequestQueue queue1 = Volley.newRequestQueue(ChatYesNo.this);
        String Url11 = "https://thebhakti.com/PbChat/API/linker.php?id=az506oth";

        StringRequest request1 = new StringRequest(Request.Method.GET, Url11, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData11(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChatYesNo.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout1 = 30000;
        RetryPolicy policy1 = new DefaultRetryPolicy(socketTimeout1, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request1.setRetryPolicy(policy1);
        queue1.add(request1);
        // parsing stop

    }
});
// click on continue button for Recharge
        rechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(ChatYesNo.this,Wallet_intro.class);
                intent2.putExtra("user", user);
                intent2.putExtra("Mobile", Mobile);
                intent2.putExtra("IMEI", IMEI);
                intent2.putExtra("type", type);
                startActivity(intent2);
            }
        });

    }// closer of onCreat method

    private void parseData11(String response) {
        try {
            // Create JSOn Object

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String auth_cmd=jsonObject.getString("auth_cmd");
                String link1=jsonObject.getString("link1");
                if(auth_cmd.equals("chat"))
                {
                    Intent intent1=new Intent(Intent.ACTION_VIEW, Uri.parse(link1));
                    startActivity(intent1);

                }
                else
                {
                    Intent intent2=new Intent(ChatYesNo.this,PhpRegistration.class);
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

    @SuppressLint("SetTextI18n")
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
                amount=jsonObject.getString("amount");
                reg_date=jsonObject.getString("rdate");
                detect_date=jsonObject.getString("detect_date");

                balanceTxt.setText("Rs "+amount);

                   /* Time and Date */
                                      /* Time and Date */
                @SuppressLint("SimpleDateFormat") DateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                Date todate1 = cal.getTime();
                Calendar cal2 = Calendar.getInstance();
                cal2.add(Calendar.DATE, -1);
                Date todate2 = cal2.getTime();
                String old_date = date1.format(todate1);
                String old_date2 = date1.format(todate2);
                String current_date = date1.format(date);


                if (old_date2.equals(reg_date))
                {

                    continueBtn.setVisibility(View.VISIBLE);


                }
                else if(old_date.equals(reg_date))
                {
                    continueBtn.setVisibility(View.VISIBLE);

                }
                else if(current_date.equals(reg_date))
                {
                    continueBtn.setVisibility(View.VISIBLE);

                }else if(detect_date.equals(current_date))
                {
                    continueBtn.setVisibility(View.VISIBLE);
                }
                else {

                    // if yes then check the balance if balance is below to 25 /- then call the user for recharge wallet
                    if (Integer.parseInt(amount) <= 25)
                    {
                        Toast.makeText(ChatYesNo.this, "Your Balance is Low Need More than Rs 25, Please Recharge", Toast.LENGTH_LONG).show();
                        rechargeBtn.setVisibility(View.VISIBLE);
                        continueBtn.setVisibility(View.GONE);

                    }
                    else
                    {
                        continueBtn.setVisibility(View.VISIBLE);

                    }
                }


            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        rechargeBtn.setVisibility(View.GONE);
        continueBtn.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(ChatYesNo.this, DisplayAct.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
