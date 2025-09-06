package com.hqumath.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.hqumath.github.R;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: PermissionUtils
 * 作    者: Created by gyd
 * 创建时间: 2019/2/27 15:12
 * 文件描述: 权限获取工具
 * 注意事项: 详见：https://github.com/yanzhenjie/AndPermission
 * 版权声明:
 * ****************************************************************
 */
public class PermissionUtil {
    public static final int REQUEST_CODE_SETTING = 1;

    //权限分组,尽量少用
    /*Permission.Group.CAMERA 相机
    Permission.Group.CONTACTS 通讯录
    Permission.Group.LOCATION 位置信息
    Permission.Group.CALENDAR 日历
    Permission.Group.MICROPHONE 麦克风
    Permission.Group.STORAGE 存储空间
    Permission.Group.PHONE 电话
    Permission.Group.SMS 短信*/

    //使用例子
    /*AndPermission.with(activity)
            .runtime()
            .permission(Permission.Group.STORAGE)
            .onGranted((permissions) -> upload())
            .onDenied((permissions) -> {
                if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {
                    PermissionUtils.showSettingDialog(mContext, permissions);//自定义弹窗 去设置界面
                }
            }).start();*/

    /**
     * 弹窗，去设置界面
     */
    public static void showSettingDialog(Activity activity, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(activity, permissions);
        String message = activity.getString(R.string.permission_always_failed_message, TextUtils.join("\n",
                permissionNames));
        new AlertDialog.Builder(activity).setCancelable(false)
                .setTitle(R.string.permission_failed_title)
                .setMessage(message)
                .setPositiveButton(R.string.permission_failed_setting,
                        ((dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                            activity.startActivity(intent);
                        }))
                .setNegativeButton(R.string.permission_failed_cancel, null).show();
    }

    /**
     * 弹窗，允许安装应用
     */
    public static void showInstallDialog(Context context, File data, RequestExecutor executor) {
        new AlertDialog.Builder(context).setCancelable(false)
                .setTitle(R.string.permission_title)
                .setMessage(R.string.permission_install_failed_message)
                .setPositiveButton(R.string.permission_failed_setting,
                        ((dialog, which) -> {
                            executor.execute();
                        }))
                .setNegativeButton(R.string.permission_failed_cancel, ((dialog, which) -> {
                    executor.cancel();
                })).show();
    }

    /**
     * 检查权限
     * @return 是否授权
     */
    /*public static boolean check(Activity activity, String permission) {
        boolean isGranted =
                ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_PERMISSION_CODE);
        }
        return isGranted;
    }*/
}
