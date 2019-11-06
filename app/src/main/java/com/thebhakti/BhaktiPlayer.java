package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.facebook.ads.*;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Created by Yeshveer on 8/30/2018.
 */

public class BhaktiPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String API_KEY = "AAIzaSyCbX0uRsGBXC5x8PrCLv79PIJhFgvzy-d0";
    String v_title, vId, play_lst, metaData, CName, cID,IMEI;
    private RecyclerView RelVideo;
    ArrayList<RelatedVideos> relatedVideos1;
    String Video_title, Channel_id, video_id, playlist_id, image_name, MetaData, Channel_name,url,url1;
    private LinearLayout adContainer;
    private AdView adView;
    private ImageView viewtab1;
    private TextView likes;
    private TextView videoTitle;
    private ImageView likeMe;
    private TextView hearts;
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
        setContentView(R.layout.bhakti_player);

        videoTitle = (TextView) findViewById(R.id.videoTitle);
        RelVideo = (RecyclerView) findViewById(R.id.Rel_video);
        viewtab1 = (ImageView) findViewById(R.id.viewtab1);
        likes = (TextView) findViewById(R.id.likes);
        likeMe = (ImageView) findViewById(R.id.likeMe);
        hearts = (TextView) findViewById(R.id.hearts);
        hearts = (TextView) findViewById(R.id.hearts);
        comment = (ImageView) findViewById(R.id.comment);
        comentLay = (LinearLayout) findViewById(R.id.comentTxt);
        recComment = (RecyclerView) findViewById(R.id.recComment);
        enterCmnt = (EditText) findViewById(R.id.enterCmnt);
        btnPost = (Button) findViewById(R.id.btnPost);

        ZoomOut= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);

// Facebook native banner ads
        adView = new AdView(BhaktiPlayer.this, "539967309761431_541729399585222", AdSize.RECTANGLE_HEIGHT_250);
        // Request an ad
        adView.loadAd();
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);

        {
            checkIMEI();
        }



        {
            Intent intent1 = getIntent();
            v_title = intent1.getStringExtra("video_title");
            vId = intent1.getStringExtra("Video_id");
            play_lst = intent1.getStringExtra("Playlist_id");
            metaData = intent1.getStringExtra("Metadata");
            CName = intent1.getStringExtra("Channel_name");
            cID = intent1.getStringExtra("ChannelID");
            url = intent1.getStringExtra("url");

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
                qry=cr21.whereEqualTo("vid_title",v_title);
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
                                postValue.put("vid_title",v_title);
                                postValue.put("vid_status","liked");
                                postValue.put("vuid",IMEI);
                                cr22.add(postValue).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(BhaktiPlayer.this, "You Like it", Toast.LENGTH_SHORT).show();
                                        hearts.setText("1");
                                    }
                                });

                                // add meta data of total counts
                                FirebaseFirestore db23;
                                CollectionReference cr23;
                                db23=FirebaseFirestore.getInstance();
                                cr23=db23.collection("count_likes");
                                Map<String,Object>addVal=new HashMap<>();
                                addVal.put("vid_counter","1");
                                addVal.put("vid",v_title);
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
                                    if(Objects.equals(doc.getString("vuid"), IMEI))
                                    {
                                        // user avaiable with post_id it means user is already exist here or liked
                                        Toast.makeText(BhaktiPlayer.this, "You already liked", Toast.LENGTH_SHORT).show();
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
                                        postValue.put("vid_title",v_title);
                                        postValue.put("vid_status","liked");
                                        postValue.put("vuid",IMEI);
                                        cr22.add(postValue).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(BhaktiPlayer.this, "You Like it", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                        // now update the data in count_likes
                                        FirebaseFirestore db23;
                                        CollectionReference cr23;
                                        Query qry2;
                                        db23=FirebaseFirestore.getInstance();
                                        cr23=db23.collection("count_likes");
                                        qry2=cr23.whereEqualTo("vid",v_title);
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
                                                        my_counter=doc.getString("vid_counter");
                                                        db24=FirebaseFirestore.getInstance();
                                                        cr24=db24.collection("count_likes").document(PostDocID).update("vid_counter",String.valueOf(Integer.parseInt(my_counter)+1));
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

        // Like code is completed
        // Check the user is liked or not for animation changed
        FirebaseFirestore db25;
        CollectionReference cr25;
        Query qry;
        db25=FirebaseFirestore.getInstance();
        cr25=db25.collection("post_liker");
        qry=cr25.whereEqualTo("vid_title",v_title);
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
                            if(IMEI.equals(doc.getString("vuid")))
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
        qry6=cr26.whereEqualTo("vid",v_title);
        qry6.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        String Totalhearts=doc.getString("vid_counter");
                        hearts.setText(Totalhearts);
                        Toast.makeText(BhaktiPlayer.this, "You + "+ Totalhearts+" -liked this post", Toast.LENGTH_SHORT).show();

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
        cr= db.collection("VLikes").whereEqualTo("video_title",v_title);
        cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().isEmpty())
                    {
                        FirebaseFirestore db10;
                        CollectionReference cr10;
                        Map<String,Object> postTitle=new HashMap<>();
                        postTitle.put("video_title",v_title);
                        postTitle.put("video_view","1");
                        db10=FirebaseFirestore.getInstance();
                        cr10=db10.collection("VLikes");
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
                            String my_counter=doc.getString("video_view");
                            String PostDocID=doc.getId();

                            FirebaseFirestore db10;
                            Task<Void> cr11;

                            // Now update the child of particular image
                            db10=FirebaseFirestore.getInstance();
                            cr11=db10.collection("VLikes").document(PostDocID).update("video_view",String.valueOf(Integer.parseInt(my_counter)+1));
                            likes.setText(String.valueOf(Integer.parseInt(my_counter)+1));
                        }
                    }
                }
            }
        });

        // Set Video Title
        videoTitle.setText(v_title);



        YouTubePlayerView bhaktiPlayer1 = (YouTubePlayerView) findViewById(R.id.bhaktiPlayer1);
        bhaktiPlayer1.initialize(API_KEY, this);
        relatedVideos1 = new ArrayList<>();

