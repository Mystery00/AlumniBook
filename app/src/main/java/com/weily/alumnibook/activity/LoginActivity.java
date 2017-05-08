package com.weily.alumnibook.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.classs.Response;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int ACTIVITY_CODE = 4589;
    private ProgressDialog progressDialog;
    private String loginUserName, loginPassowrd;
    private EditText lun, lpw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lun = (EditText) findViewById(R.id.username_login);
        lpw = (EditText) findViewById(R.id.password_login);
        Button login = (Button) findViewById(R.id.bt_login);
        TextView register = (TextView) findViewById(R.id.register);
        setPassLoginInfo(null);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bt_login:
                loginUserName = lun.getText().toString();
                loginPassowrd = lpw.getText().toString();
                if (loginUserName.equals("") || loginPassowrd.equals(""))
                {
                    Snackbar.make(view, "账号或密码不能为空!", Snackbar.LENGTH_SHORT).show();
                } else
                {
                    //登录处理
                    postLoginInfo();
                }
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, ACTIVITY_CODE);
                break;
            default:
        }
    }

    private void postLoginInfo()
    {
        showProgressDialog();
        loginUserName = lun.getText().toString();
        loginPassowrd = lpw.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("userType", "user");
        map.put("method", "sign_in");
        map.put("username", loginUserName);
        map.put("password", loginPassowrd);
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
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
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

    //注册成功后跳转过来的登录信息
    private void setPassLoginInfo(Intent intent)
    {
        String username = intent == null ? "" : intent.getStringExtra("username");
        String password = intent == null ? "" : intent.getStringExtra("password");
        lun.setText(username);
        lpw.setText(password);
    }

    private void showProgressDialog()
    {
        if (progressDialog == null)
        {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("正在登录...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog()
    {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == ACTIVITY_CODE)
        {
            switch (resultCode)
            {
                case RESULT_OK:
                    setPassLoginInfo(data);
                    break;
                default:
                    break;
            }
        }
    }
}