package com.cai.zq.lesu.uart.packet;

/**
 * TODO<>
 * Created by digiengine
 * DATE: 16/5/19 下午3:49
 */
public class UartPacket {

    private byte cmd;
    private byte cmdLength;
    private byte[] data;
    private byte checkSum;

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public byte getCmdLength() {
        return cmdLength;
    }

    public void setCmdLength(byte cmdLength) {
        this.cmdLength = cmdLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(byte checkSum) {
        this.checkSum = checkSum;
    }
}
