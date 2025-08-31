package com.hqumath.demo.ui.tools

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hqumath.demo.base.BaseFragment
import com.hqumath.demo.databinding.FragmentToolsBinding
import com.hqumath.demo.dialog.CommonDialog
import com.hqumath.demo.dialog.DownloadDialog
import com.hqumath.demo.ui.fileupdown.FileUpDownActivity
import com.tgdz.belt.ui.mine.ToolsViewModel

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
        binding.fileUpDown.setOnClickListener {
            mContext.startActivity(Intent(mContext, FileUpDownActivity::class.java))
        }
        binding.vCheckUpdate.setOnClickListener {
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
}