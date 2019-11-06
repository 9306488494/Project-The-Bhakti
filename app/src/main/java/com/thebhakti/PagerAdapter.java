package com.thebhakti;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Yeshveer on 8/9/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int nNoOftabs;
    PagerAdapter(FragmentManager fm, int Numberoftabs)
    {
        super(fm);
        this.nNoOftabs=Numberoftabs;
    }





    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Tab14();

            case 1:
                Tab5  tab5= new Tab5();
                return tab5;
            case 2:
                Tab3 tab3=new Tab3();
                return tab3;
            case 3:
                Tab11  tab11= new Tab11();
                return tab11;
            case 4:
                Tab1 tab1=new Tab1();
                return tab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nNoOftabs;
    }
}
