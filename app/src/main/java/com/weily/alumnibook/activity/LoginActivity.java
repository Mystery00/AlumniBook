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

import com.weily.alumnibook.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.Utility;

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
        setPassLoginInfo();
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
                    Snackbar.make(view, "账号或密码不能为空!", Snackbar.LENGTH_SHORT).show();
                } else {
                    //登录处理
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
        HttpUtil.sendInfo("http://www.mystery0.vip/php/alumnibook/alumnibook.php", loginUserName,loginPassowrd,"sign_in",new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(LoginActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到返回数据
                responseText=response.body().string();
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();//此处涉及到更新UI，切回主线程
                        //解析返回数据
                        Utility.handleLoginReturnInfo(responseText);
                        //判断执行下一步动作
                        if(Utility.resultCode.equals("0")){
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,Utility.resultContent,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    //注册成功后跳转过来的登录信息
    private void setPassLoginInfo(){
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        String password=intent.getStringExtra("password");
        lun.setText(username);
        lpw.setText(password);
    }
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("正在登录...");
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