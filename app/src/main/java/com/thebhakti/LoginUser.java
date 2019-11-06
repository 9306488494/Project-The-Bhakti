package com.thebhakti;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yeshveer on 8/24/2018.
 */

public class LoginUser extends AppCompatActivity {
    private ImageView astro_login;


    private Button btnReg;
    private LinearLayout layout2;
    private EditText textUsername;
    private EditText textMobile;
    private EditText textMail;
    private Button btnReset;
    private Button btnSave;
    String IMEI, contacts;
    FirebaseFirestore db, db2;
    CollectionReference cr,cr2;
    FirebaseDatabase fd;
    DatabaseReference dr;
    ProgressDialog pd;
    private LinearLayout layout3;
    private EditText adminUser;
    private EditText adminMob;
    private EditText adminState;
     Button adminReset;
    Button adminSave;
    private EditText adminCode;
    private Button adminReg;
    String Dir1;
    Task<QuerySnapshot> qry;
    Query query;
    FirebaseDatabase db5;
    DatabaseReference dr5;
    String TodayDate,TodayTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginuser_lay);
        astro_login = (ImageView) findViewById(R.id.astro_login);


        btnReg = (Button) findViewById(R.id.btnReg);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        textUsername = (EditText) findViewById(R.id.textUsername);
        textMobile = (EditText) findViewById(R.id.textMobile);
        textMail = (EditText) findViewById(R.id.textMail);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnSave = (Button) findViewById(R.id.btnSave);
       /* layout3 = (LinearLayout) findViewById(R.id.layout3);*/
      /*  adminUser = (EditText) findViewById(R.id.adminUser);
        adminMob = (EditText) findViewById(R.id.adminMob);
        adminState = (EditText) findViewById(R.id.adminState);
        adminReset = (Button) findViewById(R.id.adminReset);
        adminSave = (Button) findViewById(R.id.adminSave);
        adminCode = (EditText) findViewById(R.id.adminCode);
        adminReg = (Button) findViewById(R.id.adminReg);*/


   /*     adminReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                btnReg.setVisibility(View.VISIBLE);
                adminReg.setVisibility(View.GONE);

            }
        });*/
        // New user click and make visible registration page
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout2.setVisibility(View.VISIBLE);
                /*layout3.setVisibility(View.GONE);*/

             /*   adminReg.setVisibility(View.VISIBLE);*/
                btnReg.setVisibility(View.GONE);

            }
        });

/* Time and Date */
        @SuppressLint("SimpleDateFormat") DateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");
        @SuppressLint("SimpleDateFormat") DateFormat time1 = new SimpleDateFormat("HH:mm:ss a");
        Date date = new Date();
        Date time = new Date();
        TodayDate = date1.format(date);
        TodayTime = time1.format(time);


        // Admin login
      /*  adminSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    pd.show();
                }
                if (adminUser.getText().toString().equals("")) {
                    Toast.makeText(LoginUser.this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
                    btnSave.setVisibility(View.VISIBLE);
                    adminUser.requestFocus();
                } else if (adminMob.getText().toString().length() < 10) {
                    Toast.makeText(LoginUser.this, "Please Enter 10 Digit Mobile No", Toast.LENGTH_SHORT).show();
                    btnSave.setVisibility(View.VISIBLE);
                    adminMob.requestFocus();
                } else if (adminState.getText().toString().equals("")) {
                    Toast.makeText(LoginUser.this, "Please Enter Your State", Toast.LENGTH_SHORT).show();
                    btnSave.setVisibility(View.VISIBLE);
                    adminState.requestFocus();
                } else {
                    if (adminCode.getText().toString().equals("DREAM")) {

                        // done and establish push operations
                        {
                            imeiNumber();
                        }

                        db = FirebaseFirestore.getInstance();
                        Map<String, Object> user2 = new HashMap<>();
                        user2.put("user_email", adminState.getText().toString());//email id
                        user2.put("user_imei", IMEI);// imei
                        user2.put("user_mobile", adminMob.getText().toString());// mobile no
                        user2.put("user_name", adminUser.getText().toString());// name
                        user2.put("user_type", "admin");// type
                        user2.put("user_status", "seen");// type
                        user2.put("user_rdate", TodayDate);// type
                        user2.put("share_times", "5");// sharing time set default 5 for Admin registration


// Add a new document with a generated ID
                        cr = db.collection("Login_users");
                        cr
                                .add(user2)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        String no = "+91" + textMobile.getText().toString();
                                        String msg = "Dear Astrologer, "+" : "+ textUsername.getText().toString() +" ,"+ "  Welcome to The Bhakti. "+"\n"+" You have successfully Registered";
                                        Intent intent = new Intent(getApplicationContext(), UserList.class);
                                        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                                        //Get the SmsManager instance and call the sendTextMessage method to send message
                                        SmsManager sms = SmsManager.getDefault();
                                        sms.sendTextMessage(no, null, msg, pi, null);

                                        Intent intent1 = new Intent(LoginUser.this, EvaluateMe.class);
                                        startActivity(intent1);
                                        finish();
                                        pd.dismiss();
                                    }
                                });


                    } else {
                        Toast.makeText(LoginUser.this, "You are not eligible for Admin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/

        // Admin login panel closed
        pd = new ProgressDialog(LoginUser.this);
        pd.setTitle("Registering");
        pd.setMessage("Please wait ...");
        pd.setCancelable(false);

        // Collect IMEI no
        {
            imeiNumber();
        }

//check wheater user is already registered or not
       /* db2 = FirebaseFirestore.getInstance();
        cr2 = db2.collection("Login_users");
        query=cr2.whereEqualTo("user_imei",IMEI);
          query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                               if(Objects.equals(doc.getString("user_imei"), IMEI))
                               {
                                   pd.show();

                                            if(Objects.equals(doc.getString("user_type"), "chatter")) {
                                                Intent intent2 = new Intent(LoginUser.this, ChatPanelUser.class);
                                                // also pass intent
                                                intent2.putExtra("user", doc.getString("user_name"));
                                                intent2.putExtra("Mobile", doc.getString("user_mobile"));
                                                intent2.putExtra("IMEI", IMEI);

                                                intent2.putExtra("type", doc.getString("user_type"));
                                                startActivity(intent2);
                                                pd.dismiss();
                                                finish();
                                            }
                                            else
                                            {
                                                Intent intent2 = new Intent(LoginUser.this, UserList.class);
                                                startActivity(intent2);
                                                pd.dismiss();
                                                finish();
                                            }

                                } else {
                                    btnReg.setVisibility(View.VISIBLE);
                                    adminReg.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
*/

