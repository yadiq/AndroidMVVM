package com.hqumath.demo.repository;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hqumath.demo.bean.UserInfoEntity;
import com.hqumath.demo.repository.dao.UserInfoDao;
import com.hqumath.demo.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: AppDatabase
 * 作    者: Created by gyd
 * 创建时间: 2019/7/4 11:35
 * 文件描述: 数据库
 * 注意事项: onDestroy时关闭数据库
 * 版权声明:
 * ****************************************************************
 */
@Database(entities = {UserInfoEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static class AppDataBaseHolder {
        private static final AppDatabase instance =
                Room.databaseBuilder(CommonUtil.getContext(), AppDatabase.class, "basic.db")
                        .fallbackToDestructiveMigration()//升级时丢弃原来表
                        .build();
    }

    public static AppDatabase getInstance() {
        return AppDataBaseHolder.instance;
    }

    public abstract UserInfoDao userInfoDao();

    //注意，一般不需要关闭数据库，随进程销毁自动回收。
}
