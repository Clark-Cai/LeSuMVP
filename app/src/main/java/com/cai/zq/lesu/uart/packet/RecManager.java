package com.cai.zq.lesu.uart.packet;


import com.cai.zq.lesu.common.Constant;
import com.cai.zq.lesu.utils.LogUtil;

/**
 * TODO<>
 * Created by digiengine
 * DATE: 16/5/19 下午3:52
 */
public class RecManager {
    private static final String TAG = RecManager.class.getSimpleName();


    private static RecManager sInstance = null;
    private DataState dataState = DataState.FIRST_HEAD;
    private UartPacket uartPacket = new UartPacket();

    private enum DataState {
        FIRST_HEAD, SECOND_HEAD, LENGTH_STATE, CMD_STATE, DATA_STATE, CHECK_SUM
    }

    private RecManager() {

    }

    public static RecManager getInstance() {
        if (sInstance == null) {
            synchronized (RecManager.class) {
                if (sInstance == null) {
                    sInstance = new RecManager();
                }
            }
        }
        return sInstance;
    }

    public UartPacket receiveData(byte[] data) {
        byte checksum = 0;
        byte length = 0;
        byte count = 0;
        byte[] packetData = new byte[20];
        UartPacket packet = null;



        for (byte dataByte : data) {

            switch (dataState) {
                case FIRST_HEAD:
                    if (dataByte == Constant.FIRST_BYTE) {
                        dataState = DataState.SECOND_HEAD;
                    }
                    break;

                case SECOND_HEAD:
                    if (dataByte == Constant.SECOND_BYTE) {
                        dataState = DataState.LENGTH_STATE;
                    } else {
                        dataState = DataState.FIRST_HEAD;
                    }
                    break;

                case LENGTH_STATE:
                    uartPacket.setCmdLength(dataByte);
                    checksum = dataByte;
                    dataState = DataState.CMD_STATE;
                    length = dataByte;
                    count++;
                    break;

                case CMD_STATE:
                    uartPacket.setCmd(dataByte);
                    checksum = (byte) (checksum ^ dataByte);
                    if (dataByte > 2) {  //no data
                        dataState = DataState.DATA_STATE;
                    } else {
                        dataState = DataState.CHECK_SUM;
                    }
                    count++;
                    break;

                case DATA_STATE:
                    checksum = (byte) (checksum ^ dataByte);
                    packetData[count - 2] = dataByte;
                    count++;
                    if (count == length) {
                        uartPacket.setData(packetData);
                        dataState = DataState.CHECK_SUM;
                    }
                    break;

                case CHECK_SUM:
                    if (checksum == dataByte) {
                        uartPacket.setCheckSum(dataByte);
                        packet = uartPacket;
                    } else {
                        LogUtil.e(TAG, "rec data checksum error!");
                    }
                    break;
            }
        }

        return packet;
    }


}
