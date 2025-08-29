package com.hqumath.demo.net;

import com.hqumath.demo.app.Constant;
import com.hqumath.demo.net.download.DownloadInterceptor;
import com.hqumath.demo.net.download.DownloadListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ****************************************************************
 * 文件名称: RetrofitClient
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 14:47
 * 文件描述: RetrofitClient封装单例类, 实现网络请求
 * 注意事项: https://github.com/wzgiceman/RxjavaRetrofitDemo-master
 * 每次发送请求，new Retrofit,以便动态更改baseUrl
 * 版权声明:
 * ****************************************************************
 */
public class RetrofitClient {
    private volatile static RetrofitClient INSTANCE;
    private final static int connectTimeout = 6;//s,连接超时
    private final static int readTimeout = 6;//s,读取超时
    private final static int writeTimeout = 6;//s,写超时

    private ApiService apiService;//api服务器
    private ApiService downloadService;//下载服务器

    //获取单例
    public static RetrofitClient getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient();
                }
            }
        }
        return INSTANCE;
    }

    //构造方法私有
    private RetrofitClient() {
    }

    //api服务器
    public ApiService getApiService() {
        if (apiService == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
            builder.readTimeout(readTimeout, TimeUnit.SECONDS);
            builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
            builder.retryOnConnectionFailure(false);//出现错误时会重新发送请求
            builder.addInterceptor(new LogInterceptor());//自定义拦截器（token过期后刷新token，打印日志）
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.baseUrl)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    //下载服务器
    public ApiService getDownloadService(DownloadListener listener) {
        if (downloadService == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
            builder.readTimeout(readTimeout, TimeUnit.SECONDS);
            builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
            builder.retryOnConnectionFailure(false);//出现错误时会重新发送请求
            if (listener != null)
                builder.addInterceptor(new DownloadInterceptor(listener));//下载拦截器（显示进度）
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.downloadHost)
                    .build();
            downloadService = retrofit.create(ApiService.class);
        }
        return downloadService;
    }
}