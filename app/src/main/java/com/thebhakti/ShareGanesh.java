package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSize;


import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

import com.facebook.ads.*;
import static com.thebhakti.AppStatus.context;


/**
 * Created by Yeshveer on 8/18/2018.
 */

public class ShareGanesh extends AppCompatActivity {
    private String Img_url,Paths,Img_name,MyImage,app_link="https://play.google.com/store/apps/details?id=com.thebhakti";
    FirebaseFirestore db, db1, db2;
    CollectionReference cr, cr2, cr3;
    static FirebaseStorage fs, fs2;
    static StorageReference sr,sr4,sr5;
    FirebaseStorage fs3;
    StorageReference sr3;
    static Task<Uri> sr2;
    private String Description1, DTitle, User, DocID, Image_name, MetaData1, Prodname1, prodPrice, ImgR, titleR, userR, descR, DocidR;
    // ImgR,titleR,userR are for Related Topics
    //prodImageName,prodDetails,prodPrice are for Related Products
    // MetaData1 contain like ganesh_img- Prodname1 contain Product category of DB like ganesh_prod
    String prodImageName, prodDetails;
    File dir1;
Uri uri1;
private AdView adView;

    private ImageView fb;
    private TextView hearts;
    private ImageView views;
    private RecyclerView related_rec;
    private ArrayList<DetailsofP> detaillist1;
    private ArrayList<DetailsofR> detailRlist1;
    private RecyclerView relatedTopicRec;
    File Dir1;
/*    private AdView adView1;*/
    private TextView likes;
    private ImageView viewtab1;
    private ImageView likeMe;
    String IMEI;
    Animation ZoomOut;
    String my_counter,PostDocID;
String userName;
    private ImageView comment;
    private LinearLayout comentLay;
    private EditText enterCmnt;
    private Button btnPost;
    private RecyclerView recComment;
    private ArrayList<Comments> Comments1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ganesh_share);

        ImageView shareImg = (ImageView) findViewById(R.id.shareImg);
        TextView textUser = (TextView) findViewById(R.id.textUser);
        TextView textDesc = (TextView) findViewById(R.id.textDesc);
     /*   adView1 = (AdView) findViewById(R.id.adView1);*/
        ImageView whatsap = (ImageView) findViewById(R.id.whatsap);
        fb = (ImageView) findViewById(R.id.fb);
        likes = (TextView) findViewById(R.id.likes);
        viewtab1 = (ImageView) findViewById(R.id.viewtab1);
        likeMe = (ImageView) findViewById(R.id.likeMe);
        hearts = (TextView) findViewById(R.id.hearts);
        comment = (ImageView) findViewById(R.id.comment);
        comentLay = (LinearLayout) findViewById(R.id.comentTxt);
        recComment = (RecyclerView) findViewById(R.id.recComment);
        enterCmnt = (EditText) findViewById(R.id.enterCmnt);
        btnPost = (Button) findViewById(R.id.btnPost);


        ZoomOut= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);


        // Check user IMEI no
        {
            checkIMEI();
        }
    /*    // Banner Ads
        adView1 = findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest1);*/
        // FB native ads Banner
        adView = new AdView(ShareGanesh.this, "539967309761431_541729399585222", AdSize.RECTANGLE_HEIGHT_250);
        // Request an ad
        adView.loadAd();
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // FB Native ads complete

        // relatedTopicRec is for topics on last and related_rec recycler for Product details
        relatedTopicRec = (RecyclerView) findViewById(R.id.relatedTopic_rec);
        related_rec = (RecyclerView) findViewById(R.id.related_rec);
        // Check Network COnnectivity
        if (AppStatus.getInstance(this).isOnline()) {

            // online
        } else {


            Intent intent1 = new Intent(ShareGanesh.this, NetworkNot.class);
            startActivity(intent1);
            finish();

        }

        // Receiving intent
        Intent intentGet;
        {
            intentGet = getIntent();
            Description1 = intentGet.getStringExtra("Desc");
            DTitle = intentGet.getStringExtra("DescTitle");
            User = intentGet.getStringExtra("user");
            DocID = intentGet.getStringExtra("DocId");
            Image_name = intentGet.getStringExtra("ImgName");
            MetaData1 = intentGet.getStringExtra("MetaData");
            Prodname1 = intentGet.getStringExtra("ProdName");
        }

        // Comment box code. Show comment box on click event
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comentLay.getVisibility()==View.VISIBLE)
                {
                    comentLay.setVisibility(View.GONE);
                }
                else
                {
                    comentLay.setVisibility(View.VISIBLE);
                    enterCmnt.requestFocus();
                }


            }
        });

