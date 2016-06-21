package com.cai.zq.lesu.utils;

import android.util.Log;

/**
 * TODO<log工具类>
 * Created by digiengine
 * DATE: 16/5/11 下午4:55
 */

public class LogUtil {

    private static boolean isShow = true; // 是否打印日志

    public static void setLogStatus(boolean flag){
        isShow = flag;
    }

    public static void d(String tag, String msg) {
        if (isShow)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isShow)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isShow)
            Log.v(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isShow)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isShow)
            Log.w(tag, msg);
    }

    public static void wtf(String tag, String msg) {
        if (isShow)
            Log.wtf(tag, msg);
    }

}
