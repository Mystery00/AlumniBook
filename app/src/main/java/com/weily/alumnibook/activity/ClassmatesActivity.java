package com.weily.alumnibook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.weily.alumnibook.R;

import java.util.ArrayList;

public class ClassmatesActivity extends AppCompatActivity {
    private EditText name;
    private EditText birthday;
    private EditText address;
    private EditText fRelationship;
    private EditText relationshipWithMe;
    private EditText telInput;
    private RadioGroup sex;
    private DatePicker datePicker;
    private EditText emailAddress;
    private EditText ps;
    private EditText scandal;
    private RecyclerView telephoneList;
    private RecyclerView emailList;
    private RadioButton manChoose;
    private RadioButton womanChoose;
    private RelativeLayout birthdayChoose;

    private Menu menu;
    private int isEdit = 1;


    private Button addTel;
    private Button addEmail;
    private Button chooseDate;

    private ArrayList<EditText> editTextList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classmates);

        name = (EditText) findViewById(R.id.name_edit);
        birthday = (EditText) findViewById(R.id.birthday_edit);
        address = (EditText) findViewById(R.id.add_edit);
        fRelationship = (EditText) findViewById(R.id.fam_re_edit);
        relationshipWithMe = (EditText) findViewById(R.id.relation_edit);
        telInput = (EditText) findViewById(R.id.tel_edit);
        emailAddress = (EditText) findViewById(R.id.email_edit);
        scandal = (EditText) findViewById(R.id.scandal_edit);
        sex = (RadioGroup) findViewById(R.id.sex_choose);
        telephoneList = (RecyclerView) findViewById(R.id.tel_recycler);
        emailList = (RecyclerView) findViewById(R.id.email_recycler);
        ps = (EditText) findViewById(R.id.other_edit);
        manChoose = (RadioButton) findViewById(R.id.sex_choose_man);
        womanChoose = (RadioButton) findViewById(R.id.sex_choose_woman);
        birthdayChoose= (RelativeLayout) findViewById(R.id.birthday_choose);
        chooseDate= (Button) findViewById(R.id.data_ok);


        addTel = (Button) findViewById(R.id.tel_add);
        addEmail = (Button) findViewById(R.id.email_add);


        editTextList.add(name);
        editTextList.add(birthday);
        editTextList.add(address);
        editTextList.add(fRelationship);
        editTextList.add(relationshipWithMe);
        editTextList.add(telInput);
        editTextList.add(emailAddress);
        editTextList.add(scandal);
        editTextList.add(ps);
        datePicker= (DatePicker) findViewById(R.id.data_picker);

        initial();

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthdayChoose.setVisibility(View.VISIBLE);
            }
        });
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthday.setText(datePicker.getYear()+"/"+datePicker.getMonth()+"/"+datePicker.getDayOfMonth());
                birthdayChoose.setVisibility(View.GONE);
            }
        });

    }


    public void initial() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("position");
        if (!data.isEmpty()) {
            for (EditText e : editTextList) {
                e.setEnabled(false);
                e.setCursorVisible(true);
            }
            sex.setEnabled(false);
            addTel.setEnabled(false);
            addEmail.setEnabled(false);
            womanChoose.setEnabled(false);
            manChoose.setEnabled(false);
        } else {
            for (EditText e : editTextList) {
                e.setCursorVisible(true);
                e.setEnabled(true);
                e.setText("");
            }
            sex.setEnabled(true);
            addTel.setEnabled(true);
            addEmail.setEnabled(true);
            womanChoose.setEnabled(true);
            manChoose.setEnabled(true);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_classmates, menu);
        this.menu = menu;
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.edit) {
            if (isEdit == 1) {

                menu.findItem(R.id.picture).setVisible(false);
                for (EditText e : editTextList) {
                    e.setEnabled(true);
                    e.setCursorVisible(true);

                }
                name.requestFocus();
                sex.setEnabled(true);
                addTel.setEnabled(true);
                addEmail.setEnabled(true);
                womanChoose.setEnabled(true);
                manChoose.setEnabled(true);
                //item.setIcon(R.mipmap.success);
                isEdit = 0;
            } else if (isEdit == 0) {
                menu.findItem(R.id.picture).setVisible(true);
                for (EditText e : editTextList) {
                    e.setEnabled(false);
                    e.setCursorVisible(false);
                }
                sex.setEnabled(false);
                addTel.setEnabled(false);
                addEmail.setEnabled(false);
                womanChoose.setEnabled(false);
                manChoose.setEnabled(false);
                //item.setIcon(R.mipmap.success);
                isEdit = 1;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}