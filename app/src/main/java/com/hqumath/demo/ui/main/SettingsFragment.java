package com.hqumath.demo.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.demo.app.ActivityManager;
import com.hqumath.demo.app.Constant;
import com.hqumath.demo.app.DataStoreKey;
import com.hqumath.demo.base.BaseFragment;
import com.hqumath.demo.databinding.FragmentSettingsBinding;
import com.hqumath.demo.ui.fileupdown.FileUpDownActivity;
import com.hqumath.demo.ui.login.LoginActivity;
import com.hqumath.demo.utils.CommonUtil;
import com.hqumath.demo.utils.DataStoreUtil;
import com.hqumath.demo.utils.SPUtil;

import java.util.Random;

public class SettingsFragment extends BaseFragment {

    private FragmentSettingsBinding binding;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.fileUpDown.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, FileUpDownActivity.class);
            startActivity(intent);
        });
        binding.vCheckUpdate.setOnClickListener(v -> {
            //检查升级
            boolean needUpdate = new Random().nextBoolean();
            if (!needUpdate) {
                CommonUtil.toast("已是最新版本");
                return;
            }
//            SPUtil.getInstance().put(Constant.APK_URL, FileUpDownActivity.url);
//            SPUtil.getInstance().put(Constant.APK_NAME, "AndroidMVP V2.0");
//            DialogUtil alterDialogUtils = new DialogUtil(mContext);
//            alterDialogUtils.setTitle("新版本V2.0");
//            alterDialogUtils.setMessage("适配 Android 11");
//            alterDialogUtils.setTwoConfirmBtn("立即更新", v1 -> {
//                CommonUtil.toast("已在后台下载");
////                mContext.startService(new Intent(mContext, UpdateService.class));
//            });
//            alterDialogUtils.setTwoCancelBtn("下次提醒", v2 -> {
//            });
//            alterDialogUtils.setCancelable(false);
//            alterDialogUtils.show();
        });
        binding.vLogout.setOnClickListener(v -> {
            CommonUtil.toast("已退出登录");
            //保留账号，清空全部数据
            String username = DataStoreUtil.INSTANCE.getData(DataStoreKey.USER_NAME, "");
            DataStoreUtil.INSTANCE.clearAllData();
            DataStoreUtil.INSTANCE.putData(DataStoreKey.USER_NAME, username);
            //跳转登录界面
            ActivityManager.INSTANCE.finishAllActivities();
            startActivity(new Intent(mContext, LoginActivity.class));

            mContext.finish(); //旧代码
        });
    }

    @Override
    protected void initData() {
    }

}
