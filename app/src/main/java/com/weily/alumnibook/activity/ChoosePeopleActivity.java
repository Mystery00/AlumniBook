package com.weily.alumnibook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.ChoosePeopleAdapter;
import com.weily.alumnibook.classs.Classmates;
import com.weily.alumnibook.classs.ShowList;
import com.weily.alumnibook.classs.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoosePeopleActivity extends AppCompatActivity implements ActivityMethod
{
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChoosePeopleAdapter adapter;
    private List<Classmates> classmatesList = new ArrayList<>();
    private List<Teacher> teacherList = new ArrayList<>();
    private ArrayList<Classmates> checkedClassmatesList = new ArrayList<>();
    private ArrayList<Teacher> checkedTeacherList = new ArrayList<>();
    private List<Map<String, String>> checkedList = new ArrayList<>();
    private List<Map<String, String>> showArrayList = new ArrayList<>();

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
        checkedClassmatesList.clear();
        checkedTeacherList.clear();
        checkedClassmatesList.addAll((List) (getIntent().getBundleExtra("list").getSerializable("student")));
        checkedTeacherList.addAll((List) (getIntent().getBundleExtra("list").getSerializable("teacher")));
        for (int n = 0; n < checkedClassmatesList.size(); n++)
        {
            Map<String, String> map = new HashMap<>();
            map.put("type", "student");
            map.put("name", checkedClassmatesList.get(n).getName());
            checkedList.add(map);
        }
        for (int n = 0; n < checkedTeacherList.size(); n++)
        {
            Map<String, String> map = new HashMap<>();
            map.put("type", "teacher");
            map.put("name", checkedTeacherList.get(n).getName());
            checkedList.add(map);
        }
        setContentView(R.layout.activity_choose_people);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        adapter = new ChoosePeopleAdapter(showArrayList, checkedList);
        adapter.setOnCheckListener(new ChoosePeopleAdapter.OnCheckListener()
        {
            @Override
            public void onCheck(Map<String, String> map)
            {
                switch (map.get("type"))
                {
                    case "student":
                        checkedClassmatesList.add(classmatesList.get(Integer.parseInt(map.get("position"))));
                        break;
                    case "teacher":
                        checkedTeacherList.add(teacherList.get(Integer.parseInt(map.get("position"))));
                        break;
                }
            }

            @Override
            public void onNotCheck(Map<String, String> map)
            {
                switch (map.get("type"))
                {
                    case "student":
                        checkedClassmatesList.remove(classmatesList.get(Integer.parseInt(map.get("position"))));
                        break;
                    case "teacher":
                        checkedTeacherList.remove(teacherList.get(Integer.parseInt(map.get("position"))));
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setRefreshing(true);
        refresh();

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
                refresh();
            }
        });
    }

    private void refresh()
    {
        Map<String, String> map = new HashMap<>();
        map.put("username", getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).getString("username", "test"));
        map.put("userType", "user");
        map.put("method", "getList");
        new HttpUtil(App.getContext())
                .setUrl(getString(R.string.request_url))
                .setMap(map)
                .setRequestMethod(HttpUtil.RequestMethod.POST)
                .setResponseListener(new ResponseListener()
                {
                    @Override
                    public void onResponse(int i, String s)
                    {
                        if (i == 1)
                        {
                            ShowList showList = new Gson().fromJson(s, ShowList.class);
                            showArrayList.clear();
                            classmatesList.clear();
                            teacherList.clear();
                            classmatesList.addAll(Arrays.asList(showList.getClassmates()));
                            teacherList.addAll(Arrays.asList(showList.getTeachers()));
                            for (int n = 0; n < showList.getClassmates().length; n++)
                            {
                                Map<String, String> map = new HashMap<>();
                                map.put("type", "student");
                                map.put("position", String.valueOf(n));
                                map.put("name", showList.getClassmates()[n].getName());
                                showArrayList.add(map);
                            }
                            for (int n = 0; n < showList.getTeachers().length; n++)
                            {
                                Map<String, String> map = new HashMap<>();
                                map.put("type", "teacher");
                                map.put("position", String.valueOf(n));
                                map.put("name", showList.getTeachers()[n].getName());
                                showArrayList.add(map);
                            }
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
        getMenuInflater().inflate(R.menu.menu_choose, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_done:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("student", checkedClassmatesList);
                bundle.putSerializable("teacher", checkedTeacherList);
                intent.putExtra("list", bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
