package model.musta.it.apiit.com.model;

import java.io.Serializable;

/**
 * Created by musta on 05-Jan-18.
 */

public class Device implements Serializable {
    private String ipAddress;
    private String name;
    private String macAddress;

    public Device(String ipAddress, String name, String macAddress) {
        this.ipAddress = ipAddress;
        this.name = name;
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

}
