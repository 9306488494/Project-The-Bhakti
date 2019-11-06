package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

/**
 * Created by Yeshveer on 10/26/2018.
 */

public class TrackUsers extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_user);

        final EditText userMob = (EditText) findViewById(R.id.userMob);
        Button transHistory = (Button) findViewById(R.id.transHistory);
        Button trackAmount = (Button) findViewById(R.id.trackAmount);
        final TextView txtShow = (TextView) findViewById(R.id.txtShow);

        // Click Event here to show the Transaction history
        trackAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if(userMob.getText().toString().equals(""))
{
    Toast.makeText(TrackUsers.this, "Enter Mobile No", Toast.LENGTH_SHORT).show();
}
else if(userMob.getText().toString().length()<=9){
    Toast.makeText(TrackUsers.this, "Enter 10 digit Mobile No", Toast.LENGTH_SHORT).show();

}
else {
    FirebaseFirestore db1;
    CollectionReference cr1;
    Query qry;

    db1 = FirebaseFirestore.getInstance();
    cr1 = db1.collection("Login_users");
    qry = cr1.whereEqualTo("user_mobile", userMob.getText().toString());
    qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                if (Objects.equals(task.getResult(), "")) {
                    Toast.makeText(TrackUsers.this, "Record Not Found on this Mobile", Toast.LENGTH_SHORT).show();
                } else {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String AMT = doc.getString("amount");
                        String Rdate = doc.getString("user_rdate");
                        String IMEI=doc.getString("user_imei");
                        txtShow.append(" \n"+IMEI + " \n " + AMT + "\n"+ Rdate);
                    }
                }
            }
        }
    });
}
            } // click block closed here
        });


    }// onCreate Closer

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(TrackUsers.this, ControlPanel.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
