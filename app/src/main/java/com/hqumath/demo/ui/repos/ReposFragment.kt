package com.hqumath.demo.ui.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hqumath.demo.adapter.MyFragmentStateAdapter
import com.hqumath.demo.base.BaseActivity
import com.hqumath.demo.base.BaseFragment
import com.hqumath.demo.databinding.FragmentReposBinding

/**
 * ****************************************************************
 * 文件名称: OneFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
class ReposFragment : BaseFragment() {

    private lateinit var binding: FragmentReposBinding

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReposBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
    }

    override fun initData() {
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(MyReposFragment())
        fragmentList.add(StarredFragment())

        val titleList: ArrayList<String> = ArrayList()
        titleList.add("MyRepos")
        titleList.add("Starred")

        val baseActivity = mContext as BaseActivity
        binding.viewPager.adapter = MyFragmentStateAdapter(baseActivity, fragmentList)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun initViewObservable() {

    }
}