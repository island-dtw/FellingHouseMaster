package com.dtw.fellinghousemaster.View.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dtw.fellinghousemaster.Bean.UserInfoBean;
import com.dtw.fellinghousemaster.Config;
import com.dtw.fellinghousemaster.Presener.LoginPresener;
import com.dtw.fellinghousemaster.R;
import com.dtw.fellinghousemaster.View.BaseActivity;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class LoginActivity extends BaseActivity implements LoginView{
    private EditText phoneNum;
    private EditText password;
    private Button loginBtn;

    private LoginPresener loginPresener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phoneNum= (EditText) findViewById(R.id.edit_phone_num);
        password= (EditText) findViewById(R.id.edit_password);
        loginBtn= (Button) findViewById(R.id.btn_login);

        loginPresener=new LoginPresener(this,this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                loginPresener.login(phoneNum.getText().toString(),password.getText().toString());
                break;
//            case R.id.text_regist:
//                Intent regist=new Intent(this, RegistActivity.class);
//                regist.putExtra(Config.Key_PhnoeNum,phoneNum.getText().toString());
//                startActivityForResult(regist,Config.Request_Code_Regist);
//                break;
//            case R.id.btn_smscode_login:
//                Intent smscodeLogin=new Intent(this, SmsCodeLoginActivity.class);
//                smscodeLogin.putExtra(Config.Key_PhnoeNum,phoneNum.getText().toString());
//                startActivityForResult(smscodeLogin,Config.Request_Code_SmsCodeLogin);
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case Config.Request_Code_Regist:
                if(requestCode==RESULT_OK){

                }
                break;
            case Config.Request_Code_SmsCodeLogin:
                if(requestCode==RESULT_OK){

                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresener.onViewDestory();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBack(UserInfoBean userInfoBean) {
        finish();
    }
}
