package com.hqumath.demo.net;

import com.hqumath.demo.app.Constant;
import com.hqumath.demo.net.download.DownloadInterceptor;
import com.hqumath.demo.net.download.DownloadListener;
import com.hqumath.demo.utils.SSLSocketClient;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
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
    private final static int connectTimeout = 8;//s,连接超时
    private final static int readTimeout = 8;//s,读取超时
    private final static int writeTimeout = 8;//s,写超时

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
            builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));//有些后端不支持http/2
            //builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());//忽略证书
            //builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());//忽略证书
            builder.addInterceptor(new LogInterceptor());//自定义拦截器（token过期后刷新token，打印日志）
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())//返回数据转换器-Gson
                    //.addConverterFactory(ScalarsConverterFactory.create())//返回数据转换器-String
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.BASE_API)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    //下载服务器
    public ApiService getDownloadService(DownloadListener listener) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        //builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        //builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        if (listener != null)
            builder.addInterceptor(new DownloadInterceptor(listener));//下载拦截器（显示进度）
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_API)//使用哪个域名都可以
                .build();
        return retrofit.create(ApiService.class);
    }


    /*APP中代码
    implementation "com.github.bumptech.glide:okhttp3-integration:4.12.0"
    Glide.get(this).getRegistry().replace(GlideUrl .class, InputStream .class,
        new OkHttpUrlLoader.Factory(RetrofitClient.getInstance().getGlideOkHttpClient()));*/

    /**
     * Glide忽略证书替换OkHttp
     */
    public OkHttpClient getGlideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));//不使用http/2
        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());//忽略证书
        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());//忽略证书
        return builder.build();
    }
}
