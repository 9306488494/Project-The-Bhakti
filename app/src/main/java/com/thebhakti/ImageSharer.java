package com.thebhakti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * Created by Yeshveer on 8/28/2018.
 */

public class ImageSharer extends AppCompatActivity {
    private ImageView imgView;
    private Button shareBtn;
    FirebaseStorage fs;
    StorageReference sr,sr1;
    String Img_name;
    File dir1;
    Uri uri1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_sharer);
        imgView = (ImageView) findViewById(R.id.imgView);
        shareBtn = (Button) findViewById(R.id.shareBtn);

        // Initilize firebasestorage instance
        fs=FirebaseStorage.getInstance();
        sr=fs.getReference();
        Img_name="25.jpg";
        sr1=sr.child("shiva/"+Img_name);
        final String Paths= Environment.getExternalStorageDirectory()+ File.separator+"The_Bhakti"+File.separator+"Data";
dir1=new File(Paths);
if(!dir1.isDirectory())
{
    dir1.mkdirs();
}
   sr1.getFile(new File(dir1,Img_name)).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
       @Override
       public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
           sr1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                   uri1= Uri.parse(uri.toString());
               }
           });

       }
   }) ;

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uri1=Uri.parse(Paths+File.separator+Img_name);
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                //intent.putExtra(intent.EXTRA_SUBJECT,"Insert Something new");
                String data = "Hello";
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_TEXT,data);
                intent.putExtra(Intent.EXTRA_STREAM,uri1);
                intent.setPackage("com.whatsapp");
                // for particular choose we will set getPackage()
                /*startActivity(intent.createChooser(intent,"Share Via"));*/// this code use for universal sharing
                /*startActivity(intent);*/

                // end Share code
            }
        });

    }// onCreate closer
}
