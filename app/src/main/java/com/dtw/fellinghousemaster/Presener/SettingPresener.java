package com.dtw.fellinghousemaster.Presener;

import android.content.Context;

import com.dtw.fellinghousemaster.Model.JMessageModel;
import com.dtw.fellinghousemaster.View.Setting.SettingView;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class SettingPresener {
    private Context context;
    private SettingView settingView;
    private JMessageModel jMessageModel;
    public SettingPresener(Context context, SettingView settingView){
        this.context=context;
        this.settingView=settingView;
        jMessageModel=JMessageModel.getInstance(context);
    }

    public int getNotifyFlag(){
        return jMessageModel.getNotifyFlag();
    }

    public void setNotifyFlag(int flag){
        jMessageModel.setNotifyFlag(flag);
    }
}
