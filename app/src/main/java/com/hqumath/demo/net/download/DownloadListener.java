package com.hqumath.demo.net.download;

public interface DownloadListener {
    void onSuccess(Object object);

    void onError(String errorMsg, String code);

    void update(long read, long count);//已下载，总量
}
