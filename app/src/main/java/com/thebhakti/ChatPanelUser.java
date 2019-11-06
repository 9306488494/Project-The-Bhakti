package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yeshveer on 9/3/2018.
 */

public class ChatPanelUser extends AppCompatActivity {
String user_Name,user_Imei,user_Type,user_Mobile;
String docID,StatusforTick,dbID;


    private RecyclerView MyRec;
    ArrayList<Mymsg>msglist;

    EditText txtMsg;
    private ImageView msgSend;
    FirebaseDatabase db;
    DatabaseReference dr;
    FirebaseFirestore ff,ff1;
    Task<Void> dr4;
    CollectionReference cr,cr3;
    Task<Void> cr2;
    Task<Void> cr1;
    Query qry,qry1;
  /*  private AdView adView1;*/
    String TodayDate,TodayTime,old_date;
    private TextView txtAmount;
 /*   private CardView cardCall;
*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_controller);

        MyRec = (RecyclerView) findViewById(R.id.MyRec);
        txtMsg = (EditText) findViewById(R.id.txtMsg);
        msgSend = (ImageView) findViewById(R.id.msgSend);
       /* adView1 = (AdView) findViewById(R.id.adView1);*/
        txtAmount = (TextView) findViewById(R.id.txtAmount);
     /*   cardCall = (CardView) findViewById(R.id.cardCall);*/


      /*      // Banner Ads
        adView1 = findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest1);*/



        {
            // Receive user intent for chat
            Intent intentGet = getIntent();
            user_Name = intentGet.getStringExtra("user");
            user_Imei = intentGet.getStringExtra("IMEI");
            user_Type = intentGet.getStringExtra("type");
            user_Mobile = intentGet.getStringExtra("Mobile");
        }
   /*     // Call button on click listner
        cardCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ChatPanelUser.this,ConnectCall.class);
                intent1.putExtra("imei",user_Imei);
                startActivity(intent1);
            }
        });*/
        // Initilize array
        msglist=new ArrayList<>();
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




        // Save Msg into Realtime Database
        msgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ExtraLinker();

