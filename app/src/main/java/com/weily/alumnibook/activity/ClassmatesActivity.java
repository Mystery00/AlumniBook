package com.weily.alumnibook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.PhoneEmailAdapter;
import com.weily.alumnibook.adapter.PhotoAdapter;
import com.weily.alumnibook.classs.Classmates;
import com.weily.alumnibook.classs.Email;
import com.weily.alumnibook.classs.Phone;

import java.util.ArrayList;
import java.util.List;

public class ClassmatesActivity extends AppCompatActivity implements ActivityMethod
{
    private Classmates classmates;
    private TextInputLayout name;
    private TextInputLayout birthday;
    private TextInputLayout address;
    private TextInputLayout fRelationship;
    private TextInputLayout relationshipWithMe;
    private RadioGroup sex;
    private TextInputLayout ps;
    private TextInputLayout scandal;

    private RecyclerView phoneRecycler;
    private RecyclerView emailRecycler;
    private RecyclerView photoRecycler;

    private RadioButton manChoose;
    private RadioButton womanChoose;

    private Button addTel;
    private Button addEmail;

    private Phone phone;
    private Email email;

    private List<String> phoneList;
    private List<String> emailList;
    private List<String> photoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initialization();
        monitor();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void initialization()
    {
        Bundle bundle = getIntent().getBundleExtra("classmates");
        phoneList = new ArrayList<>();
        emailList = new ArrayList<>();
        photoList = new ArrayList<>();
        if (bundle != null)
        {
            classmates = (Classmates) bundle.getSerializable("classmates");
            phoneList.add("123465798");
            phoneList.add("64579123");
            emailList.add("myas@qq.com");
            emailList.add("123@qq.com");
            photoList.add("http://ww2.sinaimg.cn/orj480/76da98c1gw1f5yhzht65hj20qo1bfgul.jpg");
            photoList.add("http://ww2.sinaimg.cn/orj480/76da98c1gw1f5yhzht65hj20qo1bfgul.jpg");
            photoList.add("http://i0.hdslb.com/bfs/archive/7c83ebda27b6e2c6fc6670f08aec28bd224da69c.jpg");
            photoList.add("http://p1.music.126.net/fUVCts6yu0k2QkXjVAfsjw==/18771962022655227.jpg");
        } else
        {
            classmates = new Classmates();
        }
        setContentView(R.layout.activity_classmates);

        name = (TextInputLayout) findViewById(R.id.name_edit);
        birthday = (TextInputLayout) findViewById(R.id.birthday_edit);
        address = (TextInputLayout) findViewById(R.id.add_edit);
        fRelationship = (TextInputLayout) findViewById(R.id.fam_re_edit);
        relationshipWithMe = (TextInputLayout) findViewById(R.id.relation_edit);
        scandal = (TextInputLayout) findViewById(R.id.scandal_edit);
        sex = (RadioGroup) findViewById(R.id.sex_choose);
        phoneRecycler = (RecyclerView) findViewById(R.id.tel_recycler);
        emailRecycler = (RecyclerView) findViewById(R.id.email_recycler);
        photoRecycler = (RecyclerView) findViewById(R.id.photo_recycler);
        ps = (TextInputLayout) findViewById(R.id.other_edit);
        manChoose = (RadioButton) findViewById(R.id.sex_choose_man);
        womanChoose = (RadioButton) findViewById(R.id.sex_choose_woman);

        addTel = (Button) findViewById(R.id.tel_add);
        addEmail = (Button) findViewById(R.id.email_add);

        name.getEditText().setText(classmates.getName());
        birthday.getEditText().setText(classmates.getBirthday());
        address.getEditText().setText(classmates.getAddress());
        fRelationship.getEditText().setText(classmates.getHome());
        relationshipWithMe.getEditText().setText(classmates.getWithMe());
        scandal.getEditText().setText(classmates.getEmbarrassing());
        ps.getEditText().setText(classmates.getRemark());
        switch (classmates.getSex())
        {
            case 0://男
                sex.check(R.id.sex_choose_man);
                break;
            case 1://女
                sex.check(R.id.sex_choose_woman);
                break;
        }
        PhoneEmailAdapter phoneAdapter = new PhoneEmailAdapter(phoneList);
        PhoneEmailAdapter emailAdapter = new PhoneEmailAdapter(emailList);
        PhotoAdapter photoAdapter = new PhotoAdapter(photoList);

        phoneRecycler.setLayoutManager(new LinearLayoutManager(App.getContext()));
        phoneRecycler.setAdapter(phoneAdapter);

        emailRecycler.setLayoutManager(new LinearLayoutManager(App.getContext()));
        emailRecycler.setAdapter(emailAdapter);

        photoRecycler.setLayoutManager(new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false));
        photoRecycler.setAdapter(photoAdapter);
    }

    @Override
    public void monitor()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_classmates, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}