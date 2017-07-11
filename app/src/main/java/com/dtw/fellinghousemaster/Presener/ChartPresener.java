package com.dtw.fellinghousemaster.Presener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dtw.fellinghousemaster.Model.JMessageListener;
import com.dtw.fellinghousemaster.Model.JMessageModel;
import com.dtw.fellinghousemaster.Utils.SPUtil;
import com.dtw.fellinghousemaster.View.Chart.ChartView;

import java.io.File;
import java.util.List;

import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ChartPresener implements JMessageListener {
    private Context context;
    private ChartView chartView;
    private JMessageModel jMessageModel;
    public ChartPresener(Context context, ChartView chartView) {
        this.context = context;
        this.chartView = chartView;
        jMessageModel = JMessageModel.getInstance(context);
        jMessageModel.setjMessageListener(this);
    }

    public void onViewResume() {
        jMessageModel.setChartActivityON(true);
    }

    public void onViewPause() {
        jMessageModel.exitConversation();
        jMessageModel.setChartActivityON(false);
    }

    @Override
    public void onMessage(Message message) {
        Log.v("dtw", message.getFromUser().getUserName());
        if (message.getFromUser().getUserName().equals(new SPUtil(context).getLastChartUserName())) {//收到的消息属于当前聊天页面
            chartView.onMessage(message);
        }else{
            chartView.onUnreadMessage(message);
        }
    }

    @Override
    public void onMessage(List<Message> messageList) {
        chartView.onMessage(messageList);
    }

    @Override
    public void changeConversation(Message message) {
        if(message.getDirect()== MessageDirect.send){
            new SPUtil(context).setLastChartUserName(((UserInfo)message.getTargetInfo()).getUserName());
        }else {
            new SPUtil(context).setLastChartUserName(message.getFromUser().getUserName());
        }
        getLocalConversation();
    }

    @Override
    public void onLocalConversation(List<Conversation> conversationList) {
        chartView.onLocalConversation(conversationList);
    }

    public void getLocalMessages(String targetName) {
        new SPUtil(context).setLastChartUserName(targetName);
        jMessageModel.getSingleConversation(targetName);
        jMessageModel.enterConversation(targetName);
    }

    public void getLocalConversation() {
        jMessageModel.getLocalConversation();
        if (!TextUtils.isEmpty(new SPUtil(context).getLastChartUserName())) {
            getLocalMessages(new SPUtil(context).getLastChartUserName());
        }
    }

    public Message sendTextMessage(String msg) {
        return jMessageModel.sendTextMesage(new SPUtil(context).getLastChartUserName(), msg);
    }

    public Message sendImageMessage(File msg) {
        return jMessageModel.sendImageMessage(new SPUtil(context).getLastChartUserName(), msg);
    }

    public void deleteConversation() {
        if (!TextUtils.isEmpty(new SPUtil(context).getLastChartUserName())) {
            jMessageModel.deleteConversation(new SPUtil(context).getLastChartUserName());
            getLocalConversation();
        }
    }
}
