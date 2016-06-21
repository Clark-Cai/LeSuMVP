package com.cai.zq.lesu.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * TODO<关闭工具类>
 * Created by digiengine
 * DATE: 16/5/12 下午1:32
 */
public class CloseUtil {

    public static void closeQuietly(Closeable closeable){
        if(null != closeable){
            try {
                closeable.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
