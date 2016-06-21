package com.cai.zq.lesu.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * TODO<获取屏幕参数>
 * Created by digiengine
 * DATE: 16/5/11 下午4:55
 */
public class ScreenUtil {

    /**
     * 获取屏幕宽高
     *
     * @param context 上下文
     * @return DisplayMetrics
     */
    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }


    /**
     * 获取屏幕像素密度
     *
     * @param context 上下文
     * @return float
     */
    public static float getDeviceDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

}