//Initilize Firebase Firestore for image
        // Instantiate the String Request.
        RequestQueue queue = Volley.newRequestQueue(BhaktiPlayer.this);
        String Url = metaData;
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BhaktiPlayer.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);
        super.onStart();

        // Extra linker for other activities to send user on another linker mech part verification such as google docs
ExtraLinker();




    }// closer of onCreate

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
            IMEI = tm.getDeviceId();

        }
    }

    private void parseData(String response) {

            try {
                // Create JSOn Object
                JSONObject jsonObject = new JSONObject(response);


                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                    // JSON Objects complete now Collect JSON Array Data which we need
                    Video_title=jsonObject1.getJSONObject("snippet").getString("title");
                    Channel_id=jsonObject1.getJSONObject("snippet").getString("channelId");
                    video_id=jsonObject1.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                    playlist_id=jsonObject1.getJSONObject("snippet").getString("playlistId");
                    image_name=jsonObject1.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                    Channel_name=jsonObject1.getJSONObject("snippet").getString("channelTitle");
                    url=url1;


                    MetaData = metaData;
                    //Youtube
                    RelatedVideos relatedVideos = new RelatedVideos(Video_title, Channel_id, video_id, playlist_id, image_name, MetaData, Channel_name,url1);
                    relatedVideos1.add(relatedVideos);


                    RecyclerAdapterRvideos recyclerAdapterRvideos = new RecyclerAdapterRvideos(BhaktiPlayer.this, relatedVideos1);
                    RelVideo.setHasFixedSize(true);
                    RelVideo.setLayoutManager(new LinearLayoutManager(BhaktiPlayer.this));
                    RelVideo.setAdapter(recyclerAdapterRvideos);



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }





    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
youTubePlayer.setPlaybackEventListener(playbackEventListener);
// Start Buffering
        if(!b)
        {
            // select category as per plaeyer
            youTubePlayer.cueVideo(vId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Unable to initilize", Toast.LENGTH_SHORT).show();
    }
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener=new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
    private YouTubePlayer.PlaybackEventListener playbackEventListener=new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
    private void loadComments() {
        FirebaseFirestore loadCdb;
        CollectionReference loadCcr;
        Query loadCQry;

        loadCdb=FirebaseFirestore.getInstance();
        loadCcr=loadCdb.collection("comments");
        loadCQry=loadCcr.whereEqualTo("post_id",v_title);

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
                            RecyclerAdapterComments recyclerAdapterComments= new RecyclerAdapterComments(BhaktiPlayer.this, Comments1);
                            recComment.setHasFixedSize(true);
                            recComment.setLayoutManager(new LinearLayoutManager(BhaktiPlayer.this));
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
        else if(enterCmnt.getText().toString().contains("choot") || enterCmnt.getText().toString().contains("gaand") || enterCmnt.getText().toString().contains("bhosdi") || enterCmnt.getText().toString().contains("chutiya") || enterCmnt.getText().toString().contains("gandwe") || enterCmnt.getText().toString().contains("maderchod") || enterCmnt.getText().toString().contains("ki") || enterCmnt.getText().toString().contains("bhan ke land") || enterCmnt.getText().toString().contains("land") || enterCmnt.getText().toString().contains("sala") || enterCmnt.getText().toString().contains("gandwe") || enterCmnt.getText().toString().contains("bhadwe") || enterCmnt.getText().toString().contains("tatte") || enterCmnt.getText().toString().contains("bawli") || enterCmnt.getText().toString().contains("jaan") || enterCmnt.getText().toString().contains("chut") || enterCmnt.getText().toString().contains("loda")|| enterCmnt.getText().toString().contains("fucking") || enterCmnt.getText().toString().contains("bhosda") || enterCmnt.getText().toString().contains("beej") || enterCmnt.getText().toString().contains("boobs") || enterCmnt.getText().toString().contains("bra") || enterCmnt.getText().toString().contains("penty") || enterCmnt.getText().toString().contains("porn") || enterCmnt.getText().toString().contains("sexy") || enterCmnt.getText().toString().contains("shot") || enterCmnt.getText().toString().contains("masala") || enterCmnt.getText().toString().contains("sex") || enterCmnt.getText().toString().contains("chudai") || enterCmnt.getText().toString().contains("de de") || enterCmnt.getText().toString().contains("bhan k land") || enterCmnt.getText().toString().contains("chod") || enterCmnt.getText().toString().contains("bhan") || enterCmnt.getText().toString().contains("le le") || enterCmnt.getText().toString().contains("tatte") || enterCmnt.getText().toString().contains("sala") || enterCmnt.getText().toString().contains("chalu") || enterCmnt.getText().toString().contains("chaloo") || enterCmnt.getText().toString().contains("tharki") || enterCmnt.getText().toString().contains("chooot") || enterCmnt.getText().toString().contains("looda") || enterCmnt.getText().toString().contains("land") || enterCmnt.getText().toString().contains("laand") || enterCmnt.getText().toString().contains("SEX") || enterCmnt.getText().toString().contains("LODA") || enterCmnt.getText().toString().contains("CHUT") || enterCmnt.getText().toString().contains("CHOOT") || enterCmnt.getText().toString().contains("MADERCHOD") || enterCmnt.getText().toString().contains("CHOD") || enterCmnt.getText().toString().contains("LODI") || enterCmnt.getText().toString().contains("SALA") || enterCmnt.getText().toString().contains("sale") || enterCmnt.getText().toString().contains("SAALE") || enterCmnt.getText().toString().contains("PENTY") || enterCmnt.getText().toString().contains("BRA") || enterCmnt.getText().toString().contains("BOOBS") || enterCmnt.getText().toString().contains("patola") || enterCmnt.getText().toString().contains("pataka") || enterCmnt.getText().toString().contains("SEXY") || enterCmnt.getText().toString().contains("hot") || enterCmnt.getText().toString().contains("HOT") || enterCmnt.getText().toString().contains("gand") || enterCmnt.getText().toString().contains("lodaa"))
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
            addCMT.put("post_id",v_title);
            addCMT.put("user_name",userName);
            addCMT.put("comment",enterCmnt.getText().toString());

            crc.add(addCMT).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(BhaktiPlayer.this, userName+" जी :अपना तर्क देने के लिए धन्यवाद || आपका तर्क हमारे लिए बहुत महत्वपूर्ण है ", Toast.LENGTH_LONG).show();
                    enterCmnt.setText("");
                    Comments1.clear();
                    loadComments();
                }
            });

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
            Intent back1Intent = new Intent(BhaktiPlayer.this, DisplayAct.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
