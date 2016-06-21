package com.cai.zq.lesu.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cai.zq.lesu.R;
import com.cai.zq.lesu.annotation.ActivityFragmentInject;

/**
 * TODO<>
 * Created by digiengine
 * DATE: 16/5/17 下午4:49
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    protected int mContentViewId;
    protected Toolbar mToolBar;
    protected View mView;
    protected T mPresenter;  //Presenter 对象


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(getClass().isAnnotationPresent(ActivityFragmentInject.class)){
            ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
        }else {
            throw new RuntimeException("Class must add annotation of ActivityFragmentInjectInitParams.class!");
        }
        mView = inflater.inflate(mContentViewId, container, false);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        mPresenter = createPresenter();
        mPresenter.attachView((V) this);    //View与Presenter 建立关联

        initViews();
        initEvents();
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();

    }

    public View findViewById(int id){
        return mView.findViewById(id);
    }

    protected abstract T createPresenter();

    protected abstract void initViews();

    protected abstract void initEvents();

    /** 通过Class跳转界面 **/
    protected void startActivity(Context context, Class<?> cls) {
        Intent in = new Intent(context, cls);
        startActivity(in);
    }

    /** 含有Bundle通过Class跳转界面 **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