// Register user panel
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnSave.setVisibility(View.GONE);

                {
                    pd.show();
                }
                if (textUsername.getText().toString().equals("")) {
                    Toast.makeText(LoginUser.this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
                    btnSave.setVisibility(View.VISIBLE);
                    textUsername.requestFocus();
                    pd.dismiss();
                } else if (textMobile.getText().toString().length() < 10) {
                    Toast.makeText(LoginUser.this, "Please Enter 10 Digit Mobile No", Toast.LENGTH_SHORT).show();
                    btnSave.setVisibility(View.VISIBLE);
                    textMobile.requestFocus();
                    pd.dismiss();
                } else if (textMail.getText().toString().equals("")) {
                    Toast.makeText(LoginUser.this, "Please Enter Your City", Toast.LENGTH_SHORT).show();
                    btnSave.setVisibility(View.VISIBLE);
                    textMail.requestFocus();
                    pd.dismiss();
                } else {

                    // done and establish push operations
                    {
                        imeiNumber();
                    }

                    // send data for registration
                  /*  String no = "+91" + textMobile.getText().toString();
                    String msg = "Our Dear User, " + " : " + textUsername.getText().toString() + " ," + " Welcome to The Bhakti. " + "\n" + " You have successfully Registered";
                    Intent intent = new Intent();
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    //Get the SmsManager instance and call the sendTextMessage method to send message
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(no, null, msg, pi, null);*/

                    Intent intent1 = new Intent(LoginUser.this, NewUser.class);
                    intent1.putExtra("user", textUsername.getText().toString());
                    intent1.putExtra("Mobile", textMobile.getText().toString());
                    intent1.putExtra("IMEI", IMEI);
                    intent1.putExtra("type", "agent");
                    intent1.putExtra("state", textMail.getText().toString());
                    intent1.putExtra("IMEI", IMEI);
                    intent1.putExtra("user_status", "unseen");
                    intent1.putExtra("total_unseen", "0");// Total unseen message
                    intent1.putExtra("user_rdate", TodayDate);// Total unseen message
                    intent1.putExtra("amount", "26");// Total unseen message
                    intent1.putExtra("share_times", "0");


                    startActivity(intent1);
                    pd.dismiss();
                    finish();



                   /* // start firestore
                    // This code creates null point exception during user register
                   *//* // create directory in Realtimedata base
                    fd=FirebaseDatabase.getInstance();
                    dr=fd.getReference().child(textMobile.getText().toString());
                    dr.push().setValue("hi");*//*

                    // closed directory creation
                    db = FirebaseFirestore.getInstance();
                    Map<String, Object> user2 = new HashMap<>();
                    user2.put("user_email", textMail.getText().toString());
                    user2.put("user_imei", IMEI);
                    user2.put("user_mobile", textMobile.getText().toString());
                    user2.put("user_name", textUsername.getText().toString());
                    user2.put("user_type", "chatter");
                    user2.put("user_status", "unseen");
                    user2.put("total_unseen", "0");// Total unseen message
                    user2.put("user_rdate", TodayDate);// Total unseen message
                    user2.put("amount", "00");// Total unseen message
                    user2.put("share_times", "0");// shareApps fields initilize by 0 for users


                    cr = db.collection("Login_users");
                    cr
                            .add(user2)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    // dont take null byte in database for chat

                                                db5=FirebaseDatabase.getInstance();
                                                dr5=db5.getReference().child(textMobile.getText().toString());
                                                Map<String,Object> myVal=new HashMap<>();
                                                myVal.put("name",textUsername.getText().toString());
                                                myVal.put("msg","I m new user Acharya ji");
                                                myVal.put("type","chatter");
                                                myVal.put("status","unseen");
                                                myVal.put("time",TodayTime);


                                                dr.push().setValue(myVal, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                                        if(databaseError!=null)
                                                        {
                                                            Toast.makeText(LoginUser.this, "Network Issue", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else
                                                        {

                                                            Toast.makeText(LoginUser.this, "Sucessfully registered", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });



                                }
                            });
*/
                }


            }
        });
      /*  Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new LoadMyData().execute();
            }
        },2000);*/


    }// main onCreate closer











    @SuppressLint("HardwareIds")
    private void imeiNumber() {
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




    @Override
    protected void onStart() {
        super.onStart();
        layout2.setVisibility(View.GONE);

       /* layout3.setVisibility(View.GONE);*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(LoginUser.this, DisplayAct.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}



