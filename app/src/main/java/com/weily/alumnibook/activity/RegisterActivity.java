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

import com.weily.alumnibook.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.Utility;

public class RegisterActivity extends AppCompatActivity {
    private String responseText;
    private ProgressDialog progressDialog;
    private Button registerButton;
    private String registerName,registerPassword1,registerPassword2;
    private EditText run,rpw1,rpw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton= (Button) findViewById(R.id.bt_register);
        run= (EditText) findViewById(R.id.username_regster);
        rpw1= (EditText) findViewById(R.id.password_register);
        rpw2= (EditText) findViewById(R.id.password_register_confirm);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerName=run.getText().toString();
                registerPassword1=rpw1.getText().toString();
                registerPassword2=rpw2.getText().toString();
                if(registerPassword1.equals("")||registerPassword2.equals("")||registerName.equals("")){
                    Snackbar.make(view, "请填写完整信息！", Snackbar.LENGTH_SHORT).show();
                }else if(!registerPassword1.equals(registerPassword2)){
                    Snackbar.make(view, "密码输入不一致！", Snackbar.LENGTH_SHORT).show();
                }else{
                    postRegisterInfo();
                }
            }
        });
    }
    private  void postRegisterInfo(){
        showProgressDialog();
        registerName=run.getText().toString();
        registerPassword1=rpw1.getText().toString();
        HttpUtil.sendInfo("http://www.mystery0.vip/php/alumnibook/alumnibook.php", registerName, registerPassword1, "sign_up",new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(RegisterActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseText=response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Utility.handleLoginReturnInfo(responseText);
                        if(Utility.resultCode.equals("0")){
                            Toast.makeText(RegisterActivity.this,Utility.resultContent,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            intent.putExtra("username",registerName);
                            intent.putExtra("password",registerPassword1);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, Utility.resultContent, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("请稍后...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

}