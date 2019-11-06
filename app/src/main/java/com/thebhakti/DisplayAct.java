package com.thebhakti;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yeshveer on 8/8/2018.
 */

public class DisplayAct extends AppCompatActivity implements Tab1.OnFragmentInteractionListener,Tab3.OnFragmentInteractionListener,Tab11.OnFragmentInteractionListener,Tab14.OnFragmentInteractionListener,Tab5.OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLay;
    public static ViewPager viewPager2;
    private RelativeLayout mainLay;
    private ImageView imgBanner;
    FirebaseFirestore db,db1,db3;
    CollectionReference cr,cr1,cr3;
    FirebaseStorage fs;
    Task<Uri> sr;
    private ArrayList<String> ImageUrl;
    int i;
  /*  private FloatingActionButton fab;*/
    String IMEI;
    TextView mainWallet;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        builder = new AlertDialog.Builder(DisplayAct.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLay = (TabLayout) findViewById(R.id.tabLay);
        viewPager2 = (ViewPager) findViewById(R.id.viewPager2);
       /* fab = (FloatingActionButton) findViewById(R.id.fab);*/
        BottomNavigationView navigationView1 = (BottomNavigationView) findViewById(R.id.navigationView);
// Navigration Drawer Start
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Navigation drawer closed
// click on mainwallet on top
   /*     mainWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(DisplayAct.this,Wallet_intro.class);
                startActivity(intent1);
                finish();
            }
        });*/

        // get the version details
        // Initilize array list
        ImageUrl=new ArrayList<String>();
// fill detail in wallet





        final ViewPager viewPager2=(ViewPager)findViewById(R.id.viewPager2);
        // first make Adapeter class and then set
        final PagerAdapter pagerAdapter=new com.thebhakti.PagerAdapter(getSupportFragmentManager(),tabLay.getTabCount());
        viewPager2.setAdapter(pagerAdapter);
        // add page change listner on viewpager for slide
        viewPager2.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLay));
        tabLay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//collect IMEI
        {
            imeiData();
        }
// Navigation Views
        navigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               /* if(R.id.mhome==item.getItemId())
                {
                    DisplayAct.viewPager2.setCurrentItem(0);
                }
                else*/
               if(R.id.mchat==item.getItemId())
                {
                    Intent intent1=new Intent(DisplayAct.this,EvaluateMe.class);
                    startActivity(intent1);
                    finish();
                }
                else if(R.id.mvideos==item.getItemId())
                {

                    DisplayAct.viewPager2.setCurrentItem(2);
                }
                else if(R.id.mwallet==item.getItemId())
                {

                    Intent intent1=new Intent(DisplayAct.this,Wallet_intro.class);
                    startActivity(intent1);
                    finish();
                }
                return true;
            }
        });









    }// closer of onCreate Method
// create instance fo public use
    @SuppressLint("StaticFieldLeak")
    public static DisplayAct closer;

    @Override
    protected void onStart() {
        closer=this;
        super.onStart();
    }



    @SuppressLint("HardwareIds")
    private void imeiData() {

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








        /*db1=FirebaseFirestore.getInstance();
        DocumentReference contactListener = db1.collection("Notify").document("notify");
        contactListener.addSnapshotListener(new EventListener< DocumentSnapshot >() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot,
                                FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("ERROR", e.getMessage());
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {




                       Intent intent1=new Intent(DisplayAct.this,LoginUser.class);


                       PendingIntent pendingIntent = PendingIntent.getActivity(DisplayAct.this, 0 , intent1,
                               PendingIntent.FLAG_ONE_SHOT);

                       NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(DisplayAct.this)
                               .setSmallIcon(R.drawable.icon)
                               .setContentTitle(documentSnapshot.getString("User"))
                               .setContentText(documentSnapshot.getString("Msg"))
                               .setContentIntent(pendingIntent);
                       notificationbuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                       NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(DisplayAct.this);


                       notificationManagerCompat.notify(0,notificationbuilder.build());






                }
            }
        });*/


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.privacy32)
         {
            Intent intent1=new Intent(DisplayAct.this,Privacy.class);
            startActivity(intent1);
            finish();
        }
        else if (id == R.id.about32) {


        } else if (id == R.id.share32) {
             Intent shareintent=new Intent(Intent.ACTION_SEND);
             shareintent.setType("text/url");
             String data="Download Prabhu Bhakti \n धार्मिक वीडियो, भजन, कथा, रोचक तथ्य , भारत के प्रसिद्ध मंदिर और ज्योतिर्लिंग लाइव दर्शन सभी पाए एक साथ , प्रभु भक्ति से जुड़ने के लिए नीचे दिए हुए लिंक पर क्लिक करे  : \n  https://play.google.com/store/apps/details?id=com.thebhakti";
             shareintent.putExtra(Intent.EXTRA_TEXT,data);
             //intent=MainActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
             startActivity(Intent.createChooser(shareintent,"Share Via"));
             startActivity(shareintent);

        } else if (id == R.id.chat32) {
             Intent intent1=new Intent(DisplayAct.this,EvaluateMe.class);
             startActivity(intent1);
             finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            builder.setMessage("Do you want to close this application ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MainActivity.fs.finish();
                 /*       Intent intent1=new Intent(DisplayAct.this,NullByteException.class);
                        startActivity(intent1);*/
                   /*   finish();*/
                      finishAffinity();
                          /*  System.exit(0);*/

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Exit to The Bhakti");
            alert.show();
        }
        return super.onKeyDown(keyCode, event);

    }

    private void closer() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
