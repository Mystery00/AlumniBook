package com.weily.alumnibook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.fragment.PageFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter
{
    private String type;

    public SimpleFragmentPagerAdapter(FragmentManager fm, String type)
    {
        super(fm);
        this.type = type;
    }

    @Override
    public Fragment getItem(int position)
    {
        return PageFragment.newInstance(type, position + 1);
    }

    @Override
    public int getCount()
    {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String[] tabTitles;
        switch (type)
        {
            case "student":
                tabTitles = App.getContext().getResources().getStringArray(R.array.student_titles);
                break;
            case "teacher":
                tabTitles = App.getContext().getResources().getStringArray(R.array.teacher_titles);
                break;
            default:
                tabTitles = new String[0];
                break;
        }
        return tabTitles[position];
    }

}
