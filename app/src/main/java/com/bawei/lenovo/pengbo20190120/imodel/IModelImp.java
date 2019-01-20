package com.bawei.lenovo.pengbo20190120.imodel;

import com.bawei.lenovo.pengbo20190120.iview.MyCallBack;
import com.bawei.lenovo.pengbo20190120.until.RetrofitUntil;
import com.google.gson.Gson;

import java.util.Map;

/**
 * @author lenovo
 *      Model层
 */
public class IModelImp implements Imodel {
    @Override
    public void requestGet(String path, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUntil.getInstance().get(path, new RetrofitUntil.CallBack() {
            @Override
            public void fail(String error) {
                if (myCallBack!=null){
                    myCallBack.getError(error);
                }
            }

            @Override
            public void success(String string) {
                Gson gson = new Gson();
                Object o = gson.fromJson(string, clazz);
                if (myCallBack!=null){
                    myCallBack.getData(o);
                }
            }
        });
    }

    @Override
    public void requestPost(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
            RetrofitUntil.getInstance().post(path, map, new RetrofitUntil.CallBack() {
                @Override
                public void fail(String error) {
                    if (myCallBack!=null){
                        myCallBack.getError(error);
                    }
                }

                @Override
                public void success(String string) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(string, clazz);
                    if (myCallBack!=null){
                        myCallBack.getData(o);
                    }
                }
            });
    }
}
