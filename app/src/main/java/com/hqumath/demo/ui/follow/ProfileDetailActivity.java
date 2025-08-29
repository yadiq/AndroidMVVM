package com.hqumath.demo.ui.follow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hqumath.demo.base.BaseActivityOld;
import com.hqumath.demo.databinding.ActivityProfileDetailBinding;
import com.hqumath.demo.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: ProfileActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/7/26 10:04
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ProfileDetailActivity extends BaseActivityOld {

    private ActivityProfileDetailBinding binding;
    private ProfileDetailViewModel viewModel;

    public static Intent getStartIntent(Context mContext, String mUserName) {
        Intent intent = new Intent(mContext, ProfileDetailActivity.class);
        intent.putExtra("UserName", mUserName);
        return intent;
    }

    @Override
    public View initContentView(Bundle savedInstanceState) {
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
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
            viewModel.getUserInfo();
        });
    }

    @Override
    public void initData() {
        viewModel = new ViewModelProvider(this).get(ProfileDetailViewModel.class);
        binding.setViewModel(viewModel);
        //data
        viewModel.userName = getIntent().getStringExtra("UserName");
        setTitle(viewModel.userName);
        //详情
        binding.refreshLayout.autoRefresh();//触发自动刷新
    }

    public void initViewObservable() {
        viewModel.userInfoResultCode.observe(this, code -> {
            if (code.equals("0")) {
                if (!TextUtils.isEmpty(viewModel.avatar_url)) {
                    Glide.with(mContext).load(viewModel.avatar_url).into(binding.ivAvatarBg);
                    Glide.with(mContext).load(viewModel.avatar_url)
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形
                            .into(binding.ivAvatar);
                    binding.refreshLayout.finishRefresh();
                }
            }  else {
                CommonUtil.toast(viewModel.userInfoResultMsg);
                binding.refreshLayout.finishRefresh(false);
            }
        });
        viewModel.company.observe(this, value -> binding.tvCompany.setVisibility(TextUtils.isEmpty(value) ? View.GONE : View.VISIBLE));
        viewModel.email.observe(this, value -> binding.tvEmail.setVisibility(TextUtils.isEmpty(value) ? View.GONE : View.VISIBLE));
        viewModel.blog.observe(this, value -> binding.tvBlog.setVisibility(TextUtils.isEmpty(value) ? View.GONE : View.VISIBLE));
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
