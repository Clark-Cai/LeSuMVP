package com.cai.zq.lesu.base;


import com.cai.zq.lesu.uart.packet.UartPacket;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * TODO<Presenter基础类>
 * Created by digiengine
 * DATE: 16/5/13 下午5:08
 */
public abstract class BasePresenter<T> {
    protected Reference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);  //建立连接
    }

    protected T getView() {
        return mViewRef.get();
    }

//    public boolean isViewAttached() {
//        return mViewRef != null && mViewRef.get() != null;
//    }

    public void detachView() {
        if(mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

//    public abstract void receivePacket(UartPacket packet);
}
