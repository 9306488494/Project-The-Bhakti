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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yeshveer on 9/5/2018.
 */


public class ControlPanel extends AppCompatActivity {
    private Button btnError;
    FirebaseFirestore db,db1;
    CollectionReference cr;
    private TextView linkerStatus;
    private Button btnChat;
    private Button btnNull;
    private Button Trackusers;




    String Value,DocID;







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_panel);
        linkerStatus = (TextView) findViewById(R.id.linkerStatus);
        btnChat = (Button) findViewById(R.id.btnChat);
        btnNull = (Button) findViewById(R.id.btnNull);
        Trackusers = (Button) findViewById(R.id.Trackusers);


        {
            linkerSearch();
        }

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Value.equals(""))
                {
                    FirebaseFirestore db1;
                    CollectionReference cr1;
                    Task<Void> qry1;
                    db1 = FirebaseFirestore.getInstance();
                    cr1 = db1.collection("linker");
                    qry1 = cr1.document(DocID).update("auth_cmd","chat").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ControlPanel.this, "Update to chat", Toast.LENGTH_SHORT).show();
                            linkerSearch();
                        }
                    });
                }
                else if(Value.equals("chat")) {
                    FirebaseFirestore db1;
                    CollectionReference cr1;
                    Task<Void> qry1;
                    db1 = FirebaseFirestore.getInstance();
                    cr1 = db1.collection("linker");
                    qry1 = cr1.document(DocID).update("auth_cmd","").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ControlPanel.this, "Update to Blank", Toast.LENGTH_SHORT).show();
                            linkerSearch();
                        }
                    });
                }
            }
        });



    }// onCreate closer

    private void linkerSearch() {
        db=FirebaseFirestore.getInstance();
        cr=db.collection("linker");
      cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
             if(task.isSuccessful())
             {
                 for(QueryDocumentSnapshot doc:task.getResult())
                 {
                    linkerStatus.setText(doc.getString("auth_cmd"));
                    Value=doc.getString("auth_cmd");
                     DocID=doc.getId();
                 }
             }
          }
      });

      // open intent for track users
        Trackusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ControlPanel.this,TrackUsers.class);
                startActivity(intent1);
                finish();
            }
        });


    }// onCreate closer



}

