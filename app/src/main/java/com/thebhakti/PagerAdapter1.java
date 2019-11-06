package com.thebhakti;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.*;
import android.view.View;

/**
 * Created by Yeshveer on 9/25/2018.
 */

public class PagerAdapter1 extends FragmentStatePagerAdapter {
    private int nNoOftabs;
    PagerAdapter1(FragmentManager fm, int Numberoftabs)
    {
        super(fm);
        this.nNoOftabs=Numberoftabs;
    }





    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                NewUserTab1 tab1=new NewUserTab1();
                return tab1;
            case 1:
                NewUserTab2 tab2=new NewUserTab2();
                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nNoOftabs;
    }
}
