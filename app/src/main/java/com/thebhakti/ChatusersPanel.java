package com.thebhakti;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Yeshveer on 9/25/2018.
 */

public class ChatusersPanel extends AppCompatActivity implements NewUserTab1.OnFragmentInteractionListener,NewUserTab2.OnFragmentInteractionListener{
    private RelativeLayout mainLay;
    private LinearLayout mainLay1;
    private TabLayout tabLay;
    private ViewPager viewPager2;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatuserspanel);

        mainLay = (RelativeLayout) findViewById(R.id.mainLay);
        mainLay1 = (LinearLayout) findViewById(R.id.mainLay1);
        tabLay = (TabLayout) findViewById(R.id.tabLay);

        viewPager2 = (ViewPager) findViewById(R.id.viewPager2);
        final ViewPager viewPager2=(ViewPager)findViewById(R.id.viewPager2);
        // first make Adapeter class and then set
        final PagerAdapter1 pagerAdapter1= new PagerAdapter1(getSupportFragmentManager(),tabLay.getTabCount());
        viewPager2.setAdapter(pagerAdapter1);
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

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
