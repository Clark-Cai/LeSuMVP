package com.cai.zq.lesu.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * TODO<bitmap工具类>
 * Created by digiengine
 * DATE: 16/5/13 上午9:54
 */
public class BitmapUtil {


    /**
     * 将资源图片压缩
     * @param res   resource
     * @param resId  资源图片id
     * @param reqWidth  要求的宽度
     * @param reqHeight  要求的高度
     * @return bitmap
     */
    public static Bitmap decodeSmapledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        if(reqWidth==0 || reqHeight==0){
            return 1;
        }

        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSample = 1;

        if(height>reqHeight || width>reqWidth){
            final int halfHeight = height/2;
            final int halfWidth = width/2;

            while((halfHeight/inSample) >= reqHeight && (halfWidth/inSample) >= reqHeight){
                inSample *= 2;
            }
        }

        return inSample;
    }
}
