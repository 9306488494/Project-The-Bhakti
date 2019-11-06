package com.thebhakti;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.Internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import javax.xml.transform.Result;

/**
 * Created by Yeshveer on 9/3/2018.
 */

public class UserList extends AppCompatActivity{
    private RecyclerView chatUserRec;
    private RecyclerView chatUserRec2;

    ArrayList<ListedUsers>userslist;
    ArrayList<ListedUsers>userslist1;
    FirebaseFirestore db;
    CollectionReference cr;
    Query qry;
    String name,id,mobile,colors,state,counter1;
    FirebaseDatabase fd;
    DatabaseReference dr;
    ProgressDialog pd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist4baba_lay);
        chatUserRec = (RecyclerView) findViewById(R.id.chatUserRec);
        chatUserRec2 = (RecyclerView) findViewById(R.id.chatUserRec2);
        ImageView refresh = (ImageView) findViewById(R.id.refresh);

        // progress dialogue
        pd=new ProgressDialog(this);
        pd.setTitle("Loading Users...");
        pd.setMessage("Please wait...");
        pd.setCancelable(true);

        // Initilize Array List
        userslist=new ArrayList<>();
        userslist1=new ArrayList<>();











    }//onCreate method closed



}
