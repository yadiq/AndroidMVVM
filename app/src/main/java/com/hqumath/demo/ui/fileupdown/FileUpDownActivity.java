package com.hqumath.demo.ui.fileupdown;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.hqumath.demo.base.BaseActivity;
import com.hqumath.demo.databinding.ActivityFileupdownBinding;
import com.hqumath.demo.utils.CommonUtil;
import com.hqumath.demo.utils.FileUtil;
import com.hqumath.demo.widget.DownloadingDialog;

import java.io.File;

public class FileUpDownActivity extends BaseActivity {
    public final static String url = "http://cps.yingyonghui.com/cps/yyh/channel/ac.union.m2/com.yingyonghui.market_1_30063293.apk";

    private ActivityFileupdownBinding binding;
    private FileUpDownViewModel viewModel;
    private DownloadingDialog mDownloadingDialog;

    @Override
    public View initContentView(Bundle savedInstanceState) {
        binding = ActivityFileupdownBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.btnDownload.setOnClickListener(v -> {
            viewModel.download(url);
        });
        binding.btnUpload.setOnClickListener(v -> {
            File file = FileUtil.getFileFromUrl(url);
            if(file.exists()) {
                viewModel.upload("testFile", file);
            } else {
                CommonUtil.toast("文件不存在，请先下载");
            }
        });
    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(this).get(FileUpDownViewModel.class);
    }

    @Override
    protected void initViewObservable() {
        viewModel.isDownloading.observe(this, b -> {
            if (b) {
                if (mDownloadingDialog == null) {
                    mDownloadingDialog = new DownloadingDialog(this);
                }
                mDownloadingDialog.show();
            } else {
                if (mDownloadingDialog != null) {
                    mDownloadingDialog.dismiss();
                }
            }
        });
        viewModel.downloadProgress.observe(this, progress -> {
            if (mDownloadingDialog != null && mDownloadingDialog.isShowing()) {
                mDownloadingDialog.setProgress(progress, viewModel.downloadMax);
            }
        });
        viewModel.downloadResultCode.observe(this, code -> {
            if (code.equals("0")) {
                String fileName = (viewModel.downloadResultData).getName();
                CommonUtil.toast(fileName + "Download success.");
            } else {
                CommonUtil.toast(viewModel.downloadResultMsg);
            }
        });
        viewModel.isLoading.observe(this, b -> {
            if (b) {
                showProgressDialog("loading");
            } else {
                dismissProgressDialog();
            }
        });
        viewModel.uploadResultCode.observe(this, code -> {
            if (code.equals("0")) {
                CommonUtil.toast("Upload success.");
            } else {
                CommonUtil.toast(viewModel.uploadResultMsg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (viewModel != null) {
            viewModel.dispose();
            viewModel = null;
        }
        super.onDestroy();
    }
}
