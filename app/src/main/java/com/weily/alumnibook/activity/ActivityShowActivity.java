package com.weily.alumnibook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.R;

/**
 * Created by HS on 2017/5/7.
 */

public class ActivityShowActivity extends AppCompatActivity implements ActivityMethod
{
    private Toolbar toolbar;
    private TextView activity_name;
    private TextView activity_content;
    private TextView activity_date;
    private TextView activity_userList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initialization();
        monitor();
    }

    @Override
    public void initialization()
    {
        setContentView(R.layout.activity_show_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activity_name = (TextView) findViewById(R.id.activity_name_show);
        activity_content = (TextView) findViewById(R.id.activity_content_show);
        activity_date = (TextView) findViewById(R.id.send_time);
        activity_userList = (TextView) findViewById(R.id.userList);

        setSupportActionBar(toolbar);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void monitor()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}