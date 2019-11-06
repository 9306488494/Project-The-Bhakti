package com.thebhakti;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yeshveer on 8/23/2018.
 */

public class ChatAll extends AppCompatActivity {
    private EditText textMsg;
    private Button btnSend;
    private TextView textRec;
FirebaseFirestore db,db1;
Task<Void> cr1;
DocumentReference cr;
String Value="Lio5FfGFVVOHBD1LqTrL";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatall_lay);

        textMsg = (EditText) findViewById(R.id.textMsg);
        btnSend = (Button) findViewById(R.id.btnSend);
        textRec = (TextView) findViewById(R.id.textRec);




db=FirebaseFirestore.getInstance();
                DocumentReference contactListener = db.collection("chatme").document("msgme");
                        contactListener.addSnapshotListener(new EventListener< DocumentSnapshot >() {
                            @Override
                            public void onEvent(DocumentSnapshot documentSnapshot,
                                                FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.d("ERROR", e.getMessage());
                                    return;
                                }
                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                    Toast.makeText(ChatAll.this, "Current data:" +
                                            documentSnapshot.getString("Hello"), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
Map<String,Object> users=new HashMap<>();
users.put("Hum","Kuch");

db1=FirebaseFirestore.getInstance();
cr1=db1.collection("chatme").document("Hello2").update(users);
cr1.addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void aVoid) {

    }
});




    }// onCreate closer

    }

