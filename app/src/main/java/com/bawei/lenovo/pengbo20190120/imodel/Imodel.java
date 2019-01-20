package com.bawei.lenovo.pengbo20190120.imodel;

import com.bawei.lenovo.pengbo20190120.iview.MyCallBack;

import java.util.Map;

/**
 * @author lenovo
 *          M层
 */
public interface Imodel {
    void requestGet(String path, Class clazz, MyCallBack myCallBack);
    void requestPost(String path, Map<String,String>map, Class clazz, MyCallBack myCallBack);
}