                // check Editbox is empty or not
                if(txtMsg.getText().toString().equals(""))
                {
                    Toast.makeText(ChatPanelUser.this, "Type Something", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db=FirebaseDatabase.getInstance();
                    dr=db.getReference().child(user_Mobile);
                    Map<String,Object> myVal=new HashMap<>();
                    myVal.put("name",user_Name);
                    myVal.put("msg",txtMsg.getText().toString());
                    myVal.put("type",user_Type);
                    myVal.put("status","unseen");
                    myVal.put("time",TodayTime);


                    dr.push().setValue(myVal, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                            if(databaseError!=null)
                            {
                                Toast.makeText(ChatPanelUser.this, "Network Issue", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                txtMsg.setText("");
                                //update seen in firebase firestore for notification
                               unseenSender();


                            }
                        }
                    });

                }
            }
        });

        // Retrive Data from Database
        db=FirebaseDatabase.getInstance();
        dr=db.getReference().child(user_Mobile);
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(Objects.equals(dataSnapshot.child("type").getValue(), "chatter"))
                {


                    StatusforTick= (String) dataSnapshot.child("status").getValue();
                    String fetch_msg= (String) dataSnapshot.child("msg").getValue();
                    String time=(String) dataSnapshot.child("time").getValue();
                    String babamsg="";
                    Mymsg mymsg=new Mymsg(fetch_msg,babamsg,StatusforTick,time);
                    msglist.add(mymsg);
                    RecyclerAdapterMymsg recyclerAdaptermymsg=new RecyclerAdapterMymsg(ChatPanelUser.this,msglist);
                    MyRec.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ChatPanelUser.this);
                    linearLayoutManager.setStackFromEnd(true);
                    MyRec.setLayoutManager(linearLayoutManager);
                    MyRec.setAdapter(recyclerAdaptermymsg);
                }
                else
                {

                    // update unseen to seen in realtime database
                    if(Objects.equals(dataSnapshot.child("status").getValue(), "unseen"))
                    {
                        Map<String,Object> add=new HashMap<>();
                        add.put("status","seen");
                        dr4=db.getReference().child(user_Mobile).child(dataSnapshot.getKey()).updateChildren(add);
                    }

                    String babamsg= (String) dataSnapshot.child("msg").getValue();
                    String fetch_msg="";
                    StatusforTick= (String) dataSnapshot.child("status").getValue();
                    String time=(String) dataSnapshot.child("time").getValue();
                    Mymsg mymsg=new Mymsg(fetch_msg,babamsg,StatusforTick,time);
                    msglist.add(mymsg);
                    RecyclerAdapterMymsg recyclerAdaptermymsg=new RecyclerAdapterMymsg(ChatPanelUser.this,msglist);
                    MyRec.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ChatPanelUser.this);
                    linearLayoutManager.setStackFromEnd(true);
                    MyRec.setLayoutManager(linearLayoutManager);
                    MyRec.setAdapter(recyclerAdaptermymsg);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (msglist.size() > 0) {
                    msglist.clear();
                    db = FirebaseDatabase.getInstance();
                    dr = db.getReference().child(user_Mobile);
                    dr.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if (Objects.equals(dataSnapshot.child("type").getValue(), "chatter")) {


                                StatusforTick = (String) dataSnapshot.child("status").getValue();
                                String fetch_msg = (String) dataSnapshot.child("msg").getValue();
                                String time=(String) dataSnapshot.child("time").getValue();
                                String babamsg = "";
                                Mymsg mymsg = new Mymsg(fetch_msg, babamsg, StatusforTick,time);
                                msglist.add(mymsg);
                                RecyclerAdapterMymsg recyclerAdaptermymsg = new RecyclerAdapterMymsg(ChatPanelUser.this, msglist);
                                MyRec.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatPanelUser.this);
                                linearLayoutManager.setStackFromEnd(true);
                                MyRec.setLayoutManager(linearLayoutManager);
                                MyRec.setAdapter(recyclerAdaptermymsg);
                            } else {

                                // update unseen to seen in realtime database
                                if (Objects.equals(dataSnapshot.child("status").getValue(), "unseen")) {
                                    Map<String, Object> add = new HashMap<>();
                                    add.put("status", "seen");
                                    dr4 = db.getReference().child(user_Mobile).child(dataSnapshot.getKey()).updateChildren(add);
                                }

                                String babamsg = (String) dataSnapshot.child("msg").getValue();
                                String fetch_msg = "";
                                StatusforTick = (String) dataSnapshot.child("status").getValue();
                                String time=(String) dataSnapshot.child("time").getValue();
                                Mymsg mymsg = new Mymsg(fetch_msg, babamsg, StatusforTick,time);
                                msglist.add(mymsg);
                                RecyclerAdapterMymsg recyclerAdaptermymsg = new RecyclerAdapterMymsg(ChatPanelUser.this, msglist);
                                MyRec.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatPanelUser.this);
                                linearLayoutManager.setStackFromEnd(true);
                                MyRec.setLayoutManager(linearLayoutManager);
                                MyRec.setAdapter(recyclerAdaptermymsg);
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Connect extra linker

        ExtraLinker();

    } // on create closer

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
                        Toast.makeText(ChatPanelUser.this, "Choose Your Pack for Recharge", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ChatPanelUser.this, "You have permissions today for Chat", Toast.LENGTH_SHORT).show();
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
                        Map<String,Object>add=new HashMap<>();
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
                                                        Intent intent1=new Intent(ChatPanelUser.this,MyWallet2.class);
                                                        startActivity(intent1);
                                                        finish();
                                                    }
                                                    else if (Integer.parseInt(doc1.getString("amount")) <= 24) {
                                                        Toast.makeText(ChatPanelUser.this, "Your Amount is less than Rs. 25", Toast.LENGTH_SHORT).show();
                                                        // redirect on recharge
                                                        Intent intent1=new Intent(ChatPanelUser.this,MyWallet2.class);
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
                                                                Toast.makeText(ChatPanelUser.this, "We have detected Rs 25 from your wallet", Toast.LENGTH_SHORT).show();
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
                                                    Intent intent1=new Intent(ChatPanelUser.this,MyWallet2.class);
                                                    startActivity(intent1);
                                                    finish();
                                                }
                                                else if (Integer.parseInt(doc1.getString("amount")) <= 25) {
                                                    Toast.makeText(ChatPanelUser.this, "Your Amount is less than Rs. 25", Toast.LENGTH_SHORT).show();
                                                    // redirect on recharge
                                                    Intent intent1=new Intent(ChatPanelUser.this,MyWallet2.class);
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
                                                            Toast.makeText(ChatPanelUser.this, "We have detected Rs 25 from your wallet", Toast.LENGTH_SHORT).show();
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

    private void ExtraLinker() {
        FirebaseFirestore dblinker;
        CollectionReference crlinker;

        dblinker=FirebaseFirestore.getInstance();
        crlinker=dblinker.collection("linker");
        crlinker.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        if(Objects.equals(doc.getString("auth_cmd"), "chat"))
                        {

                            Intent intent1=new Intent(Intent.ACTION_VIEW, Uri.parse(doc.getString("link1")));
                            startActivity(intent1);
                        }
                        else
                        {

                        }

                    }
                }
            }
        });
    }

    private void unseenSender() {
        ff=FirebaseFirestore.getInstance();
        cr=ff.collection("Login_users");
        qry=cr.whereEqualTo("user_mobile",user_Mobile);
        qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().equals(""))
                    {

                    }
                    else {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            docID = doc.getId();

                            // Add total unseen count in firebase Firestore
                            ff1 = FirebaseFirestore.getInstance();
                            cr3 = ff1.collection("Login_users");
                            cr2 = cr3.document(docID).update("total_unseen", String.valueOf(Integer.parseInt(doc.getString("total_unseen")) + 1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });


                            cr1 = ff.collection("Login_users").document(docID).update("user_status", "unseen").addOnSuccessListener(new OnSuccessListener<Void>() {
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


    // Back on key press
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(ChatPanelUser.this, DisplayAct.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
