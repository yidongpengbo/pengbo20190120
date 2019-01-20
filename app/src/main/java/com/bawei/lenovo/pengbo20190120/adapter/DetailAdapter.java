package com.bawei.lenovo.pengbo20190120.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bawei.lenovo.pengbo20190120.bean.DetailBean;
import com.bawei.lenovo.pengbo20190120.bean.ShopBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends PagerAdapter {
        private Context mContext;
        private List<String> mjihe;

    public DetailAdapter(Context context, List<String> jihe) {
        mContext = context;
        this.mjihe = jihe;
    }

    @Override
    public int getCount() {
        return 5000;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(mContext);
        container.addView(imageView);
        //加载图片
        Glide.with(mContext).load(mjihe.get(position%mjihe.size())).into(imageView);
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
