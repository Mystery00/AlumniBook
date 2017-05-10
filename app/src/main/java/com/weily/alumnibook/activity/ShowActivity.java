package com.weily.alumnibook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.ActivityShowAdapter;
import com.weily.alumnibook.classs.Activity;
import com.weily.alumnibook.classs.ActivityShow;
import com.weily.alumnibook.classs.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;


public class ShowActivity extends AppCompatActivity implements ActivityMethod
{

    private static final String TAG = "ShowActivity";
    private Toolbar toolbar;
    private RecyclerView activityRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActivityShowAdapter adapter;
    private List<ActivityShow> activityShowList = new ArrayList<>();

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
        setContentView(R.layout.activity_show_activity_recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityRecycler = (RecyclerView) findViewById(R.id.activity_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getContext());
        activityRecycler.setLayoutManager(linearLayoutManager);
        adapter = new ActivityShowAdapter(activityShowList);
        activityRecycler.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(true);
        request();

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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                request();
            }
        });
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0, RIGHT)
        {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                int position = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Logs.i(TAG, "onSwiped: 滑动删除");
                Map<String, String> map = new HashMap<>();
                map.put("username", getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).getString("username", "test"));
                map.put("method", "deleteActivity");
                map.put("userType", "activity");
                map.put("activityName", activityShowList.remove(position).getActivityName());
                new HttpUtil(App.getContext())
                        .setUrl(getString(R.string.request_url))
                        .setRequestMethod(HttpUtil.RequestMethod.POST)
                        .setMap(map)
                        .setResponseListener(new ResponseListener()
                        {
                            @Override
                            public void onResponse(int i, String s)
                            {
                                if (i == 1)
                                {
                                    Response response = new Gson().fromJson(s, Response.class);
                                    Toast.makeText(App.getContext(), response.getContent(), Toast.LENGTH_SHORT)
                                            .show();
                                    swipeRefreshLayout.setRefreshing(true);
                                    request();
                                }
                            }
                        })
                        .open();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(activityRecycler);
    }

    private void request()
    {
        activityShowList.clear();
        Map<String, String> map = new HashMap<>();
        map.put("username", App.getContext().getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).getString("username", "test"));
        map.put("userType", "activity");
        map.put("method", "getActivityList");
        new HttpUtil(App.getContext())
                .setRequestMethod(HttpUtil.RequestMethod.POST)
                .setUrl(getString(R.string.request_url))
                .setMap(map)
                .setResponseListener(new ResponseListener()
                {
                    @Override
                    public void onResponse(int i, String s)
                    {
                        if (i == 1)
                        {
                            Logs.i(TAG, "onResponse: " + s);
                            Activity activity = new Gson().fromJson(s, Activity.class);
                            activityShowList.addAll(Arrays.asList(activity.getActivityShow()));
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                })
                .open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.send_act)
        {
            Intent intent = new Intent(getApplicationContext(), SendActivity.class);
            startActivity(intent);
        }
        return true;
    }
}