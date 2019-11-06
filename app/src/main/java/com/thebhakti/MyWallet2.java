package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yeshveer on 9/28/2018.
 */

public class MyWallet2 extends AppCompatActivity{
    private TextView txtAmount;
    String IMEI;

    private Button btn25;
    private Button btn50;
    private Button btn100;
    private Button btn250;
    private Button btn500;
    private TextView userName;
    private TextView userMob;
    private TextView userAmount;
    private Button btn1000;
    private EditText manualAMT;
    private Button btnManual;








    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet2);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
       /* btn25 = (Button) findViewById(R.id.btn25);*/
        btn50 = (Button) findViewById(R.id.btn50);
        btn100 = (Button) findViewById(R.id.btn100);
        btn250 = (Button) findViewById(R.id.btn250);
        btn500 = (Button) findViewById(R.id.btn500);
        userName = (TextView) findViewById(R.id.userName);
        userMob = (TextView) findViewById(R.id.userMob);
        userAmount = (TextView) findViewById(R.id.userAmount);
        btn1000 = (Button) findViewById(R.id.btn1000);
        /*manualAMT = (EditText) findViewById(R.id.manualAMT);*/
     /*   btnManual = (Button) findViewById(R.id.btnManual);*/

    /*    // manual Payments click events
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manualAMT.getText().toString().equals(""))
                {
                    Toast.makeText(MyWallet2.this, "Enter Manual amount for recharge", Toast.LENGTH_SHORT).show();
                    manualAMT.requestFocus();

                }
                else if(manualAMT.getText().toString().length()<2)
                {
                    Toast.makeText(MyWallet2.this, "Enter Valid amount", Toast.LENGTH_SHORT).show();
                    manualAMT.requestFocus();
                }
                else
                {
                    Intent intent1=new Intent(MyWallet2.this,PayuMoney.class);
                    intent1.putExtra("getNewamount",manualAMT.getText().toString());
                    intent1.putExtra("imei",IMEI);
                    startActivity(intent1);
                    finish();
                }
            }
        });*/
        // call IMEI
        callImei();
        // Fill currency in text field
        fillTheCurrency();
        /* Recharge packs button click listner starts*/
     /*   btn25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //calling the method generateCheckSum() which will generate the paytm checksum for payment
                Intent intent1=new Intent(MyWallet2.this,PayuMoney.class);
                intent1.putExtra("getNewamount","30");
                intent1.putExtra("imei",IMEI);
                startActivity(intent1);
                finish();
            }
        });*/
        btn1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling the method generateCheckSum() which will generate the paytm checksum for payment
                Intent intent1=new Intent(MyWallet2.this,PayuMoney.class);
                intent1.putExtra("getNewamount","1000");
                intent1.putExtra("imei",IMEI);
                startActivity(intent1);
                finish();
            }
        });
        btn50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//calling the method generateCheckSum() which will generate the paytm checksum for payment
                Intent intent1=new Intent(MyWallet2.this,PayuMoney.class);
                intent1.putExtra("getNewamount","51");
                startActivity(intent1);
                finish();
            }
        });
        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//calling the method generateCheckSum() which will generate the paytm checksum for payment
                Intent intent1=new Intent(MyWallet2.this,PayuMoney.class);
                intent1.putExtra("getNewamount","100");
                startActivity(intent1);
                finish();
            }
        });
        btn250.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//calling the method generateCheckSum() which will generate the paytm checksum for payment
                Intent intent1=new Intent(MyWallet2.this,PayuMoney.class);
                intent1.putExtra("getNewamount","250");
                startActivity(intent1);
                finish();
            }
        });
        btn500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//calling the method generateCheckSum() which will generate the paytm checksum for payment
                Intent intent1=new Intent(MyWallet2.this,PayuMoney.class);
                intent1.putExtra("getNewamount","500");
                startActivity(intent1);
                finish();
            }
        });


    }// onCreate method Closed

    @SuppressLint("HardwareIds")
    private void callImei() {
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
            assert tm != null;
            IMEI = tm.getDeviceId();

        }
    }

    private void fillTheCurrency() {
        // Parsing Start
        RequestQueue queue = Volley.newRequestQueue(MyWallet2.this);
        String Url = "https://thebhakti.com/PbChat/API/what_is_IMEI.php?IMEI="+IMEI;

        StringRequest request = new StringRequest(Request.Method.GET, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyWallet2.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);

        // parsing stop
    }

    private void parseData(String response) {
        try {
            // Create JSOn Object

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String AMT=jsonObject.getString("amount");
                String NAME=jsonObject.getString("name");
                String MOB=jsonObject.getString("id");
                userAmount.setText("Rs "+AMT);
                userName.setText(NAME);
                userMob.setText(MOB);


            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(MyWallet2.this, DisplayAct.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }

}