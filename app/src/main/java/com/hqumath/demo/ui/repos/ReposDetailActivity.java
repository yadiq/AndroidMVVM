package com.hqumath.demo.ui.repos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.hqumath.demo.adapter.MyRecyclerAdapters;
import com.hqumath.demo.base.BaseActivityOld;
import com.hqumath.github.databinding.ActivityReposDetailBinding;
import com.hqumath.demo.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: ReposDetailActivity
 * 作    者: Created by gyd
 * 创建时间: 2020/9/4 16:33
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class ReposDetailActivity extends BaseActivityOld {
    private ActivityReposDetailBinding binding;
    private ReposDetailViewModel viewModel;
    private MyRecyclerAdapters.CommitsRecyclerAdapter recyclerAdapter;

    public static Intent getStartIntent(Context mContext, String mReposName, String mUserName) {
        Intent intent = new Intent(mContext, ReposDetailActivity.class);
        intent.putExtra("ReposName", mReposName);
        intent.putExtra("UserName", mUserName);
        return intent;
    }

    @Override
    public View initContentView(Bundle savedInstanceState) {
        binding = ActivityReposDetailBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.refreshLayout.setOnRefreshListener(v -> {
            viewModel.getReposInfo();
            viewModel.getCommits(true);
        });
        binding.refreshLayout.setOnLoadMoreListener(v -> viewModel.getCommits(false));
    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(this).get(ReposDetailViewModel.class);
        binding.setViewModel(viewModel);

        //data
        viewModel.userName = getIntent().getStringExtra("UserName");
        viewModel.reposName = getIntent().getStringExtra("ReposName");
        setTitle(viewModel.reposName);

        recyclerAdapter = new MyRecyclerAdapters.CommitsRecyclerAdapter(mContext, viewModel.commitData);
        binding.recyclerView.setAdapter(recyclerAdapter);
        //仓库详情
        viewModel.getReposInfo();
        //提交记录
        binding.refreshLayout.autoRefresh();//触发自动刷新
    }

    @Override
    protected void initViewObservable() {
        viewModel.reposResultCode.observe(this, code -> {
            if (code.equals("0")) {
                if (!TextUtils.isEmpty(viewModel.avatar_url))
                    Glide.with(mContext).load(viewModel.avatar_url).into(binding.ivAvatarBg);
            } else {
                CommonUtil.toast(viewModel.reposResultMsg);
            }
        });

        viewModel.commitResultCode.observe(this, code -> {
            if (code.equals("0")) {
                recyclerAdapter.notifyDataSetChanged();
                if (viewModel.commitRefresh) {
                    if (viewModel.commitNewEmpty) {
                        binding.refreshLayout.finishRefreshWithNoMoreData();//上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishRefresh();
                    }
                } else {
                    if (viewModel.commitNewEmpty) {
                        binding.refreshLayout.finishLoadMoreWithNoMoreData();//上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishLoadMore();
                    }
                }
            } else {
                CommonUtil.toast(viewModel.commitResultMsg);
                if (viewModel.commitRefresh) {
                    binding.refreshLayout.finishRefresh(false);//刷新失败，会影响到上次的更新时间
                } else {
                    binding.refreshLayout.finishLoadMore(false);
                }
            }
            binding.emptyLayout.llEmpty.setVisibility(viewModel.commitData.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.dispose();
            viewModel = null;
        }
        super.onDestroy();
    }
}
