package com.dtw.fellinghousemaster.Model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dtw.fellinghousemaster.Config;
import com.dtw.fellinghousemaster.View.Chart.ChartActivity;

import java.io.File;
import java.io.FileNotFoundException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class JMessageModel {
    private static JMessageModel jMessageModel;
    private JMessageListener jMessageListener;
    private Context context;
    private boolean chartActivityON=false;

    private JMessageModel(Context context) {
        this.context = context;
        JMessageClient.registerEventReceiver(this);
        JMessageClient.setDebugMode(true);
        JMessageClient.init(context);
    }

    public static JMessageModel getInstance(Context context) {
        if (jMessageModel == null) {
            jMessageModel = new JMessageModel(context);
        }
        return jMessageModel;
    }

    public void setjMessageListener(JMessageListener jMessageListener) {
        this.jMessageListener = jMessageListener;
    }

    public void regist(String phoneNum, String password, final BaseCallBack baseCallBack){
        JMessageClient.register(phoneNum, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                baseCallBack.onResult(i,s);
            }
        });
    }

    public void login(String phoneNum, String password, final BaseCallBack baseCallBack){
        JMessageClient.login(phoneNum, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                baseCallBack.onResult(i,s);
            }
        });
    }

    public void setChartActivityON(boolean on){
        chartActivityON=on;
    }

    public void logout(){
        JMessageClient.logout();
    }

    public void enterConversation(String name){
        JMessageClient.enterSingleConversation(name);
    }

    public void exitConversation(){
        JMessageClient.exitConversation();
    }

    public Message sendTextMesage(String targetName, String textMessage) {
        Conversation conversation = Conversation.createSingleConversation(targetName);
        Message message = conversation.createSendTextMessage(textMessage);
        JMessageClient.sendMessage(message);
        return message;
    }

    public Message sendImageMessage(String targetName, File imageMessage) {
        Conversation conversation = Conversation.createSingleConversation(targetName);
        Message message = null;
        try {
            message = conversation.createSendImageMessage(imageMessage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JMessageClient.sendMessage(message);
        Log.v("dtw",message.toString());
        return message;
    }

    public void getSingleConversation(String targetName) {
        Conversation conversation = Conversation.createSingleConversation(targetName);
        jMessageListener.onMessage(conversation.getAllMessage());
    }

    public void getLocalConversation(){
        jMessageListener.onLocalConversation(JMessageClient.getConversationList());
    }

    public void deleteConversation(String targetName){
        JMessageClient.deleteSingleConversation(targetName);
    }

    public void setNotifyFlag(int flag){
        switch(flag){
            case Config.NotifyDefault:
                JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_DEFAULT);
                break;
            case Config.NotifyDisEnable:
                JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_DISABLE);
                break;
            case Config.NotifySilence:
                JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_SILENCE);
                break;
        }
    }

    public int getNotifyFlag(){
        return JMessageClient.getNotificationFlag();
    }

    public void onEventMainThread(MessageEvent event) {
        if (jMessageListener != null) {
            jMessageListener.onMessage(event.getMessage());
        }
    }

    public void onEventMainThread(NotificationClickEvent event) {
        if(!chartActivityON) {
            Intent intent = new Intent(context, ChartActivity.class);
            context.startActivity(intent);//自定义跳转到指定页面
        }else{
            jMessageListener.changeConversation(event.getMessage());
        }
    }

    public interface BaseCallBack{
        void onResult(int code, String msg);
    }

}
