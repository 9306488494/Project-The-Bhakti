package com.thebhakti;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yeshveer on 9/2/2018.
 */

public class RealtimeMain extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference dr;
    String Name,rollno;
    private TextView txtMsg;
    private TextView txtMsg2;








    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arelatime_main);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        txtMsg2 = (TextView) findViewById(R.id.txtMsg2);

        // STore data- setvalue only update value in single node and if we use push then it create new node
        db=FirebaseDatabase.getInstance();
        dr=db.getReference().child("us");

        Map<String,Object> myVal=new HashMap<>();
        myVal.put("name","akshay");
        myVal.put("rollno","12");
        dr.push().setValue(myVal, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
if(databaseError!=null)
{

}
else
{
    Toast.makeText(RealtimeMain.this, "Data Saved successfully", Toast.LENGTH_SHORT).show();
}
            }
        });




        String key=dr.push().getKey();
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
// Retrive data from realtime database
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                txtMsg.append((CharSequence) dataSnapshot.child("name").getValue());
                txtMsg2.append((CharSequence) dataSnapshot.child("rollno").getValue());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                txtMsg.append((CharSequence) dataSnapshot.child("name").getValue());
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


    }// onCreate closer
}
