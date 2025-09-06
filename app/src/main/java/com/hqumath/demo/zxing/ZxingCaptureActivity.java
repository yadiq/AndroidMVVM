package com.hqumath.demo.zxing;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.zxing.Result;
import com.hqumath.github.R;
import com.king.camera.scan.AnalyzeResult;
import com.king.camera.scan.CameraScan;
import com.king.camera.scan.analyze.Analyzer;
import com.king.zxing.BarcodeCameraScanActivity;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import com.king.zxing.analyze.MultiFormatAnalyzer;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2023/3/20 11:38
 * 文件描述: 自定义扫码界面
 * 注意事项:
 * ****************************************************************
 */
public class ZxingCaptureActivity extends BarcodeCameraScanActivity {

    @Override
    public void initCameraScan(@NonNull CameraScan<Result> cameraScan) {
        super.initCameraScan(cameraScan);
        // 根据需要设置CameraScan相关配置
        cameraScan.setPlayBeep(true)
                .setVibrate(true);
    }

    @Nullable
    @Override
    public Analyzer<Result> createAnalyzer() {
        // 初始化解码配置
        DecodeConfig decodeConfig = new DecodeConfig();
        decodeConfig.setHints(DecodeFormatManager.DEFAULT_HINTS)//QR_CODE_HINTS 如果只有识别二维码的需求，这样设置效率会更高，不设置默认为DecodeFormatManager.DEFAULT_HINTS
                .setFullAreaScan(false)//设置是否全区域识别，默认false
                .setAreaRectRatio(0.8f)//设置识别区域比例，默认0.8，设置的比例最终会在预览区域裁剪基于此比例的一个矩形进行扫码识别
                .setAreaRectVerticalOffset(0)//设置识别区域垂直方向偏移量，默认为0，为0表示居中，可以为负数
                .setAreaRectHorizontalOffset(0);//设置识别区域水平方向偏移量，默认为0，为0表示居中，可以为负数
        // BarcodeCameraScanActivity默认使用的MultiFormatAnalyzer，如果只识别二维码，这里可以改为使用QRCodeAnalyzer
        return new MultiFormatAnalyzer(decodeConfig);
    }

    /**
     * 布局ID；通过覆写此方法可以自定义布局
     *
     * @return 布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_zxing_capture;
    }

    @Override
    public void onScanResultCallback(@NonNull AnalyzeResult<Result> result) {
        // 停止分析
        getCameraScan().setAnalyzeImage(false);
        // 返回结果
        Intent intent = new Intent();
        intent.putExtra(CameraScan.SCAN_RESULT, result.getResult().getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void initUI() {
        super.initUI();
        findViewById(R.id.llTitle).setOnClickListener(v -> {
            finish();
        });
    }
}
