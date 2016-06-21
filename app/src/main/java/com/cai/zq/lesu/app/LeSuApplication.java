package com.cai.zq.lesu.app;

import android.app.Application;
import android.content.Context;

import com.cai.zq.lesu.common.Constant;
import com.cai.zq.lesu.common.CrashHandler;
import com.cai.zq.lesu.utils.FontUtils;
import com.cai.zq.lesu.utils.LogUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * TODO<application>
 * Created by digiengine
 * DATE: 16/6/21 下午2:58
 */
public class LeSuApplication extends Application {

    private static final boolean mIsShowLog = true;


    @Override
    public void onCreate() {
        super.onCreate();

        //设置应用字体
        FontUtils.getInstance().replaceSystemDefaultFontFromAsset(this, Constant.APP_FONT_PATH);
        CrashHandler crashHandler = CrashHandler.getsInstance();
        crashHandler.init(this);
        LogUtil.setLogStatus(mIsShowLog);

        initImageLoader(getApplicationContext());

    }


    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(4)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024))
                .memoryCacheSize(4 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .diskCacheFileCount(100)  //缓存文件的数量
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
