package com.bawei.lenovo.pengbo20190120;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bawei.lenovo.pengbo20190120.adapter.ShopAdapter;
import com.bawei.lenovo.pengbo20190120.bean.ShopBean;
import com.bawei.lenovo.pengbo20190120.iprensenter.IPrensenterImp;
import com.bawei.lenovo.pengbo20190120.iview.IView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {

    /**
     * 定位用户的具体位置信息
     */
    private Button mPosition;
    private LinearLayout mLinear;
    private RecyclerView mRecycler;
    private ShopAdapter mShopAdapter;
    IPrensenterImp mIPrensenterImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIPrensenterImp=new IPrensenterImp(this);
        initView();
        initRecycler();
    }

    /**
     * Recycler
     */
    private void initRecycler() {
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecycler.setLayoutManager(manager);
        //适配器
        mShopAdapter = new ShopAdapter(this);
        mRecycler.setAdapter(mShopAdapter);
        //网络请求
        String path="searchProducts";
        HashMap<String, String> map = new HashMap<>();
        map.put("keywords","笔记本");
        map.put("page",1+"");
        mIPrensenterImp.startPost(path,map,ShopBean.class);
    }

    private void initView() {
        mPosition = (Button) findViewById(R.id.Position);
        mPosition.setOnClickListener(this);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mRecycler = (RecyclerView) findViewById(R.id.Recycler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
                //地图的点击事件
            case R.id.Position:
                Intent intent = new Intent(this, PositionActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取数据失败
     * @param error
     */
    @Override
    public void setError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取数据成功
     * @param o
     */
    @Override
    public void srtData(Object o) {
        if (o instanceof ShopBean){
            ShopBean shopBean=(ShopBean)o;
            //添加到适配器
            mShopAdapter.setMjihe(shopBean.getData());
            mShopAdapter.setCallBack(new ShopAdapter.CallBack() {
                @Override
                public void callback(int i) {
                    mShopAdapter.delte(i);
                }
            });
        }
    }

    /**
     * 解绑
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPrensenterImp.onDetel();
    }
}
