package com.weily.alumnibook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.weily.alumnibook.R;
import com.weily.alumnibook.fragment.ActivityFragment;

/**
 * Created by HS on 2017/5/7.
 */

public class ActivityShowActivity extends AppCompatActivity {
    private ActivityFragment activityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_activity);
        activityFragment = (ActivityFragment) getSupportFragmentManager().findFragmentById(R.id.activity_fragment);
    }
}