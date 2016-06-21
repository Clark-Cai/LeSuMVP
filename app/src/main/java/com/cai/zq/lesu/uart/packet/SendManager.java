package com.cai.zq.lesu.uart.packet;


import com.cai.zq.lesu.common.Constant;
import com.cai.zq.lesu.uart.service.UartService;

/**
 * TODO<>
 * Created by digiengine
 * DATE: 16/5/19 下午3:52
 */
public class SendManager {
    private static SendManager sInstance = null;

    private SendManager(){

    }

    public static SendManager getInstance(){
        if(sInstance == null){
            synchronized (SendManager.class){
                if(sInstance == null){
                    sInstance = new SendManager();
                }
            }
        }
        return sInstance;
    }


    /**
     * 发送指令
     * @param service ble service
     * @param cmd     command
     * @param data     value
     */
    private void sendCmd(UartService service, byte cmd, byte[] data){

        byte length = (byte)(data.length + 2);

        byte[] dataValue = new byte[length+3];
        byte checksum ;
        dataValue[0] = Constant.FIRST_BYTE;
        dataValue[1] = Constant.SECOND_BYTE;
        dataValue[2] = length;
        checksum = length;
        dataValue[3] = cmd;
        checksum = (byte)(checksum^cmd);

        for (int i=0; i<data.length; i++){
            dataValue[4+i] = data[i];
            checksum = (byte)(checksum^data[i]);
        }
        dataValue[length+2] = checksum;
        service.writeRXCharacteristic(dataValue);
    }


    public void sendCartStatusCmd(UartService service){


         byte cmd = Constant.TX_CART_STATUS;
         byte[] data = new byte[20];




         sendCmd(service, cmd, data);
    }
}
