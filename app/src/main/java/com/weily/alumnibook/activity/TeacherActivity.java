package com.weily.alumnibook.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.mystery0.tools.PictureChooser.iPictureChooser;
import com.mystery0.tools.PictureChooser.iPictureChooserListener;
import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.PhoneEmailAdapter;
import com.weily.alumnibook.classs.FileList;
import com.weily.alumnibook.classs.MyFile;
import com.weily.alumnibook.classs.Response;
import com.weily.alumnibook.classs.Teacher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@SuppressWarnings("ConstantConditions")
public class TeacherActivity extends AppCompatActivity implements ActivityMethod
{
    private static final String TAG = "ClassmatesActivity";
    private static final int HANDLER = 233;
    private Toolbar toolbar;
    private TextInputLayout name;
    private TextView birthday;
    private TextInputLayout class_edit;
    private TextInputLayout address;
    private RadioGroup sex;
    private TextInputLayout ps;
    private TextInputLayout phone_edit;
    private TextInputLayout email_edit;
    private iPictureChooser pictureChooser;
    private ImageButton addTel;
    private ImageButton addEmail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppCompatSpinner spinner;
    private List<String> phoneList;
    private List<String> emailList;
    private List<String> photoList;
    private PhoneEmailAdapter phoneAdapter;
    private PhoneEmailAdapter emailAdapter;
    private Menu menu;
    private boolean isNew = true;
    private String date = "";
    private ProgressDialog progressDialog1;
    private HttpUtil httpUtil = new HttpUtil(App.getContext());

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == HANDLER)
            {
                Response response = (Response) msg.obj;
                Toast.makeText(App.getContext(), response.getContent(), Toast.LENGTH_SHORT)
                        .show();
                progressDialog1.dismiss();
                if (msg.arg1 != 0)
                {
                    progressDialog1.show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initialization();
        monitor();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void initialization()
    {
        Bundle bundle = getIntent().getBundleExtra("teacher");
        phoneList = new ArrayList<>();
        emailList = new ArrayList<>();
        photoList = new ArrayList<>();
        Teacher teacher;
        if (bundle != null)
        {
            isNew = false;
            teacher = (Teacher) bundle.getSerializable("teacher");
            for (String temp : teacher.getPhoneList().split("!"))
            {
                if (!temp.equals(""))
                    phoneList.add(temp);
            }
            for (String temp : teacher.getEmailList().split("!"))
            {
                if (!temp.equals(""))
                    emailList.add(temp);
            }
            date = teacher.getBirthday();
            Map<String, String> map = new HashMap<>();
            map.put("username", getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).getString("username", "test"));
            map.put("type", "teacher");
            map.put("method", "getFile");
            map.put("userType", "user");
            map.put("name", teacher.getName());
            httpUtil.setRequestMethod(HttpUtil.RequestMethod.POST)
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
                                FileList fileList = new Gson().fromJson(s, FileList.class);
                                for (MyFile temp : fileList.getFiles())
                                {
                                    photoList.add("http://123.206.186.70/php/alumnibook/uploads/" + temp.getFileName());
                                }
                                pictureChooser.setList(photoList);
                            }
                        }
                    })
                    .open();
        } else
        {
            teacher = new Teacher();
        }
        setContentView(R.layout.activity_teacher);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        name = (TextInputLayout) findViewById(R.id.name_edit);
        class_edit = (TextInputLayout) findViewById(R.id.class_edit);
        TextInputLayout number_edit = (TextInputLayout) findViewById(R.id.number_edit);
        TextInputLayout work_edit = (TextInputLayout) findViewById(R.id.work_edit);
        birthday = (TextView) findViewById(R.id.birthday_edit);
        address = (TextInputLayout) findViewById(R.id.add_edit);
        TextInputLayout fRelationship = (TextInputLayout) findViewById(R.id.fam_re_edit);
        TextInputLayout relationshipWithMe = (TextInputLayout) findViewById(R.id.relation_edit);
        TextInputLayout scandal = (TextInputLayout) findViewById(R.id.scandal_edit);
        sex = (RadioGroup) findViewById(R.id.sex_choose);
        RecyclerView phoneRecycler = (RecyclerView) findViewById(R.id.tel_recycler);
        RecyclerView emailRecycler = (RecyclerView) findViewById(R.id.email_recycler);
        phone_edit = (TextInputLayout) findViewById(R.id.tel_edit);
        email_edit = (TextInputLayout) findViewById(R.id.email_edit);
        pictureChooser = (iPictureChooser) findViewById(R.id.picture_chooser);
        ps = (TextInputLayout) findViewById(R.id.other_edit);
        spinner = (AppCompatSpinner) findViewById(R.id.spinner);

        addTel = (ImageButton) findViewById(R.id.tel_add);
        addEmail = (ImageButton) findViewById(R.id.email_add);

        name.getEditText().setText(teacher.getName());
        class_edit.getEditText().setText(teacher.getSubject());
        class_edit.setHint("科目");
        number_edit.setVisibility(View.GONE);
        work_edit.setVisibility(View.GONE);
        birthday.setText("生日：" + date);
        address.getEditText().setText(teacher.getAddress());
        fRelationship.setVisibility(View.GONE);
        relationshipWithMe.setVisibility(View.GONE);
        scandal.setVisibility(View.GONE);
        ps.getEditText().setText(teacher.getRemark());
        switch (teacher.getSex())
        {
            case 0://男
                sex.check(R.id.sex_choose_man);
                break;
            case 1://女
                sex.check(R.id.sex_choose_woman);
                break;
        }
        spinner.setSelection(teacher.getType() - 1);
        phoneAdapter = new PhoneEmailAdapter(phoneList);
        emailAdapter = new PhoneEmailAdapter(emailList);

        phoneRecycler.setLayoutManager(new LinearLayoutManager(App.getContext()));
        phoneRecycler.setAdapter(phoneAdapter);

        emailRecycler.setLayoutManager(new LinearLayoutManager(App.getContext()));
        emailRecycler.setAdapter(emailAdapter);

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        setSupportActionBar(toolbar);
    }

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
        pictureChooser.setDataList(R.drawable.ic_picture_chooser, new iPictureChooserListener()
        {
            @Override
            public void MainClick()
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, iPictureChooser.REQUEST_IMG_CHOOSE);
            }
        });
        pictureChooser.setList(photoList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        addTel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String edit = phone_edit.getEditText().getText().toString();
                if (!edit.equals(""))
                {
                    phoneList.add(edit);
                    phoneAdapter.notifyItemInserted(phoneList.size() - 1);
                }
            }
        });
        addEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String edit = email_edit.getEditText().getText().toString();
                if (!edit.equals(""))
                {
                    emailList.add(edit);
                    emailAdapter.notifyItemInserted(emailList.size() - 1);
                }
            }
        });
        birthday.setOnClickListener(new View.OnClickListener()
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
                final DatePicker datePicker = new DatePicker(TeacherActivity.this,null,R.style.hmp);
                datePicker.init(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]), null);
                new AlertDialog.Builder(TeacherActivity.this)
                        .setView(datePicker)
                        .setTitle("请选择时间：")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                date = datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth();
                                birthday.setText("生日：" + date);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_classmates, menu);
        this.menu = menu;
        menu.findItem(R.id.done).setVisible(isNew);
        menu.findItem(R.id.edit).setVisible(!isNew);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.done:
                final ProgressDialog progressDialog = new ProgressDialog(TeacherActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("数据上传中……");
                progressDialog.show();
                Map<String, String> map = new HashMap<>();
                map.put("userType", "teacher");
                map.put("username", getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).getString("username", "test"));
                map.put("method", "updateTeacher");
                map.put("subject", class_edit.getEditText().getText().toString());
                map.put("name", name.getEditText().getText().toString());
                map.put("sex", sex.getCheckedRadioButtonId() == R.id.sex_choose_man ? "0" : "1");
                map.put("birthday", date);
                map.put("address", address.getEditText().getText().toString());
                map.put("remark", ps.getEditText().getText().toString());
                map.put("type", String.valueOf(spinner.getSelectedItemPosition() + 1));
                StringBuilder phoneListString = new StringBuilder();
                for (String temp : phoneList)
                {
                    phoneListString.append(temp).append("!");
                }
                map.put("phoneList", phoneListString.toString());
                StringBuilder emailListString = new StringBuilder();
                for (String temp : emailList)
                {
                    emailListString.append(temp).append("!");
                }
                map.put("emailList", emailListString.toString());
                new HttpUtil(App.getContext())
                        .setUrl(getString(R.string.request_url))
                        .setRequestMethod(HttpUtil.RequestMethod.POST)
                        .setMap(map)
                        .setResponseListener(new ResponseListener()
                        {
                            @Override
                            public void onResponse(int i, String s)
                            {
                                progressDialog.dismiss();
                                if (i == 1)
                                {
                                    Logs.i(TAG, "onResponse: " + s);
                                    Response response = new Gson().fromJson(s, Response.class);
                                    Toast.makeText(App.getContext(), response.getContent(), Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        })
                        .open();
                if (pictureChooser.getList().size() != 0)
                {
                    progressDialog1 = new ProgressDialog(TeacherActivity.this);
                    progressDialog1.setCancelable(false);
                    progressDialog1.setMessage("数据上传中……");
                    progressDialog1.show();
                }
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        final List<String> pathList = pictureChooser.getList();
                        for (int i = 0; i < pathList.size(); i++)
                        {
                            if (pathList.get(i).substring(0, 4).equals("http"))
                            {
                                if (i == pathList.size() - 1)
                                {
                                    progressDialog1.dismiss();
                                }
                                continue;
                            }
                            File file = new File(pathList.get(i));
                            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                            String fileName = file.getName();
                            RequestBody requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("username", getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).getString("username", "test"))
                                    .addFormDataPart("userType", "user")
                                    .addFormDataPart("type", "teacher")
                                    .addFormDataPart("method", "uploadFile")
                                    .addFormDataPart("name", name.getEditText().getText().toString())
                                    .addFormDataPart("upload_file", fileName, fileBody)
                                    .build();
                            Request request = new Request.Builder()
                                    .url(getString(R.string.request_url))
                                    .post(requestBody)
                                    .build();
                            OkHttpClient okHttpClient = new OkHttpClient();
                            Call call = okHttpClient.newCall(request);
                            final int finalI = i;
                            call.enqueue(new Callback()
                            {
                                @Override
                                public void onFailure(Call call, IOException e)
                                {
                                    Message message = new Message();
                                    message.what = HANDLER;
                                    message.arg1 = 0;
                                    message.obj = new Response(404, "error");
                                    handler.sendMessage(message);
                                    Logs.e(TAG, "onFailure: error");
                                }

                                @Override
                                public void onResponse(Call call, okhttp3.Response response) throws IOException
                                {
                                    String s = response.body().string();
                                    Logs.i(TAG, "onResponse: " + s);
                                    Message message = new Message();
                                    try
                                    {
                                        message.obj = new Gson().fromJson(s, Response.class);
                                    } catch (Exception e)
                                    {
                                        message.obj = new Response(404, "文件错误！");
                                    }
                                    message.what = HANDLER;
                                    if (finalI == pathList.size() - 1)
                                    {
                                        message.arg1 = 0;
                                    }
                                    handler.sendMessage(message);
                                }
                            });
                        }
                    }
                }).start();
                menu.findItem(R.id.edit).setVisible(true);
                menu.findItem(R.id.done).setVisible(false);
                break;
            case R.id.edit:
                menu.findItem(R.id.edit).setVisible(false);
                menu.findItem(R.id.done).setVisible(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == iPictureChooser.REQUEST_IMG_CHOOSE)
        {
            if (data != null)
            {
                pictureChooser.setUpdatedPicture(data.getData());
            }
        }
    }
}