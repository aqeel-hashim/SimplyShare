package data.musta.it.apiit.com.entity;


import java.io.Serializable;

/**
 * Created by musta on 05-Jan-18.
 */

public class DeviceEntity implements Serializable {
    private String ipAddress;
    private WifiP2pDevice device;

    public DeviceEntity(String ipAddress, WifiP2pDevice device) {
        this.ipAddress = ipAddress;
        this.device = device;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public WifiP2pDevice getDevice() {
        return device;
    }

    public void setDevice(WifiP2pDevice device) {
        this.device = device;
    }
}
