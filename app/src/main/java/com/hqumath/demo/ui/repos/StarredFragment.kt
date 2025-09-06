package com.hqumath.demo.ui.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hqumath.demo.adapter.MyRecyclerAdapters
import com.hqumath.demo.adapter.VerticalSpaceItemDecoration
import com.hqumath.demo.base.BaseFragment
import com.hqumath.github.databinding.FragmentStarredBinding
import com.hqumath.demo.utils.CommonUtil

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/8/31 17:19
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
class StarredFragment : BaseFragment() {
    private lateinit var binding: FragmentStarredBinding
    private lateinit var viewModel: StarredViewModel

    private var recyclerAdapter: MyRecyclerAdapters.ReposRecyclerAdapter? = null
    private var needRequest: Boolean = true //在onResume中判断，是否需要请求数据

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarredBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        binding.refreshLayout.setOnRefreshListener { viewModel.getStarred(true) }
        binding.refreshLayout.setOnLoadMoreListener { viewModel.getStarred(false) }
    }

    override fun initData() {
        viewModel = ViewModelProvider(this)[StarredViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        recyclerAdapter = MyRecyclerAdapters.ReposRecyclerAdapter(mContext, viewModel.list)
        recyclerAdapter?.setOnItemClickListener { _: View?, position: Int ->
            if (viewModel.list.size <= position)
                return@setOnItemClickListener
            val data = viewModel.list[position]
            mContext.startActivity(
                ReposDetailActivity.getStartIntent(
                    mContext,
                    data.getName(),
                    data.getOwner().getLogin()
                )
            )
        }
        binding.recyclerView.addItemDecoration(
            VerticalSpaceItemDecoration(CommonUtil.dp2px(mContext, 4f))
        )
        binding.recyclerView.adapter = recyclerAdapter
    }

    override fun initViewObservable() {
        viewModel.isLoading.observe(this) { b ->
            if (b) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
            }
        }
        //网络请求错误号
        viewModel.listResultCode.observe(this) { code: String ->
            if (code == "0") {
                recyclerAdapter?.notifyDataSetChanged()
                if (viewModel.listRefresh) {
                    if (viewModel.listNewEmpty) {
                        binding.refreshLayout.finishRefreshWithNoMoreData() //上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishRefresh()
                    }
                } else {
                    if (viewModel.listNewEmpty) {
                        binding.refreshLayout.finishLoadMoreWithNoMoreData() //上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishLoadMore()
                    }
                }
            } else {
                if (viewModel.listRefresh) {
                    binding.refreshLayout.finishRefresh(false) //刷新失败，会影响到上次的更新时间
                } else {
                    binding.refreshLayout.finishLoadMore(false)
                }
            }
            binding.emptyLayout.llEmpty.visibility =
                if (viewModel.list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if (needRequest) {
            needRequest = false
            viewModel.isLoading.postValue(true)
            viewModel.getStarred(true)
        }
    }
}