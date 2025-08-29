package com.hqumath.demo.ui.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hqumath.demo.adapter.MyRecyclerAdapters;
import com.hqumath.demo.base.BaseFragment;
import com.hqumath.demo.bean.UserInfoEntity;
import com.hqumath.demo.databinding.FragmentFollowersBinding;
import com.hqumath.demo.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: FollowersFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述: 使用 Room 持久化存储列表数据
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FollowersFragment extends BaseFragment {

    private FragmentFollowersBinding binding;
    private FollowersViewModel viewModel;
    private MyRecyclerAdapters.FollowRecyclerAdapter recyclerAdapter;
    protected boolean hasRequested;//在onResume中判断是否已经请求过数据。用于懒加载

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.refreshLayout.setOnRefreshListener(v -> viewModel.getFollowers(true));
        binding.refreshLayout.setOnLoadMoreListener(v -> viewModel.getFollowers(false));
    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(requireActivity()).get(FollowersViewModel.class);

        recyclerAdapter = new MyRecyclerAdapters.FollowRecyclerAdapter(mContext, new ArrayList<>());
        recyclerAdapter.setOnItemClickListener((v, position) -> {
            List<UserInfoEntity> list = viewModel.mData.getValue();
            if (list != null && list.size() > position) {
                UserInfoEntity data = list.get(position);
                startActivity(ProfileDetailActivity.getStartIntent(mContext, data.getLogin()));
            }
        });
        binding.recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void initViewObservable() {
        //根据请求调整UI
        viewModel.followersResultCode.observe(this, code -> {
            if (code.equals("0")) {
                if (viewModel.followersRefresh) {
                    if (viewModel.followersNewEmpty) {
                        binding.refreshLayout.finishRefreshWithNoMoreData();//上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishRefresh();
                    }
                } else {
                    if (viewModel.followersNewEmpty) {
                        binding.refreshLayout.finishLoadMoreWithNoMoreData();//上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishLoadMore();
                    }
                }
            } else {
                CommonUtil.toast(viewModel.followersResultMsg);
                if (viewModel.followersRefresh) {
                    binding.refreshLayout.finishRefresh(false);//刷新失败，会影响到上次的更新时间
                } else {
                    binding.refreshLayout.finishLoadMore(false);
                }
            }
        });
        //根据数据库刷新列表
        viewModel.mData.observe(this, list -> {
            recyclerAdapter.setData(list);
            recyclerAdapter.notifyDataSetChanged();
            binding.emptyLayout.llEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
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