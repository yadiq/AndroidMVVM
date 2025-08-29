package com.hqumath.demo.net;


import com.hqumath.demo.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 自定义拦截器
 * 打印日志
 */
public class LogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String TAG = "OkHttp";

    @Override
    public Response intercept(Chain chain) throws IOException {
        //请求
        Request request = chain.request();
        LogUtil.d(TAG, "--> " + request.method() + ' ' + request.url(), false);

        //body入参
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            String bodyStr = buffer.readString(charset);
            if (bodyStr.length() > 1500)
                bodyStr = bodyStr.substring(0, 1500);
            LogUtil.d(TAG, bodyStr, false);
        }

        //响应
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            LogUtil.d(TAG, "<-- HTTP FAILED: " + e, false);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        LogUtil.d(TAG, "<-- " + response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + ' ' + response.request().url()
                + " (" + tookMs + "ms)", false);

        //出参
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        if (contentLength == 0) {
            return response;
        }
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String content = buffer.clone().readString(charset);
        LogUtil.d(TAG, content, false);
        return response;
    }
}
