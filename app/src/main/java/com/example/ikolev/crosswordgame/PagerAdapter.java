package com.example.ikolev.crosswordgame;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private Context context;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments, Context context) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.fragments = fragments;
        this.context=context;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return this.fragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) {
            return context.getResources().getString(R.string.label1);
        }  else {
            return context.getResources().getString(R.string.label2);
            }
    }

}

