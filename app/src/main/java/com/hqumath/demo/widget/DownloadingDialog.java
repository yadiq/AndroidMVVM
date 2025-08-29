package com.hqumath.demo.widget;

import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;

import com.hqumath.demo.R;

/**
 * 上传下载进度对话框
 */
public class DownloadingDialog extends AppCompatDialog {
    private DownloadingProgressBar mProgressBar;

    public DownloadingDialog(Context context) {
        super(context, R.style.AppDialogTheme);
        setContentView(R.layout.dialog_downloading);
        mProgressBar = findViewById(R.id.pb_downloading_content);
        setCancelable(true);
    }

    //20M以上的文件下载都会出现负数，已经下载的长度*100/总长度
    public void setProgress(long progressL, long maxProgressL) {
        int intMax;
        int intProgress;
        //int最大2147483647
        if (maxProgressL > 20000000L) {
            intMax = (int) (maxProgressL / 100);
            intProgress = (int) (progressL / 100);
        } else {
            intMax = (int) (maxProgressL);
            intProgress = (int) (progressL);
        }
        mProgressBar.setMax(intMax);
        mProgressBar.setProgress(intProgress);
    }

    @Override
    public void show() {
        super.show();
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
    }
}
