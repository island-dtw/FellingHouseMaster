package com.dtw.fellinghousemaster.Presener;

import android.content.Context;
import android.os.Handler;

import com.dtw.fellinghousemaster.Bean.UserInfoBean;
import com.dtw.fellinghousemaster.Model.JMessageModel;
import com.dtw.fellinghousemaster.View.Login.LoginView;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class LoginPresener{
    private Handler handler=new Handler();
    private Context context;
    private LoginView loginView;
    private JMessageModel jMessageModel;
    public LoginPresener(Context context,LoginView loginView){
        this.context=context;
        this.loginView=loginView;
        jMessageModel=JMessageModel.getInstance(context);
    }
    public void login(String phoneNum,String password){
        jMessageModel.login(phoneNum, password, new JMessageModel.BaseCallBack() {
            @Override
            public void onResult(int code, String msg) {
                switch (code){
                    case 0:
                        loginView.goBack(new UserInfoBean());
                        break;
                }
            }
        });
    }

    public void onViewDestory(){
    }
}
