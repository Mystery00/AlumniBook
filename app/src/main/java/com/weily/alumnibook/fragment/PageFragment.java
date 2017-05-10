package com.weily.alumnibook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.ClassmatesAdapter;
import com.weily.alumnibook.adapter.TeacherAdapter;
import com.weily.alumnibook.classs.Classmates;
import com.weily.alumnibook.classs.Response;
import com.weily.alumnibook.classs.Teacher;
import com.weily.alumnibook.classs.Teachers;
import com.weily.alumnibook.classs.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

public class PageFragment extends Fragment
{
    private static final String TAG = "PageFragment";
    private static final String ARG_PARAM_INT = "ARG_PARAM_INT";
    private static final String ARG_PARAM_STRING = "ARG_PARAM_STRING";
    private static final int HANDLER_STUDENT = 111;
    private static final int HANDLER_TEACHER = 222;
    private RecyclerView classmatesRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.Adapter adapter;
    private List dataList = new ArrayList();
    private String type;//请求数据类型
    private int position;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case HANDLER_STUDENT:
                    dataList.clear();
                    try
                    {
                        User user = new Gson().fromJson((String) msg.obj, User.class);
                        dataList.addAll(Arrays.asList(user.getClassmates()).subList(0, user.getNumber()));
                    } catch (Exception e)
                    {
                        Logs.e(TAG, "handleMessage: " + e.getMessage());
                    }
                    adapter = new ClassmatesAdapter(dataList);
                    break;
                case HANDLER_TEACHER:
                    dataList.clear();
                    try
                    {
                        Teachers teachers = new Gson().fromJson((String) msg.obj, Teachers.class);
                        dataList.addAll(Arrays.asList(teachers.getTeachers()).subList(0, teachers.getNumber()));
                    } catch (Exception e)
                    {
                        Logs.e(TAG, "handleMessage: " + e.getMessage());
                    }
                    adapter = new TeacherAdapter(dataList);
                    break;
            }
            classmatesRecycler.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);
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
                    map.put("username", App.getContext().getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).getString("username", "test"));
                    Object object = dataList.remove(position);
                    if (object instanceof Classmates)
                    {
                        map.put("method", "deleteStudent");
                        map.put("userType", "student");
                        map.put("name", ((Classmates) object).getName());
                    } else
                    {
                        map.put("method", "deleteTeacher");
                        map.put("userType", "teacher");
                        map.put("name", ((Teacher) object).getName());
                    }
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
                                        postRequest(type, PageFragment.this.position);
                                    }
                                }
                            })
                            .open();
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(classmatesRecycler);
        }
    };

    public PageFragment()
    {
    }

    public static PageFragment newInstance(String type, int position)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_INT, position);
        args.putString(ARG_PARAM_STRING, type);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dataList = new ArrayList();
        if (getArguments() != null)
        {
            this.type = getArguments().getString(ARG_PARAM_STRING);
            this.position = getArguments().getInt(ARG_PARAM_INT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        classmatesRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setRefreshing(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        classmatesRecycler.setLayoutManager(linearLayoutManager);
        postRequest(type, position);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                postRequest(type, position);
            }
        });
        return view;
    }

    private void postRequest(final String type, int dataType)
    {
        Map<String, String> map = new HashMap<>();
        map.put("username", App.getContext().getSharedPreferences(App.getContext().getString(R.string.shared_preference_name), Context.MODE_PRIVATE).getString("username", "test"));
        switch (type)
        {
            case "student":
                map.put("method", "getStudentList");
                break;
            case "teacher":
                map.put("method", "getTeacherList");
                break;
        }
        map.put("userType", type);
        map.put("type", String.valueOf(dataType));
        Logs.i(TAG, "postRequest: " + type);
        Logs.i(TAG, "postRequest: " + dataType);
        new HttpUtil(App.getContext())
                .setRequestMethod(HttpUtil.RequestMethod.POST)
                .setUrl(App.getContext().getString(R.string.request_url))
                .setMap(map)
                .setResponseListener(new ResponseListener()
                {
                    @Override
                    public void onResponse(int i, String s)
                    {
                        if (i == 1)
                        {
                            Message message = new Message();
                            switch (type)
                            {
                                case "student":
                                    message.what = HANDLER_STUDENT;
                                    break;
                                case "teacher":
                                    message.what = HANDLER_TEACHER;
                                    break;
                            }
                            Logs.i(TAG, "onResponse: " + s);
                            message.obj = s;
                            handler.handleMessage(message);
                        }
                    }
                })
                .open();
    }
}
