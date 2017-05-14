package com.weily.alumnibook.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.classs.ActivityShow;
import com.weily.alumnibook.classs.Classmates;
import com.weily.alumnibook.classs.Response;
import com.weily.alumnibook.classs.ShowList;
import com.weily.alumnibook.classs.Teacher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SendActivity extends AppCompatActivity implements ActivityMethod
{
    private static final String TAG = "SendActivity";
    private static final int INTENT = 2333;
    private Toolbar toolbar;
    private Button btn_choose_user;
    private TextInputLayout textInputLayout_activity_name;
    private TextInputLayout textInputLayout_activity_content;
    private TextView textView_time;
    private TextView textView_choose;
    private Button btn_choose_time;
    private String date = "";
    private String studentList = "";
    private String teacherList = "";
    private ArrayList<Classmates> classmatesArrayList = new ArrayList<>();
    private ArrayList<Teacher> teacherArrayList = new ArrayList<>();

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
        setContentView(R.layout.activity_send_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_choose_user = (Button) findViewById(R.id.choose_people);
        textInputLayout_activity_name = (TextInputLayout) findViewById(R.id.activity_name);
        textInputLayout_activity_content = (TextInputLayout) findViewById(R.id.activity_content);
        textView_time = (TextView) findViewById(R.id.show_time);
        textView_choose = (TextView) findViewById(R.id.checkList);
        btn_choose_time = (Button) findViewById(R.id.choose_time);

        if (getIntent().getBundleExtra("activity") != null)
        {
            ActivityShow activityShow = (ActivityShow) getIntent().getBundleExtra("activity").getSerializable("activity");
            date = activityShow.getActivityDate();
            studentList = activityShow.getStudentList();
            teacherList = activityShow.getTeacherList();
            textView_time.setText("时间：" + date);
            textInputLayout_activity_name.getEditText().setText(activityShow.getActivityName());
            textInputLayout_activity_content.getEditText().setText(activityShow.getActivityContent());
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
                                Logs.i(TAG, "onResponse: "+s);
                                ShowList showList = new Gson().fromJson(s, ShowList.class);
                                classmatesArrayList.clear();
                                teacherArrayList.clear();
                                String[] checkedClassmates = studentList.split("!");
                                String[] checkedTeacher = teacherList.split("!");
                                for (int n = 0; n < showList.getClassmates().length; n++)
                                {
                                    for (String name : checkedClassmates)
                                    {
                                        if (!name.equals("") && showList.getClassmates()[n].getName().equals(name))
                                        {
                                            classmatesArrayList.add(showList.getClassmates()[n]);
                                        }
                                    }
                                }
                                for (int n = 0; n < showList.getTeachers().length; n++)
                                {
                                    for (String name : checkedTeacher)
                                    {
                                        if (!name.equals("") && showList.getTeachers()[n].getName().equals(name))
                                        {
                                            teacherArrayList.add(showList.getTeachers()[n]);
                                        }
                                    }
                                }
                                StringBuilder temp = new StringBuilder("参与人员\n");
                                for (Classmates t : classmatesArrayList)
                                {
                                    temp.append(t.getName()).append("\n");
                                }
                                for (Teacher t : teacherArrayList)
                                {
                                    temp.append(t.getName()).append("\n");
                                }
                                textView_choose.setText(temp);
                            }
                        }
                    })
                    .open();
        }

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

        btn_choose_user.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(App.getContext(), ChoosePeopleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("student", classmatesArrayList);
                bundle.putSerializable("teacher", teacherArrayList);
                intent.putExtra("list", bundle);
                startActivityForResult(intent, INTENT);
            }
        });

        btn_choose_time.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v)
            {
                String[] dates = date.split("-");
                if (dates.length == 3)
                {
                    if (dates[0].matches("^[-\\\\+]?[\\\\d]*$") &&
                            dates[1].matches("^[-\\\\+]?[\\\\d]*$") &&
                            dates[2].matches("^[-\\\\+]?[\\\\d]*$") &&
                            Integer.parseInt(dates[1]) < 13 &&
                            Integer.parseInt(dates[1]) > 0 &&
                            Integer.parseInt(dates[2]) > 0 &&
                            Integer.parseInt(dates[2]) < 32)
                    {
                        date = dates[0] + "-" + dates[1] + "-" + dates[2];
                    }
                } else
                {
                    Calendar calendar = Calendar.getInstance();
                    dates = new String[]{String.valueOf(calendar.get(Calendar.YEAR)), String.valueOf(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.DATE))};
                }
                final DatePicker datePicker = new DatePicker(SendActivity.this,null,R.style.hmp);
                datePicker.init(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]), null);
                new AlertDialog.Builder(SendActivity.this)
                        .setView(datePicker)
                        .setTitle("请选择时间：")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                date = datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth();
                                textView_time.setText("时间：" + date);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_send_activity, menu);
        return true;
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_send:
                if (!textInputLayout_activity_name.getEditText().getText().toString().equals(""))
                {
                    Map<String, String> map = new HashMap<>();
                    map.put("userType", "activity");
                    map.put("method", "updateActivity");
                    map.put("username", getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).getString("username", "test"));
                    map.put("activityName", textInputLayout_activity_name.getEditText().getText().toString());
                    map.put("activityContent", textInputLayout_activity_content.getEditText().getText().toString());
                    map.put("activityDate", date);
                    map.put("studentList", studentList);
                    map.put("teacherList", teacherList);
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
                                        if (response.getCode() == 0)
                                        {
                                            finish();
                                        }
                                    }
                                }
                            })
                            .open();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == INTENT && resultCode == RESULT_OK)
        {
            List studentList = (List) data.getBundleExtra("list").getSerializable("student");
            List teacherList = (List) data.getBundleExtra("list").getSerializable("teacher");
            classmatesArrayList.clear();
            teacherArrayList.clear();
            classmatesArrayList.addAll(studentList);
            teacherArrayList.addAll(teacherList);
            StringBuilder temp = new StringBuilder("参与人员\n");
            this.studentList = "";
            this.teacherList = "";
            for (Classmates t : classmatesArrayList)
            {
                temp.append(t.getName()).append("\n");
                this.studentList += t.getName() + "!";
            }
            for (Teacher t : teacherArrayList)
            {
                temp.append(t.getName()).append("\n");
                this.teacherList += t.getName() + "!";
            }
            textView_choose.setText(temp);
        }
    }
}