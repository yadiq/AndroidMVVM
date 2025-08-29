package com.hqumath.demo.net.upload;

/**
 * 上传进度回调类
 * Created by WZG on 2016/10/20.
 */

public interface UploadProgressListener {
    /**
     * 上传进度
     */
    void onProgress(long currentBytesCount, long totalBytesCount);
}