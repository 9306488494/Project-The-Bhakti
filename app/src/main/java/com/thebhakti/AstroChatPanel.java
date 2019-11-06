package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yeshveer on 9/3/2018.
 */

public class AstroChatPanel extends AppCompatActivity {
String mobile,name,type;
String docID;

    private RecyclerView MyRec;
    private EditText txtMsg;
    private ImageView msgSend;
    ArrayList<Mymsg> msglist;
    FirebaseDatabase db;
    DatabaseReference dr;
    Task<Void> dr4;
    private TextView chatUser;
    FirebaseFirestore ff,ff1;
    CollectionReference cr;
    Task<Void> cr2;
    Task<Void> cr1;
    Query qry,qrySeencheck;
    String StatusforTick,TodayDate,TodayTime;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.babachatpanel);
        MyRec = (RecyclerView) findViewById(R.id.MyRec);
        txtMsg = (EditText) findViewById(R.id.txtMsg);
        msgSend = (ImageView) findViewById(R.id.msgSend);
        chatUser = (TextView) findViewById(R.id.chatUser);

        txtMsg.requestFocus();
        // Receive user intent for chat
        Intent intentGet = getIntent();
        name = intentGet.getStringExtra("name");
        mobile = intentGet.getStringExtra("mobile");
        type = intentGet.getStringExtra("type");

        // Initilize array
        msglist=new ArrayList<>();
