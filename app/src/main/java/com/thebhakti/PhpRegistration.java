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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Yeshveer on 11/14/2018.
 */

public class PhpRegistration extends AppCompatActivity {
    private WebView webView;
String IMEI,user,Mobile,type,Url;
    private RecyclerView chatRec;
    private ArrayList<ChatMsg2>msgList;
    String id,name,msg,userType,time,state,rdate,amount,seen,detect_date,reg_date;

    private LinearLayout titleLay;
    private TextView chatUser;
    private TextView txtAmount;
    private LinearLayout typerLay;
    private EditText txtMsg;
    private ImageView msgSend;
    private AdView adView1;




    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.php_reg);
        chatRec = (RecyclerView) findViewById(R.id.chatRec);
        titleLay = (LinearLayout) findViewById(R.id.titleLay);
        chatUser = (TextView) findViewById(R.id.chatUser);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        typerLay = (LinearLayout) findViewById(R.id.typerLay);
        txtMsg = (EditText) findViewById(R.id.txtMsg);
        msgSend = (ImageView) findViewById(R.id.msgSend);
        adView1 = (AdView) findViewById(R.id.adView1);
        webView = (WebView) findViewById(R.id.webView);

                // Banner Ads
        //load Ads
        // Parsing Start
        RequestQueue queue1 = Volley.newRequestQueue(PhpRegistration.this);
        String Url11 = "https://thebhakti.com/PbChat/API/linker.php?id=az506oth";

        StringRequest request1 = new StringRequest(Request.Method.GET, Url11, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData11(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PhpRegistration.this, "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout1 = 30000;
        RetryPolicy policy1 = new DefaultRetryPolicy(socketTimeout1, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request1.setRetryPolicy(policy1);
        queue1.add(request1);
        // parsing stop

        msgList = new ArrayList<>();

        // getIntents
        {
            Intent intent1;
            intent1 = getIntent();
            IMEI = intent1.getStringExtra("IMEI");
            user = intent1.getStringExtra("user");
            Mobile = intent1.getStringExtra("Mobile");
            detect_date = intent1.getStringExtra("detect_date");
            reg_date = intent1.getStringExtra("reg_date");
            amount = intent1.getStringExtra("amount");


        }
// display amount on wallet corner
        txtAmount.setText("Rs "+amount);
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

// Make seen data from unseen
        makeSeen();


// detect Amount and update detect_date
                              /* Time and Date */
        @SuppressLint("SimpleDateFormat") DateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();



        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, -1);
        Date todate2 = cal2.getTime();


        String old_date1 = date1.format(todate2);
        String current_date = date1.format(date);


        if (old_date1.equals(reg_date))
        {
            Toast.makeText(this, "You are on Free Trial for this day1", Toast.LENGTH_SHORT).show();
        }

        else if(current_date.equals(reg_date))
        {
            Toast.makeText(this, "You are on Free Trial Today2", Toast.LENGTH_SHORT).show();
        }
        else if(detect_date.equals(current_date))
        {
            Toast.makeText(this, "You have already Paid3", Toast.LENGTH_SHORT).show();
        }
        else if(detect_date.equals(reg_date))
        {
            Toast.makeText(this, "Today you are register so today you are free4", Toast.LENGTH_SHORT).show();
        }

        else {
                if(Integer.parseInt(amount)< 25)
            {
                Intent intent2=new Intent(PhpRegistration.this,MyWallet2.class);
                startActivity(intent2);

            }
            else {

                    // Parsing Date
                    RequestQueue queue = Volley.newRequestQueue(PhpRegistration.this);
                    Url = "https://thebhakti.com/PbChat/API/detect_amt.php?cDate=" + current_date + "&IMEI=" + IMEI;

                    StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //weatherData.setText("Response is :- ");
                            detect_AMT(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PhpRegistration.this, "Failed Parsing Data", Toast.LENGTH_SHORT).show();

                        }
                    });
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);
                    queue.add(request);
                    Toast.makeText(this, "Detect Date" + current_date, Toast.LENGTH_SHORT).show();
                    // parsing stop
                }
        }

        //Json Method
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


        RequestQueue queue = Volley.newRequestQueue(PhpRegistration.this);
        Url = "https://thebhakti.com/PbChat/api_msg.php?usermob="+Mobile;

        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PhpRegistration.this, "Network slow", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
        timeInterval();

            }
        },2000);


        // send msg to php server
        msgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtMsg.getText().toString().equals(""))
                {
                    Toast.makeText(PhpRegistration.this, "Can't Blank, Enter Your message", Toast.LENGTH_SHORT).show();
                }
                else if(txtMsg.getText().toString().contains("choot") || txtMsg.getText().toString().contains("bhosdi ke") || txtMsg.getText().toString().contains("tatte") || txtMsg.getText().toString().contains("chutiya") || txtMsg.getText().toString().contains("gandwa") || txtMsg.getText().toString().contains("pussy") || txtMsg.getText().toString().contains("gandwa"))
                {
                    Toast.makeText(PhpRegistration.this, "Don't allow abusing words", Toast.LENGTH_SHORT).show();
                }
                else
                {


                   String Url1="https://thebhakti.com/PbChat/userMsg.php?userMob="+Mobile+"&name="+user+"&mob="+Mobile+"&type=agent&msg="+txtMsg.getText().toString();
                    webView.loadUrl(Url1);
                    txtMsg.setText("");
                    loadMsg();

                }
            }
        });




    }// onCreate closer

    private void loadMsg() {
        //Json Method
        msgList.clear();
                    RequestQueue queue = Volley.newRequestQueue(PhpRegistration.this);
                Url = "https://thebhakti.com/PbChat/api_msg.php?usermob="+Mobile;

                StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //weatherData.setText("Response is :- ");
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PhpRegistration.this, "Network slow", Toast.LENGTH_SHORT).show();

                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);
                queue.add(request);
                timeInterval();

    }











    private void parseData11(String response) {
        try {
            // Create JSOn Object

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String showAds=jsonObject.getString("showAds");
               if(showAds.equals("show"))
               {

                   AdRequest adRequest1 = new AdRequest.Builder().build();
                   adView1.loadAd(adRequest1);
               }


            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void makeSeen() {
        // Parsing Start
        RequestQueue queue1 = Volley.newRequestQueue(PhpRegistration.this);
        String Url1 = "https://thebhakti.com/PbChat/API/make_seen.php?id="+Mobile;

        StringRequest request1 = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData2(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PhpRegistration.this, "Network slow", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout1 = 30000;
        RetryPolicy policy1 = new DefaultRetryPolicy(socketTimeout1, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request1.setRetryPolicy(policy1);
        queue1.add(request1);
        // parsing stop
    }

    private void parseData2(String response) {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                makeSeen();
            }
        },8000);
    }

    private void detect_AMT(String response) {
        Toast.makeText(this, "Amount Has been Detected", Toast.LENGTH_SHORT).show();
    }

    private void timeInterval() {
        //Json Method
        msgList.clear();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                RequestQueue queue = Volley.newRequestQueue(PhpRegistration.this);
                Url = "https://thebhakti.com/PbChat/api_msg.php?usermob="+Mobile;

                StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //weatherData.setText("Response is :- ");
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PhpRegistration.this, "Network slow", Toast.LENGTH_SHORT).show();

                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);
                queue.add(request);
                timeInterval();

            }
        },20000);
    }


    private void parseData(String response) {
try {
    // Create JSOn Object

    JSONArray jsonArray = new JSONArray(response);

    for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
        id=jsonObject.getString("id");
        name=jsonObject.getString("name");
        msg=jsonObject.getString("msg");
        userType=jsonObject.getString("type");
        time=jsonObject.getString("time");
        seen=jsonObject.getString("seen");

        ChatMsg2 chatMsg2= new ChatMsg2(id, name, msg,userType,time,seen);
        msgList.add(chatMsg2);
        RecyclerAdapterChatMsg recyclerAdapterChatMsg = new RecyclerAdapterChatMsg(PhpRegistration.this, msgList);
        chatRec.setHasFixedSize(true);
        chatRec.setLayoutManager(new LinearLayoutManager(PhpRegistration.this));
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(PhpRegistration.this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager2.setStackFromEnd(true);
        linearLayoutManager2.canScrollVertically();

        chatRec.setAdapter(recyclerAdapterChatMsg);
        chatRec.setLayoutManager(linearLayoutManager2);

    }

}
catch (JSONException e) {
    e.printStackTrace();
}
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent back1Intent = new Intent(PhpRegistration.this, DisplayAct.class);
            startActivity(back1Intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }


}


