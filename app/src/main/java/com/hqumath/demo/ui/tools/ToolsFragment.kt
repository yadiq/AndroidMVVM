package com.hqumath.demo.ui.tools

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.hqumath.demo.base.BaseFragment
import com.hqumath.demo.databinding.FragmentToolsBinding
import com.hqumath.demo.dialog.CommonDialog
import com.hqumath.demo.dialog.DownloadDialog
import com.hqumath.demo.ui.fileupdown.FileUpDownActivity
import com.hqumath.demo.utils.PermissionUtil
import com.hqumath.demo.zxing.ZxingCaptureActivity
import com.king.camera.scan.CameraScan
import com.tgdz.belt.ui.mine.ToolsViewModel
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/8/31 21:02
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
class ToolsFragment : BaseFragment() {
    private lateinit var binding: FragmentToolsBinding
    private lateinit var viewModel: ToolsViewModel
    private var downloadDialog: DownloadDialog? = null

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToolsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        binding.tvFileUpDown.setOnClickListener {
            mContext.startActivity(Intent(mContext, FileUpDownActivity::class.java))
        }

        binding.tvCheckUpdate.setOnClickListener {
            val dialog = CommonDialog(
                context = mContext,
                title = "新版本V2.0",
                message = "适配 Android 11",
                positiveText = "立即更新",
                positiveAction = { viewModel.appUpdate() },
                negativeText = "下次提醒",
                negativeAction = {}
            )
            dialog.show()
        }
        binding.tvScanCode.setOnClickListener {
            AndPermission.with(mContext)
                .runtime()
                .permission(Permission.CAMERA)
                .onGranted { permissions: List<String?>? ->
                    val intent = Intent(mContext, ZxingCaptureActivity::class.java)
                    resultLauncher.launch(intent)
                }
                .onDenied { permissions: List<String?>? ->  //未全部授权
                    PermissionUtil.showSettingDialog(mContext, permissions) //自定义弹窗 去设置界面
                }.start()
        }
    }

    override fun initData() {
        viewModel = ViewModelProvider(this)[ToolsViewModel::class.java]
    }

    override fun initViewObservable() {
        viewModel.isLoading.observe(this) { b ->
            if (b) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
            }
        }
        //下载弹窗
        viewModel.showDownloadDialog.observe(this) { show: Boolean ->
            if (show) {
                if (downloadDialog == null) {
                    downloadDialog = DownloadDialog(mContext)
                    downloadDialog?.setCancelable(false)
                }
                if (downloadDialog?.isShowing == false) {
                    downloadDialog?.setProgress(0, 0)
                    downloadDialog?.show()
                }
            } else {
                downloadDialog?.dismiss()

            }
        }
        viewModel.mProgress.observe(this) { progress ->
            downloadDialog?.setProgress(progress, viewModel.mProgressTotal)
        }
    }

    //跳转扫码界面
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val result = CameraScan.parseScanResult(data)
                    result?.let {
                        binding.tvScanCode.text = "Scan Code= " + it
                    }
                }
            }
        }
}