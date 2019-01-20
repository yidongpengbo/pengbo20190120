package com.bawei.lenovo.pengbo20190120.iprensenter;

import java.util.Map;

/**
 * @author lenovo
 *      p层接口
 */
public interface IPrensneter {
    void startGet(String path,Class clazz);
    void startPost(String path, Map<String,String>map,Class clazz);
}
