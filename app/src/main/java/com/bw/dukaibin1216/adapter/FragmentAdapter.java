package com.bw.dukaibin1216.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    //初始化
    List<Fragment> mfragment;
    String[] mtitles;

    public FragmentAdapter(@NonNull FragmentManager fm,List<Fragment> fragments,String[] titles) {
        super(fm);
        mfragment = fragments;
        mtitles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mfragment.get(position);
    }

    @Override
    public int getCount() {
        return mfragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mtitles[position];
    }
}
