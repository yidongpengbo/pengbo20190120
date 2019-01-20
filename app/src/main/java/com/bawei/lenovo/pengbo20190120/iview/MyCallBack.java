package com.bawei.lenovo.pengbo20190120.iview;

/**
 * @author lenovo
 *    接收值
 */
public interface MyCallBack<T> {
    void getError(String error);
    void getData(T t);
}
