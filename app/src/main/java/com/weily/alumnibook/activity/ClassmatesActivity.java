package com.weily.alumnibook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.weily.alumnibook.ActivityMethod;
import com.weily.alumnibook.R;

public class ClassmatesActivity extends AppCompatActivity implements ActivityMethod
{
    private TextInputLayout name;
    private TextInputLayout birthday;
    private TextInputLayout address;
    private TextInputLayout fRelationship;
    private TextInputLayout relationshipWithMe;
    private RadioGroup sex;
    private TextInputLayout ps;
    private TextInputLayout scandal;
    private RecyclerView telephoneList;
    private RecyclerView emailList;
    private RadioButton manChoose;
    private RadioButton womanChoose;

    private Button addTel;
    private Button addEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initialization();
        monitor();
    }

    @Override
    public void initialization()
    {
        setContentView(R.layout.activity_classmates);

        name = (TextInputLayout) findViewById(R.id.name_edit);
        birthday = (TextInputLayout) findViewById(R.id.birthday_edit);
        address = (TextInputLayout) findViewById(R.id.add_edit);
        fRelationship = (TextInputLayout) findViewById(R.id.fam_re_edit);
        relationshipWithMe = (TextInputLayout) findViewById(R.id.relation_edit);
        scandal = (TextInputLayout) findViewById(R.id.scandal_edit);
        sex = (RadioGroup) findViewById(R.id.sex_choose);
        telephoneList = (RecyclerView) findViewById(R.id.tel_recycler);
        emailList = (RecyclerView) findViewById(R.id.email_recycler);
        ps = (TextInputLayout) findViewById(R.id.other_edit);
        manChoose = (RadioButton) findViewById(R.id.sex_choose_man);
        womanChoose = (RadioButton) findViewById(R.id.sex_choose_woman);

        addTel = (Button) findViewById(R.id.tel_add);
        addEmail = (Button) findViewById(R.id.email_add);
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