/* Time and Date */
        @SuppressLint("SimpleDateFormat") DateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");
        @SuppressLint("SimpleDateFormat") DateFormat time1 = new SimpleDateFormat("HH:mm a");
        Date date = new Date();
        Date time = new Date();
        TodayDate = date1.format(date);
        TodayTime = time1.format(time);


        // Set title of with username
        chatUser.setText(name);

        // Save Msg into Realtime Database
        msgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check Editbox is empty or not
                txtMsg.getMaxHeight();
                txtMsg.requestFocus();
                if(txtMsg.getText().toString().equals(""))
                {
                    Toast.makeText(AstroChatPanel.this, "Type Something", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db=FirebaseDatabase.getInstance();
                    dr=db.getReference().child(mobile);
                    Map<String,Object> myVal=new HashMap<>();
                    myVal.put("name","Astrologers");
                    myVal.put("msg",txtMsg.getText().toString());
                    myVal.put("type",type);
                    myVal.put("status","unseen");
                    myVal.put("time",TodayTime);

                    dr.push().setValue(myVal, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                            if(databaseError!=null)
                            {
                                Toast.makeText(AstroChatPanel.this, "Network Issue", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                txtMsg.setText("");
                            }
                        }
                    });

                }
            }
        });

        // Fetch chat Message from database

        // Retrive Data from Database
        db=FirebaseDatabase.getInstance();
        dr=db.getReference().child(mobile);
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                if(Objects.equals(dataSnapshot.child("type").getValue(), "chatter")) {
                    // update unseen to seen in realtime database
                    if(Objects.equals(dataSnapshot.child("status").getValue(), "unseen"))
                    {
                        Map<String,Object> add=new HashMap<>();
                        add.put("status","seen");
                        dr4=db.getReference().child(mobile).child(dataSnapshot.getKey()).updateChildren(add);
                    }

                    StatusforTick= (String) dataSnapshot.child("status").getValue();
                    String fetch_msg= "";
                    String time=(String) dataSnapshot.child("time").getValue();
                    String babamsg=(String) dataSnapshot.child("msg").getValue();
                    Mymsg mymsg=new Mymsg(fetch_msg,babamsg,StatusforTick,time);

                    msglist.add(mymsg);
                    RecyclerAdapterMymsg2 recyclerAdaptermymsg2=new RecyclerAdapterMymsg2(AstroChatPanel.this,msglist);
                    MyRec.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AstroChatPanel.this);
                    linearLayoutManager.setStackFromEnd(true);
                    MyRec.setLayoutManager(linearLayoutManager);
                    MyRec.setAdapter(recyclerAdaptermymsg2);
                    MyRec.invalidate();
                    // check and update unseen to seen msg

                }
                else
                {


                    StatusforTick= (String) dataSnapshot.child("status").getValue();
                    String babamsg="";
                    String fetch_msg=(String) dataSnapshot.child("msg").getValue();
                    String time=(String) dataSnapshot.child("time").getValue();
                    Mymsg mymsg=new Mymsg(fetch_msg,babamsg,StatusforTick,time);
                    msglist.add(mymsg);
                    RecyclerAdapterMymsg2 recyclerAdaptermymsg2=new RecyclerAdapterMymsg2(AstroChatPanel.this,msglist);
                    MyRec.setHasFixedSize(true);
                   /* MyRec.setLayoutManager(new LinearLayoutManager(AstroChatPanel.this));*/
                   LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AstroChatPanel.this);
                   linearLayoutManager.setStackFromEnd(true);
                    MyRec.setLayoutManager(linearLayoutManager);
                    MyRec.setAdapter(recyclerAdaptermymsg2);


                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (msglist.size() > 0) {
                    msglist.clear();

                    db = FirebaseDatabase.getInstance();
                    dr = db.getReference().child(mobile);
                    dr.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                            if (Objects.equals(dataSnapshot.child("type").getValue(), "chatter")) {
                                // update unseen to seen in realtime database
                                if (Objects.equals(dataSnapshot.child("status").getValue(), "unseen")) {
                                    Map<String, Object> add = new HashMap<>();
                                    add.put("status", "seen");
                                    dr4 = db.getReference().child(mobile).child(dataSnapshot.getKey()).updateChildren(add);
                                }

                                StatusforTick = (String) dataSnapshot.child("status").getValue();
                                String fetch_msg = "";
                                String time=(String) dataSnapshot.child("time").getValue();
                                String babamsg = (String) dataSnapshot.child("msg").getValue();
                                Mymsg mymsg = new Mymsg(fetch_msg, babamsg, StatusforTick,time);
                                msglist.add(mymsg);
                                RecyclerAdapterMymsg2 recyclerAdaptermymsg2 = new RecyclerAdapterMymsg2(AstroChatPanel.this, msglist);
                                MyRec.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AstroChatPanel.this);
                                linearLayoutManager.setStackFromEnd(true);
                                MyRec.setLayoutManager(linearLayoutManager);
                                MyRec.setAdapter(recyclerAdaptermymsg2);
                                MyRec.invalidate();
                                // check and update unseen to seen msg

                            } else {


                                StatusforTick = (String) dataSnapshot.child("status").getValue();
                                String babamsg = "";
                                String fetch_msg = (String) dataSnapshot.child("msg").getValue();
                                String time=(String) dataSnapshot.child("time").getValue();
                                Mymsg mymsg = new Mymsg(fetch_msg, babamsg, StatusforTick,time);
                                msglist.add(mymsg);
                                RecyclerAdapterMymsg2 recyclerAdaptermymsg2 = new RecyclerAdapterMymsg2(AstroChatPanel.this, msglist);
                                MyRec.setHasFixedSize(true);
                   /* MyRec.setLayoutManager(new LinearLayoutManager(AstroChatPanel.this));*/
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AstroChatPanel.this);
                                linearLayoutManager.setStackFromEnd(true);
                                MyRec.setLayoutManager(linearLayoutManager);
                                MyRec.setAdapter(recyclerAdaptermymsg2);


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


// Update seen on user
        ff= FirebaseFirestore.getInstance();
        cr=ff.collection("Login_users");
        qry=cr.whereEqualTo("user_mobile",mobile);
        qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        docID=doc.getId();

                        // Add total unseen count in firebase Firestore
                        ff1=FirebaseFirestore.getInstance();
                        cr2=ff1.collection("Login_users").document(docID).update("total_unseen","0").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                        cr1=ff.collection("Login_users").document(docID).update("user_status","seen").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                    }
                }
            }
        });













    }// onCreate closer


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(AstroChatPanel.this, ChatusersPanel.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
