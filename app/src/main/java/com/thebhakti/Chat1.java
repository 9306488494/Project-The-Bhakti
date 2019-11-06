package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Yeshveer on 9/2/2018.
 */

public class Chat1 extends AppCompatActivity {
    private static int SIGN_IN_REQUEST_CODE=1;
    private FirebaseListAdapter<Chatmsg> adapter;
FirebaseDatabase db;
    DatabaseReference dr;
    private ListView lstMsg;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.msignout)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Chat1.this, "You have signout successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN_REQUEST_CODE)
        {
            if(requestCode==RESULT_OK)
            {
                Toast.makeText(this, "Successful Sign In, Welcome !", Toast.LENGTH_SHORT).show();
                displayChatMessage();
            }
            else
            {
                Toast.makeText(this, "Couldn't Sign In, please Try Again Later", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat1_lay);


        lstMsg = (ListView) findViewById(R.id.lstMsg);




        // Check futher the user is signing in or not otherwise prompt for further login
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else
        {
            Toast.makeText(this, "welcome"+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            // Load contents
            displayChatMessage();
        }
      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().push().setValue(new Chatmsg(textMsg.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                textMsg.setText("");
            }
        });*/

    } // onCreate closer

    private void displayChatMessage() {
        adapter=new FirebaseListAdapter<Chatmsg>(this,Chatmsg.class,R.layout.list_item, FirebaseDatabase.getInstance().getReference().child("us"))
        {
            @RequiresApi(api = Build.VERSION_CODES.N)

            @Override
            protected void populateView(View v, Chatmsg model, int position) {
                TextView msgUser,msgTxt;

                msgUser = (TextView) v.findViewById(R.id.msgUser);
                msgTxt = (TextView) v.findViewById(R.id.msgTxt);
                msgTxt.setText(model.getMsgTxt());
                msgUser.setText(model.getMsgUser());



            }
        };
        lstMsg.setAdapter(adapter);
    }


}

