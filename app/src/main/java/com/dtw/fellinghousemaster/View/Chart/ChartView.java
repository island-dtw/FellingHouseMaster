package com.dtw.fellinghousemaster.View.Chart;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public interface ChartView {
    void onMessage(Message message);
    void onMessage(List<Message> messageList);
    void onLocalConversation(List<Conversation> conversationList);
    void onUnreadMessage(Message message);
}
