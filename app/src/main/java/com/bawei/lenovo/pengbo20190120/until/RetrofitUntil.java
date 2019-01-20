package com.bawei.lenovo.pengbo20190120.until;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lenovo
 *      工具类
 */
public class RetrofitUntil {
    private static RetrofitUntil instance;
    private final OkHttpClient mClient;
    private final Retrofit mRetrofit;
    private final BaseApis mBaseApis;

    public static RetrofitUntil getInstance(){
        if (instance==null){
            synchronized (RetrofitUntil.class){
                instance=new RetrofitUntil();
            }
        }
        return instance;
    }
    private RetrofitUntil(){
        //2.日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("TAG", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //3.客户端
        mClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();
        //4.retrofit
        String BASE_PATH="http://www.zhaoapi.cn/product/";
        mRetrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(BASE_PATH)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mBaseApis = mRetrofit.create(BaseApis.class);
    }
    /**
     * 5.接口
     */
    public interface CallBack{
        void fail(String error);
        void success(String string);
    }
    public CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
    /**
     * 6.observer
     */
    public Observer getObserver(final CallBack callBack){
        Observer observer=new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (callBack!=null){
                    callBack.fail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if (callBack!=null){
                        callBack.success(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        return observer;
    }
    /**
     * 7.get请求
     */
    public RetrofitUntil get(String path, CallBack callBack){
        mBaseApis.get(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
        return instance;
    }
    /**
     * 8.post请求
     */
    public RetrofitUntil post(String path,Map<String,String>map,CallBack callBack){
        mBaseApis.post(path,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
        return instance;
    }
}