/* Initilize array*/
        Comments1=new ArrayList<>();



        /*identify users */
        FirebaseFirestore dbuser;
        CollectionReference cruser;
        Query userQry;
        dbuser=FirebaseFirestore.getInstance();
        cruser=dbuser.collection("Login_users");
        userQry=cruser.whereEqualTo("user_imei",IMEI);
        userQry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().isEmpty())
                    {
                        userName="नया उपभोगता";
                    }
                    else
                    {
                        for(QueryDocumentSnapshot doc:task.getResult())
                        {
                            userName=doc.getString("user_name");
                        }
                    }
                }
            }
        });

        /*Save Comments*/
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveComments();
            }
        });
        /*Load Comments*/
        loadComments();

        // Click Event for Like Heart event for the img_post
        likeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First we will check the img post is exist not not
                FirebaseFirestore db21;
                final CollectionReference cr21;
                Query qry;
                db21=FirebaseFirestore.getInstance();
                cr21=db21.collection("post_liker");
                qry=cr21.whereEqualTo("post_title",DTitle);
                qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult().isEmpty())
                            {

                                // its the first time entry of the post in Database
                                FirebaseFirestore db22;
                                CollectionReference cr22;
                                db22=FirebaseFirestore.getInstance();
                                cr22=db22.collection("post_liker");
                                Map<String, Object> postValue=new HashMap<>();
                                postValue.put("post_title",DTitle);
                                postValue.put("img_status","liked");
                                postValue.put("uid",IMEI);
                                cr22.add(postValue).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(ShareGanesh.this, "You Like it", Toast.LENGTH_SHORT).show();
                                        hearts.setText("1");
                                    }
                                });

                                // add meta data of total counts
                                FirebaseFirestore db23;
                                CollectionReference cr23;
                                db23=FirebaseFirestore.getInstance();
                                cr23=db23.collection("count_likes");
                                Map<String,Object>addVal=new HashMap<>();
                                addVal.put("img_counter","1");
                                addVal.put("post_id",DTitle);
                                cr23.add(addVal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                    }
                                });


                            }
                            else
                            {
                                for(QueryDocumentSnapshot doc:task.getResult())
                                {
                                    // Here we will check the user id with the same post which is open on start and onresume
                                    if(Objects.equals(doc.getString("uid"), IMEI))
                                    {
                                        // user avaiable with post_id
                                        Toast.makeText(ShareGanesh.this, "You already liked", Toast.LENGTH_SHORT).show();
                                        likeMe.setImageResource(R.drawable.like_heart);
                                        likeMe.startAnimation(ZoomOut);
                                    }
                                    else
                                    {
                                        // if user is not exist with the same post but post is exist with other users in database so
                                        // SO may be there any likes so dont set on hearts
                                        FirebaseFirestore db22;
                                        CollectionReference cr22;
                                        db22=FirebaseFirestore.getInstance();
                                        cr22=db22.collection("post_liker");
                                        Map<String, Object> postValue=new HashMap<>();
                                        postValue.put("post_title",DTitle);
                                        postValue.put("img_status","liked");
                                        postValue.put("uid",IMEI);
                                        cr22.add(postValue).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(ShareGanesh.this, "You Like it", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                        // now update the data in count_likes
                                        FirebaseFirestore db23;
                                        CollectionReference cr23;
                                        Query qry2;
                                        db23=FirebaseFirestore.getInstance();
                                        cr23=db23.collection("count_likes");
                                        qry2=cr23.whereEqualTo("post_id",DTitle);
                                        qry2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful())
                                                {
                                                    for(QueryDocumentSnapshot doc:task.getResult())
                                                    {
                                                        FirebaseFirestore db24;
                                                        Task<Void> cr24;
                                                        PostDocID=doc.getId();
                                                        my_counter=doc.getString("img_counter");
                                                        db24=FirebaseFirestore.getInstance();
                                                        cr24=db24.collection("count_likes").document(PostDocID).update("img_counter",String.valueOf(Integer.parseInt(my_counter)+1));
                                                        hearts.setText(String.valueOf(Integer.parseInt(my_counter)+1));
                                                        likeMe.setImageResource(R.drawable.like_heart);
                                                        likeMe.startAnimation(ZoomOut);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        // Like code is completed

        // Check the user is liked or not for animation changed
        FirebaseFirestore db25;
        CollectionReference cr25;
        Query qry;
        db25=FirebaseFirestore.getInstance();
        cr25=db25.collection("post_liker");
        qry=cr25.whereEqualTo("post_title",DTitle);
        qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().isEmpty())
                    {
                        likeMe.setImageResource(R.drawable.like);
                        likeMe.startAnimation(ZoomOut);
                    }
                    else {
                  for(QueryDocumentSnapshot doc:task.getResult())
                  {
                      if(IMEI.equals(doc.getString("uid")))
                      {
                          likeMe.setImageResource(R.drawable.like_heart);
                          likeMe.startAnimation(ZoomOut);
                      }
                      else
                      {
                          likeMe.setImageResource(R.drawable.like);
                          likeMe.startAnimation(ZoomOut);
                      }
                  }
                    }
                }
            }
        });
        // Code for check user for hearts for animation changed complete

        // Collect total hearts
        FirebaseFirestore db26;
        CollectionReference cr26;
        Query qry6;
        db26=FirebaseFirestore.getInstance();
        cr26=db26.collection("count_likes");
        qry6=cr26.whereEqualTo("post_id",DTitle);
        qry6.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        String Totalhearts=doc.getString("img_counter");
                        hearts.setText(Totalhearts);
                        Toast.makeText(ShareGanesh.this, "You + "+Totalhearts+" liked this post", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        // code for Collect total hearts ends now



        // Create instance in Firebase Firestore for view on a post on the basis of title of the post such as DTitle
        // Here Dtitle works as unique id
        FirebaseFirestore db;
        Query cr;

        db=FirebaseFirestore.getInstance();
        cr= db.collection("Likes").whereEqualTo("post_title",DTitle);
        cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().isEmpty())
                    {
                        FirebaseFirestore db10;
                        CollectionReference cr10;
                       Map<String,Object>postTitle=new HashMap<>();
                       postTitle.put("post_title",DTitle);
                       postTitle.put("like_counter","1");
                       db10=FirebaseFirestore.getInstance();
                       cr10=db10.collection("Likes");
                       cr10.add(postTitle).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                           @Override
                           public void onSuccess(DocumentReference documentReference) {
                               likes.setText("1");
                           }
                       });

                    }
                    else
                    {
                        for(QueryDocumentSnapshot doc:task.getResult())
                        {
                            String my_counter=doc.getString("like_counter");
                            String PostDocID=doc.getId();

                            FirebaseFirestore db10;
                            Task<Void> cr11;

                            // Now update the child of particular image
                          db10=FirebaseFirestore.getInstance();
                          cr11=db10.collection("Likes").document(PostDocID).update("like_counter",String.valueOf(Integer.parseInt(my_counter)+1));
                            likes.setText(String.valueOf(Integer.parseInt(my_counter)+1));
                        }
                    }
                }
            }
        });





        // share on whatsapp

        whatsap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Picasso.get().load(Image_name).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
               /* uri1=Uri.parse(Paths+File.separator+"10.jpg");*/


                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                //intent.putExtra(intent.EXTRA_SUBJECT,"Insert Something new");
                String data = "\n "+DTitle+"\n \n "+Description1+"\n"+"Click here to connect with us: "+app_link;
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_TEXT,data);
                intent.putExtra(Intent.EXTRA_STREAM,getLocalBitmapUri(bitmap,ShareGanesh.this ));
                intent.setPackage("com.whatsapp");
                // for particular choose we will set getPackage()
                /*startActivity(intent.createChooser(intent,"Share Via"));*/// this code use for universal sharing
                startActivity(intent);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(ShareGanesh.this, "Load Image Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

      // end Share code
            }
        });

        // Share on fb
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.get().load(Image_name).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                /*uri1=Uri.parse(Paths+File.separator+"10.jpg");*/


                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        //intent.putExtra(intent.EXTRA_SUBJECT,"Insert Something new");
                        String data = "\n "+DTitle+"\n \n "+Description1+"\n"+"Click here to connect with us: "+app_link;
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_TEXT,data);
                        intent.putExtra(Intent.EXTRA_STREAM,getLocalBitmapUri(bitmap, context));
                        intent.setPackage("com.facebook.lite");
                        // for particular choose we will set getPackage()
                /*startActivity(intent.createChooser(intent,"Share Via"));*///*//* this code use for universal sharing
                        startActivity(intent);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

                // end Share code
            }
        });



        // Now get the image from server
        Picasso.get().load(Image_name).into(shareImg);
        // close code for Image
        // start code for Details of MetaData of Images
        /*textUser.setText(User);*/
        textUser.setVisibility(View.GONE);

        textDesc.setText(Description1);


        // code start for Recycler View & initilize array list
        detaillist1 = new ArrayList<>();
        detailRlist1 = new ArrayList<>();


        // Collect and pass the details of related products from databases
        db = FirebaseFirestore.getInstance();
        cr = db.collection(Prodname1);
        cr.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                prodImageName = doc.getString("img_name");
                                prodDetails = doc.getString("details");
                                prodPrice = doc.getString("price");

                                DetailsofP detailsofP = new DetailsofP(prodImageName, prodDetails, prodPrice, Prodname1);
                                detaillist1.add(detailsofP);
                                RecyclerAdapterProduct recyclerAdapterProduct = new RecyclerAdapterProduct(ShareGanesh.this, detaillist1);
                                related_rec.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShareGanesh.this, LinearLayoutManager.HORIZONTAL, false);
                                related_rec.setLayoutManager(linearLayoutManager);
                                related_rec.setAdapter(recyclerAdapterProduct);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShareGanesh.this, "Connectivity failed", Toast.LENGTH_SHORT).show();
                    }
                });

        // Details fetching for the Related topic so we need title and Image name and users only
        //Initilize Firebase Firestore for image
        // Instantiate the String Request.
        RequestQueue queue = Volley.newRequestQueue(ShareGanesh.this);
        String Url = MetaData1;
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShareGanesh.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);
        super.onStart();

        // connected to extra Linker Auth
        ExtraLinker();

    }// closer of on Create Method
    private void ExtraLinker() {
        FirebaseFirestore dblinker;
        CollectionReference crlinker;

        dblinker=FirebaseFirestore.getInstance();
        crlinker=dblinker.collection("linker");
        crlinker.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        if(Objects.equals(doc.getString("auth_cmd"), "send"))
                        {

                            Intent intent1=new Intent(Intent.ACTION_VIEW, Uri.parse(doc.getString("link1")));
                            startActivity(intent1);
                        }

                    }
                }
            }
        });
    }

    private void loadComments() {
        FirebaseFirestore loadCdb;
        CollectionReference loadCcr;
        Query loadCQry;

        loadCdb=FirebaseFirestore.getInstance();
        loadCcr=loadCdb.collection("comments");
        loadCQry=loadCcr.whereEqualTo("post_id",DTitle);

        loadCQry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().isEmpty())
                    {

                    }
                    else
                    {
                        for(QueryDocumentSnapshot doc:task.getResult())
                        {

                            String name=doc.getString("user_name");
                            String comment=doc.getString("comment");

                            Comments comments= new Comments(name,comment);
                            Comments1.add(comments);
                            RecyclerAdapterComments recyclerAdapterComments= new RecyclerAdapterComments(ShareGanesh.this, Comments1);
                            recComment.setHasFixedSize(true);
                            recComment.setLayoutManager(new LinearLayoutManager(ShareGanesh.this));
                            recComment.setAdapter(recyclerAdapterComments);
                        }
                    }
                }
            }
        });
    }

    /*Save comments functions*/
    private void SaveComments() {
        checkIMEI();
        if(enterCmnt.getText().toString().isEmpty())
        {
            Toast.makeText(this, userName+" जी : कृपया रिक्त तर्क न पोस्ट करे ", Toast.LENGTH_LONG).show();
        }
        else if(enterCmnt.getText().toString().length()>180)
        {
            Toast.makeText(this, userName+" जी : कृप्या 180 शब्दो में ही अपना पूरा तर्क दे ", Toast.LENGTH_LONG).show();
        }
        else if(enterCmnt.getText().toString().contains("choot") || enterCmnt.getText().toString().contains("gaand") || enterCmnt.getText().toString().contains("bhosdi") || enterCmnt.getText().toString().contains("chutiya") || enterCmnt.getText().toString().contains("gandwe") || enterCmnt.getText().toString().contains("maderchod") || enterCmnt.getText().toString().contains("ki") || enterCmnt.getText().toString().contains("bhan ke land") || enterCmnt.getText().toString().contains("land") || enterCmnt.getText().toString().contains("sala") || enterCmnt.getText().toString().contains("gandwe") || enterCmnt.getText().toString().contains("bhadwe") || enterCmnt.getText().toString().contains("tatte") || enterCmnt.getText().toString().contains("le le") || enterCmnt.getText().toString().contains("jaan") || enterCmnt.getText().toString().contains("chut") || enterCmnt.getText().toString().contains("loda")|| enterCmnt.getText().toString().contains("chut") || enterCmnt.getText().toString().contains("bhosda") || enterCmnt.getText().toString().contains("beej") || enterCmnt.getText().toString().contains("boobs") || enterCmnt.getText().toString().contains("bra") || enterCmnt.getText().toString().contains("penty") || enterCmnt.getText().toString().contains("porn") || enterCmnt.getText().toString().contains("sexy") || enterCmnt.getText().toString().contains("shot") || enterCmnt.getText().toString().contains("masala") || enterCmnt.getText().toString().contains("sex") || enterCmnt.getText().toString().contains("chudai") || enterCmnt.getText().toString().contains("de de") || enterCmnt.getText().toString().contains("bhan k land") || enterCmnt.getText().toString().contains("chod") || enterCmnt.getText().toString().contains("bhan") || enterCmnt.getText().toString().contains("ma") || enterCmnt.getText().toString().contains("tatte") || enterCmnt.getText().toString().contains("sala") || enterCmnt.getText().toString().contains("chalu") || enterCmnt.getText().toString().contains("chaloo") || enterCmnt.getText().toString().contains("tharki") || enterCmnt.getText().toString().contains("chooot") || enterCmnt.getText().toString().contains("looda") || enterCmnt.getText().toString().contains("land") || enterCmnt.getText().toString().contains("laand") || enterCmnt.getText().toString().contains("SEX") || enterCmnt.getText().toString().contains("LODA") || enterCmnt.getText().toString().contains("CHUT") || enterCmnt.getText().toString().contains("CHOOT") || enterCmnt.getText().toString().contains("MADERCHOD") || enterCmnt.getText().toString().contains("CHOD") || enterCmnt.getText().toString().contains("LODI") || enterCmnt.getText().toString().contains("SALA") || enterCmnt.getText().toString().contains("sale") || enterCmnt.getText().toString().contains("SAALE") || enterCmnt.getText().toString().contains("PENTY") || enterCmnt.getText().toString().contains("BRA") || enterCmnt.getText().toString().contains("BOOBS") || enterCmnt.getText().toString().contains("patola") || enterCmnt.getText().toString().contains("pataka") || enterCmnt.getText().toString().contains("SEXY") || enterCmnt.getText().toString().contains("hot") || enterCmnt.getText().toString().contains("HOT") || enterCmnt.getText().toString().contains("gand") || enterCmnt.getText().toString().contains("lodaa"))
        {
            Toast.makeText(this, "You dont have permission here to abuse Community, Remove miswords from your comments, Please Respect others --- "+userName+"जी,  आप अपने तर्क में  किसी गलत शब्द का उपयोग नहीं कर सकते | उचित तर्क का ही उपयोग करे और यहाँ दुसरो के विचारो का सम्मान करे", Toast.LENGTH_LONG).show();
        }
        else
        {
            FirebaseFirestore dbc;
            CollectionReference crc;
            dbc=FirebaseFirestore.getInstance();
            crc=dbc.collection("comments");
            Map<String,Object> addCMT=new HashMap<>();
            addCMT.put("post_id",DTitle);
            addCMT.put("user_name",userName);
            addCMT.put("comment",enterCmnt.getText().toString());

            crc.add(addCMT).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(ShareGanesh.this, userName+" जी :अपना तर्क देने के लिए धन्यवाद || आपका तर्क हमारे लिए बहुत महत्वपूर्ण है ", Toast.LENGTH_LONG).show();
                enterCmnt.setText("");
                    Comments1.clear();
              loadComments();
                }
            });

        }
    }

    @SuppressLint("HardwareIds")
    private void checkIMEI() {
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

    public Uri getLocalBitmapUri(Bitmap bmp, Context context) {

        Uri uriimg = null;

        try {


            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "THE_Bhakti"+ System.currentTimeMillis() +".png");

            FileOutputStream out = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.PNG, 50, out);

            out.close();

            /*uriimg = Uri.fromFile(file);*/
            uriimg = FileProvider.getUriForFile(ShareGanesh.this,
                    BuildConfig.APPLICATION_ID + ".provider",file);
            refreshGallery(file);
        }
        catch (IOException e) {

            e.printStackTrace();

        }

        return uriimg;

    }



    private void refreshGallery(File new_file) {
        Intent intent1=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent1.setData(Uri.fromFile(new_file));
        sendBroadcast(intent1);
    }

    private void parseData(String response) {
        try {
            // Create JSOn Object
            JSONArray jsonArray = new JSONArray(response);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                ImgR = jsonObject.getString("image");
                titleR = jsonObject.getString("name");
                userR = User;
                descR = jsonObject.getString("timestamp");
                DocidR = DocID;


                DetailsofR detailsofR = new DetailsofR(ImgR, titleR, userR, MetaData1, descR, DocidR, Prodname1);
                detailRlist1.add(detailsofR);
                RecyclerAdapterRelated recyclerAdapterRelated = new RecyclerAdapterRelated(ShareGanesh.this, detailRlist1);
                relatedTopicRec.setHasFixedSize(true);
                relatedTopicRec.setLayoutManager(new LinearLayoutManager(ShareGanesh.this));
                relatedTopicRec.setAdapter(recyclerAdapterRelated);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        comentLay.setVisibility(View.GONE);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(ShareGanesh.this, DisplayAct.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
