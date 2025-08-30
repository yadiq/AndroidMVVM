package com.hqumath.demo.ui.repos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hqumath.demo.adapter.MyRecyclerAdapters;
import com.hqumath.demo.adapter.VerticalSpaceItemDecoration;
import com.hqumath.demo.base.BaseFragmentOld;
import com.hqumath.demo.bean.ReposEntity;
import com.hqumath.demo.databinding.FragmentSwipeListBinding;
import com.hqumath.demo.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: StarredFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class StarredFragment extends BaseFragmentOld {

    private FragmentSwipeListBinding binding;
    private ReposViewModel viewModel;
    private MyRecyclerAdapters.ReposRecyclerAdapter recyclerAdapter;
    protected boolean hasRequested;//在onResume中判断是否已经请求过数据。用于懒加载

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSwipeListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.refreshLayout.setOnRefreshListener(v -> viewModel.getStarred(true));
        binding.refreshLayout.setOnLoadMoreListener(v -> viewModel.getStarred(false));
    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(requireActivity()).get(ReposViewModel.class);

        recyclerAdapter = new MyRecyclerAdapters.ReposRecyclerAdapter(mContext, viewModel.starredData);
        recyclerAdapter.setOnItemClickListener((v, position) -> {
            ReposEntity data = viewModel.starredData.get(position);
            startActivity(ReposDetailActivity.getStartIntent(mContext, data.getName(), data.getOwner().getLogin()));
        });
        binding.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(CommonUtil.dp2px(mContext, 4f)));
        binding.recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void initViewObservable() {
        viewModel.starredResultCode.observe(this, code -> {
            if (code.equals("0")) {
                recyclerAdapter.notifyDataSetChanged();
                if (viewModel.starredRefresh) {
                    if (viewModel.starredNewEmpty) {
                        binding.refreshLayout.finishRefreshWithNoMoreData();//上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishRefresh();
                    }
                } else {
                    if (viewModel.starredNewEmpty) {
                        binding.refreshLayout.finishLoadMoreWithNoMoreData();//上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishLoadMore();
                    }
                }
            } else {
                CommonUtil.toast(viewModel.starredResultMsg);
                if (viewModel.starredRefresh) {
                    binding.refreshLayout.finishRefresh(false);//刷新失败，会影响到上次的更新时间
                } else {
                    binding.refreshLayout.finishLoadMore(false);
                }
            }
            binding.emptyLayout.llEmpty.setVisibility(viewModel.starredData.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!hasRequested) {
            hasRequested = true;
            binding.refreshLayout.autoRefresh();//触发自动刷新
        }
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