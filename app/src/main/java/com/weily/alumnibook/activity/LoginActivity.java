package com.weily.alumnibook.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.weily.alumnibook.R;
import com.weily.alumnibook.util.HttpUtil;
import com.weily.alumnibook.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private  String responseText;
    private String result[];
    private ProgressDialog progressDialog;
    private String loginUserName, loginPassowrd;
    private EditText lun,lpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lun= (EditText) findViewById(R.id.username_login);
        lpw= (EditText) findViewById(R.id.password_login);
        Button login = (Button) findViewById(R.id.bt_login);
        TextView register = (TextView) findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                loginUserName = lun.getText().toString();
                loginPassowrd = lpw.getText().toString();
                if (loginUserName.equals("") || loginPassowrd.equals("")) {
                    Snackbar.make(view, "账号或密码不能为空", Snackbar.LENGTH_SHORT).show();
                } else {
                    //提交登录信息
                    postLoginInfo();

                }
                break;
            case R.id.register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }

    private void postLoginInfo(){
        showProgressDialog();
        loginUserName = lun.getText().toString();
        loginPassowrd = lpw.getText().toString();
        HttpUtil.sendInfo("http://123.206.186.70/php/alumnibook/login.php", loginUserName,loginPassowrd,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                closeProgressDialog();
                responseText=response.body().string();
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //解析返回信息,dayin
                        Utility.handleLoginReturnInfo(responseText);

                        Log.i("------", Utility.code2);
                        //判断执行下一步动作
                        if(Utility.code2.equals("1")){
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                });





            }
        });
    }
    private void handleLoginReturnInfo(){
        //解析返回信息
    }
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("正在登录");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}