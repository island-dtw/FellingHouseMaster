package com.dtw.fellinghousemaster.Utils;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.dtw.fellinghousemaster.Config;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class SPUtil {
    SharedPreferences sharedPreferences;
    public SPUtil(Context context){
        sharedPreferences=context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
    }
    public String getUserName(){
        return sharedPreferences.getString(Config.Key_SP_UserName,"");
    }
    public String getUserPassword(){
        return sharedPreferences.getString(Config.Key_SP_UserPassworde,"");
    }
    public void setUserName(String userName){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Config.Key_SP_UserName,userName).commit();
    }
    public void setUserPassword(String password){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Config.Key_SP_UserPassworde,password).commit();
    }
    public void setKeyBoardHeight(int height){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(Config.Key_KeyBoardHeight,height).commit();
    }
    public int getKeyBoardHeight(){
        return sharedPreferences.getInt(Config.Key_KeyBoardHeight,-1);
    }
    public void setLastChartUserName(String userName){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Config.Key_SP_LastChartUserName,userName).commit();
    }
    public String getLastChartUserName(){
        return sharedPreferences.getString(Config.Key_SP_LastChartUserName,null);
    }
}

