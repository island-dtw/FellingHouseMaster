package com.dtw.fellinghousemaster.View.Chart;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dtw.fellinghousemaster.R;
import com.dtw.fellinghousemaster.Utils.ScreenUtil;

import java.util.List;

import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {
    private Context context;
    private List<Message> messageList;
    private SimpleOnRecycleItemClickListener simpleOnRecycleItemClickListener;
    public final int TYPE_SEND = 10;
    public final int TYPE_RECEIVE = 20;

    public MessageListAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public void setSimpleOnRecycleItemClickListener(SimpleOnRecycleItemClickListener simpleOnRecycleItemClickListener){
        this.simpleOnRecycleItemClickListener = simpleOnRecycleItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        switch (messageList.get(position).getDirect()) {
            case receive:
                return TYPE_RECEIVE;
            case send:
                return TYPE_SEND;
            default:
                return TYPE_RECEIVE;
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case TYPE_RECEIVE:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_message_receive, parent, false);
                break;
            case TYPE_SEND:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_message_send, parent, false);
                break;
            default:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_message_receive, parent, false);
                break;
        }
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message=messageList.get(position);
        switch (message.getContentType()){
            case text:
                holder.text.setText(((TextContent)message.getContent()).getText());
                hideItem(holder);
                holder.text.setVisibility(View.VISIBLE);
                break;
            case image:
                int w,h;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(((ImageContent)message.getContent()).getLocalThumbnailPath(), options);
                if(options.outWidth> ScreenUtil.dip2px(context,150)){
                    w=ScreenUtil.dip2px(context,150);
                    h=options.outHeight* ScreenUtil.dip2px(context,150)/options.outWidth;
                }else{
                    w=options.outWidth;
                    h=options.outHeight;
                }
                Glide.with(context)
                        .load(((ImageContent)message.getContent()).getLocalThumbnailPath())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .override(w,h)
                        .into(holder.img);
                hideItem(holder);
                holder.img.setVisibility(View.VISIBLE);
                Log.v("dtw","imgmessage:"+message.toString());
                break;
            case voice:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView img;
        FrameLayout voice;
        public MessageViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.text_text);
            img= (ImageView) itemView.findViewById(R.id.img_img);
            voice= (FrameLayout) itemView.findViewById(R.id.layout_voicd);
            FrameLayout messageContent= (FrameLayout) itemView.findViewById(R.id.message_content);
            messageContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(simpleOnRecycleItemClickListener !=null){
                        simpleOnRecycleItemClickListener.onRecycleItemClick(MessageListAdapter.class.getName(),v,getAdapterPosition());
                    }
                }
            });
        }
    }

    private void hideItem(MessageViewHolder holder){
        holder.text.setVisibility(View.GONE);
        holder.img.setVisibility(View.GONE);
        holder.voice.setVisibility(View.GONE);
    }
}
