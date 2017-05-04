package com.weily.alumnibook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.classs.Classmates;
import com.weily.alumnibook.fragment.PageFragment;

import java.util.ArrayList;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter
{
    private String tabTitles[] = App.getContext().getResources().getStringArray(R.array.titles);

    public SimpleFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        ArrayList<Classmates> classmates = new ArrayList<>();
        switch (position)
        {
            case 0:
                classmates.add(new Classmates("小学同学1", "class", "school_number", 1, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                classmates.add(new Classmates("小学同学1", "class", "school_number", 0, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                break;
            case 1:
                classmates.add(new Classmates("初中同学1", "class", "school_number", 1, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                classmates.add(new Classmates("初中同学2", "class", "school_number", 0, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                break;
            case 2:
                classmates.add(new Classmates("高中同学1", "class", "school_number", 1, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                classmates.add(new Classmates("高中同学2", "class", "school_number", 0, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                break;
            case 3:
                classmates.add(new Classmates("大学同学1", "class", "school_number", 1, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                classmates.add(new Classmates("大学同学2", "class", "school_number", 0, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                break;
            case 4:
                classmates.add(new Classmates("其他同学1", "class", "school_number", 1, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                classmates.add(new Classmates("其他同学2", "class", "school_number", 0, "birthday", "address", "work", "home", "withme", "embarrassing", "remark", 0, 0));
                break;
        }
        return PageFragment.newInstance(classmates);
    }

    @Override
    public int getCount()
    {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles[position];
    }
}
