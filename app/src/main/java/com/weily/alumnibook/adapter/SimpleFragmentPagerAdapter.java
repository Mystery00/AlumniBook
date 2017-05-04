package com.weily.alumnibook.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weily.alumnibook.classs.Classmates;
import com.weily.alumnibook.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter
{
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"tab1", "tab2", "tab3"};

    public SimpleFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        ArrayList<Classmates> list=new ArrayList<>();
        list.add(new Classmates("name","class","school_number",0,"birthday","address","work","home","withme","embarrassing","remark",0,0));
        return PageFragment.newInstance(list);
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles[position];
    }
}
