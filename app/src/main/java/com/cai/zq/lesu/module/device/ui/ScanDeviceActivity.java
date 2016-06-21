package com.cai.zq.lesu.module.device.ui;

import com.cai.zq.lesu.R;
import com.cai.zq.lesu.annotation.ActivityFragmentInject;
import com.cai.zq.lesu.base.BaseActivity;
import com.cai.zq.lesu.module.device.presenter.IScanDevicePresenterImpl;
import com.cai.zq.lesu.module.device.view.IScanDeviceView;

/**
 * TODO<扫描设备>
 * Created by digiengine
 * DATE: 16/6/21 下午3:58
 */
@ActivityFragmentInject( contentViewId = R.layout.activity_scan_device )
public class ScanDeviceActivity extends BaseActivity<IScanDeviceView, IScanDevicePresenterImpl> implements IScanDeviceView {
    @Override
    protected IScanDevicePresenterImpl createPresenter() {
        return new IScanDevicePresenterImpl();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

}
