package com.dtw.fellinghousemaster.View.Login;

import com.dtw.fellinghousemaster.Bean.UserInfoBean;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public interface LoginView {
    void showToast(String msg);
    void goBack(UserInfoBean userInfoBean);
}
