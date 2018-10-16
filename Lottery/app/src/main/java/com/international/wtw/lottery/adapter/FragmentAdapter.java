package com.international.wtw.lottery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 *   彩票的fragementAdapter
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager fManager;
    private List<Fragment> fragmentsList;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fManager = fm;
        this.fragmentsList = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentsList.get(arg0);
    }

    @Override
    public int getCount() {
        return null==fragmentsList?0:fragmentsList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragmentsList.get(position);
        fManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

}