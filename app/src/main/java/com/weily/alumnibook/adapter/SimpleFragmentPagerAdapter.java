package com.weily.alumnibook.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weily.alumnibook.classs.Classmates;
import com.weily.alumnibook.fragment.PageFragment;

import java.util.ArrayList;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter
{
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[]{"小学同学", "初中同学", "高中同学", "大学同学", "其他"};
    private Context context;


    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        ArrayList<Classmates> classmates = new ArrayList<>();
        switch (position)
        {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        return PageFragment.newInstance(classmates);
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    public void setTabTitles(String[] tabTitles) {
        this.tabTitles = tabTitles;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles[position];
    }

}
