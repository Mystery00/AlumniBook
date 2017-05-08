package com.weily.alumnibook.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.classs.Response;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
{
    private ProgressDialog progressDialog;
    private String registerName, registerPassword1, registerPassword2;
    private EditText run, rpw1, rpw2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = (Button) findViewById(R.id.bt_register);
        run = (EditText) findViewById(R.id.username_regster);
        rpw1 = (EditText) findViewById(R.id.password_register);
        rpw2 = (EditText) findViewById(R.id.password_register_confirm);
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                registerName = run.getText().toString();
                registerPassword1 = rpw1.getText().toString();
                registerPassword2 = rpw2.getText().toString();
                if (registerPassword1.equals("") || registerPassword2.equals("") || registerName.equals(""))
                {
                    Snackbar.make(view, "请填写完整信息！", Snackbar.LENGTH_SHORT).show();
                } else if (!registerPassword1.equals(registerPassword2))
                {
                    Snackbar.make(view, "密码输入不一致！", Snackbar.LENGTH_SHORT).show();
                } else
                {
                    postRegisterInfo();
                }
            }
        });
    }

    private void postRegisterInfo()
    {
        showProgressDialog();
        registerName = run.getText().toString();
        registerPassword1 = rpw1.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("userType", "user");
        map.put("method", "sign_up");
        map.put("username", registerName);
        map.put("password", registerPassword1);
        new HttpUtil(App.getContext())
                .setUrl(getString(R.string.request_url))
                .setRequestMethod(HttpUtil.RequestMethod.POST)
                .setMap(map)
                .setResponseListener(new ResponseListener()
                {
                    @Override
                    public void onResponse(int i, String s)
                    {
                        closeProgressDialog();
                        if (i == 1)
                        {
                            Response response = new Gson().fromJson(s, Response.class);
                            if (response.getCode() == 0)
                            {
                                Intent intent = new Intent();
                                intent.putExtra("username", registerName);
                                intent.putExtra("password", registerPassword1);
                                setResult(RESULT_OK, intent);
                                finish();
                            } else
                            {
                                Toast.makeText(App.getContext(), response.getContent(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        } else
                        {
                            Toast.makeText(App.getContext(), "请求失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                })
                .open();
    }

    private void showProgressDialog()
    {
        if (progressDialog == null)
        {
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("请稍后...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog()
    {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

}