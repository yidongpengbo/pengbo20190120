package com.bawei.lenovo.pengbo20190120.iprensenter;

import com.bawei.lenovo.pengbo20190120.imodel.IModelImp;
import com.bawei.lenovo.pengbo20190120.iview.IView;
import com.bawei.lenovo.pengbo20190120.iview.MyCallBack;

import java.util.Map;

/**
 * @author lenovo
 *          P层
 */
public class IPrensenterImp implements IPrensneter{
    IView mIView;
    IModelImp mIModelImp;

    public IPrensenterImp(IView IView) {
        mIView = IView;
        mIModelImp=new IModelImp();
    }

    @Override
    public void startGet(String path, Class clazz) {
        mIModelImp.requestGet(path, clazz, new MyCallBack() {
            @Override
            public void getError(String error) {
                mIView.setError(error);
            }

            @Override
            public void getData(Object o) {
                mIView.srtData(o);
            }
        });
    }

    @Override
    public void startPost(String path, Map<String, String> map, Class clazz) {
            mIModelImp.requestPost(path, map, clazz, new MyCallBack() {
                @Override
                public void getError(String error) {
                    mIView.setError(error);
                }

                @Override
                public void getData(Object o) {
                    mIView.srtData(o);
                }
            });
    }

    /**
     * 解绑
     */
    public void onDetel(){
        if (mIModelImp!=null){
            mIModelImp=null;
        }
        if (mIView!=null){
            mIView=null;
        }
    }
}
