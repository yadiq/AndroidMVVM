package com.hqumath.demo.net.upload;

import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.*;

import java.io.IOException;

/**
 * 自定义回调加载速度类RequestBody
 * Created by WZG on 2016/10/20.
 */

public class ProgressRequestBody extends RequestBody {
    //实际起作用的RequestBody
    private RequestBody delegate;
    //进度回调接口
    private final UploadProgressListener progressListener;

    public ProgressRequestBody(RequestBody requestBody, UploadProgressListener progressListener) {
        this.delegate = requestBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        //不需要检测日志拦截器进度 HttpLoggingInterceptor
        if (sink instanceof Buffer) {
            delegate.writeTo(sink);
            return;
        }
        //将CountingSink转化为BufferedSink供writeTo()使用
        BufferedSink bufferedSink = Okio.buffer(new CountingSink(sink));
        delegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private class CountingSink extends ForwardingSink {
        private long byteWritten;

        private CountingSink(Sink delegate) {
            super(delegate);
        }

        /**
         * 上传时调用该方法,在其中调用回调函数将上传进度暴露出去,该方法提供了缓冲区的自己大小
         */
        @Override
        public void write(@NonNull Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten += byteCount;
            progressListener.onProgress(byteWritten, contentLength());
        }
    }

    /**
     * 返回文件总的字节大小
     * 如果文件大小获取失败则返回-1
     */
    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }
}
