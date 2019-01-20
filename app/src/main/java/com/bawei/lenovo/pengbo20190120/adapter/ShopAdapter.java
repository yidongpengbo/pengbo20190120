package com.bawei.lenovo.pengbo20190120.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.lenovo.pengbo20190120.DetailActivity;
import com.bawei.lenovo.pengbo20190120.R;
import com.bawei.lenovo.pengbo20190120.bean.ShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private Context mContext;
    private List<ShopBean.DataBean>mjihe;

    public ShopAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<ShopBean.DataBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    public void delte(int i){
        mjihe.remove(i);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.shopadapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String image = mjihe.get(i).getImages().replace("https", "http");
        Uri uri = Uri.parse(image);
        viewHolder.shopSDV.setImageURI(uri);
        //标题
        viewHolder.shopTitle.setText(mjihe.get(i).getTitle());
        //价格
        viewHolder.shopPrice.setText("¥"+mjihe.get(i).getPrice());
        //条目的点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                int pid = mjihe.get(i).getPid();
                intent.putExtra("pid",pid);
                mContext.startActivity(intent);

            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    mCallBack.callback(i);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView shopSDV;
        TextView shopTitle,shopPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //获取资源ID
            shopSDV=itemView.findViewById(R.id.shopSDV);
            shopTitle=itemView.findViewById(R.id.shopTitle);
            shopPrice=itemView.findViewById(R.id.shopPrice);
        }
    }

    /**
     * 接口
     */
    public interface CallBack{
        void callback(int i);
    }
    public CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
}
