package com.weily.alumnibook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.ActivityShowAdapter;
import com.weily.alumnibook.classs.Activity;
import com.weily.alumnibook.classs.ActivityShow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityFragment extends Fragment
{

    private static final String TAG = "ActivityFragment";
    private RecyclerView activityRecycler;
    private LinearLayoutManager linearLayoutManager;
    private ActivityShowAdapter adapter;
    private List<ActivityShow> activityShowList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.show_activity_recycler, container, false);
        activityRecycler = (RecyclerView) view.findViewById(R.id.activity_recycler);

        initial();
        linearLayoutManager = new LinearLayoutManager(getContext());
        activityRecycler.setLayoutManager(linearLayoutManager);
        adapter = new ActivityShowAdapter(activityShowList);
        activityRecycler.setAdapter(adapter);

        return view;
    }


    public void initial()
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
                        }
                    }
                })
                .open();
    }
}