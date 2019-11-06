package com.thebhakti;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.webkit.WebView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yeshveer on 11/1/2018.
 */

public class Jivochat2 extends Activity{
    String user_Name,user_Imei,user_Type,user_Mobile;
    String TodayDate,TodayTime,old_date;
    String docID,StatusforTick,dbID;
    private TextView txtAmount;



    //**************
    JivoSdk jivoSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jivo);
        txtAmount = (TextView) findViewById(R.id.txtAmount);



        {
            // Receive user intent for chat
            Intent intentGet = getIntent();
            user_Name = intentGet.getStringExtra("user");
            user_Imei = intentGet.getStringExtra("IMEI");
            user_Type = intentGet.getStringExtra("type");
            user_Mobile = intentGet.getStringExtra("Mobile");
        }


        // official detection and codes start

        /* Time and Date */
        @SuppressLint("SimpleDateFormat") DateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");
        @SuppressLint("SimpleDateFormat") DateFormat time1 = new SimpleDateFormat("HH:mm a");
        Date date = new Date();
        Date time = new Date();
        TodayDate = date1.format(date);
        TodayTime = time1.format(time);
        /*Compare dates to old registration date and detect money*/

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        Date todate1 = cal.getTime();
        old_date = date1.format(todate1);

        /*check today is amount is detected or not*/
        checkDetectionofAmount();
        // Fill top wallet price
        fillTheCurrency();
        // official partition ends

        String lang = Locale.getDefault().getLanguage().contains("ru") ? "ru": "en";

        //*********************************************************
        jivoSdk = new JivoSdk((WebView) findViewById(R.id.webview), lang);
        jivoSdk.delegate = this;
        jivoSdk.prepare();
    }

    private void fillTheCurrency() {
        FirebaseFirestore db1;
        CollectionReference cr1;
        Query qry1;

        db1=FirebaseFirestore.getInstance();
        cr1=db1.collection("Login_users");
        qry1=cr1.whereEqualTo("user_imei",user_Imei);
        qry1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(Objects.equals(task.getResult(), "")){
                        Toast.makeText(Jivochat2.this, "Choose Your Pack for Recharge", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        for(QueryDocumentSnapshot doc:task.getResult())
                        {
                            txtAmount.setText("Rs."+doc.getString("amount")+" /-");
                        }
                    }
                }
            }
        });
    }

    private void checkDetectionofAmount() {
        FirebaseFirestore db21;
        CollectionReference cr21;
        Query qry21;
        db21=FirebaseFirestore.getInstance();
        cr21=db21.collection("Login_users");
        qry21=cr21.whereEqualTo("user_imei",user_Imei);
        qry21.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        if(Objects.equals(doc.getString("user_rdate"), TodayDate))
                        {
                            Toast.makeText(Jivochat2.this, "You have permissions today for Chat", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // update the detection date in Login_users in user_rdate
                            String ID1=doc.getId();
                            FirebaseFirestore db22;
                            CollectionReference cr22;
                            Task<Void> qry22;
                            db22=FirebaseFirestore.getInstance();
                            cr22=db22.collection("Login_users");
                            qry22=cr22.document(ID1).update("user_rdate",TodayDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                            // Now run detection code

                            FirebaseFirestore detectToday;
                            CollectionReference crDetect;
                            detectToday=FirebaseFirestore.getInstance();
                            crDetect=detectToday.collection("detect_amount");
                            Query qry12=crDetect.whereEqualTo("imei",user_Imei);
                            qry12.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        if(task.getResult().isEmpty())
                                        {
                                            FirebaseFirestore db7;
                                            CollectionReference cr7;
                                            Map<String,Object> add=new HashMap<>();
                                            add.put("via","chat");
                                            add.put("detect_date","0");
                                            add.put("imei",user_Imei);
                                            add.put("detect_amount","0");
                                            add.put("left_amount","0");
                                            db7=FirebaseFirestore.getInstance();
                                            cr7=db7.collection("detect_amount");
                                            cr7.add(add).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {

                                                }
                                            });

                                        }
                                        else
                                        {
                                            for(QueryDocumentSnapshot doc:task.getResult())
                                            {
                                                if(Objects.equals(doc.getString("via"), "chat")) {
                                                    if (Objects.equals(doc.getString("detect_date"), TodayDate)) {
                                                        // already detected
                                                    } else {
                                                        // check balance in wallet if wallet is null then send to recharge otherwise detect amount
                                                        // Detect 25 ruppees for today and enable user and update new data in detect_amount table
                                                        String detect_date = doc.getString("detect_date");
                                                        String via = doc.getString("via");
                                                        String left_amount = doc.getString("left_amount");

                        /*Now detect amount via chat*/

                                                        FirebaseFirestore db3;
                                                        CollectionReference cr3;
                                                        Query qry3;

                                                        db3 = FirebaseFirestore.getInstance();
                                                        cr3 = db3.collection("Login_users");
                                                        qry3 = cr3.whereEqualTo("user_imei", user_Imei);
                                                        qry3.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (task.getResult().isEmpty()) {
                                                                        // data can't blank
                                                                    }
                                                                    for (QueryDocumentSnapshot doc1 : task.getResult()) {
                                                                        if (Objects.equals(doc1.getString("amount"), "")) {
                                                                            Intent intent1=new Intent(Jivochat2.this,MyWallet2.class);
                                                                            startActivity(intent1);
                                                                            finish();
                                                                        }
                                                                        else if (Integer.parseInt(doc1.getString("amount")) <= 24) {
                                                                            Toast.makeText(Jivochat2.this, "Your Amount is less than Rs. 25", Toast.LENGTH_SHORT).show();
                                                                            // redirect on recharge
                                                                            Intent intent1=new Intent(Jivochat2.this,MyWallet2.class);
                                                                            startActivity(intent1);
                                                                            finish();
                                                                        }
                                                                        else {
                                                                            String Id = doc1.getId();
                                                                            String amount = doc1.getString("amount");
                                        /*now convert amount to integer and update on Login_users and in detect_amount table*/
                                                                            String detect_cash = String.valueOf(Integer.parseInt(amount) - 25);
                                        /*Now update data to detect AMount*/
                                                                            FirebaseFirestore db5;
                                                                            CollectionReference cr5;
                                                                            Map<String, Object> add = new HashMap<>();
                                                                            add.put("via", "chat");
                                                                            add.put("detect_amount", "25");
                                                                            add.put("detect_date", TodayDate);
                                                                            add.put("left_amount", detect_cash);

                                                                            db5 = FirebaseFirestore.getInstance();
                                                                            cr5 = db5.collection("detect_amount");
                                                                            cr5.add(add).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                @Override
                                                                                public void onSuccess(DocumentReference documentReference) {
                                                                                    Toast.makeText(Jivochat2.this, "We have detected Rs 25 from your wallet", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                            /*Also update amount on Login_users*/

                                                                            FirebaseFirestore db6;
                                                                            CollectionReference cr6;
                                                                            Task<Void> qry6;
                                                                            db6 = FirebaseFirestore.getInstance();
                                                                            cr6 = db6.collection("Login_users");
                                                                            qry6 = cr6.document(Id).update("amount", detect_cash);
                                                                            qry6.addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

                                                                                }
                                                                            });
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                                else
                                                {
                                                    // check balance in wallet if wallet is null then send to recharge otherwise detect amount
                                                    // Detect 25 ruppees for today and enable user and update new data in detect_amount table
                                                    String detect_date = doc.getString("detect_date");
                                                    String via = doc.getString("via");
                                                    String left_amount = doc.getString("left_amount");

                        /*Now detect amount via chat*/

                                                    FirebaseFirestore db3;
                                                    CollectionReference cr3;
                                                    Query qry3;

                                                    db3 = FirebaseFirestore.getInstance();
                                                    cr3 = db3.collection("Login_users");
                                                    qry3 = cr3.whereEqualTo("user_imei", user_Imei);
                                                    qry3.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                if (task.getResult().isEmpty()) {
                                                                    // data can't blank
                                                                }
                                                                for (QueryDocumentSnapshot doc1 : task.getResult()) {
                                                                    if (Objects.equals(doc1.getString("amount"), "")) {
                                                                        Intent intent1=new Intent(Jivochat2.this,MyWallet2.class);
                                                                        startActivity(intent1);
                                                                        finish();
                                                                    }
                                                                    else if (Integer.parseInt(doc1.getString("amount")) <= 25) {
                                                                        Toast.makeText(Jivochat2.this, "Your Amount is less than Rs. 25", Toast.LENGTH_SHORT).show();
                                                                        // redirect on recharge
                                                                        Intent intent1=new Intent(Jivochat2.this,MyWallet2.class);
                                                                        startActivity(intent1);
                                                                        finish();
                                                                    }
                                                                    else {
                                                                        String Id = doc1.getId();
                                                                        String amount = doc1.getString("amount");
                                        /*now convert amount to integer and update on Login_users and in detect_amount table*/
                                                                        String detect_cash = String.valueOf(Integer.parseInt(amount) - 25);
                                        /*Now update data to detect AMount*/
                                                                        FirebaseFirestore db5;
                                                                        CollectionReference cr5;
                                                                        Map<String, Object> add = new HashMap<>();
                                                                        add.put("via", "chat");
                                                                        add.put("detect_amount", "25");
                                                                        add.put("detect_date", TodayDate);
                                                                        add.put("left_amount", detect_cash);

                                                                        db5 = FirebaseFirestore.getInstance();
                                                                        cr5 = db5.collection("detect_amount");
                                                                        cr5.add(add).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                            @Override
                                                                            public void onSuccess(DocumentReference documentReference) {
                                                                                Toast.makeText(Jivochat2.this, "We have detected Rs 25 from your wallet", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });

                                            /*Also update amount on Login_users*/

                                                                        FirebaseFirestore db6;
                                                                        CollectionReference cr6;
                                                                        Task<Void> qry6;
                                                                        db6 = FirebaseFirestore.getInstance();
                                                                        cr6 = db6.collection("Login_users");
                                                                        qry6 = cr6.document(Id).update("amount", detect_cash);
                                                                        qry6.addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {

                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            }// main for loop
                                        }
                                    }
                                }
                            });
                        }// main else closer
                    }
                }
            }
        });
    }

    //*********************************************

    public void onEvent(String name, String data) {
        if(name.equals("url.click")){
            if(data.length() > 2){
                String url = data.substring(1, data.length() - 1);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        }
    }



}