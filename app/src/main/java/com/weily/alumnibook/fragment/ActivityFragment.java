package com.weily.alumnibook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.weily.alumnibook.R;
import com.weily.alumnibook.activity.ActivityShowActivity;
import com.weily.alumnibook.adapter.ActivityShowAdapter;
import com.weily.alumnibook.classs.Activity;
import com.weily.alumnibook.classs.ActivityShow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ActivityFragment extends Fragment implements ActivityShowAdapter.OnItemClickListener{

    private RecyclerView activityRecycler;
    private LinearLayoutManager linearLayoutManager;
    private ActivityShowAdapter adapter;
    private List<ActivityShow> activityShowList=new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.show_activity_recycler,container,false);
        activityRecycler= (RecyclerView) view.findViewById(R.id.activity_recycler);

        initial();
        linearLayoutManager=new LinearLayoutManager(getContext());
        activityRecycler.setLayoutManager(linearLayoutManager);
        adapter=new ActivityShowAdapter(activityShowList);
        activityRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        return view;
    }



    public void initial(){
        activityShowList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                String username="asd";
                RequestBody requestBody=new FormBody.Builder()
                        .add("username",username)
                        .add("userType","activity")
                        .add("method","getActivityList")
                        .build();
                Request request = new Request.Builder()
                        .url("http://www.mystery0.vip/php/alumnibook/alumnibook.php")
                        .post(requestBody)
                        .build();
                try {
                    final Response response=client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        final String responseData = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                Activity activity = gson.fromJson(responseData, Activity.class);
                                ActivityShow activityShow[]=activity.getActivityShow();
                                for (ActivityShow a:activityShow){
                                    activityShowList.add(a);
                                    adapter.notifyDataSetChanged();
                                }
                                Log.i("info", "run: "+responseData);

                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(getContext(), ActivityShowActivity.class);
        startActivity(intent);
    }
}