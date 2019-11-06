package com.thebhakti;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Yeshveer on 9/17/2018.
 */

public class NotificationRDirect extends AppCompatActivity{
String value,Desc,DescTitle,ImgName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        {
            Intent intentGet;
            {
                intentGet = getIntent();
                value = intentGet.getStringExtra("value");
                Desc = intentGet.getStringExtra("desc");
                DescTitle = intentGet.getStringExtra("title");
                ImgName = intentGet.getStringExtra("img");
            }
        }


        // Notification code
        // chat-for chat pannel
        // video for video player
        // astro-for astrology post
        // dharam - for dharam post
        // share- for share post
        // lokp-for lokpriya fron page

        if(value.equals("chat"))
        {
            Intent intent1=new Intent(NotificationRDirect.this,EvaluateMe.class);
            startActivity(intent1);
            finish();
        }
        else if(value.equals("video"))
        {
            DisplayAct.viewPager2.setCurrentItem(2);
            finish();
        }
        else if(value.equals("astro"))
        {
            DisplayAct.viewPager2.setCurrentItem(1);
            finish();
        }
        else if(value.equals("dharam"))
        {
            DisplayAct.viewPager2.setCurrentItem(3);
            finish();
        }
        else if(value.equals("lokp"))
        {
            DisplayAct.viewPager2.setCurrentItem(0);
           finish();
        }
        else if(value.equals("lived"))
        {
            DisplayAct.viewPager2.setCurrentItem(4);
            finish();
        }
        else {
            Intent shareintent=new Intent(Intent.ACTION_SEND);
            shareintent.setType("text/url");
            String data="Download Prabhu Bhakti \n धार्मिक वीडियो, भजन, कथा, रोचक तथ्य , भारत के प्रसिद्ध मंदिर और ज्योतिर्लिंग लाइव दर्शन सभी पाए एक साथ , प्रभु भक्ति से जुड़ने के लिए नीचे दिए हुए लिंक पर क्लिक करे  : \n  https://play.google.com/store/apps/details?id=com.thebhakti";
            shareintent.putExtra(Intent.EXTRA_TEXT,data);
            //intent=MainActivity.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            startActivity(Intent.createChooser(shareintent,"Share Via"));
            startActivity(shareintent);
        }
    }
}
