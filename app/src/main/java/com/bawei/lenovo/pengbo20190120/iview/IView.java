package com.bawei.lenovo.pengbo20190120.iview;

/**
 * @author lenovo
 *      接收值
 */
public interface IView<T> {
    void setError(String error);
    void srtData(T t);
}
