package com.hqumath.demo.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.hqumath.github.R
import com.hqumath.github.databinding.DialogDownloadBinding

class DownloadDialog(
    private val context: Context,
) : Dialog(context, R.style.dialog_common) {

    private val binding = DialogDownloadBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root) //根布局会被改为自适应宽高,居中
    }

    fun setProgress(progress: Long, total: Long) {
        if (progress > 0 && total > 0) {
            val currProgress = (progress * 1.0f / total * 100.0f).toInt()
            binding.tvProgress.setText("正在下载…$currProgress%")
            binding.progressBar.progress = currProgress
        } else {
            binding.tvProgress.setText("正在获取下载数据…")
            binding.progressBar.progress = 0
        }
    }
}