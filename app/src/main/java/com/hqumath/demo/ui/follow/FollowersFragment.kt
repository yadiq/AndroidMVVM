package com.hqumath.demo.ui.follow

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hqumath.github.R
import com.hqumath.demo.adapter.MyRecyclerAdapters
import com.hqumath.demo.adapter.VerticalSpaceItemDecoration
import com.hqumath.demo.base.BaseFragment
import com.hqumath.demo.bean.UserInfoEntity
import com.hqumath.github.databinding.FragmentFollowersBinding
import com.hqumath.demo.utils.CommonUtil
import com.yanzhenjie.recyclerview.SwipeMenu
import com.yanzhenjie.recyclerview.SwipeMenuBridge
import com.yanzhenjie.recyclerview.SwipeMenuItem

/**
 * 人员列表界面
 * 使用数据库缓存数据
 */
class FollowersFragment : BaseFragment() {
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var viewModel: FollowersViewModel

    private var recyclerAdapter: MyRecyclerAdapters.FollowRecyclerAdapter? = null
    private var needRequest: Boolean = true //在onResume中判断，是否需要请求数据

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        binding.refreshLayout.setOnRefreshListener { viewModel.getFollowers(true) }
        binding.refreshLayout.setOnLoadMoreListener { viewModel.getFollowers(false) }

        binding.recyclerView.setSwipeMenuCreator { leftMenu: SwipeMenu, rightMenu: SwipeMenu, position: Int ->
            val deleteItem: SwipeMenuItem =
                SwipeMenuItem(mContext).setBackground(R.color.swipe_menu_green)
                    .setText("详情")
                    .setTextColor(Color.WHITE)
                    .setWidth(CommonUtil.dp2px(mContext, 70f))
                    .setHeight(CommonUtil.dp2px(mContext, 55f))
            rightMenu.addMenuItem(deleteItem)
        }
        binding.recyclerView.setOnItemMenuClickListener { menuBridge: SwipeMenuBridge, position: Int ->
            menuBridge.closeMenu()

            val list = viewModel.daoList.value
            if (list == null || list.size <= position)
                return@setOnItemMenuClickListener
            val data = list[position]
            mContext.startActivity(ProfileDetailActivity.getStartIntent(mContext, data.login))
        }
    }

    override fun initData() {
        viewModel = ViewModelProvider(this)[FollowersViewModel::class.java]

        recyclerAdapter = MyRecyclerAdapters.FollowRecyclerAdapter(
            mContext,
            ArrayList() //viewModel.daoList.value
        )
        recyclerAdapter?.setOnItemClickListener { _: View?, position: Int ->
            val list = viewModel.daoList.value
            if (list == null || list.size <= position)
                return@setOnItemClickListener
            val data = list[position]
            mContext.startActivity(ProfileDetailActivity.getStartIntent(mContext, data.login))
        }
        binding.recyclerView.addItemDecoration(VerticalSpaceItemDecoration(CommonUtil.dp2px(mContext, 4f)))
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
                //recyclerAdapter?.notifyDataSetChanged()
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
            //binding.emptyLayout.llEmpty.visibility = if (viewModel.daoList.value!!.isEmpty()) View.VISIBLE else View.GONE
        }

        //根据数据库刷新列表
        viewModel.daoList.observe(this, { list: List<UserInfoEntity?> ->
            recyclerAdapter?.setData(list)
            recyclerAdapter?.notifyDataSetChanged()
            binding.emptyLayout.llEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    override fun onResume() {
        super.onResume()
        if (needRequest) {
            needRequest = false
            viewModel.isLoading.postValue(true)
            viewModel.getFollowers(true)
        }
    }
}