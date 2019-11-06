package com.thebhakti;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
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
 * Created by Yeshveer on 9/5/2018.
 */

public class EvaluateMe extends AppCompatActivity {
String IMEI,Url,IMEI2,userType,name,Mobile,detect_date;
ProgressDialog pd;


FirebaseFirestore db;
CollectionReference cr;
Query qry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initilize Progress dialogue box
        pd = new ProgressDialog(this);
        pd.setTitle("Evaluating Users Profile");
        pd.setMessage("Please Wait a while....");
        pd.setCancelable(true);
        {
            IMEInum();
        }

        // Initilize progress Dialogue Box
        pd=new ProgressDialog(this);
        pd.setTitle("Evaluating User Profile");
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);



            // Parsing Start
        RequestQueue queue = Volley.newRequestQueue(EvaluateMe.this);
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
                Toast.makeText(EvaluateMe.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
        // parsing stop

    }// onCreate closer

    private void parseData(String response) {
pd.show();
        try {
            // Create JSOn Object
            if(response.contains("9416917046"))
            {
                pd.dismiss();
                Intent intent2 = new Intent(EvaluateMe.this, LoginUser.class);
                startActivity(intent2);

                finish();
            }
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                IMEI2=jsonObject.getString("IMEI");
                userType=jsonObject.getString("type");
                name=jsonObject.getString("name");
                Mobile=jsonObject.getString("id");
                detect_date=jsonObject.getString("detect_date");

              if(IMEI2.equals(IMEI))
                {
                    if (userType.equals("agent")) {
                        Intent intent2 = new Intent(EvaluateMe.this, OptionsBeforeChat.class);
                        // also pass intent
                        intent2.putExtra("user", name);
                        intent2.putExtra("Mobile", Mobile);
                        intent2.putExtra("IMEI", IMEI);
                        intent2.putExtra("type", userType);
                        intent2.putExtra("detect_date", detect_date);
                        startActivity(intent2);
                        pd.dismiss();
                        finish();
                    } else {
                        Intent intent2 = new Intent(EvaluateMe.this, ChatusersPanel.class);
                        startActivity(intent2);
                        pd.dismiss();
                        finish();
                    }
                }


            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("HardwareIds")
    private void IMEInum() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            assert tm != null;
            IMEI = tm.getImei();

        } else {
            if (tm != null) {
                IMEI = tm.getDeviceId();
            }

        }
    }

}
