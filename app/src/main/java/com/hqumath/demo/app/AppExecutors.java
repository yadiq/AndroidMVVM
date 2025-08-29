/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hqumath.demo.app;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 全局线程池
 * Global executor pools for the whole application.
 * 对任务进行分组，io操作和网络请求可同时执行
 * 用法：AppExecutors.getInstance().driveWorkThread().execute(() -> {});
 */
public class AppExecutors {

    //双重校验锁
    /*private volatile static AppExecutors sInstance;

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new AppExecutors();
                }
            }
        }
        return sInstance;
    }*/

    //静态内部类
    private static class AppExecutorsHolder {
        private static final AppExecutors instance = new AppExecutors();
    }

    public static AppExecutors getInstance() {
        return AppExecutorsHolder.instance;
    }

    private MainThreadExecutor mainThread;//ui线程操作
    private ExecutorService workThread;//工作线程池，执行普通任务。例如：网络通讯和多媒体操作
    private ScheduledExecutorService scheduledWork;//循环线程池，执行普通循环任务。

    public MainThreadExecutor mainThread() {
        if (mainThread == null) {
            mainThread = new MainThreadExecutor();
        }
        return mainThread;
    }

    public static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }

        public void postDelayed(Runnable command, int delayMillis) {
            mainThreadHandler.postDelayed(command, delayMillis);
        }
    }

    public ExecutorService workThread() {
        if (workThread == null || workThread.isShutdown())
            workThread = Executors.newFixedThreadPool(8);//骁龙888八个CPU核心
        return workThread;
    }

    public ScheduledExecutorService scheduledWork() {
        if (scheduledWork == null || scheduledWork.isShutdown())
            scheduledWork = Executors.newScheduledThreadPool(4);
        return scheduledWork;
    }

    public void shutdownWorkThread() {
        if (workThread != null) {
            workThread.shutdown();
            workThread = null;
        }
    }

    public void shutdownScheduledWork() {
        if (scheduledWork != null) {
            scheduledWork.shutdown();
            scheduledWork = null;
        }
    }
}
