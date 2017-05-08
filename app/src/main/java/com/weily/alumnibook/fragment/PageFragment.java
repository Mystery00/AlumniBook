package com.weily.alumnibook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.weily.alumnibook.adapter.ClassmatesAdapter;
import com.weily.alumnibook.adapter.TeacherAdapter;
import com.weily.alumnibook.classs.Teachers;
import com.weily.alumnibook.classs.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageFragment extends Fragment
{
    private static final String TAG = "PageFragment";
    private static final String ARG_PARAM_INT = "ARG_PARAM_INT";
    private static final String ARG_PARAM_STRING = "ARG_PARAM_STRING";
    private static final int HANDLER_STUDENT = 111;
    private static final int HANDLER_TEACHER = 222;
    private RecyclerView classmatesRecycler;
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
                    User user = new Gson().fromJson((String) msg.obj, User.class);
                    dataList.clear();
                    dataList.addAll(Arrays.asList(user.getClassmates()).subList(0, user.getNumber()));
                    adapter = new ClassmatesAdapter(dataList);
                    break;
                case HANDLER_TEACHER:
                    Teachers teachers = new Gson().fromJson((String) msg.obj, Teachers.class);
                    dataList.clear();
                    dataList.addAll(Arrays.asList(teachers.getTeachers()).subList(0, teachers.getNumber()));
                    adapter = new TeacherAdapter(dataList);
                    break;
            }
            classmatesRecycler.setAdapter(adapter);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        classmatesRecycler.setLayoutManager(linearLayoutManager);
        postRequest(type, position);
        return view;
    }

    private void postRequest(final String type, int dataType)
    {
        Map<String, String> map = new HashMap<>();
        map.put("username", App.getContext().getSharedPreferences(App.getContext().getString(R.string.shared_preference_name), Context.MODE_PRIVATE).getString("username", "asd"));
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
