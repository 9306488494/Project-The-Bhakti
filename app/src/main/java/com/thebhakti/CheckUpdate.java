package com.thebhakti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by Yeshveer on 8/13/2018.
 */

public class CheckUpdate extends AppCompatActivity {
    private ImageView upArrow;
    private ImageView playstore1;
    private ImageView banner2;
    private Button clickUpdate;
    private ImageView banner1;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_lay);


        upArrow = (ImageView) findViewById(R.id.up_arrow);
        playstore1 = (ImageView) findViewById(R.id.playstore1);
        banner2 = (ImageView) findViewById(R.id.banner2);
        clickUpdate = (Button) findViewById(R.id.clickUpdate);
        banner1 = (ImageView) findViewById(R.id.banner1);

        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.thebhakti"));
                startActivity(intent1);

            }
        });
       playstore1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent2=new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.thebhakti"));
               startActivity(intent2);

           }
       });
       banner2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent3=new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.thebhakti"));
               startActivity(intent3);

           }
       });
       banner1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent4=new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.thebhakti"));
               startActivity(intent4);

           }
       });
       clickUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent5=new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.thebhakti"));
               startActivity(intent5);

           }
       });







    }// onCreate closer

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if(keyCode==KeyEvent.KEYCODE_BACK)
       {
           Intent intent1=new Intent(CheckUpdate.this,CheckUpdate.class);
           startActivity(intent1);
           finish();
       }
        return super.onKeyDown(keyCode, event);
    }
}
