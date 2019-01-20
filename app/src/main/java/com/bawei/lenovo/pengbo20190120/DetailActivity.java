package com.bawei.lenovo.pengbo20190120;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lenovo.pengbo20190120.adapter.DetailAdapter;
import com.bawei.lenovo.pengbo20190120.bean.DetailBean;
import com.bawei.lenovo.pengbo20190120.iprensenter.IPrensenterImp;
import com.bawei.lenovo.pengbo20190120.iview.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener,IView {
    /**
     * 用户头像
     */
    private TextView mDetailHead;
    private LinearLayout mLinear;
    private ViewPager mDetailViewPager;
    private TextView mDetailTitle;
    private TextView mDetailPrice;
    /**
     * 加入购物车
     */
    private Button mDetailShop;
    IPrensenterImp mIPrensenterImp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        //获取pid
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 1);
        Log.i("TAG","pid="+pid);
        HashMap<String, String> map = new HashMap<>();
        map.put("pid",pid+"");
        //网络请求
        String path="getProductDetail";
        mIPrensenterImp=new IPrensenterImp(this);
        mIPrensenterImp.startPost(path,map,DetailBean.class);
    }

    private void initView() {
        mDetailHead = (TextView) findViewById(R.id.DetailHead);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mDetailViewPager = (ViewPager) findViewById(R.id.DetailViewPager);
        mDetailTitle = (TextView) findViewById(R.id.DetailTitle);
        mDetailPrice = (TextView) findViewById(R.id.DetailPrice);
        mDetailShop = (Button) findViewById(R.id.DetailShop);
        mDetailShop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.DetailShop:
                break;
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
    List<String> mList=new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = mDetailViewPager.getCurrentItem();
           mDetailViewPager.setCurrentItem( item+1);
           mHandler.sendEmptyMessageDelayed(0,2000);
        }
    };

    /**
     * 得到数据
     * @param o
     */
    @Override
    public void srtData(Object o) {
            if (o instanceof DetailBean){
                DetailBean detailBean=(DetailBean)o;
                String title = detailBean.getData().getTitle();
                mDetailTitle.setText(title);
                mDetailPrice.setText("¥"+detailBean.getData().getPrice());
                String images = detailBean.getData().getImages();
                String image = images.split("\\|")[0].replace("https", "http");
                mList.add(image);
                DetailAdapter detailAdapter = new DetailAdapter(this, mList);
                mDetailViewPager.setAdapter(detailAdapter);
                int count = detailAdapter.getCount();
                mDetailViewPager.setCurrentItem(count%count);
                //轮播
                mHandler.sendEmptyMessageDelayed(0,2000);
            }
    }
}
