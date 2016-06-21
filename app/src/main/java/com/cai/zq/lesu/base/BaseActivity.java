package com.cai.zq.lesu.base;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cai.zq.lesu.R;
import com.cai.zq.lesu.annotation.ActivityFragmentInject;
import com.cai.zq.lesu.uart.packet.RecManager;
import com.cai.zq.lesu.uart.packet.UartPacket;
import com.cai.zq.lesu.uart.service.UartService;


/**
 * TODO<Activity基础类>
 * Created by digiengine
 * DATE: 16/5/13 下午5:13
 */
public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends AppCompatActivity implements BaseView {
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected T mPresenter;  //Presenter 对象
    protected int mContentViewId;
    protected Toolbar mToolbar;

    protected UartService mService = null;
//    private BluetoothAdapter mBtAdapter = null;
    private static final int REQUEST_ENABLE_BT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getClass().isAnnotationPresent(ActivityFragmentInject.class)){
            ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
        }else {
            throw new RuntimeException("Class must add annotation of ActivityFragmentInjectInitParams.class!");
        }
        setContentView(mContentViewId);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);    //View与Presenter 建立关联

        initViews();
        initEvents();
        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();

        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService= null;
    }

    protected abstract T createPresenter();

    protected abstract void initViews();
    protected abstract void initEvents();


    //UART service connected/disconnected
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            Log.d(TAG, "onServiceConnected mService= " + mService);
            initBle();
        }

        public void onServiceDisconnected(ComponentName classname) {
            mService = null;
        }
    };

    private void bindService(){
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    /**
     * 初始化ble
     */
    public void initBle(){
        if (!mService.initialize()) {
            Log.e(TAG, "Unable to initialize Bluetooth");
            finish();
        }else {
            BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            if(!mBtAdapter.isEnabled()){
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action){
                case UartService.ACTION_GATT_NO_DEVICE:
                    Log.i(TAG, "ble = ACTION_GATT_NO_DEVICE ");
                    showConnectFailed();
                    break;
                case UartService.ACTION_GATT_CONNECTING:
                    Log.i(TAG, "ble = ACTION_GATT_CONNECTING ");
                    showConnecting();
                    break;
                case UartService.ACTION_GATT_CONNECTED:
                    showConnectSuccess();
                    break;
                case UartService.ACTION_GATT_DISCONNECTED:
                    initBle();
                    break;
                case UartService.ACTION_GATT_SERVICES_DISCOVERED:
                    break;
                case UartService.ACTION_DATA_AVAILABLE:
                    byte[] byteValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                    UartPacket packet = RecManager.getInstance().receiveData(byteValue);
//                    if(packet != null){
//                        mPresenter.receivePacket(packet);
//                    }
                    break;
                case UartService.DEVICE_DOES_NOT_SUPPORT_UART:
                    mService.disconnect();
                    break;

            }

        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_NO_DEVICE);
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTING);
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }
    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    mService.scanLeDevice(true);
                }else {
                    mPresenter.getView().showConnectFailed();
                }
                break;
        }
    }


    @Override
    public void showConnecting() {

    }

    @Override
    public void showConnectSuccess() {

    }

    @Override
    public void showConnectFailed() {

    }
}
