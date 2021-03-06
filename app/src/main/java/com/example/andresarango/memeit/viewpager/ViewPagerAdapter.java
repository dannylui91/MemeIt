package com.example.andresarango.memeit.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.andresarango.memeit.viewpager.tabfragments.HomeFragment;
import com.example.andresarango.memeit.viewpager.tabfragments.StockPicsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dannylui on 1/18/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentTitleList = new ArrayList<>();
    public static ViewPagerAdapter vpInstance = null;

    public ViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        this.vpInstance = this;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public void setStockFragment() {
        fragmentList.set(0, new StockPicsFragment());
        notifyDataSetChanged();
    }

    public void setHomeFragment() {
        fragmentList.set(0, new HomeFragment());
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        // Causes notifySetChanged to redraw
        return POSITION_NONE;
    }


}
