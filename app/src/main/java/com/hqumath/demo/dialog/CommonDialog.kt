package com.hqumath.demo.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.hqumath.demo.R
import com.hqumath.demo.databinding.DialogCommonBinding

/*
用法
val dialog = CommonDialog(
  context = mContext,
  title = "提示",
  message = "是否确认退出登录？",
  positiveText = "确定",
  positiveAction = {},
  negativeText = "取消",
  negativeAction = {}
            )
            dialog.show()
 */
class CommonDialog(
    private val context: Context,
    private val title: String? = null,
    private val message: String,
    private val positiveText: String? = null, //确定
    private val negativeText: String? = null,
    private val positiveAction: (() -> Unit)? = null, //取消
    private val negativeAction: (() -> Unit)? = null,
    private val oneButtonText: String? = null, //只有一个按键
    private val oneButtonAction: (() -> Unit)? = null,
) : Dialog(context, R.style.dialog_common) {

    private val binding = DialogCommonBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root) //根布局会被改为自适应宽高,居中
        //根布局为自适应宽高，有软键盘时必须全屏，否则mate40等手机软键盘无法上推
        /*Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/

        binding.tvMessage.setText(message)
        title?.let {
            binding.tvTitle.setText(it)
        }
        //确定
        positiveText?.let {
            binding.btnYes.text = it
        }
        binding.btnYes.setOnClickListener {
            positiveAction?.invoke()
            dismiss()
        }
        //取消
        negativeText?.let {
            binding.btnNo.text = it
        }
        binding.btnNo.setOnClickListener {
            negativeAction?.invoke()
            dismiss()
        }
        //只有一个按键时
        oneButtonText?.let {
            binding.btnOneYes.text = it
            setOneOrTwoBtn(true)
        }
        binding.btnOneYes.setOnClickListener {
            oneButtonAction?.invoke()
            dismiss()
        }
    }

    /**
     * 设置按键类型
     *
     * @param one true 只有一个确认按键 ； false 显示 确认 和取消 按键
     */
    private fun setOneOrTwoBtn(one: Boolean) {
        if (one) {
            binding.btnOneYes.visibility = View.VISIBLE
            binding.llTwo.visibility = View.GONE
        } else {
            binding.btnOneYes.visibility = View.GONE
            binding.llTwo.visibility = View.VISIBLE
        }
    }
}