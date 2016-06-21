package com.cai.zq.lesu.bean;

/**
 * TODO<car status>
 * Created by digiengine
 * DATE: 16/6/21 下午4:35
 */
public class CartStatus {
    private byte preMotorDirection;
    private byte preMotorSpeed;
    private byte rearMotorDirection;
    private byte rearMotorSpeed;
    private byte cartLedLight;
    private byte cartSkill1;
    private byte cartSkill2;
    private byte cartSkill3;
    private byte cartSkill4;

    public byte getRearMotorDirection() {
        return rearMotorDirection;
    }

    public void setRearMotorDirection(byte rearMotorDirection) {
        this.rearMotorDirection = rearMotorDirection;
    }

    public byte getPreMotorDirection() {
        return preMotorDirection;
    }

    public void setPreMotorDirection(byte preMotorDirection) {
        this.preMotorDirection = preMotorDirection;
    }

    public byte getPreMotorSpeed() {
        return preMotorSpeed;
    }

    public void setPreMotorSpeed(byte preMotorSpeed) {
        this.preMotorSpeed = preMotorSpeed;
    }

    public byte getRearMotorSpeed() {
        return rearMotorSpeed;
    }

    public void setRearMotorSpeed(byte rearMotorSpeed) {
        this.rearMotorSpeed = rearMotorSpeed;
    }

    public byte getCartLedLight() {
        return cartLedLight;
    }

    public void setCartLedLight(byte cartLedLight) {
        this.cartLedLight = cartLedLight;
    }

    public byte getCartSkill1() {
        return cartSkill1;
    }

    public void setCartSkill1(byte cartSkill1) {
        this.cartSkill1 = cartSkill1;
    }

    public byte getCartSkill2() {
        return cartSkill2;
    }

    public void setCartSkill2(byte cartSkill2) {
        this.cartSkill2 = cartSkill2;
    }

    public byte getCartSkill3() {
        return cartSkill3;
    }

    public void setCartSkill3(byte cartSkill3) {
        this.cartSkill3 = cartSkill3;
    }

    public byte getCartSkill4() {
        return cartSkill4;
    }

    public void setCartskill4(byte cartSkill4) {
        this.cartSkill4 = cartSkill4;
    }
}
