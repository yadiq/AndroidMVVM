/*
 * Copyright (c) 1992-2015, ZheJiang Dahua Technology Stock CO.LTD.
 * All Rights Reserved.
 */

package com.hqumath.demo.utils;

import android.util.Log;

import com.hqumath.demo.BuildConfig;

/**
 * Log统一管理类
 */
public class LogUtil {

    private static boolean isDebug = BuildConfig.DEBUG;//是否需要打印bug，buildTypes.debug中配置

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean b) {
        LogUtil.isDebug = b;
    }

    private static final String TAG = "DEBUG";

    // *********************************下面四个是默认tag的函数**********************************//
    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
        }
    }

    public static void v(String msg) {
        if (isDebug) {
            Log.v(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            Log.w(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
        }
    }

    // *********************************下面四个是默认tag的函数**********************************//
    public static void i(String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.i(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.i(TAG, msg);
            }
        }
    }

    public static void d(String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.d(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.d(TAG, msg);
            }
        }
    }

    public static void e(String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.e(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.e(TAG, msg);
            }
        }
    }

    public static void v(String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.v(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.v(TAG, msg);
            }
        }
    }

    public static void w(String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.w(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.w(TAG, msg);
            }
        }
    }

    // *********************************下面是传入异常的函数*************************************//
    public static void i(String msg, Throwable tr) {
        if (isDebug) {
            Log.i(TAG, msg, tr);
        }
    }

    public static void d(String msg, Throwable tr) {
        if (isDebug) {
            Log.d(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
        }
    }

    public static void e(String msg, Throwable tr) {
        if (isDebug) {
            Log.e(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
        }
    }

    public static void v(String msg, Throwable tr) {
        if (isDebug) {
            Log.v(TAG, msg, tr);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (isDebug) {
            Log.w(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
        }
    }

    // *********************************下面是传入异常的函数*************************************//
    public static void i(String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.i(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.i(TAG, msg, tr);
            }
        }
    }

    public static void d(String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.d(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.d(TAG, msg, tr);
            }
        }
    }

    public static void e(String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.e(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.e(TAG, msg, tr);
            }
        }
    }

    public static void v(String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.v(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.v(TAG, msg, tr);
            }
        }
    }

    public static void w(String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.w(TAG, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.w(TAG, msg, tr);
            }
        }
    }

    // *********************************下面是传入自定义tag的函数********************************//
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
        }
    }

    // *********************************下面是传入自定义tag的函数********************************//
    public static void i(String tag, String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.i(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.i(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.d(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.e(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.e(tag, msg);
            }
        }
    }

    public static void v(String tag, String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.v(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.v(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.w(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()));
            } else {
                Log.w(tag, msg);
            }
        }
    }

    // ****************************下面是传入自定义tag及异常的函数*******************************//
    public static void i(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.i(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.d(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.v(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.w(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
        }
    }

    // ****************************下面是传入自定义tag及异常的函数*******************************//
    public static void i(String tag, String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.i(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.i(tag, msg, tr);
            }
        }
    }

    public static void d(String tag, String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.d(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.d(tag, msg, tr);
            }
        }
    }

    public static void e(String tag, String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.e(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.e(tag, msg, tr);
            }
        }
    }

    public static void v(String tag, String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.v(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.v(tag, msg, tr);
            }
        }
    }

    public static void w(String tag, String msg, Throwable tr, boolean isShowLineNo) {
        if (isDebug) {
            if (isShowLineNo) {
                Log.w(tag, msg + buildStackTraceElements(Thread.currentThread().getStackTrace()), tr);
            } else {
                Log.w(tag, msg, tr);
            }
        }
    }

    // ******************************************************************************************//

    /**
     * 获取当前代码所在类、方法、行数
     *
     * @return 返回当前线程栈信息
     */
    private static String buildStackTraceElements(StackTraceElement[] elements) {
        StringBuilder sb = new StringBuilder();
        sb.append("  \t===> ");
        if (elements.length < 4) {
            Log.e(TAG, "Stack to shallow");
        } else {
            String fullClassName = elements[3].getClassName();
            sb.append(fullClassName.substring(fullClassName.lastIndexOf(".") + 1)).append(".").append(elements[3]
                    .getMethodName()).append("(...)").append(" [").append(elements[3].getLineNumber()).append("行]");
        }
        return sb.toString();
    }

}