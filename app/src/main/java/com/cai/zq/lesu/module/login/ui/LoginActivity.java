package com.cai.zq.lesu.module.login.ui;

import com.cai.zq.lesu.R;
import com.cai.zq.lesu.annotation.ActivityFragmentInject;
import com.cai.zq.lesu.base.BaseActivity;
import com.cai.zq.lesu.module.login.presenter.ILoginPresenterImpl;
import com.cai.zq.lesu.module.login.view.ILoginView;

/**
 * TODO<登陆>
 * Created by digiengine
 * DATE: 16/6/21 下午4:21
 */
@ActivityFragmentInject( contentViewId = R.layout.activity_login )
public class LoginActivity extends BaseActivity<ILoginView, ILoginPresenterImpl> implements ILoginView{
    @Override
    protected ILoginPresenterImpl createPresenter() {
        return new ILoginPresenterImpl();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }
}
