package com.dtw.fellinghousemaster.View.Setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dtw.fellinghousemaster.Config;
import com.dtw.fellinghousemaster.Presener.SettingPresener;
import com.dtw.fellinghousemaster.R;
import com.dtw.fellinghousemaster.View.BaseActivity;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class SettingActivity extends BaseActivity implements SettingView, CompoundButton.OnCheckedChangeListener {
    private SettingPresener settingPresener;
    private Switch switchNotifyDisenable,switchNotifySilence;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingPresener=new SettingPresener(this,this);

        switchNotifyDisenable= (Switch) findViewById(R.id.switch_notify_disenable);
        switchNotifySilence= (Switch) findViewById(R.id.switch_notify_silence);

        switchNotifyDisenable.setOnCheckedChangeListener(this);
        switchNotifySilence.setOnCheckedChangeListener(this);

        switch(settingPresener.getNotifyFlag()){
            case Config.NotifyDisEnable:
                switchNotifyDisenable.setEnabled(false);
                break;
            case Config.NotifySilence:
                switchNotifySilence.setEnabled(false);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_notify_disenable:
                if(isChecked) {
                    settingPresener.setNotifyFlag(Config.NotifyDefault);
                    switchNotifySilence.setEnabled(true);
                    switchNotifyDisenable.setHint("弹出提示信息");
                }else{
                    settingPresener.setNotifyFlag(Config.NotifyDisEnable);
                    switchNotifySilence.setEnabled(false);
                    switchNotifyDisenable.setHint("不做任何提示");
                }
                break;
            case R.id.switch_notify_silence:
                if(isChecked){
                    settingPresener.setNotifyFlag(Config.NotifyDefault);
                    switchNotifySilence.setHint("发出提示音");
                }else{
                    settingPresener.setNotifyFlag(Config.NotifySilence);
                    switchNotifySilence.setHint("不发出提示音");
                }
                break;
        }
    }
}
