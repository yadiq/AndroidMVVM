package com.hqumath.demo.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hqumath.demo.app.ActivityManager
import com.hqumath.demo.app.DataStoreKey
import com.hqumath.demo.base.BaseFragment
import com.hqumath.demo.databinding.FragmentMineBinding
import com.hqumath.demo.dialog.CommonDialog
import com.hqumath.demo.ui.follow.ProfileDetailActivity
import com.hqumath.demo.ui.login.LoginActivity
import com.hqumath.demo.ui.repos.ReposDetailActivity
import com.hqumath.demo.utils.CommonUtil
import com.hqumath.demo.utils.DataStoreUtil

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/8/31 21:02
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
class MineFragment : BaseFragment() {
    private lateinit var binding: FragmentMineBinding

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        binding.llSourcecode.setOnClickListener {
            mContext.startActivity(ReposDetailActivity.getStartIntent(mContext, "androidmvvm", "yadiq"))
        }
        binding.llProfile.setOnClickListener {
            mContext.startActivity(ProfileDetailActivity.getStartIntent(mContext, "yadiq"))
        }
        binding.vLogout.setOnClickListener {
            val dialog = CommonDialog(
                context = mContext,
                title = "提示",
                message = "是否确认退出登录？",
                positiveText = "确定",
                positiveAction = { logout() },
                negativeText = "取消",
                negativeAction = {}
            )
            dialog.show()
        }
    }

    override fun initData() {
        binding.tvVersion.setText(CommonUtil.getVersion());
    }

    override fun initViewObservable() {

    }

    private fun logout() {
        CommonUtil.toast("已退出登录");
        //保留账号，清空全部数据
        val username = DataStoreUtil.getData(DataStoreKey.USER_NAME, "");
        DataStoreUtil.clearAllData();
        DataStoreUtil.putData(DataStoreKey.USER_NAME, username);
        //跳转登录界面
        ActivityManager.finishAllActivities();
        startActivity(Intent(mContext, LoginActivity::class.java))
    }
}