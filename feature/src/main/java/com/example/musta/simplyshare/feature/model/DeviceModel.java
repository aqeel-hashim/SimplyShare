package com.example.musta.simplyshare.feature.model;

/**
 * Created by musta on 11-Jan-18.
 */

public class DeviceModel {
    private String ip;
    private String macAddress;
    private String name;
    private int image;

    public DeviceModel(String ip, String macAddress, String name, int image) {
        this.ip = ip;
        this.macAddress = macAddress;
        this.name = name;
        this.image = image;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
