package com.cai.zq.lesu.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TODO<crash监听类>
 * Created by digiengine
 * DATE: 16/5/6 下午4:18
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static final String TAG = CrashHandler.class.getSimpleName();
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath()+"/LeSu/Crash/Ø";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".log";

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler(){

    }

    public static CrashHandler getsInstance(){
        return sInstance;
    }

    public void init(Context context){
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try{
            //保存到SD卡
            dumpExceptionToSDCard(ex);
            //上传到服务器
            uploadExceptionToServer();
        }catch (IOException e){
            e.printStackTrace();
        }

        ex.printStackTrace();

        if(mDefaultCrashHandler != null){
            mDefaultCrashHandler.uncaughtException(thread,ex);
        }else {
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * crash信息保存在SD卡
     * @param ex  异常
     * @throws IOException
     */
    private void dumpExceptionToSDCard(Throwable ex) throws IOException{
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            if(DEBUG){
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }

        File dir = new File(PATH);
        if(!dir.exists()){
          boolean isSuccess = dir.mkdirs();
            if(!isSuccess){
                return;
            }
        }

        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss", Locale.CHINA).format(new Date(current));
        File file = new File(PATH +FILE_NAME+time+FILE_NAME_SUFFIX);

        try{
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter pw = new PrintWriter(bufferedWriter);
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "dump crash info failed");
        }
    }


    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException{
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        pw.print("Model: ");
        pw.println(Build.MODEL);

        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);

    }

    private void uploadExceptionToServer() {

    }
}
