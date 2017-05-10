package com.weily.alumnibook.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.SimpleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity implements ActivityMethod
{
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private LinearLayout student_layout, teacher_layout;

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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        student_layout = (LinearLayout) findViewById(R.id.student_layout);
        ViewPager student_viewPager = (ViewPager) findViewById(R.id.student_viewpager);
        TabLayout student_tabLayout = (TabLayout) findViewById(R.id.student_sliding_tabs);
        teacher_layout = (LinearLayout) findViewById(R.id.teacher_layout);
        ViewPager teacher_viewPager = (ViewPager) findViewById(R.id.teacher_viewpager);
        TabLayout teacher_tabLayout = (TabLayout) findViewById(R.id.teacher_sliding_tabs);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView textView_header = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);

        textView_header.setText(getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).getString("username", "用户名"));

        SimpleFragmentPagerAdapter studentAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), "student");
        student_viewPager.setAdapter(studentAdapter);
        student_tabLayout.setupWithViewPager(student_viewPager);
        student_tabLayout.setTabMode(TabLayout.MODE_FIXED);

        SimpleFragmentPagerAdapter teacherAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), "teacher");
        teacher_viewPager.setAdapter(teacherAdapter);
        teacher_tabLayout.setupWithViewPager(teacher_viewPager);
        teacher_tabLayout.setTabMode(TabLayout.MODE_FIXED);

        navigationView.setCheckedItem(R.id.nav_classmates);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                drawerLayout.closeDrawers();
                switch (item.getItemId())
                {
                    case R.id.nav_teacher:
                        student_layout.setVisibility(View.GONE);
                        teacher_layout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_classmates:
                        student_layout.setVisibility(View.VISIBLE);
                        teacher_layout.setVisibility(View.GONE);
                        break;
                    case R.id.nav_activity:
                        Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    public void monitor()
    {
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(App.getContext(), student_layout.getVisibility()==View.VISIBLE ? ClassmatesActivity.class : TeacherActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.re_load:
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();
                startActivity(new Intent(App.getContext(), LoginActivity.class));
                finish();
                break;
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
