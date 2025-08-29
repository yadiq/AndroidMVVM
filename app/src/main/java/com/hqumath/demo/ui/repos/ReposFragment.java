package com.hqumath.demo.ui.repos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.demo.adapter.MyFragmentPagerAdapter;
import com.hqumath.demo.base.BaseFragment;
import com.hqumath.demo.databinding.FragmentReposBinding;

import java.util.ArrayList;
import java.util.List;

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
public class ReposFragment extends BaseFragment {

    private FragmentReposBinding binding;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReposBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyReposFragment());
        fragmentList.add(new StarredFragment());

        List<String> titles = new ArrayList<>();
        titles.add("MyRepos");
        titles.add("Starred");

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        pagerAdapter.setData(fragmentList, titles);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(fragmentList.size());
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }
}