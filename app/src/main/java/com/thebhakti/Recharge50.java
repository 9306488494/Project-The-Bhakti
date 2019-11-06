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
import android.telephony.TelephonyManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yeshveer on 9/29/2018.
 */

public class Recharge50 extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String IMEI,TodayDate,TodayTime;
    Animation blink;
    private TextView loadTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_common);

        loadTxt = (TextView) findViewById(R.id.loadTxt);
        blink= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_my);
        loadTxt.startAnimation(blink);
        generateCheckSum50();

        {
            myIMEI();
        }

  /* Time and Date */
        @SuppressLint("SimpleDateFormat") DateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");
        @SuppressLint("SimpleDateFormat") DateFormat time1 = new SimpleDateFormat("HH:mm a");
        Date date = new Date();
        Date time = new Date();
        TodayDate = date1.format(date);
        TodayTime = time1.format(time);


    }// closed onCreate Method

    @SuppressLint("HardwareIds")
    private void myIMEI() {
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

    private void generateCheckSum50() {
        //getting the tax amount first.
        String txnAmount ="50";

        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(@NonNull Call<Checksum> call, @NonNull Response<Checksum> response) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment1(response.body().getChecksumHash(), paytm);
            }

            @Override
            public void onFailure(@NonNull Call<Checksum> call, Throwable t) {

            }
        });
    }

    private void initializePaytmPayment1(String checksumHash, Paytm paytm) {
        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());


        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true,this);

    }

    /*Call back for btn 50*/
    @Override
    public void onTransactionResponse(Bundle inResponse) {
        updateinLoginUsers();
        updateinDetectAmount();
    }

    private void updateinLoginUsers() {
        FirebaseFirestore db1;
        CollectionReference cr1;
        Query qry1;
/* First findout the document id and then update data*/
        db1=FirebaseFirestore.getInstance();
        cr1=db1.collection("Login_users");
        qry1=cr1.whereEqualTo("user_imei",IMEI);
        qry1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        String docID=doc.getId();
                        String newAmount=String.valueOf(Integer.parseInt(doc.getString("amount"))+25);
                /*Now we will update the amount in wallet after successful transaction via paytm*/
                        FirebaseFirestore db2;
                        CollectionReference cr2;
                        Task<Void> qry2;

                        db2=FirebaseFirestore.getInstance();
                        cr2=db2.collection("Login_users");
                        qry2=cr2.document(docID).update("amount",newAmount);
                        qry2.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Recharge50.this, "प्रिय प्रभु भक्ति उपभोगता , आपके वॉलेट में 25 रु आ गए है |", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void updateinDetectAmount() {
        FirebaseFirestore db3;
        CollectionReference cr3;
        Query qry3;

        db3=FirebaseFirestore.getInstance();
        cr3=db3.collection("Login_users");
        qry3=cr3.whereEqualTo("user_imei",IMEI);
        qry3.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().equals(""))
                    {
                        Toast.makeText(Recharge50.this, "Unable to Detect your Details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        for(QueryDocumentSnapshot doc:task.getResult())
                        {
                            String newCash=doc.getString("amount");
                            FirebaseFirestore db4;
                            CollectionReference cr4;


                            db4=FirebaseFirestore.getInstance();
                            cr4=db4.collection("detect_amount");
                            Map<String,Object> add=new HashMap<>();
                            add.put("via", "Paytm");
                            add.put("detect_amount", "0");
                            add.put("detect_date", TodayDate);
                            add.put("left_amount", newCash);
                            cr4.add(add).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                }
                            });
                        }
                    }
                }
            }
        });
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network Not Available", Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(Recharge50.this,MyWallet2.class);
        startActivity(intent1);
        finish();
    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(Recharge50.this,MyWallet2.class);
        startActivity(intent1);
        finish();
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Toast.makeText(this, "Error Occur in Payment Transaction", Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(Recharge50.this,MyWallet2.class);
        startActivity(intent1);
        finish();
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        Toast.makeText(this, "Error Loading webpage", Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(Recharge50.this,MyWallet2.class);
        startActivity(intent1);
        finish();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Intent intent1=new Intent(Recharge50.this,MyWallet2.class);
        startActivity(intent1);
        finish();
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(Recharge50.this,MyWallet2.class);
        startActivity(intent1);
        finish();
    }
    /*End call back for btn 50*/
}
