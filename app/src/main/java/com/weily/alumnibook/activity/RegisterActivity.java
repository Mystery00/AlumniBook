package com.weily.alumnibook.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.weily.alumnibook.R;

public class RegisterActivity extends AppCompatActivity {
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
                    Snackbar.make(view, "请填写完整信息", Snackbar.LENGTH_SHORT).show();
                }else if(!registerPassword1.equals(registerPassword2)){
                    Snackbar.make(view, "密码输入不一致", Snackbar.LENGTH_SHORT).show();
                }else{
                    postRegisterInfo();
                    Snackbar.make(view, "注册成功", Snackbar.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                }
            }
        });
    }
    private  void postRegisterInfo(){
        //提交住蹙额信息
    }
}
