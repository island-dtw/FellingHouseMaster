package com.dtw.fellinghousemaster.View.Chart;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.dtw.fellinghousemaster.R;

import org.w3c.dom.Text;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class FriendRecycleAdapter extends RecyclerView.Adapter<FriendRecycleAdapter.FriendHolder> {
    private Handler handler=new Handler();
    private Context context;
    private List<Conversation> conversationList;
    private SimpleOnRecycleItemClickListener simpleOnRecycleItemClickListener;
    private int lastSelectedPosition = -1;
    private String defaultCheckedUserName = null;

    public FriendRecycleAdapter(Context context, List<Conversation> conversationList, String defaultCheckedUserName) {
        this.context = context;
        this.conversationList = conversationList;
        this.defaultCheckedUserName = defaultCheckedUserName;
    }

    public void setSimpleOnRecycleItemClickListener(SimpleOnRecycleItemClickListener simpleOnRecycleItemClickListener) {
        this.simpleOnRecycleItemClickListener = simpleOnRecycleItemClickListener;
    }

    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_friend_recycle, parent, false);
        return new FriendHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            UserInfo userInfo = (UserInfo) conversationList.get(position).getTargetInfo();
            holder.friendName.setText(userInfo.getNickname() + "(" + userInfo.getUserName() + ")");
            if(conversationList.get(position).getUnReadMsgCnt()>0){
                holder.unreadView.setVisibility(View.VISIBLE);
            }
            if (lastSelectedPosition == -1 && userInfo.getUserName().equals(defaultCheckedUserName)) {
                holder.friendName.setChecked(true);
                lastSelectedPosition = position;
            }else{
                if(lastSelectedPosition==position){
                    holder.friendName.setChecked(false);
                }
            }
        } else {
            holder.friendName.setChecked(lastSelectedPosition == position);
            if(conversationList.get(position).getUnReadMsgCnt()>0) {
                holder.unreadView.setVisibility(View.VISIBLE);
            }
            Log.v("dtw", "last:" + lastSelectedPosition + "posi:" + position);
        }
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public void checkConversation(final String userName) {
        for(int i=0;i<conversationList.size();i++){
            if(((UserInfo) conversationList.get(i).getTargetInfo()).getUserName().equals(userName)){
                final int finalI = i;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lastSelectedPosition=finalI;
                        notifyItemChanged(finalI,0);
                    }
                });
                break;
            }
        }
    }

    class FriendHolder extends RecyclerView.ViewHolder {
        RadioButton friendName;
        View unreadView;
        public FriendHolder(View itemView) {
            super(itemView);
            friendName = (RadioButton) itemView.findViewById(R.id.radio_friend_name);
            unreadView=itemView.findViewById(R.id.view_unread);
            friendName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (simpleOnRecycleItemClickListener != null) {
                        if (lastSelectedPosition != getAdapterPosition()) {
                            simpleOnRecycleItemClickListener.onRecycleItemClick(FriendRecycleAdapter.class.getName(), v, getAdapterPosition());
                            unreadView.setVisibility(View.GONE);
                            if (lastSelectedPosition != -1) {
                                notifyItemChanged(lastSelectedPosition, 0);
                            }
                            lastSelectedPosition = getAdapterPosition();
                        }
                    }
                }
            });
        }
    }
}
