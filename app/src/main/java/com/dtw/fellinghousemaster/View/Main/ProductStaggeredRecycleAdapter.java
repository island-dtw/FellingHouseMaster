package com.dtw.fellinghousemaster.View.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dtw.fellinghousemaster.Bean.SimpleProductBean;
import com.dtw.fellinghousemaster.R;
import com.dtw.fellinghousemaster.View.SimpleOnRecycleItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public class ProductStaggeredRecycleAdapter extends RecyclerView.Adapter<ProductStaggeredRecycleAdapter.ProductViewHolder> {
    private List<SimpleProductBean> simpleProductBeanList;
    private Context context;
    private SimpleOnRecycleItemClickListener simpleOnRecycleItemClickListener;
    public ProductStaggeredRecycleAdapter(Context context, List<SimpleProductBean> simpleProductBeanList){
        this.context=context;
        this.simpleProductBeanList = simpleProductBeanList;
    }
    public void setSimpleOnRecycleItemClickListener(SimpleOnRecycleItemClickListener simpleOnRecycleItemClickListener){
        this.simpleOnRecycleItemClickListener=simpleOnRecycleItemClickListener;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.item_product_simple,parent,false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return simpleProductBeanList.size();
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        SimpleProductBean simpleProductBean = simpleProductBeanList.get(position);
        holder.locationText.setText(simpleProductBean.getLocation());
        RequestOptions options = new RequestOptions()
                .error(R.drawable.nav_head)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .load(simpleProductBean.getProductImgUrl())
                .apply(options)
                .into(holder.productImage);

    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView locationText;

        public ProductViewHolder(final View itemView) {
            super(itemView);
            productImage= (ImageView) itemView.findViewById(R.id.img_product);
            locationText= (TextView) itemView.findViewById(R.id.text_location);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(simpleOnRecycleItemClickListener!=null){
                        simpleOnRecycleItemClickListener.onRecycleItemClick(ProductStaggeredRecycleAdapter.class.getName(),itemView,getAdapterPosition());
                    }
                }
            });
        }
    }
}
