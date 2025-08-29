package com.hqumath.demo.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.hqumath.demo.R
import com.hqumath.demo.databinding.DialogLoadingBinding

class LoadingDialog(
    private val context: Context,
    private var message: String? = null
) : Dialog(context, R.style.dialog_loading) {

    private val binding = DialogLoadingBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root) //根布局会被改为自适应宽高,居中
        message?.let {
            binding.tvMsg.text = it
        }
    }

    fun setMessage(msg: String) {
        message = msg
        binding.tvMsg.text = message
    }
}