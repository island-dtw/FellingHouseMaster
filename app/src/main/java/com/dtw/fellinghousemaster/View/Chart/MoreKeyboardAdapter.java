package com.dtw.fellinghousemaster.View.Chart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtw.fellinghousemaster.Bean.KeyBoardMoreItemBean;
import com.dtw.fellinghousemaster.R;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class MoreKeyboardAdapter extends RecyclerView.Adapter<MoreKeyboardAdapter.KeyBoardViewHolder>{
    private Context context;
    private List<KeyBoardMoreItemBean> keyBoardMoreItemBeanList;
    private SimpleOnRecycleItemClickListener simpleOnRecycleItemClickListener;
    public MoreKeyboardAdapter(Context context,List<KeyBoardMoreItemBean> keyBoardMoreItemBeanList){
        this.context=context;
        this.keyBoardMoreItemBeanList=keyBoardMoreItemBeanList;
    }

    public void setSimpleOnRecycleItemClickListener(SimpleOnRecycleItemClickListener simpleOnRecycleItemClickListener){
        this.simpleOnRecycleItemClickListener = simpleOnRecycleItemClickListener;
    }

    @Override
    public KeyBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_morekeyboard,parent,false);
        return new KeyBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KeyBoardViewHolder holder, int position) {
        KeyBoardMoreItemBean keyBoardMoreItemBean=keyBoardMoreItemBeanList.get(position);
        holder.titleText.setText(keyBoardMoreItemBean.getTitle());
        holder.moreImg.setImageResource(keyBoardMoreItemBean.getImgResID());
    }

    @Override
    public int getItemCount() {
        return keyBoardMoreItemBeanList.size();
    }
    class KeyBoardViewHolder extends RecyclerView.ViewHolder {
        ImageView moreImg;
        TextView titleText;
        public KeyBoardViewHolder(View itemView) {
            super(itemView);
            moreImg= (ImageView) itemView.findViewById(R.id.img_more);
            titleText= (TextView) itemView.findViewById(R.id.text_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(simpleOnRecycleItemClickListener !=null){
                        simpleOnRecycleItemClickListener.onRecycleItemClick(MoreKeyboardAdapter.class.getName(),v,getAdapterPosition());
                    }
                }
            });
        }
    }
}
