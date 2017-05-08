package com.weily.alumnibook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.SimpleFragmentPagerAdapter;
import com.weily.alumnibook.fragment.PageFragment;

public class MainActivity extends AppCompatActivity implements ActivityMethod
{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private SimpleFragmentPagerAdapter simpleFragmentPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String showType = "student";

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView= (NavigationView) findViewById(R.id.nav_view);

        simpleFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(simpleFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

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
                        showType = "teacher";
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("showType", showType);
                        PageFragment pageFragment = new PageFragment();
                        pageFragment.setArguments(bundle);

                        simpleFragmentPagerAdapter.setTabTitles(new String[]{"小学老师", "初中老师", "高中老师", "大学老师", "其他"});
                        viewPager.setAdapter(simpleFragmentPagerAdapter);
                        break;
                    case R.id.nav_classmates:
                        showType = "student";
                        simpleFragmentPagerAdapter.setTabTitles(new String[]{"小学同学", "初中同学", "高中同学", "大学同学", "其他"});
                        viewPager.setAdapter(simpleFragmentPagerAdapter);
                        break;
                    case R.id.nav_activity:
                        Intent intent = new Intent(getApplicationContext(), MyActivity.class);
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
                startActivity(new Intent(MainActivity.this, ClassmatesActivity.class));
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
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
