package com.example.hp.simpleviewpagerindicator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public TabFragmentPagerAdapter(FragmentManager manager,List<Fragment> list){
        super(manager);
        mList = list;
    }
    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
