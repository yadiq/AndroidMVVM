package com.hqumath.demo.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.hqumath.demo.adapter.MyFragmentStateAdapter
import com.hqumath.demo.base.BaseActivity
import com.hqumath.demo.databinding.ActivityMainBinding
import com.hqumath.demo.ui.follow.FollowersFragment
import com.hqumath.demo.ui.repos.ReposFragment
import com.hqumath.demo.utils.DataStoreUtil

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2023/10/25 9:35
 * 文件描述: 主界面
 * 注意事项:
 * ****************************************************************
 */
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun initContentView(savedInstanceState: Bundle?): View {
        //enableEdgeToEdge() 启用沉浸式布局
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(ReposFragment())
        fragmentList.add(FollowersFragment())
        fragmentList.add(SettingsFragment())
        fragmentList.add(AboutFragment())

        binding.viewPager.adapter = MyFragmentStateAdapter(mContext, fragmentList)
        binding.viewPager.isUserInputEnabled = false //禁止滑动
        //binding.viewPager.offscreenPageLimit = 2 //缓存当前界面每一侧的界面数
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.navigation.menu[position].setChecked(true)
            }
        })
        binding.navigation.setOnItemSelectedListener { item ->
            binding.viewPager.currentItem = item.order
            true
        }
    }

    override fun initData() {
    }

    override fun initViewObservable() {

    }
}
